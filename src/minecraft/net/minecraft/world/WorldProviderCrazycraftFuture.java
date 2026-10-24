package net.minecraft.world;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.biome.BiomeProviderCrazycraft;
import net.minecraft.world.gen.ChunkGeneratorCrazycraft;
import net.minecraft.world.gen.IChunkGenerator;

/** Blue fog and open terrain for the futuristic Crazycraft dimension. */
public class WorldProviderCrazycraftFuture extends WorldProvider
{
    @Override
    protected void init()
    {
        this.hasSkyLight = true;
        this.biomeProvider = new BiomeProviderCrazycraft(this.world.getSeed(), true);
    }

    @Override
    public IChunkGenerator createChunkGenerator()
    {
        return new ChunkGeneratorCrazycraft(this.world, this.world.getSeed(), true);
    }

    @Override
    public Vec3d getFogColor(float angle, float partialTicks)
    {
        return new Vec3d(0.14D, 0.22D, 0.38D);
    }

    @Override
    public boolean canRespawnHere()
    {
        return false;
    }

    @Override
    public DimensionType getDimensionType()
    {
        return DimensionType.CRAZYCRAFT_FUTURE;
    }
}
