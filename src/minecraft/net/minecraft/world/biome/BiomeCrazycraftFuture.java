package net.minecraft.world.biome;

import net.minecraft.crazycraft.CrazycraftContentBlocks;
import net.minecraft.crazycraft.EntityCrazycraftWorkerBot;

/** Machine-covered city biome with no vanilla animals or trees. */
public class BiomeCrazycraftFuture extends Biome
{
    public BiomeCrazycraftFuture()
    {
        super(new Biome.BiomeProperties("Kelajak shahri")
            .setBaseHeight(0.18F).setHeightVariation(0.12F)
            .setTemperature(0.8F).setRainfall(0.0F).setRainDisabled()
            .setWaterColor(0x27BBDD));
        this.topBlock = CrazycraftContentBlocks.FUTURE[0].getDefaultState();
        this.fillerBlock = CrazycraftContentBlocks.FUTURE[10].getDefaultState();
        this.spawnableCreatureList.clear();
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityCrazycraftWorkerBot.class, 8, 1, 2));
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
        this.decorator.treesPerChunk = 0;
        this.decorator.grassPerChunk = 0;
        this.decorator.flowersPerChunk = 0;
    }

    @Override
    public int getSkyColorByTemp(float temperature)
    {
        return 0x668DCE;
    }
}
