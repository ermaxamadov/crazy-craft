package net.minecraft.world.biome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;

/** Stable broad regions, independent of the overworld biome map. */
public class BiomeProviderCrazycraft extends BiomeProvider
{
    private final long seed;
    private final boolean future;
    private final List<Biome> available = new ArrayList<Biome>();

    public BiomeProviderCrazycraft(long seed, boolean future)
    {
        super();
        this.seed = seed;
        this.future = future;
        for (int i = 0; i < (future ? 8 : 10); ++i)
            this.available.add(Biome.getBiome((future ? 210 : 200) + i));
    }

    @Override
    public List<Biome> getBiomesToSpawnIn() { return this.available; }

    @Override
    public Biome getBiome(BlockPos pos) { return this.select(pos.getX(), pos.getZ()); }

    @Override
    public Biome getBiome(BlockPos pos, Biome fallback) { return this.select(pos.getX(), pos.getZ()); }

    @Override
    public Biome[] getBiomesForGeneration(Biome[] result, int x, int z, int width, int height)
    {
        if (result == null || result.length < width * height) result = new Biome[width * height];
        for (int row = 0; row < height; ++row)
            for (int col = 0; col < width; ++col)
                result[col + row * width] = this.select((x + col) * 4, (z + row) * 4);
        return result;
    }

    @Override
    public Biome[] getBiomes(@Nullable Biome[] result, int x, int z, int width, int height)
    {
        if (result == null || result.length < width * height) result = new Biome[width * height];
        for (int row = 0; row < height; ++row)
            for (int col = 0; col < width; ++col)
                result[col + row * width] = this.select(x + col, z + row);
        return result;
    }

    @Override
    public Biome[] getBiomes(@Nullable Biome[] result, int x, int z, int width, int height, boolean cacheFlag)
    {
        return this.getBiomes(result, x, z, width, height);
    }

    @Override
    public boolean areBiomesViable(int x, int z, int radius, List<Biome> allowed)
    {
        for (int px = x - radius; px <= x + radius; px += 16)
            for (int pz = z - radius; pz <= z + radius; pz += 16)
                if (!allowed.contains(this.select(px, pz))) return false;
        return true;
    }

    @Nullable
    @Override
    public BlockPos findBiomePosition(int x, int z, int range, List<Biome> allowed, Random random)
    {
        for (int attempt = 0; attempt < 64; ++attempt)
        {
            int px = x - range + random.nextInt(range * 2 + 1);
            int pz = z - range + random.nextInt(range * 2 + 1);
            if (allowed.contains(this.select(px, pz))) return new BlockPos(px, 0, pz);
        }
        return null;
    }

    @Override
    public boolean isFixedBiome() { return false; }

    private Biome select(int x, int z)
    {
        double phase = (this.seed & 0xFFFFL) * 0.0001D;
        double noise = Math.sin(x * 0.0036D + phase) + Math.cos(z * 0.0042D - phase)
            + 0.7D * Math.sin((x + z) * 0.0025D + phase * 0.7D);
        int index = (int)Math.floor((noise + 2.7D) / 5.4D * this.available.size());
        index = Math.max(0, Math.min(this.available.size() - 1, index));
        return this.available.get(index);
    }
}
