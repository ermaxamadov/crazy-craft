package net.minecraft.world.gen;

import java.util.Random;
import java.util.List;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import net.minecraft.crazycraft.old.EntityPteranodon;
import net.minecraft.crazycraft.old.EntityQuetzalcoatlus;
import net.minecraft.crazycraft.old.EntityRhamphorhynchus;
import net.minecraft.crazycraft.CrazycraftContentBlocks;
import net.minecraft.crazycraft.EntityCrazycraftWorkerBot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

/** Adds themed landmarks to ordinary terrain in the two Crazycraft worlds. */
public class ChunkGeneratorCrazycraft extends ChunkGeneratorOverworld
{
    private final World world;
    private final boolean future;
    private final Random decorationRandom;

    public ChunkGeneratorCrazycraft(World world, long seed, boolean future)
    {
        super(world, seed, false, world.getWorldInfo().getGeneratorOptions());
        this.world = world;
        this.future = future;
        this.decorationRandom = new Random(seed ^ (future ? 0x4352414654L : 0x4F4C44574FL));
    }

    @Override
    public Chunk generateChunk(int chunkX, int chunkZ)
    {
        Chunk chunk = super.generateChunk(chunkX, chunkZ);

        if (this.future)
        {
            IBlockState pavement = CrazycraftContentBlocks.FUTURE[0].getDefaultState();

            for (int x = 0; x < 16; ++x)
            {
                for (int z = 0; z < 16; ++z)
                {
                    int topY = chunk.getHeightValue(x, z) - 1;
                    BlockPos pos = new BlockPos(chunkX * 16 + x, topY, chunkZ * 16 + z);
                    IBlockState surface = chunk.getBlockState(pos);

                    if (surface.getBlock() == Blocks.GRASS || surface.getBlock() == Blocks.DIRT
                        || surface.getBlock() == CrazycraftContentBlocks.FUTURE[0])
                    {
                        int roadX = Math.floorMod(pos.getX(), 64);
                        int roadZ = Math.floorMod(pos.getZ(), 64);
                        boolean road = roadX < 5 || roadX > 59 || roadZ < 5 || roadZ > 59;
                        boolean edgeLight = roadX == 5 || roadX == 59 || roadZ == 5 || roadZ == 59;
                        chunk.setBlockState(pos, edgeLight ? CrazycraftContentBlocks.FUTURE[1].getDefaultState()
                            : road ? CrazycraftContentBlocks.FUTURE[10].getDefaultState() : pavement);
                    }
                }
            }
        }
        else
        {
            this.generateVolcanoTerrain(chunk, chunkX, chunkZ);
        }

        return chunk;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType type, BlockPos pos)
    {
        return super.getPossibleCreatures(type, pos);
    }

    @Override
    public void populate(int chunkX, int chunkZ)
    {
        super.populate(chunkX, chunkZ);

        if (this.future)
        {
            if (Math.floorMod(chunkX, 3) == 0 && Math.floorMod(chunkZ, 3) == 0)
            {
                this.generateTower(chunkX, chunkZ);
            }
            else if (Math.floorMod(chunkX, 3) == 1 && Math.floorMod(chunkZ, 3) == 0)
            {
                this.generateHoverCar(chunkX, chunkZ);
            }
            this.spawnWorkerBot(chunkX, chunkZ);
            if (Math.floorMod(chunkX, 6) == 2 && Math.floorMod(chunkZ, 6) == 2)
                this.generateRobotFactory(chunkX, chunkZ);
            int futureBiome = Biome.getIdForBiome(this.world.getBiome(new BlockPos(chunkX * 16 + 8, 70, chunkZ * 16 + 8)));
            if (futureBiome == 211 && Math.floorMod(chunkX, 5) == 0 && Math.floorMod(chunkZ, 5) == 0)
                this.generateFloatingIsland(chunkX, chunkZ);
            if (futureBiome == 214 && Math.floorMod(chunkX, 6) == 1 && Math.floorMod(chunkZ, 6) == 1)
                this.generateSpaceStation(chunkX, chunkZ);
            if (futureBiome == 217 && Math.floorMod(chunkX, 4) == 0 && Math.floorMod(chunkZ, 4) == 0)
                this.generateAsteroid(chunkX, chunkZ);
            if (futureBiome == 210 && Math.floorMod(chunkX, 7) == 3 && Math.floorMod(chunkZ, 7) == 3)
                this.generateResearchLab(chunkX, chunkZ);
            if (futureBiome == 213 && Math.floorMod(chunkX, 6) == 2 && Math.floorMod(chunkZ, 6) == 2)
                this.generateEnergyTower(chunkX, chunkZ);
            this.decorateFutureRegion(chunkX, chunkZ, futureBiome);
        }
        else
        {
            this.decoratePrehistoric(chunkX, chunkZ);
            if (Math.floorMod(chunkX, 5) == 0 && Math.floorMod(chunkZ, 5) == 0)
                this.generateFossil(chunkX, chunkZ);
            int oldBiome = Biome.getIdForBiome(this.world.getBiome(new BlockPos(chunkX * 16 + 8, 70, chunkZ * 16 + 8)));
            if (oldBiome == 208 && Math.floorMod(chunkX, 4) == 0 && Math.floorMod(chunkZ, 4) == 0)
                this.generateTarPit(chunkX, chunkZ);
            if (oldBiome == 205 && Math.floorMod(chunkX, 6) == 0 && Math.floorMod(chunkZ, 6) == 0)
                this.generateAncientTree(chunkX, chunkZ);
            if (oldBiome == 200 && Math.floorMod(chunkX, 8) == 4 && Math.floorMod(chunkZ, 8) == 4)
                this.generateAncientTemple(chunkX, chunkZ);
            if (oldBiome == 209 && Math.floorMod(chunkX, 5) == 2 && Math.floorMod(chunkZ, 5) == 2)
                this.generateDinosaurNest(chunkX, chunkZ);
        }

        if (!this.future)
        {
            this.spawnPteranodon(chunkX, chunkZ);
        }
    }

    private void spawnWorkerBot(int chunkX, int chunkZ)
    {
        Random botRandom = new Random(this.world.getSeed() ^ ((long)chunkX * 1199540897363517989L)
            ^ ((long)chunkZ * 1015072439789178187L));
        if (botRandom.nextInt(7) != 0) return;
        int x = chunkX * 16 + 3 + botRandom.nextInt(10);
        int z = chunkZ * 16 + 3 + botRandom.nextInt(10);
        BlockPos ground = this.world.getHeight(new BlockPos(x, 0, z));
        if (ground.getY() < 64 || !this.world.isAirBlock(ground.up())) return;
        EntityCrazycraftWorkerBot bot = new EntityCrazycraftWorkerBot(this.world);
        bot.setLocationAndAngles(x + 0.5D, ground.getY(), z + 0.5D, botRandom.nextFloat() * 360.0F, 0.0F);
        this.world.spawnEntity(bot);
    }

    private void generateRobotFactory(int chunkX, int chunkZ)
    {
        int x = chunkX * 16 + 8;
        int z = chunkZ * 16 + 8;
        int y = this.world.getHeight(new BlockPos(x, 0, z)).getY();
        if (y < 64 || y > 220) return;
        IBlockState metal = CrazycraftContentBlocks.FUTURE[8].getDefaultState();
        IBlockState window = CrazycraftContentBlocks.FUTURE[3].getDefaultState();
        for (int dx = -5; dx <= 5; ++dx)
            for (int dz = -5; dz <= 5; ++dz)
            {
                this.world.setBlockState(new BlockPos(x + dx, y, z + dz), metal, 2);
                for (int dy = 1; dy <= 8; ++dy)
                    if (dy == 8 || Math.abs(dx) == 5 || Math.abs(dz) == 5)
                        this.world.setBlockState(new BlockPos(x + dx, y + dy, z + dz),
                            dy > 2 && dy < 7 && (Math.abs(dx) == 5 || Math.abs(dz) == 5)
                                ? window : metal, 2);
            }
        for (int dz = -1; dz <= 1; ++dz)
            for (int dy = 1; dy <= 3; ++dy)
                this.world.setBlockState(new BlockPos(x, y + dy, z - 5 + dz), Blocks.AIR.getDefaultState(), 2);
        this.world.setBlockState(new BlockPos(x, y + 9, z), CrazycraftContentBlocks.FUTURE[2].getDefaultState(), 2);
        for (int dx = -4; dx <= 4; dx += 4)
            for (int dz = -4; dz <= 4; dz += 4)
                this.world.setBlockState(new BlockPos(x + dx, y + 9, z + dz),
                    CrazycraftContentBlocks.FUTURE[14].getDefaultState(), 2);
    }

    private void generateVolcanoTerrain(Chunk chunk, int chunkX, int chunkZ)
    {
        int minGridX = Math.floorDiv(chunkX * 16 - 56, 384);
        int maxGridX = Math.floorDiv(chunkX * 16 + 71, 384);
        int minGridZ = Math.floorDiv(chunkZ * 16 - 56, 384);
        int maxGridZ = Math.floorDiv(chunkZ * 16 + 71, 384);
        for (int gridX = minGridX; gridX <= maxGridX; ++gridX)
            for (int gridZ = minGridZ; gridZ <= maxGridZ; ++gridZ)
            {
                Random volcanoRandom = new Random(this.world.getSeed() ^ ((long)gridX * 73428767L)
                    ^ ((long)gridZ * 912931L) ^ 0x564F4C43414E4FL);
                if (volcanoRandom.nextInt(3) == 0) continue;
                int centerX = gridX * 384 + 192 + volcanoRandom.nextInt(81) - 40;
                int centerZ = gridZ * 384 + 192 + volcanoRandom.nextInt(81) - 40;
                int summit = 145 + volcanoRandom.nextInt(26);
                for (int x = 0; x < 16; ++x)
                    for (int z = 0; z < 16; ++z)
                    {
                        int globalX = chunkX * 16 + x;
                        int globalZ = chunkZ * 16 + z;
                        double distance = Math.sqrt((double)(globalX - centerX) * (globalX - centerX)
                            + (double)(globalZ - centerZ) * (globalZ - centerZ));
                        if (distance > 54.0D) continue;
                        int top = chunk.getHeightValue(x, z);
                        int target = distance < 6.0D ? summit - 12 : summit - (int)(distance * 1.65D);
                        if (target <= top) continue;
                        for (int y = top; y <= target; ++y)
                        {
                            IBlockState rock = y == target && distance < 14.0D
                                ? Blocks.OBSIDIAN.getDefaultState() : CrazycraftContentBlocks.OLD[0].getDefaultState();
                            if (y == target && distance >= 9.0D && distance < 30.0D
                                && ((globalX * 13 + globalZ * 7) & 7) == 0)
                                rock = CrazycraftContentBlocks.OLD[3].getDefaultState();
                            chunk.setBlockState(new BlockPos(globalX, y, globalZ), rock);
                        }
                        if (distance < 5.0D && target + 1 < summit - 9)
                            for (int y = target + 1; y <= summit - 9; ++y)
                                chunk.setBlockState(new BlockPos(globalX, y, globalZ), Blocks.MAGMA.getDefaultState());
                    }
            }
    }

    private void decoratePrehistoric(int chunkX, int chunkZ)
    {
        this.decorationRandom.setSeed(this.world.getSeed() ^ ((long)chunkX * 341873128712L)
            ^ ((long)chunkZ * 132897987541L) ^ 0x4A555241L);

        // Open fern grassland with scattered flat-canopied prehistoric trees.
        for (int i = 0; i < 14; ++i)
        {
            int x = chunkX * 16 + this.decorationRandom.nextInt(16);
            int z = chunkZ * 16 + this.decorationRandom.nextInt(16);
            BlockPos ground = this.world.getHeight(new BlockPos(x, 0, z)).down();
            if (ground.getY() > 62 && ground.getY() < 220 && this.world.getBlockState(ground).getBlock() == Blocks.GRASS
                && this.world.isAirBlock(ground.up()))
                this.world.setBlockState(ground.up(), Blocks.TALLGRASS.getDefaultState()
                    .withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.FERN), 2);
        }

        if (this.decorationRandom.nextInt(4) == 0)
        {
            int x = chunkX * 16 + 5 + this.decorationRandom.nextInt(6);
            int z = chunkZ * 16 + 5 + this.decorationRandom.nextInt(6);
            BlockPos base = this.world.getHeight(new BlockPos(x, 0, z));
            int height = 10 + this.decorationRandom.nextInt(7);
            if (base.getY() > 64 && base.getY() + height < 235
                && this.world.getBlockState(base.down()).getBlock() == Blocks.GRASS)
            {
                IBlockState trunk = CrazycraftContentBlocks.OLD[8].getDefaultState();
                IBlockState crown = Blocks.LEAVES2.getDefaultState()
                    .withProperty(net.minecraft.block.BlockNewLeaf.VARIANT, BlockPlanks.EnumType.ACACIA)
                    .withProperty(net.minecraft.block.BlockLeaves.CHECK_DECAY, Boolean.FALSE);
                for (int y = 0; y < height; ++y) this.world.setBlockState(base.up(y), trunk, 2);
                for (int arm = -1; arm <= 1; ++arm)
                {
                    this.world.setBlockState(base.add(arm * 2, height - 2, 0), trunk, 2);
                    this.world.setBlockState(base.add(0, height - 2, arm * 2), trunk, 2);
                }
                for (int y = height - 3; y <= height; ++y)
                    for (int dx = -5; dx <= 5; ++dx)
                        for (int dz = -5; dz <= 5; ++dz)
                            if (dx * dx + dz * dz <= (y == height ? 14 : 27))
                            {
                                BlockPos leaf = base.add(dx, y, dz);
                                if (this.world.isAirBlock(leaf)) this.world.setBlockState(leaf, crown, 2);
                            }
            }
        }

        if (this.decorationRandom.nextInt(7) == 0)
        {
            int x = chunkX * 16 + 6 + this.decorationRandom.nextInt(4);
            int z = chunkZ * 16 + 6 + this.decorationRandom.nextInt(4);
            BlockPos base = this.world.getHeight(new BlockPos(x, 0, z));
            if (base.getY() > 64 && base.getY() < 215 && this.world.getBlockState(base.down()).getBlock() == Blocks.GRASS)
                for (int y = 0; y < 5 + this.decorationRandom.nextInt(7); ++y)
                    this.world.setBlockState(base.up(y), Blocks.MOSSY_COBBLESTONE.getDefaultState(), 2);
        }
        this.decorateOldRegion(chunkX, chunkZ);
    }

    private boolean isOldSoil(BlockPos ground)
    {
        net.minecraft.block.Block block = this.world.getBlockState(ground).getBlock();
        return block == Blocks.GRASS || block == Blocks.DIRT || block == Blocks.SAND
            || block == CrazycraftContentBlocks.OLD[0] || block == CrazycraftContentBlocks.OLD[3]
            || block == CrazycraftContentBlocks.OLD[4] || block == CrazycraftContentBlocks.OLD[7]
            || block == CrazycraftContentBlocks.OLD[12] || block == CrazycraftContentBlocks.OLD[15];
    }

    private void decorateOldRegion(int chunkX, int chunkZ)
    {
        int x = chunkX * 16 + 8;
        int z = chunkZ * 16 + 8;
        int biome = Biome.getIdForBiome(this.world.getBiome(new BlockPos(x, 70, z)));
        BlockPos base = this.world.getHeight(new BlockPos(x, 0, z));
        if (base.getY() < 63 || base.getY() > 215 || !this.isOldSoil(base.down())) return;
        if (biome == 206 && this.decorationRandom.nextInt(2) == 0)
        {
            // Coastal palms bend toward the sea and have a small, radial crown.
            for (int y = 0; y < 11; ++y)
                this.world.setBlockState(base.add(y > 6 ? 1 : 0, y, 0), CrazycraftContentBlocks.OLD[9].getDefaultState(), 2);
            IBlockState leaves = prehistoricLeaves();
            for (int dx = -4; dx <= 4; ++dx)
                for (int dz = -4; dz <= 4; ++dz)
                    if (Math.abs(dx) <= 1 || Math.abs(dz) <= 1)
                    {
                        int drop = Math.max(Math.abs(dx), Math.abs(dz)) > 2 ? -1 : 0;
                        this.world.setBlockState(base.add(1 + dx, 11 + drop, dz), leaves, 2);
                    }
        }
        else if (biome == 202 && this.decorationRandom.nextInt(3) == 0)
        {
            // Wide baobab trunk and flat crown keep the savanna mostly open.
            for (int y = 0; y < 9; ++y)
                for (int dx = 0; dx <= 1; ++dx)
                    for (int dz = 0; dz <= 1; ++dz)
                        this.world.setBlockState(base.add(dx, y, dz), CrazycraftContentBlocks.OLD[8].getDefaultState(), 2);
            for (int dx = -5; dx <= 6; ++dx)
                for (int dz = -5; dz <= 6; ++dz)
                    if (dx * dx + dz * dz < 30)
                        this.world.setBlockState(base.add(dx, 9, dz), prehistoricLeaves(), 2);
        }
        else if (biome == 204 && this.decorationRandom.nextInt(2) == 0)
        {
            // Cycad: short woody stem with a broad ring of fronds.
            for (int y = 0; y < 5; ++y)
                this.world.setBlockState(base.up(y), CrazycraftContentBlocks.OLD[9].getDefaultState(), 2);
            for (int dx = -3; dx <= 3; ++dx)
                for (int dz = -3; dz <= 3; ++dz)
                    if (Math.abs(dx) <= 1 || Math.abs(dz) <= 1)
                        this.world.setBlockState(base.add(dx, 5 - (Math.max(Math.abs(dx), Math.abs(dz)) == 3 ? 1 : 0), dz), prehistoricLeaves(), 2);
        }
        else if (biome == 207 && this.decorationRandom.nextInt(3) == 0)
        {
            for (int y = 0; y < 8; ++y)
                this.world.setBlockState(base.up(y), CrazycraftContentBlocks.OLD[10].getDefaultState(), 2);
            for (int dx = -2; dx <= 2; ++dx)
                for (int dz = -2; dz <= 2; ++dz)
                    if (dx * dx + dz * dz <= 5)
                        this.world.setBlockState(base.add(dx, 8, dz), CrazycraftContentBlocks.OLD[10].getDefaultState(), 2);
        }
        else if (biome == 201 && this.decorationRandom.nextInt(4) == 0)
        {
            for (int y = 0; y < 4; ++y)
                this.world.setBlockState(base.up(y), CrazycraftContentBlocks.OLD[11].getDefaultState(), 2);
        }
        if ((biome == 200 || biome == 204 || biome == 205 || biome == 209)
            && this.decorationRandom.nextInt(3) == 0)
        {
            int fernX = chunkX * 16 + 3 + this.decorationRandom.nextInt(10);
            int fernZ = chunkZ * 16 + 3 + this.decorationRandom.nextInt(10);
            BlockPos fern = this.world.getHeight(new BlockPos(fernX, 0, fernZ));
            if (fern.getY() > 64 && fern.getY() < 225 && this.isOldSoil(fern.down()))
            {
                for (int y = 0; y < 5; ++y)
                    this.world.setBlockState(fern.up(y), CrazycraftContentBlocks.OLD[9].getDefaultState(), 2);
                for (int arm = -4; arm <= 4; ++arm)
                    for (int width = -1; width <= 1; ++width)
                    {
                        int drop = Math.abs(arm) > 2 ? -1 : 0;
                        if (this.world.isAirBlock(fern.add(arm, 5 + drop, width)))
                            this.world.setBlockState(fern.add(arm, 5 + drop, width), prehistoricLeaves(), 2);
                        if (this.world.isAirBlock(fern.add(width, 5 + drop, arm)))
                            this.world.setBlockState(fern.add(width, 5 + drop, arm), prehistoricLeaves(), 2);
                    }
            }
        }
    }

    private IBlockState prehistoricLeaves()
    {
        return Blocks.LEAVES2.getDefaultState()
            .withProperty(net.minecraft.block.BlockNewLeaf.VARIANT, BlockPlanks.EnumType.ACACIA)
            .withProperty(net.minecraft.block.BlockLeaves.CHECK_DECAY, Boolean.FALSE);
    }

    private void decorateFutureRegion(int chunkX, int chunkZ, int biome)
    {
        Random random = new Random(this.world.getSeed() ^ ((long)chunkX * 4815162342L)
            ^ ((long)chunkZ * 1357924680L) ^ 0x465554555245L);
        if (random.nextInt(3) != 0) return;
        int x = chunkX * 16 + 8;
        int z = chunkZ * 16 + 8;
        BlockPos base = this.world.getHeight(new BlockPos(x, 0, z));
        if (base.getY() < 64 || base.getY() > 220) return;
        if (biome == 215 && this.world.getBlockState(base.down()).getBlock() != CrazycraftContentBlocks.FUTURE[1]) return;
        if (biome == 212 && this.world.getBlockState(base.down()).getBlock() != CrazycraftContentBlocks.FUTURE[9]) return;
        if (biome == 215)
        {
            // Luminous artificial canopy in the neon forest.
            for (int y = 0; y < 11; ++y)
                this.world.setBlockState(base.up(y), CrazycraftContentBlocks.FUTURE[5].getDefaultState(), 2);
            for (int dx = -4; dx <= 4; ++dx)
                for (int dz = -4; dz <= 4; ++dz)
                    if (dx * dx + dz * dz <= 16)
                        this.world.setBlockState(base.add(dx, 11, dz),
                            ((dx + dz) & 3) == 0 ? CrazycraftContentBlocks.FUTURE[6].getDefaultState()
                                : CrazycraftContentBlocks.FUTURE[1].getDefaultState(), 2);
        }
        else if (biome == 212)
        {
            for (int y = 0; y < 9; ++y)
            {
                int radius = y < 3 ? 2 : y < 6 ? 1 : 0;
                for (int dx = -radius; dx <= radius; ++dx)
                    for (int dz = -radius; dz <= radius; ++dz)
                        if (dx * dx + dz * dz <= radius * radius)
                            this.world.setBlockState(base.add(dx, y, dz),
                                CrazycraftContentBlocks.FUTURE[y > 5 ? 2 : 7].getDefaultState(), 2);
            }
        }
    }

    private void spawnPteranodon(int chunkX, int chunkZ)
    {
        Random flyingRandom = new Random(this.world.getSeed() ^ ((long)chunkX * 778654123L)
            ^ ((long)chunkZ * 938275911L) ^ 0x505445524FL);
        if (flyingRandom.nextInt(20) != 0) return;
        int x = chunkX * 16 + 4 + flyingRandom.nextInt(8);
        int z = chunkZ * 16 + 4 + flyingRandom.nextInt(8);
        BlockPos top = this.world.getHeight(new BlockPos(x, 0, z));
        int y = Math.min(top.getY() + 18 + flyingRandom.nextInt(12), 235);
        if (!this.world.isAirBlock(new BlockPos(x, y, z))) return;
        int kind = flyingRandom.nextInt(16);
        EntityPteranodon flying = kind == 0 ? new EntityQuetzalcoatlus(this.world)
            : kind < 5 ? new EntityRhamphorhynchus(this.world) : new EntityPteranodon(this.world);
        flying.setLocationAndAngles(x + 0.5D, y, z + 0.5D, flyingRandom.nextFloat() * 360.0F, 0.0F);
        flying.setIsBatHanging(false);
        this.world.spawnEntity(flying);
    }

    private void generateTower(int chunkX, int chunkZ)
    {
        int centerX = chunkX * 16 + 8;
        int centerZ = chunkZ * 16 + 8;
        int groundY = this.world.getHeight(new BlockPos(centerX, 0, centerZ)).getY();
        this.decorationRandom.setSeed(this.world.getSeed() ^ ((long)chunkX * 341873128712L) ^ ((long)chunkZ * 132897987541L));
        int topY = Math.min(groundY + 28 + this.decorationRandom.nextInt(29), 230);
        IBlockState wall = CrazycraftContentBlocks.FUTURE[8].getDefaultState();
        IBlockState window = CrazycraftContentBlocks.FUTURE[3].getDefaultState();

        for (int x = centerX - 4; x <= centerX + 4; ++x)
        {
            for (int z = centerZ - 4; z <= centerZ + 4; ++z)
            {
                this.world.setBlockState(new BlockPos(x, groundY, z), wall, 2);

                for (int y = groundY + 1; y <= topY; ++y)
                {
                    boolean edge = x == centerX - 4 || x == centerX + 4 || z == centerZ - 4 || z == centerZ + 4;

                    if (edge)
                    {
                        IBlockState block = y % 5 == 0 ? window : wall;
                        this.world.setBlockState(new BlockPos(x, y, z), block, 2);
                    }
                    else if ((y - groundY) % 7 == 0)
                    {
                        this.world.setBlockState(new BlockPos(x, y, z), wall, 2);
                    }
                }

                this.world.setBlockState(new BlockPos(x, topY + 1, z), wall, 2);
            }
        }

        for (int y = groundY + 5; y <= topY; y += 5)
        {
            this.world.setBlockState(new BlockPos(centerX - 4, y, centerZ - 4), CrazycraftContentBlocks.FUTURE[1].getDefaultState(), 2);
            this.world.setBlockState(new BlockPos(centerX + 4, y, centerZ + 4), CrazycraftContentBlocks.FUTURE[1].getDefaultState(), 2);
        }

        this.world.setBlockState(new BlockPos(centerX, topY + 2, centerZ), CrazycraftContentBlocks.FUTURE[2].getDefaultState(), 2);
    }

    private void generateDinosaurNest(int chunkX, int chunkZ)
    {
        int x = chunkX * 16 + 8;
        int z = chunkZ * 16 + 8;
        BlockPos base = this.world.getHeight(new BlockPos(x, 0, z));
        if (base.getY() < 65 || base.getY() > 220 || !this.isOldSoil(base.down())) return;
        for (int dx = -4; dx <= 4; ++dx)
            for (int dz = -4; dz <= 4; ++dz)
            {
                int distance = dx * dx + dz * dz;
                if (distance > 17) continue;
                BlockPos at = base.add(dx, -1, dz);
                this.world.setBlockState(at, distance > 8
                    ? CrazycraftContentBlocks.OLD[1].getDefaultState()
                    : CrazycraftContentBlocks.OLD[4].getDefaultState(), 2);
                if (distance <= 8) this.world.setBlockState(at.up(), Blocks.AIR.getDefaultState(), 2);
            }
        for (int[] offset : new int[][] {{-1, 0}, {1, 1}, {0, -2}})
            this.world.setBlockState(base.add(offset[0], 0, offset[1]),
                CrazycraftContentBlocks.OLD[2].getDefaultState(), 2);
    }

    private void generateAncientTemple(int chunkX, int chunkZ)
    {
        int x = chunkX * 16 + 8;
        int z = chunkZ * 16 + 8;
        BlockPos base = this.world.getHeight(new BlockPos(x, 0, z));
        if (base.getY() < 65 || base.getY() > 212 || !this.isOldSoil(base.down())) return;
        IBlockState stone = CrazycraftContentBlocks.OLD[5].getDefaultState();
        IBlockState accent = CrazycraftContentBlocks.OLD[0].getDefaultState();
        for (int dx = -5; dx <= 5; ++dx)
            for (int dz = -5; dz <= 5; ++dz)
                this.world.setBlockState(base.add(dx, -1, dz), stone, 2);
        for (int dx : new int[] {-4, 4})
            for (int dz : new int[] {-4, 4})
                for (int y = 0; y <= 7; ++y)
                    this.world.setBlockState(base.add(dx, y, dz), y == 0 || y == 7 ? accent : stone, 2);
        for (int dx = -5; dx <= 5; ++dx)
            for (int dz = -5; dz <= 5; ++dz)
                if (Math.abs(dx) == 5 || Math.abs(dz) == 5)
                    this.world.setBlockState(base.add(dx, 8, dz), accent, 2);
        for (int y = 0; y < 3; ++y)
            for (int dx = -2 + y; dx <= 2 - y; ++dx)
                for (int dz = -2 + y; dz <= 2 - y; ++dz)
                    this.world.setBlockState(base.add(dx, y, dz), accent, 2);
        this.world.setBlockState(base.up(3), CrazycraftContentBlocks.OLD[2].getDefaultState(), 2);
    }

    private void generateResearchLab(int chunkX, int chunkZ)
    {
        int x = chunkX * 16 + 8;
        int z = chunkZ * 16 + 8;
        int y = this.world.getHeight(new BlockPos(x, 0, z)).getY();
        if (y < 64 || y > 220) return;
        IBlockState metal = CrazycraftContentBlocks.FUTURE[9].getDefaultState();
        IBlockState glass = CrazycraftContentBlocks.FUTURE[3].getDefaultState();
        for (int dx = -5; dx <= 5; ++dx)
            for (int dz = -5; dz <= 5; ++dz)
                for (int dy = 0; dy <= 6; ++dy)
                    if (dy == 0 || dy == 6 || Math.abs(dx) == 5 || Math.abs(dz) == 5)
                        this.world.setBlockState(new BlockPos(x + dx, y + dy, z + dz),
                            dy > 1 && dy < 5 && (Math.abs(dx) == 5 || Math.abs(dz) == 5) ? glass : metal, 2);
        for (int dy = 1; dy <= 3; ++dy)
            this.world.setBlockState(new BlockPos(x, y + dy, z - 5), Blocks.AIR.getDefaultState(), 2);
        this.world.setBlockState(new BlockPos(x, y + 1, z), CrazycraftContentBlocks.FUTURE[8].getDefaultState(), 2);
        this.world.setBlockState(new BlockPos(x, y + 7, z), CrazycraftContentBlocks.FUTURE[6].getDefaultState(), 2);
    }

    private void generateEnergyTower(int chunkX, int chunkZ)
    {
        int x = chunkX * 16 + 8;
        int z = chunkZ * 16 + 8;
        int y = this.world.getHeight(new BlockPos(x, 0, z)).getY();
        if (y < 64 || y > 207) return;
        for (int dy = 0; dy < 24; ++dy)
            for (int dx = -2; dx <= 2; ++dx)
                for (int dz = -2; dz <= 2; ++dz)
                    if (Math.abs(dx) == 2 || Math.abs(dz) == 2)
                        this.world.setBlockState(new BlockPos(x + dx, y + dy, z + dz),
                            dy % 5 == 0 ? CrazycraftContentBlocks.FUTURE[1].getDefaultState()
                                : CrazycraftContentBlocks.FUTURE[8].getDefaultState(), 2);
        for (int dy = 0; dy <= 23; dy += 4)
            this.world.setBlockState(new BlockPos(x, y + dy, z),
                CrazycraftContentBlocks.FUTURE[2].getDefaultState(), 2);
        for (int dx = -3; dx <= 3; ++dx)
            for (int dz = -3; dz <= 3; ++dz)
                this.world.setBlockState(new BlockPos(x + dx, y + 24, z + dz),
                    CrazycraftContentBlocks.FUTURE[11].getDefaultState(), 2);
        this.world.setBlockState(new BlockPos(x, y + 25, z), CrazycraftContentBlocks.FUTURE[2].getDefaultState(), 2);
    }

    private void generateTarPit(int chunkX, int chunkZ)
    {
        int x = chunkX * 16 + 8;
        int z = chunkZ * 16 + 8;
        int y = this.world.getHeight(new BlockPos(x, 0, z)).getY() - 1;
        if (y < 63 || y > 220) return;
        for (int dx = -5; dx <= 5; ++dx)
            for (int dz = -5; dz <= 5; ++dz)
            {
                int radius = dx * dx + dz * dz;
                if (radius > 27) continue;
                BlockPos at = new BlockPos(x + dx, y, z + dz);
                this.world.setBlockState(at, radius < 17
                    ? CrazycraftContentBlocks.OLD[6].getDefaultState()
                    : CrazycraftContentBlocks.OLD[1].getDefaultState(), 2);
                if (radius < 17) this.world.setBlockState(at.up(), Blocks.AIR.getDefaultState(), 2);
            }
    }

    private void generateAncientTree(int chunkX, int chunkZ)
    {
        int x = chunkX * 16 + 8;
        int z = chunkZ * 16 + 8;
        BlockPos base = this.world.getHeight(new BlockPos(x, 0, z));
        if (base.getY() < 65 || base.getY() > 205) return;
        int height = 27;
        IBlockState trunk = CrazycraftContentBlocks.OLD[8].getDefaultState();
        IBlockState leaves = Blocks.LEAVES2.getDefaultState()
            .withProperty(net.minecraft.block.BlockNewLeaf.VARIANT, BlockPlanks.EnumType.ACACIA)
            .withProperty(net.minecraft.block.BlockLeaves.CHECK_DECAY, Boolean.FALSE);
        for (int y = 0; y < height; ++y)
            for (int dx = 0; dx <= 1; ++dx)
                for (int dz = 0; dz <= 1; ++dz)
                    this.world.setBlockState(base.add(dx, y, dz), trunk, 2);
        for (int y = height - 9; y <= height + 2; y += 3)
        {
            int radius = y >= height ? 4 : 7;
            for (int dx = -radius; dx <= radius; ++dx)
                for (int dz = -radius; dz <= radius; ++dz)
                    if (dx * dx + dz * dz <= radius * radius)
                    {
                        BlockPos at = base.add(dx, y, dz);
                        if (this.world.isAirBlock(at)) this.world.setBlockState(at, leaves, 2);
                    }
        }
    }

    private void generateFloatingIsland(int chunkX, int chunkZ)
    {
        int x = chunkX * 16 + 8;
        int z = chunkZ * 16 + 8;
        int y = this.world.getHeight(new BlockPos(x, 0, z)).getY() + 36;
        if (y > 224) return;
        for (int dy = -5; dy <= 0; ++dy)
        {
            int radius = dy == 0 ? 8 : 8 + dy;
            for (int dx = -radius; dx <= radius; ++dx)
                for (int dz = -radius; dz <= radius; ++dz)
                    if (dx * dx + dz * dz <= radius * radius)
                        this.world.setBlockState(new BlockPos(x + dx, y + dy, z + dz),
                            dy == 0 ? CrazycraftContentBlocks.FUTURE[11].getDefaultState()
                                : CrazycraftContentBlocks.FUTURE[10].getDefaultState(), 2);
        }
        this.world.setBlockState(new BlockPos(x, y + 1, z), CrazycraftContentBlocks.FUTURE[2].getDefaultState(), 2);
        for (int dx : new int[] {-5, 5})
            for (int dz : new int[] {-5, 5})
                this.world.setBlockState(new BlockPos(x + dx, y + 1, z + dz),
                    CrazycraftContentBlocks.FUTURE[1].getDefaultState(), 2);
    }

    private void generateSpaceStation(int chunkX, int chunkZ)
    {
        int x = chunkX * 16 + 8;
        int z = chunkZ * 16 + 8;
        int y = this.world.getHeight(new BlockPos(x, 0, z)).getY() + 45;
        if (y > 226) return;
        for (int dx = -6; dx <= 6; ++dx)
            for (int dz = -6; dz <= 6; ++dz)
            {
                if (dx * dx + dz * dz > 72) continue;
                this.world.setBlockState(new BlockPos(x + dx, y, z + dz),
                    CrazycraftContentBlocks.FUTURE[0].getDefaultState(), 2);
                if (Math.abs(dx) == 6 || Math.abs(dz) == 6)
                    for (int dy = 1; dy <= 4; ++dy)
                        this.world.setBlockState(new BlockPos(x + dx, y + dy, z + dz),
                            CrazycraftContentBlocks.FUTURE[3].getDefaultState(), 2);
                this.world.setBlockState(new BlockPos(x + dx, y + 5, z + dz),
                    CrazycraftContentBlocks.FUTURE[9].getDefaultState(), 2);
            }
        this.world.setBlockState(new BlockPos(x, y + 6, z),
            CrazycraftContentBlocks.FUTURE[2].getDefaultState(), 2);
    }

    private void generateAsteroid(int chunkX, int chunkZ)
    {
        int x = chunkX * 16 + 8;
        int z = chunkZ * 16 + 8;
        int y = this.world.getHeight(new BlockPos(x, 0, z)).getY() + 32;
        if (y > 228) return;
        for (int dx = -5; dx <= 5; ++dx)
            for (int dy = -5; dy <= 5; ++dy)
                for (int dz = -5; dz <= 5; ++dz)
                    if (dx * dx + dy * dy + dz * dz < 23)
                        this.world.setBlockState(new BlockPos(x + dx, y + dy, z + dz),
                            ((dx * 5 + dy * 3 + dz) & 7) == 0
                                ? CrazycraftContentBlocks.OLD[13].getDefaultState()
                                : CrazycraftContentBlocks.FUTURE[5].getDefaultState(), 2);
    }

    private void generateFossil(int chunkX, int chunkZ)
    {
        int centerX = chunkX * 16 + 8;
        int centerZ = chunkZ * 16 + 8;
        int y = this.world.getHeight(new BlockPos(centerX, 0, centerZ)).getY();
        IBlockState bone = Blocks.BONE_BLOCK.getDefaultState();
        this.world.setBlockState(new BlockPos(centerX, y, centerZ), CrazycraftContentBlocks.OLD[1].getDefaultState(), 2);

        // A long exposed spine, paired curved ribs, limbs, tapering tail and jaw.
        for (int z = -12; z <= 17; ++z)
            this.world.setBlockState(new BlockPos(centerX, y + (z > 10 ? 2 : 4), centerZ + z), bone, 2);
        for (int z = -7; z <= 7; z += 2)
            for (int side : new int[] {-1, 1})
            {
                for (int x = 1; x <= 4; ++x)
                    this.world.setBlockState(new BlockPos(centerX + side * x, y + 4 - Math.max(0, x - 1), centerZ + z), bone, 2);
                for (int leg = 0; leg < 4; ++leg)
                    if (z == -7 || z == 7)
                        this.world.setBlockState(new BlockPos(centerX + side * 4, y + leg, centerZ + z), bone, 2);
            }
        for (int z = -17; z <= -12; ++z)
            for (int x = -2; x <= 2; ++x)
            {
                if (Math.abs(x) == 2 || z == -17 || z == -12)
                    this.world.setBlockState(new BlockPos(centerX + x, y + 4, centerZ + z), bone, 2);
                if (Math.abs(x) == 2 && z > -17 && z < -12)
                    this.world.setBlockState(new BlockPos(centerX + x, y + 2, centerZ + z), bone, 2);
            }
        for (int z = 13; z <= 22; ++z)
            if ((z & 1) == 0)
                this.world.setBlockState(new BlockPos(centerX, y + 2, centerZ + z), bone, 2);
    }

    private void generateHoverCar(int chunkX, int chunkZ)
    {
        int x = chunkX * 16 + 8;
        int z = chunkZ * 16 + 8;
        int y = Math.min(this.world.getHeight(new BlockPos(x, 0, z)).getY() + 11, 235);
        IBlockState body = CrazycraftContentBlocks.FUTURE[11].getDefaultState();
        IBlockState glass = CrazycraftContentBlocks.FUTURE[3].getDefaultState();

        for (int dx = -3; dx <= 3; ++dx)
        {
            for (int dz = -1; dz <= 1; ++dz)
            {
                this.world.setBlockState(new BlockPos(x + dx, y, z + dz), body, 2);
            }
        }

        for (int dx = -1; dx <= 1; ++dx)
        {
            this.world.setBlockState(new BlockPos(x + dx, y + 1, z), glass, 2);
        }

        this.world.setBlockState(new BlockPos(x - 3, y, z - 1), Blocks.SEA_LANTERN.getDefaultState(), 2);
        this.world.setBlockState(new BlockPos(x - 3, y, z + 1), Blocks.SEA_LANTERN.getDefaultState(), 2);
        this.world.setBlockState(new BlockPos(x + 3, y, z - 1), Blocks.SEA_LANTERN.getDefaultState(), 2);
        this.world.setBlockState(new BlockPos(x + 3, y, z + 1), Blocks.SEA_LANTERN.getDefaultState(), 2);
    }
}
