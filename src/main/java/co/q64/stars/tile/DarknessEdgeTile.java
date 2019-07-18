package co.q64.stars.tile;

import co.q64.stars.block.AirDecayBlock;
import co.q64.stars.block.AirDecayEdgeBlock;
import co.q64.stars.block.DarkAirBlock;
import co.q64.stars.block.DarknessBlock;
import co.q64.stars.block.DarknessEdgeBlock;
import co.q64.stars.block.DecayBlock;
import co.q64.stars.block.DecayEdgeBlock;
import co.q64.stars.block.DecayingBlock;
import co.q64.stars.block.FormedBlock;
import co.q64.stars.block.SpecialAirBlock;
import co.q64.stars.tile.type.DarknessEdgeTileType;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

@AutoFactory
public class DarknessEdgeTile extends TileEntity implements ITickableTileEntity {
    private static final Direction[] DIRECTIONS = Direction.values();
    private static final long SALT = 0xabcd0123dcba3210L;

    private DarknessEdgeBlock darknessEdgeBlock;
    private DarknessBlock darknessBlock;
    private SpecialAirBlock specialAirBlock;

    public DarknessEdgeTile(@Provided DarknessEdgeTileType type, @Provided DarknessEdgeBlock darknessEdgeBlock, @Provided DarknessBlock darknessBlock, @Provided SpecialAirBlock specialAirBlock) {
        super(type);
        this.darknessEdgeBlock = darknessEdgeBlock;
        this.darknessBlock = darknessBlock;
        this.specialAirBlock = specialAirBlock;
    }

    public void tick() {
        if (!world.isRemote) {
            for (Direction direction : DIRECTIONS) {
                BlockPos target = getPos().offset(direction);
                Block block = world.getBlockState(target).getBlock();
                if (block == Blocks.AIR || block instanceof DarkAirBlock || block instanceof AirDecayBlock || block instanceof AirDecayEdgeBlock) {
                    world.setBlockState(target, darknessBlock.getDefaultState());
                } else if (block instanceof FormedBlock || block instanceof DecayBlock || block instanceof DecayEdgeBlock || block instanceof DecayingBlock) {
                    world.setBlockState(target, darknessEdgeBlock.getDefaultState());
                }
            }
            world.setBlockState(getPos(), specialAirBlock.getDefaultState());
        }
    }
}
