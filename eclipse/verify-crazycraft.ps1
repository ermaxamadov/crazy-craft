param([switch]$Install)
$ErrorActionPreference = 'Stop'
$project = (Resolve-Path '..').Path
$output = Join-Path (Get-Location).Path '.crazycraft-compile'
New-Item -ItemType Directory -Force -Path $output | Out-Null
try {
    $jars = Get-ChildItem -LiteralPath (Join-Path $project 'jars\libraries') -Recurse -Filter '*.jar' -File | ForEach-Object FullName
    $classpath = (@((Join-Path (Get-Location).Path 'Client\bin'), (Join-Path $project 'jars\versions\1.12\1.12.jar')) + $jars) -join ';'
    $src = Join-Path $project 'src\minecraft\net\minecraft'
    $files = @(
        'client\Minecraft.java', 'client\gui\GuiMainMenu.java', 'client\gui\GuiCrazycraftButton.java',
        'client\gui\GuiOverlayDebug.java', 'client\gui\GuiVideoSettings.java',
        'client\gui\inventory\GuiContainerCreative.java',
        'client\model\ModelCrazycraftDinosaur.java', 'client\renderer\entity\RenderCrazycraftDinosaur.java',
        'client\model\ModelCrazycraftStegosaurus.java', 'client\model\ModelCrazycraftVelociraptor.java',
        'client\model\ModelCrazycraftSpecies.java', 'client\renderer\entity\RenderCrazycraftSpecies.java',
        'client\model\ModelCrazycraftPteranodon.java', 'client\renderer\entity\RenderCrazycraftPteranodon.java',
        'client\renderer\entity\RenderCrazycraftWorkerBot.java',
        'client\renderer\entity\RenderManager.java', 'client\renderer\RenderItem.java',
        'client\resources\LanguageManager.java',
        'creativetab\CreativeTabs.java', 'block\Block.java', 'item\Item.java', 'item\ItemMonsterPlacer.java',
        'crazycraft\CrazycraftContentBlocks.java', 'crazycraft\CrazycraftDecorativeBlock.java',
        'crazycraft\BlockCrazycraftFuturePortal.java',
        'crazycraft\EntityCrazycraftWorkerBot.java',
        'entity\EntityList.java', 'entity\player\EntityPlayerMP.java',
        'crazycraft\old\CrazycraftOldBlocks.java', 'crazycraft\old\BlockCrazycraftOldFrame.java',
        'crazycraft\old\BlockCrazycraftOldPortal.java', 'crazycraft\old\OldArrivalScene.java', 'crazycraft\old\EntityTRex.java',
        'crazycraft\old\EntityTriceratops.java', 'crazycraft\old\EntityPteranodon.java',
        'crazycraft\old\EntityStegosaurus.java', 'crazycraft\old\EntityVelociraptor.java',
        'crazycraft\old\EntityCrazycraftSpecies.java',
        'world\DimensionType.java', 'world\WorldProviderCrazycraftOld.java',
        'world\WorldProviderCrazycraftFuture.java', 'world\biome\Biome.java',
        'world\biome\BiomeCrazycraftOld.java', 'world\biome\BiomeCrazycraftFuture.java',
        'world\biome\BiomeCrazycraftRegion.java', 'world\biome\BiomeProviderCrazycraft.java',
        'world\gen\ChunkGeneratorCrazycraft.java',
        'server\MinecraftServer.java', 'server\integrated\IntegratedServer.java',
        'server\management\PlayerList.java', 'world\chunk\storage\AnvilSaveHandler.java', 'command\CommandCrazycraft.java',
        'command\ServerCommandManager.java'
    ) | ForEach-Object { Join-Path $src $_ }
    & javac -encoding UTF-8 -cp $classpath -sourcepath $output -d $output $files
    if ($LASTEXITCODE -ne 0) { throw "javac failed: $LASTEXITCODE" }
    @'
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeCrazycraftOld;
import net.minecraft.world.biome.BiomeCrazycraftFuture;
import net.minecraft.world.biome.BiomeProviderCrazycraft;
import net.minecraft.init.Bootstrap;
import net.minecraft.block.Block;
import net.minecraft.crazycraft.old.CrazycraftOldBlocks;
import net.minecraft.crazycraft.CrazycraftContentBlocks;
import net.minecraft.crazycraft.old.EntityTRex;
import net.minecraft.crazycraft.old.EntityTriceratops;
import net.minecraft.crazycraft.old.EntityPteranodon;
import net.minecraft.crazycraft.old.EntityStegosaurus;
import net.minecraft.crazycraft.old.EntityVelociraptor;
import net.minecraft.crazycraft.old.EntityCrazycraftSpecies;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.MetadataSerializer;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
public class VerifyCrazycraft {
    public static void main(String[] args) throws Exception {
        Bootstrap.register();
        if (!(Biome.getBiome(200) instanceof BiomeCrazycraftOld)) throw new AssertionError("Prehistoric biome missing");
        if (!(Biome.getBiome(210) instanceof BiomeCrazycraftFuture)) throw new AssertionError("Future biome missing");
        for (int id = 201; id <= 209; ++id) if (!(Biome.getBiome(id) instanceof net.minecraft.world.biome.BiomeCrazycraftRegion)) throw new AssertionError("Old biome " + id);
        for (int id = 211; id <= 217; ++id) if (!(Biome.getBiome(id) instanceof net.minecraft.world.biome.BiomeCrazycraftRegion)) throw new AssertionError("Future biome " + id);
        BiomeProviderCrazycraft oldProvider = new BiomeProviderCrazycraft(12345L, false);
        BiomeProviderCrazycraft futureProvider = new BiomeProviderCrazycraft(12345L, true);
        HashSet<Biome> oldSeen = new HashSet<Biome>();
        HashSet<Biome> futureSeen = new HashSet<Biome>();
        for (int x = -4096; x <= 4096; x += 128) for (int z = -4096; z <= 4096; z += 128) {
            oldSeen.add(oldProvider.getBiome(new net.minecraft.util.math.BlockPos(x, 64, z)));
            futureSeen.add(futureProvider.getBiome(new net.minecraft.util.math.BlockPos(x, 64, z)));
        }
        if (oldSeen.size() != 10 || futureSeen.size() != 8) throw new AssertionError("Not all Crazycraft biome regions are reachable");
        if (DimensionType.getById(2).createDimension().getDimensionType() != DimensionType.CRAZYCRAFT_OLD) throw new AssertionError("Old dimension");
        if (DimensionType.getById(3).createDimension().getDimensionType() != DimensionType.CRAZYCRAFT_FUTURE) throw new AssertionError("Future dimension");
        if (Block.getIdFromBlock(CrazycraftOldBlocks.FRAME) != 3000 || Block.getIdFromBlock(CrazycraftOldBlocks.PORTAL) != 3001) throw new AssertionError("Portal blocks not registered");
        for (int n = 0; n < CrazycraftContentBlocks.OLD.length; ++n) {
            if (Block.getIdFromBlock(CrazycraftContentBlocks.OLD[n]) != 3100 + n || Item.getItemFromBlock(CrazycraftContentBlocks.OLD[n]) == null) throw new AssertionError("Old content block " + n);
        }
        for (int n = 0; n < CrazycraftContentBlocks.FUTURE.length; ++n) {
            if (Block.getIdFromBlock(CrazycraftContentBlocks.FUTURE[n]) != 3200 + n || Item.getItemFromBlock(CrazycraftContentBlocks.FUTURE[n]) == null) throw new AssertionError("Future content block " + n);
        }
        if (Block.getIdFromBlock(CrazycraftContentBlocks.FUTURE_PORTAL) != 3300) throw new AssertionError("Future portal missing");
        if (Item.getItemFromBlock(CrazycraftOldBlocks.FRAME) == null || Item.getItemFromBlock(CrazycraftOldBlocks.PORTAL) == null) throw new AssertionError("Portal items not registered");
        if (EntityList.getClassFromID(201) != EntityTRex.class || EntityList.getClassFromID(202) != EntityTriceratops.class) throw new AssertionError("Dinosaur entities not registered");
        if (EntityList.getClassFromID(203) != EntityPteranodon.class) throw new AssertionError("Flying dinosaur not registered");
        if (EntityList.getClassFromID(205) != EntityStegosaurus.class || EntityList.getClassFromID(206) != EntityVelociraptor.class) throw new AssertionError("New dinosaurs not registered");
        Class<?>[] moreDinosaurs = {EntityCrazycraftSpecies.Spinosaurus.class, EntityCrazycraftSpecies.Carnotaurus.class,
            EntityCrazycraftSpecies.Allosaurus.class, EntityCrazycraftSpecies.Dilophosaurus.class,
            EntityCrazycraftSpecies.Brachiosaurus.class, EntityCrazycraftSpecies.Ankylosaurus.class,
            EntityCrazycraftSpecies.Parasaurolophus.class, EntityCrazycraftSpecies.Iguanodon.class};
        for (int i = 0; i < moreDinosaurs.length; ++i) if (EntityList.getClassFromID(207 + i) != moreDinosaurs[i]) throw new AssertionError("Dinosaur registration " + i);
        if (EntityList.getClassFromID(204) != net.minecraft.crazycraft.EntityCrazycraftWorkerBot.class) throw new AssertionError("Worker bot not registered");
        NonNullList<ItemStack> oldItems = NonNullList.create();
        CreativeTabs.CRAZYCRAFT_OLD.displayAllRelevantItems(oldItems);
        int eggCount = 0;
        boolean trexEgg = false;
        boolean triceratopsEgg = false;
        boolean pteranodonEgg = false;
        boolean stegosaurusEgg = false;
        boolean velociraptorEgg = false;
        for (ItemStack stack : oldItems) {
            if (stack.getItem() instanceof ItemMonsterPlacer) {
                ++eggCount;
                ResourceLocation id = ItemMonsterPlacer.getNamedIdFrom(stack);
                trexEgg |= new ResourceLocation("crazycraft_trex").equals(id);
                triceratopsEgg |= new ResourceLocation("crazycraft_triceratops").equals(id);
                pteranodonEgg |= new ResourceLocation("crazycraft_pteranodon").equals(id);
                stegosaurusEgg |= new ResourceLocation("crazycraft_stegosaurus").equals(id);
                velociraptorEgg |= new ResourceLocation("crazycraft_velociraptor").equals(id);
            }
        }
        if (oldItems.size() != 15 + CrazycraftContentBlocks.OLD.length || eggCount != 13) throw new AssertionError("Old Dimension creative tab content");
        NonNullList<ItemStack> futureItems = NonNullList.create();
        CreativeTabs.CRAZYCRAFT_FUTURE.displayAllRelevantItems(futureItems);
        if (futureItems.size() != CrazycraftContentBlocks.FUTURE.length + 2) throw new AssertionError("Future Dimension creative tab content");
        if (!trexEgg || !triceratopsEgg || !pteranodonEgg || !stegosaurusEgg || !velociraptorEgg) throw new AssertionError("Dinosaur spawn eggs have wrong IDs");
        MetadataSerializer serializer = new MetadataSerializer();
        LanguageManager languages = new LanguageManager(serializer, "uz_uz");
        languages.parseLanguageMetadata(Collections.<IResourcePack>emptyList());
        if (languages.getLanguage("uz_uz") == null) throw new AssertionError("Uzbek missing from language menu");
        LanguageManager english = new LanguageManager(serializer, "en_us");
        english.parseLanguageMetadata(Collections.<IResourcePack>emptyList());
        if (english.getCurrentLanguage() == null || !"en_us".equals(english.getCurrentLanguage().getLanguageCode())) throw new AssertionError("Default English language missing");
        try (InputStream in = VerifyCrazycraft.class.getResourceAsStream("/assets/minecraft/lang/uz_uz.lang")) {
            if (in == null) throw new AssertionError("Uzbek locale resource missing");
            net.minecraft.client.resources.Locale locale = new net.minecraft.client.resources.Locale();
            Method load = net.minecraft.client.resources.Locale.class.getDeclaredMethod("loadLocaleData", InputStream.class);
            load.setAccessible(true);
            load.invoke(locale, in);
            if (!"Bekor qilish".equals(locale.formatMessage("gui.cancel", new Object[0]))) throw new AssertionError("Uzbek menu translation not loaded");
        }
        System.out.println("Both Crazycraft dimension providers registered.");
        System.out.println("Portal blocks, dinosaurs and dedicated creative tab registered.");
        System.out.println("Uzbek language listed and translated menu resource loaded.");
    }
}
'@ | Set-Content -LiteralPath (Join-Path $output 'VerifyCrazycraft.java') -Encoding Ascii
    & javac -cp "$output;$classpath" -d $output (Join-Path $output 'VerifyCrazycraft.java')
    if ($LASTEXITCODE -ne 0) { throw "verification compile failed: $LASTEXITCODE" }
    & java -cp "$output;$classpath" VerifyCrazycraft
    if ($LASTEXITCODE -ne 0) { throw "verification runtime failed: $LASTEXITCODE" }
    $assetRoot = Join-Path $project 'src\minecraft\assets\minecraft'
    $portalState = Get-Content -LiteralPath (Join-Path $assetRoot 'blockstates\crazycraft_old_portal.json') -Raw | ConvertFrom-Json
    if ($null -eq $portalState.variants.'axis=x' -or $null -eq $portalState.variants.'axis=z') { throw 'Portal axis models missing' }
    foreach ($name in @('crazycraft_old_frame', 'crazycraft_old_portal')) {
        $itemModel = Get-Content -LiteralPath (Join-Path $assetRoot "models\item\$name.json") -Raw | ConvertFrom-Json
        if ($itemModel.parent -ne "block/$name") { throw "Item model missing: $name" }
    }
    foreach ($name in @('crazycraft_future_portal') +
        @('ancient_stone','fossil_block','amber_block','volcanic_rock','jungle_dirt','mossy_stone','tar_pit_block','prehistoric_sand','redwood_log','palm_log','crystal_stone','sulfur_block','mud_block','meteorite_ore','fossil_ore','prehistoric_grass','metal_block','neon_block','energy_core','glass_panel','circuit_block','carbon_block','hologram_block','force_field','machine_block','white_metal','dark_metal','blue_alloy','gravity_block','teleporter_block','solar_panel','battery' | ForEach-Object { "crazycraft_$_" })) {
        foreach ($relative in @("blockstates\$name.json", "models\block\$name.json",
            "models\item\$name.json", "textures\blocks\$name.png")) {
            if (-not (Test-Path -LiteralPath (Join-Path $assetRoot $relative))) { throw "Asset missing: $relative" }
        }
    }
    foreach ($name in @('stegosaurus', 'velociraptor', 'spinosaurus', 'carnotaurus',
        'allosaurus', 'dilophosaurus', 'brachiosaurus', 'ankylosaurus', 'parasaurolophus', 'iguanodon')) {
        if (-not (Test-Path -LiteralPath (Join-Path $assetRoot "textures\entity\crazycraft\$name.png"))) {
            throw "Dinosaur texture missing: $name"
        }
    }
    if ($Install) {
        $destinationRoot = Join-Path (Get-Location).Path 'Client\bin'
        Get-ChildItem -LiteralPath $output -Recurse -Filter '*.class' -File | Where-Object { $_.Name -ne 'VerifyCrazycraft.class' } | ForEach-Object {
            $relative = $_.FullName.Substring($output.Length + 1)
            $destination = Join-Path $destinationRoot $relative
            New-Item -ItemType Directory -Force -Path (Split-Path $destination -Parent) | Out-Null
            Copy-Item -LiteralPath $_.FullName -Destination $destination -Force
        }
        $assets = @(
            'lang\en_us.lang', 'lang\uz_uz.lang',
            'blockstates\crazycraft_old_frame.json', 'blockstates\crazycraft_old_portal.json',
            'models\block\crazycraft_old_frame.json', 'models\block\crazycraft_old_portal.json',
            'models\item\crazycraft_old_frame.json', 'models\item\crazycraft_old_portal.json',
            'textures\blocks\crazycraft_old_frame.png', 'textures\blocks\crazycraft_old_portal.png',
            'textures\entity\crazycraft\trex.png', 'textures\entity\crazycraft\triceratops.png',
            'textures\entity\crazycraft\pteranodon.png',
            'textures\entity\crazycraft\stegosaurus.png',
            'textures\entity\crazycraft\velociraptor.png',
            'textures\entity\crazycraft\worker_bot.png'
        )
        $assets += Get-ChildItem -LiteralPath $assetRoot -Recurse -File |
            Where-Object { $_.Name -like 'crazycraft_*' } |
            ForEach-Object { $_.FullName.Substring($assetRoot.Length + 1) }
        $assets += Get-ChildItem -LiteralPath (Join-Path $assetRoot 'textures\entity\crazycraft') -File |
            ForEach-Object { $_.FullName.Substring($assetRoot.Length + 1) }
        foreach ($relative in $assets) {
            $destination = Join-Path $destinationRoot (Join-Path 'assets\minecraft' $relative)
            New-Item -ItemType Directory -Force -Path (Split-Path $destination -Parent) | Out-Null
            Copy-Item -LiteralPath (Join-Path $assetRoot $relative) -Destination $destination -Force
        }
        Write-Output 'Compiled classes installed in Client/bin.'
    }
    Write-Output 'Crazycraft sources compiled successfully.'
}
finally { Remove-Item -LiteralPath $output -Recurse -Force }
