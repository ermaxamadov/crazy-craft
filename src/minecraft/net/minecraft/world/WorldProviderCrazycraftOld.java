package net.minecraft.world;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.biome.BiomeProviderCrazycraft;
import net.minecraft.world.gen.ChunkGeneratorCrazycraft;
import net.minecraft.world.gen.IChunkGenerator;

/** Warm, overgrown terrain for the prehistoric Crazycraft dimension. */
public class WorldProviderCrazycraftOld extends WorldProvider
{
    @Override
    protected void init()
    {
        this.hasSkyLight = true;
        this.biomeProvider = new BiomeProviderCrazycraft(this.world.getSeed(), false);
    }

    @Override
    public IChunkGenerator createChunkGenerator()
    {
        return new ChunkGeneratorCrazycraft(this.world, this.world.getSeed(), false);
    }

    @Override
    public Vec3d getFogColor(float angle, float partialTicks)
    {
        return new Vec3d(0.91D, 0.73D, 0.54D);
    }

    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks)
    {
        return 0.19F;
    }

    @Override
    public boolean canRespawnHere()
    {
        return false;
    }

    @Override
    public DimensionType getDimensionType()
    {
        return DimensionType.CRAZYCRAFT_OLD;
    }
}
