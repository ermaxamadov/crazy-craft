package net.minecraft.crazycraft.old;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

/** Content reserved for the prehistoric dimension. */
public final class CrazycraftOldBlocks
{
    public static final Block FRAME = new BlockCrazycraftOldFrame()
        .setUnlocalizedName("crazycraftOldFrame")
        .setCreativeTab(CreativeTabs.CRAZYCRAFT_OLD);
    public static final Block PORTAL = new BlockCrazycraftOldPortal()
        .setUnlocalizedName("crazycraftOldPortal")
        .setCreativeTab(CreativeTabs.CRAZYCRAFT_OLD);

    private CrazycraftOldBlocks() {}
}
