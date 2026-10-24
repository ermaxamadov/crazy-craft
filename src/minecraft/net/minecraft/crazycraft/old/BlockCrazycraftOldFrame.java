package net.minecraft.crazycraft.old;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** A 4 by 5 frame can be lit with flint and steel. */
public class BlockCrazycraftOldFrame extends Block
{
    public BlockCrazycraftOldFrame()
    {
        super(Material.ROCK);
        this.setSoundType(SoundType.STONE);
        this.setHardness(5.0F);
        this.setResistance(30.0F);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (player.getHeldItem(hand).getItem() != Items.FLINT_AND_STEEL)
        {
            return false;
        }

        if (!world.isRemote && BlockCrazycraftOldPortal.lightFrame(world, pos))
        {
            player.getHeldItem(hand).damageItem(1, player);
        }

        return true;
    }
}
