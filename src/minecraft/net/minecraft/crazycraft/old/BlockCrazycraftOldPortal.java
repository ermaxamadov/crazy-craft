package net.minecraft.crazycraft.old;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

/** A portal surface that joins the overworld and the prehistoric world. */
public class BlockCrazycraftOldPortal extends Block
{
    public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.<EnumFacing.Axis>create("axis", EnumFacing.Axis.class, EnumFacing.Axis.X, EnumFacing.Axis.Z);
    private static final AxisAlignedBB X_SHAPE = new AxisAlignedBB(0.0D, 0.0D, 0.4375D, 1.0D, 1.0D, 0.5625D);
    private static final AxisAlignedBB Z_SHAPE = new AxisAlignedBB(0.4375D, 0.0D, 0.0D, 0.5625D, 1.0D, 1.0D);

    public BlockCrazycraftOldPortal()
    {
        super(Material.PORTAL);
        this.setLightOpacity(0);
        this.setHardness(0.3F);
        this.setLightLevel(0.7F);
        this.setTickRandomly(false);
        this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.X));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {AXIS});
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(AXIS, (meta & 1) == 1 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(AXIS) == EnumFacing.Axis.Z ? 1 : 0;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return state.getValue(AXIS) == EnumFacing.Axis.Z ? Z_SHAPE : X_SHAPE;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) { return false; }

    @Override
    public boolean isFullCube(IBlockState state) { return false; }

    @Override
    public BlockRenderLayer getBlockLayer() { return BlockRenderLayer.TRANSLUCENT; }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return NULL_AABB;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
    {
        if (world.isRemote || !(entity instanceof EntityPlayerMP) || entity.isRiding() || entity.isBeingRidden()
            || entity.timeUntilPortal > 0 || (entity.dimension != 0 && entity.dimension != 2))
        {
            return;
        }

        EntityPlayerMP player = (EntityPlayerMP)entity;
        int destination = player.dimension == 2 ? 0 : 2;
        MinecraftServer server = world.getMinecraftServer();
        if (server == null || server.getWorld(destination) == null) return;

        if (destination == 2)
        {
            player.crazycraftReturnX = player.posX;
            player.crazycraftReturnZ = player.posZ;
            player.crazycraftReturnValid = true;
        }

        player.timeUntilPortal = player.getPortalCooldown();
        server.getPlayerList().changePlayerDimension(player, destination);
        player.timeUntilPortal = player.getPortalCooldown();
        if (destination == 2)
        {
            WorldServer arrivalWorld = server.getWorld(destination);
            BlockPos arrival = new BlockPos(player.posX, player.posY, player.posZ).east(3);
            BlockPos gate = makeReturnPortal(arrivalWorld, arrival);
            BlockPos landing = gate.west(2).up();
            player.setLocationAndAngles(landing.getX() + 0.5D, landing.getY(), landing.getZ() + 0.5D,
                0.0F, 0.0F);
            player.connection.setPlayerLocation(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch);
        }
    }

    public static boolean lightFrame(World world, BlockPos touched)
    {
        for (EnumFacing.Axis axis : new EnumFacing.Axis[] {EnumFacing.Axis.X, EnumFacing.Axis.Z})
        {
            for (int dx = -3; dx <= 0; ++dx)
            {
                for (int dy = -4; dy <= 0; ++dy)
                {
                    BlockPos bottomLeft = touched.add(axis == EnumFacing.Axis.X ? dx : 0, dy,
                                                       axis == EnumFacing.Axis.Z ? dx : 0);
                    if (hasFrame(world, bottomLeft, axis))
                    {
                        fillInterior(world, bottomLeft, axis);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean hasFrame(World world, BlockPos origin, EnumFacing.Axis axis)
    {
        for (int x = 0; x < 4; ++x)
        {
            for (int y = 0; y < 5; ++y)
            {
                BlockPos at = offset(origin, axis, x, y);
                Block block = world.getBlockState(at).getBlock();
                boolean border = x == 0 || x == 3 || y == 0 || y == 4;
                if (border ? block != CrazycraftOldBlocks.FRAME
                           : block != CrazycraftOldBlocks.PORTAL && !world.isAirBlock(at)) return false;
            }
        }
        return true;
    }

    private static void fillInterior(World world, BlockPos origin, EnumFacing.Axis axis)
    {
        for (int x = 1; x <= 2; ++x)
        {
            for (int y = 1; y <= 3; ++y)
            {
                world.setBlockState(offset(origin, axis, x, y), CrazycraftOldBlocks.PORTAL.getDefaultState().withProperty(AXIS, axis), 3);
            }
        }
    }

    private static BlockPos offset(BlockPos origin, EnumFacing.Axis axis, int x, int y)
    {
        return origin.add(axis == EnumFacing.Axis.X ? x : 0, y, axis == EnumFacing.Axis.Z ? x : 0);
    }

    private static BlockPos makeReturnPortal(World world, BlockPos near)
    {
        for (BlockPos at : BlockPos.getAllInBox(near.add(-8, -5, -8), near.add(8, 5, 8)))
        {
            if (world.getBlockState(at).getBlock() == CrazycraftOldBlocks.PORTAL)
            {
                BlockPos gate = at.west();
                prepareLanding(world, gate);
                return gate;
            }
        }

        BlockPos origin = world.getHeight(new BlockPos(near.getX(), 0, near.getZ())).down();
        while (origin.getY() > 65)
        {
            Block cover = world.getBlockState(origin).getBlock();
            if (cover != Blocks.LEAVES && cover != Blocks.LEAVES2 && cover != Blocks.LOG
                && cover != Blocks.LOG2 && cover != Blocks.VINE) break;
            origin = origin.down();
        }
        OldArrivalScene.build(world, origin);
        // Reserve a safe landing next to the return gate, including in dense jungle.
        for (int x = -4; x <= 0; ++x)
        {
            for (int z = -2; z <= 2; ++z)
            {
                world.setBlockState(origin.add(x, 0, z), Blocks.STONE.getDefaultState(), 3);
                for (int y = 1; y <= 5; ++y) world.setBlockToAir(origin.add(x, y, z));
            }
        }

        for (int x = 0; x < 4; ++x)
        {
            for (int y = 0; y < 5; ++y)
            {
                BlockPos at = origin.add(x, y, 0);
                if (x == 0 || x == 3 || y == 0 || y == 4)
                    world.setBlockState(at, CrazycraftOldBlocks.FRAME.getDefaultState(), 3);
                else
                    world.setBlockState(at, CrazycraftOldBlocks.PORTAL.getDefaultState().withProperty(AXIS, EnumFacing.Axis.X), 3);
            }
        }
        return origin;
    }

    private static void prepareLanding(World world, BlockPos gate)
    {
        for (int x = -4; x <= -2; ++x)
            for (int z = -1; z <= 1; ++z)
            {
                world.setBlockState(gate.add(x, 0, z), Blocks.STONE.getDefaultState(), 3);
                for (int y = 1; y <= 4; ++y) world.setBlockToAir(gate.add(x, y, z));
            }
    }
}
