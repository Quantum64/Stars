package co.q64.stars.type;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public interface FormingBlockType {
    public static final Direction[] horizontal = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

    public int getId();

    public float getR();

    public float getG();

    public float getB();

    public int getBuildTime();

    public Block getFormedBlock();

    public String getName();

    public int getBuildTimeOffset();

    public int getDecayTime(long seed);

    public int getIterations(long seed);

    public Direction getInitialDirection(World world, BlockPos position);

    public List<Direction> getNextDirections(World world, BlockPos position, Direction last, int iterations);

    public default boolean hasBlock(World world, BlockPos pos, Direction direction) {
        BlockPos target = pos.offset(direction);
        return world.getBlockState(target).getBlock() != Blocks.AIR;
    }
}
