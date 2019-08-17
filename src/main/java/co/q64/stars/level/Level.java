package co.q64.stars.level;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

public interface Level {
    public LevelType getType();

    public List<BlockPos> getChallengeStars(BlockPos start);

    public BlockPos createChallenge(ServerWorld world, BlockPos start);

    public default void cube(ServerWorld world, Block block, BlockPos start, BlockPos end) {
        for (int x = start.getX(); x <= end.getX(); x++) {
            for (int y = start.getY(); y <= end.getY(); y++) {
                for (int z = start.getZ(); z <= end.getZ(); z++) {
                    set(world, block, new BlockPos(x, y, z));
                }
            }
        }
    }

    public default void box(ServerWorld world, Block block, BlockPos start, BlockPos end) {
        for (int x = start.getX(); x <= end.getX(); x++) {
            for (int z = start.getZ(); z <= end.getZ(); z++) {
                set(world, block, new BlockPos(x, start.getY(), z));
                set(world, Blocks.BARRIER, new BlockPos(x, start.getY() - 1, z));
                set(world, block, new BlockPos(x, end.getY(), z));
                set(world, Blocks.BARRIER, new BlockPos(x, end.getY() + 1, z));
            }
        }
        for (int x = start.getX(); x <= end.getX(); x++) {
            for (int y = start.getY(); y <= end.getY(); y++) {
                set(world, block, new BlockPos(x, y, start.getZ()));
                set(world, Blocks.BARRIER, new BlockPos(x, y, start.getZ() - 1));
                set(world, block, new BlockPos(x, y, end.getZ()));
                set(world, Blocks.BARRIER, new BlockPos(x, y, end.getZ() + 1));
            }
        }
        for (int z = start.getZ(); z <= end.getZ(); z++) {
            for (int y = start.getY(); y <= end.getY(); y++) {
                set(world, block, new BlockPos(start.getX(), y, z));
                set(world, Blocks.BARRIER, new BlockPos(start.getX() - 1, y, z));
                set(world, block, new BlockPos(end.getX(), y, z));
                set(world, Blocks.BARRIER, new BlockPos(end.getX() + 1, y, z));
            }
        }
    }

    public default void set(ServerWorld world, Block block, BlockPos pos) {
        world.setBlockState(pos, block.getDefaultState(), 0);
    }
}
