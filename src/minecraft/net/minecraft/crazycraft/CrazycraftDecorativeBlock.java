package net.minecraft.crazycraft;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;

/** Shared behavior for dimension building materials. */
public class CrazycraftDecorativeBlock extends Block
{
    private final String kind;

    public CrazycraftDecorativeBlock(String kind, boolean future)
    {
        super(kind.equals("glass_panel") || kind.equals("hologram_block")
            || kind.equals("force_field") ? Material.GLASS
            : kind.endsWith("_log") ? Material.WOOD
            : kind.equals("jungle_dirt") || kind.equals("mud_block") ? Material.GROUND
            : kind.equals("prehistoric_sand") ? Material.SAND : Material.ROCK);
        this.kind = kind;
        this.setHardness(future ? 3.0F : 2.0F);
        this.setResistance(future ? 15.0F : 8.0F);
        this.setSoundType(kind.endsWith("_log") ? SoundType.WOOD : SoundType.STONE);
        if (kind.equals("amber_block") || kind.equals("crystal_stone")
            || kind.equals("neon_block") || kind.equals("energy_core")
            || kind.equals("hologram_block")) this.setLightLevel(0.8F);
        if (kind.equals("volcanic_rock")) this.setLightLevel(0.35F);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return !"tar_pit_block".equals(this.kind) && !"glass_panel".equals(this.kind) && !"hologram_block".equals(this.kind)
            && !"force_field".equals(this.kind);
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return !"tar_pit_block".equals(this.kind);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return "tar_pit_block".equals(this.kind) ? new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D)
            : FULL_BLOCK_AABB;
    }

    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return "glass_panel".equals(this.kind) || "hologram_block".equals(this.kind)
            || "force_field".equals(this.kind) ? BlockRenderLayer.TRANSLUCENT : BlockRenderLayer.SOLID;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
    {
        if (this.kind.equals("tar_pit_block"))
        {
            entity.motionX *= 0.35D;
            entity.motionZ *= 0.35D;
        }
        else if (this.kind.equals("gravity_block") && !entity.isSneaking())
        {
            entity.motionY = Math.max(entity.motionY, 0.35D);
            entity.fallDistance = 0.0F;
        }
        else if (this.kind.equals("volcanic_rock") && !world.isRemote)
        {
            entity.attackEntityFrom(DamageSource.HOT_FLOOR, 1.0F);
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!"energy_core".equals(this.kind) || player.getHeldItem(hand).getItem() != Items.FLINT_AND_STEEL)
            return false;
        if (!world.isRemote && BlockCrazycraftFuturePortal.lightFutureFrame(world, pos))
            player.getHeldItem(hand).damageItem(1, player);
        return true;
    }
}
