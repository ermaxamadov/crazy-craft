package net.minecraft.crazycraft;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crazycraft.old.BlockCrazycraftOldPortal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

/** Blue portal between the overworld and the Future Dimension. */
public class BlockCrazycraftFuturePortal extends BlockCrazycraftOldPortal
{
    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
    {
        if (world.isRemote || !(entity instanceof EntityPlayerMP) || entity.isRiding() || entity.isBeingRidden()
            || entity.timeUntilPortal > 0 || (entity.dimension != 0 && entity.dimension != 3)) return;
        EntityPlayerMP player = (EntityPlayerMP)entity;
        int destination = player.dimension == 3 ? 0 : 3;
        MinecraftServer server = world.getMinecraftServer();
        if (server == null || server.getWorld(destination) == null) return;
        player.timeUntilPortal = player.getPortalCooldown();
        server.getPlayerList().changePlayerDimension(player, destination);
        player.timeUntilPortal = player.getPortalCooldown();
        if (destination == 3)
        {
            BlockPos gate = makeReturnPortal(server.getWorld(3), new BlockPos(player.posX, player.posY, player.posZ).east(3));
            BlockPos landing = gate.west(2).up();
            player.setLocationAndAngles(landing.getX() + 0.5D, landing.getY(), landing.getZ() + 0.5D, 0.0F, 0.0F);
            player.connection.setPlayerLocation(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch);
        }
    }

    public static boolean lightFutureFrame(World world, BlockPos touched)
    {
        for (EnumFacing.Axis axis : new EnumFacing.Axis[] {EnumFacing.Axis.X, EnumFacing.Axis.Z})
            for (int dx = -3; dx <= 0; ++dx)
                for (int dy = -4; dy <= 0; ++dy)
                {
                    BlockPos origin = touched.add(axis == EnumFacing.Axis.X ? dx : 0, dy,
                        axis == EnumFacing.Axis.Z ? dx : 0);
                    boolean valid = true;
                    for (int x = 0; x < 4 && valid; ++x)
                        for (int y = 0; y < 5; ++y)
                        {
                            BlockPos at = offset(origin, axis, x, y);
                            Block block = world.getBlockState(at).getBlock();
                            boolean border = x == 0 || x == 3 || y == 0 || y == 4;
                            if (border ? block != CrazycraftContentBlocks.FUTURE[2]
                                : block != CrazycraftContentBlocks.FUTURE_PORTAL && !world.isAirBlock(at))
                                valid = false;
                        }
                    if (valid)
                    {
                        for (int x = 1; x <= 2; ++x)
                            for (int y = 1; y <= 3; ++y)
                                world.setBlockState(offset(origin, axis, x, y),
                                    CrazycraftContentBlocks.FUTURE_PORTAL.getDefaultState().withProperty(AXIS, axis), 3);
                        return true;
                    }
                }
        return false;
    }

    private static BlockPos offset(BlockPos origin, EnumFacing.Axis axis, int x, int y)
    {
        return origin.add(axis == EnumFacing.Axis.X ? x : 0, y, axis == EnumFacing.Axis.Z ? x : 0);
    }

    private static BlockPos makeReturnPortal(WorldServer world, BlockPos near)
    {
        for (BlockPos at : BlockPos.getAllInBox(near.add(-8, -5, -8), near.add(8, 5, 8)))
            if (world.getBlockState(at).getBlock() == CrazycraftContentBlocks.FUTURE_PORTAL)
                return at.west();

        BlockPos origin = world.getHeight(new BlockPos(near.getX(), 0, near.getZ())).down();
        for (int x = -4; x <= 3; ++x)
            for (int z = -2; z <= 2; ++z)
            {
                world.setBlockState(origin.add(x, 0, z), CrazycraftContentBlocks.FUTURE[0].getDefaultState(), 3);
                for (int y = 1; y <= 5; ++y) world.setBlockToAir(origin.add(x, y, z));
            }
        for (int x = 0; x < 4; ++x)
            for (int y = 0; y < 5; ++y)
            {
                BlockPos at = origin.add(x, y, 0);
                world.setBlockState(at, x == 0 || x == 3 || y == 0 || y == 4
                    ? CrazycraftContentBlocks.FUTURE[2].getDefaultState()
                    : CrazycraftContentBlocks.FUTURE_PORTAL.getDefaultState().withProperty(AXIS, EnumFacing.Axis.X), 3);
            }
        return origin;
    }
}
