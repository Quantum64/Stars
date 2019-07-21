package co.q64.stars.util;

import co.q64.stars.block.DecayingBlock;
import co.q64.stars.block.FormedBlock;
import co.q64.stars.block.GreenFruitBlock;
import co.q64.stars.block.RedPrimedBlock;
import co.q64.stars.block.SeedBlock;
import co.q64.stars.tile.DecayingTile;
import co.q64.stars.tile.SeedTile;
import co.q64.stars.type.FormingBlockType;
import co.q64.stars.type.FormingBlockTypes;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SeedManager {
    protected @Inject FormingBlockTypes types;
    protected @Inject SeedBlock seedBlock;

    protected @Inject SeedManager() {}

    public boolean tryGrow(World world, BlockPos pos, FormingBlockType type) {
        Block block = world.getBlockState(pos).getBlock();
        boolean primed = block instanceof RedPrimedBlock;
        if (block instanceof FormedBlock) {
            Direction direction = type.getInitialDirection(world, pos);
            if (direction == null || block instanceof GreenFruitBlock) {
                return false;
            }
            if (types.get(block) == type) {
                return false;
            }
            world.setBlockState(pos, seedBlock.getDefaultState(), 3);
            SeedTile tile = (SeedTile) world.getTileEntity(pos);
            if (tile == null) {
                System.out.println("null tile on place " + pos.toString());
            } else {
                tile.setFormingBlockType(types.get(block));
                tile.setPrimed(primed);
                tile.setSeedType(type);
                tile.setCalculated(true);
            }
            return true;
        }
        if (block instanceof DecayingBlock) {
            DecayingTile tile = (DecayingTile) world.getTileEntity(pos);
            if (tile.isFruit() || tile.isHasSeed() || tile.getFormingBlockType() == type) {
                return false;
            }
            if (tile != null) {
                tile.grow(type);
            }
            return true;
        }
        return false;
    }
}
