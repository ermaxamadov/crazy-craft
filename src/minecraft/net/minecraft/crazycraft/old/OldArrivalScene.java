package net.minecraft.crazycraft.old;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Builds an open prehistoric vista on the first arrival at an Old Dimension gate. */
public final class OldArrivalScene
{
    private OldArrivalScene() {}

    public static void build(World world, BlockPos gate)
    {
        int level = Math.max(64, gate.getY() - 3);
        BlockPos lake = gate.add(6, 0, 29);

        for (int x = -17; x <= 18; ++x)
            for (int z = 1; z <= 9; ++z)
            {
                BlockPos column = gate.add(x, 0, z);
                int top = world.getHeight(new BlockPos(column.getX(), 0, column.getZ())).getY();
                for (int y = gate.getY() + 1; y <= Math.max(top, gate.getY() + 8); ++y)
                    world.setBlockState(new BlockPos(column.getX(), y, column.getZ()), Blocks.AIR.getDefaultState(), 2);
            }

        for (int dx = -21; dx <= 21; ++dx)
            for (int dz = -21; dz <= 21; ++dz)
            {
                int distance = dx * dx + dz * dz;
                if (distance > 441) continue;
                int x = lake.getX() + dx;
                int z = lake.getZ() + dz;
                int top = world.getHeight(new BlockPos(x, 0, z)).getY() - 1;
                if (distance > 324)
                {
                    if (top <= level + 2 && top >= level - 3)
                        world.setBlockState(new BlockPos(x, top, z), Blocks.SAND.getDefaultState(), 2);
                    continue;
                }
                int floor = level - 2 - (distance < 100 ? 2 : 0);
                if (top < floor)
                    for (int y = top + 1; y <= floor; ++y)
                        world.setBlockState(new BlockPos(x, y, z), Blocks.STONE.getDefaultState(), 2);
                else
                    for (int y = floor + 1; y <= top; ++y)
                        world.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState(), 2);
                world.setBlockState(new BlockPos(x, floor, z), Blocks.SAND.getDefaultState(), 2);
                for (int y = floor + 1; y <= level; ++y)
                    world.setBlockState(new BlockPos(x, y, z), Blocks.WATER.getDefaultState(), 2);
            }

        stoneTower(world, lake.add(-22, 0, 25), 24, false);
        stoneTower(world, lake.add(0, 0, 26), 34, true);
        stoneTower(world, lake.add(24, 0, 24), 27, false);
    }

    private static void stoneTower(World world, BlockPos at, int height, boolean cascade)
    {
        int base = world.getHeight(new BlockPos(at.getX(), 0, at.getZ())).getY();
        if (base + height > 245) return;
        for (int y = 0; y < height; ++y)
        {
            int radius = y < 7 ? 5 : y < height - 6 ? 3 : 2;
            for (int dx = -radius; dx <= radius; ++dx)
                for (int dz = -radius; dz <= radius; ++dz)
                    if (dx * dx + dz * dz <= radius * radius)
                        world.setBlockState(new BlockPos(at.getX() + dx, base + y, at.getZ() + dz),
                            y > height - 4 ? Blocks.MOSSY_COBBLESTONE.getDefaultState() : Blocks.STONE.getDefaultState(), 2);
        }
        for (int dx = -3; dx <= 3; ++dx)
            for (int dz = -3; dz <= 3; ++dz)
                if (dx * dx + dz * dz < 13)
                    world.setBlockState(new BlockPos(at.getX() + dx, base + height, at.getZ() + dz),
                        Blocks.GRASS.getDefaultState(), 2);
        if (cascade)
            for (int y = base + 1; y < base + height - 3; ++y)
                for (int dx = -1; dx <= 1; ++dx)
                    world.setBlockState(new BlockPos(at.getX() + dx, y, at.getZ() - 3),
                        Blocks.FLOWING_WATER.getDefaultState(), 2);
    }
}
