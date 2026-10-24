package net.minecraft.crazycraft;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

/** Registered terrain and building blocks for the two dimensions. */
public final class CrazycraftContentBlocks
{
    public static final String[] OLD_NAMES = {
        "ancient_stone", "fossil_block", "amber_block", "volcanic_rock",
        "jungle_dirt", "mossy_stone", "tar_pit_block", "prehistoric_sand",
        "redwood_log", "palm_log", "crystal_stone", "sulfur_block",
        "mud_block", "meteorite_ore", "fossil_ore", "prehistoric_grass"
    };
    public static final String[] FUTURE_NAMES = {
        "metal_block", "neon_block", "energy_core", "glass_panel",
        "circuit_block", "carbon_block", "hologram_block", "force_field",
        "machine_block", "white_metal", "dark_metal", "blue_alloy",
        "gravity_block", "teleporter_block", "solar_panel", "battery"
    };
    public static final Block[] OLD = make(OLD_NAMES, false);
    public static final Block[] FUTURE = make(FUTURE_NAMES, true);
    public static final Block FUTURE_PORTAL = new BlockCrazycraftFuturePortal()
        .setUnlocalizedName("crazycraftFuturePortal")
        .setCreativeTab(CreativeTabs.CRAZYCRAFT_FUTURE);

    private CrazycraftContentBlocks() {}

    private static Block[] make(String[] names, boolean future)
    {
        Block[] blocks = new Block[names.length];
        for (int i = 0; i < names.length; ++i)
            blocks[i] = new CrazycraftDecorativeBlock(names[i], future)
                .setUnlocalizedName("crazycraft_" + names[i])
                .setCreativeTab(future ? CreativeTabs.CRAZYCRAFT_FUTURE : CreativeTabs.CRAZYCRAFT_OLD);
        return blocks;
    }
}
