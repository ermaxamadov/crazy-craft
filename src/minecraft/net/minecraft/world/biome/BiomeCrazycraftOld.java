package net.minecraft.world.biome;

import net.minecraft.crazycraft.old.EntityTRex;
import net.minecraft.crazycraft.old.EntityTriceratops;
import net.minecraft.crazycraft.old.EntityStegosaurus;
import net.minecraft.crazycraft.old.EntityVelociraptor;
import net.minecraft.crazycraft.old.EntityCrazycraftSpecies;

/** A humid prehistoric forest populated by dinosaurs instead of vanilla animals. */
public class BiomeCrazycraftOld extends BiomeJungle
{
    public BiomeCrazycraftOld()
    {
        super(false, new Biome.BiomeProperties("Qadimgi dunyo")
            .setBaseHeight(0.42F).setHeightVariation(0.38F)
            .setTemperature(1.0F).setRainfall(0.9F).setWaterColor(0x346B62));
        this.spawnableCreatureList.clear();
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityTriceratops.class, 5, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityStegosaurus.class, 4, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityVelociraptor.class, 2, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityTRex.class, 1, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityCrazycraftSpecies.Allosaurus.class, 1, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityCrazycraftSpecies.Dilophosaurus.class, 2, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityCrazycraftSpecies.Mapusaurus.class, 1, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityCrazycraftSpecies.Giganotosaurus.class, 1, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityCrazycraftSpecies.Brachiosaurus.class, 1, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityCrazycraftSpecies.Ankylosaurus.class, 2, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityCrazycraftSpecies.Compsognathus.class, 3, 1, 2));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityCrazycraftSpecies.Gallimimus.class, 2, 1, 2));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityCrazycraftSpecies.Oviraptor.class, 1, 1, 1));
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
        this.decorator.treesPerChunk = 1;
        this.decorator.grassPerChunk = 9;
        this.decorator.flowersPerChunk = 0;
    }

    @Override
    public int getGrassColorAtPos(net.minecraft.util.math.BlockPos pos)
    {
        return 0x719B58;
    }

    @Override
    public int getFoliageColorAtPos(net.minecraft.util.math.BlockPos pos)
    {
        return 0x388F79;
    }

    @Override
    public float getSpawningChance() { return 0.025F; }
}
