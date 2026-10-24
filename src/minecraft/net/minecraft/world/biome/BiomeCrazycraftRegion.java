package net.minecraft.world.biome;

import net.minecraft.block.state.IBlockState;
import net.minecraft.crazycraft.CrazycraftContentBlocks;
import net.minecraft.crazycraft.EntityCrazycraftWorkerBot;
import net.minecraft.crazycraft.old.EntityTRex;
import net.minecraft.crazycraft.old.EntityTriceratops;
import net.minecraft.crazycraft.old.EntityStegosaurus;
import net.minecraft.crazycraft.old.EntityVelociraptor;
import net.minecraft.crazycraft.old.EntityCrazycraftSpecies;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

/** Distinct terrain, climate, color and creature rules for named Crazycraft regions. */
public class BiomeCrazycraftRegion extends Biome
{
    private static final String[] OLD_NAMES = {
        "Volcanic Lands", "Prehistoric Savanna", "Fossil Desert", "Ancient Swamp",
        "Redwood Forest", "Prehistoric Coast", "Ancient Canyon", "Tar Pit",
        "Ancient Plains"
    };
    private static final String[] FUTURE_NAMES = {
        "Floating Islands", "Crystal Desert", "Cyber Mountains", "Space Station",
        "Neon Forest", "Robot Factory", "Asteroid Field"
    };

    private final int foliage;
    private final int grass;
    private final int sky;
    private final boolean future;

    public BiomeCrazycraftRegion(boolean future, int variant)
    {
        super(properties(future, variant));
        this.future = future;
        this.topBlock = surface(future, variant);
        this.fillerBlock = future ? CrazycraftContentBlocks.FUTURE[10].getDefaultState()
            : variant == 2 || variant == 5 ? Blocks.SAND.getDefaultState()
            : CrazycraftContentBlocks.OLD[4].getDefaultState();
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
        if (future)
        {
            this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityCrazycraftWorkerBot.class, 8, 1, 2));
            this.decorator.treesPerChunk = 0;
            this.decorator.grassPerChunk = 0;
        }
        else
        {
            if (variant == 1 || variant == 4 || variant == 8)
            {
                this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityTriceratops.class, 5, 1, 1));
                this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityStegosaurus.class, 4, 1, 1));
                this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityCrazycraftSpecies.Brachiosaurus.class, 1, 1, 1));
                this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityCrazycraftSpecies.Parasaurolophus.class, 2, 1, 1));
                this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityCrazycraftSpecies.Iguanodon.class, 2, 1, 1));
                this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityCrazycraftSpecies.Ankylosaurus.class, 2, 1, 1));
            }
            if (variant == 1 || variant == 3 || variant == 4 || variant == 8)
                this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityVelociraptor.class, 2, 1, 1));
            if (variant == 1 || variant == 4 || variant == 8)
                this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityTRex.class, 1, 1, 1));
            if (variant == 3 || variant == 5)
            {
                this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityCrazycraftSpecies.Spinosaurus.class, 1, 1, 1));
                this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityCrazycraftSpecies.Dilophosaurus.class, 2, 1, 1));
            }
            if (variant == 1 || variant == 6 || variant == 8)
            {
                this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityCrazycraftSpecies.Carnotaurus.class, 1, 1, 1));
                this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityCrazycraftSpecies.Allosaurus.class, 1, 1, 1));
            }
            if (variant == 1 || variant == 4 || variant == 8)
                this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityCrazycraftSpecies.Mapusaurus.class, 1, 1, 1));
            if (variant == 1 || variant == 6)
                this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityCrazycraftSpecies.Giganotosaurus.class, 1, 1, 1));
            if (variant == 1 || variant == 3 || variant == 4 || variant == 8)
            {
                this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityCrazycraftSpecies.Compsognathus.class, 3, 1, 2));
                this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityCrazycraftSpecies.Gallimimus.class, 2, 1, 2));
                this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityCrazycraftSpecies.Oviraptor.class, 1, 1, 1));
            }
            this.decorator.treesPerChunk = variant == 4 ? 2 : variant == 3 ? 1 : 0;
            this.decorator.grassPerChunk = variant == 1 || variant == 4 || variant == 8 ? 8 : 2;
        }
        this.decorator.flowersPerChunk = 0;
        this.foliage = future ? 0x2E9DB9 : variant == 3 ? 0x397A59 : 0x559676;
        this.grass = future ? 0x598AB9 : variant == 2 || variant == 6 ? 0xB7A56B : 0x8AA55A;
        this.sky = future ? 0x6675C4 : variant == 0 ? 0xD77B61 : 0x84ACD0;
    }

    private static Biome.BiomeProperties properties(boolean future, int variant)
    {
        String name = future ? FUTURE_NAMES[variant] : OLD_NAMES[variant];
        float base = future ? (variant == 2 || variant == 6 ? 1.3F : 0.25F)
            : variant == 0 || variant == 6 ? 1.0F : variant == 3 || variant == 5 || variant == 7 ? -0.1F : 0.22F;
        float variation = future ? (variant == 2 || variant == 6 ? 0.75F : 0.18F)
            : variant == 0 || variant == 6 ? 0.65F : 0.17F;
        Biome.BiomeProperties props = new Biome.BiomeProperties(name)
            .setBaseHeight(base).setHeightVariation(variation)
            .setTemperature(future ? 0.8F : 1.0F).setRainfall(future ? 0.0F : 0.65F);
        if (future) props.setRainDisabled();
        return props;
    }

    private static IBlockState surface(boolean future, int variant)
    {
        if (future)
        {
            int[] material = {11, 9, 10, 0, 1, 8, 5};
            return CrazycraftContentBlocks.FUTURE[material[variant]].getDefaultState();
        }
        switch (variant)
        {
            case 0: return CrazycraftContentBlocks.OLD[3].getDefaultState();
            case 2: return CrazycraftContentBlocks.OLD[7].getDefaultState();
            case 3: return CrazycraftContentBlocks.OLD[12].getDefaultState();
            case 5: return CrazycraftContentBlocks.OLD[7].getDefaultState();
            case 6: return CrazycraftContentBlocks.OLD[0].getDefaultState();
            case 7: return CrazycraftContentBlocks.OLD[6].getDefaultState();
            default: return Blocks.GRASS.getDefaultState();
        }
    }

    @Override
    public int getGrassColorAtPos(BlockPos pos) { return this.grass; }

    @Override
    public int getFoliageColorAtPos(BlockPos pos) { return this.foliage; }

    @Override
    public int getSkyColorByTemp(float temperature) { return this.sky; }

    @Override
    public float getSpawningChance() { return this.future ? super.getSpawningChance() : 0.025F; }
}
