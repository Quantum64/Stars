package co.q64.stars.tile;

import co.q64.stars.block.AirDecayBlock;
import co.q64.stars.block.AirDecayEdgeBlock;
import co.q64.stars.block.DarkAirBlock;
import co.q64.stars.block.DarknessBlock;
import co.q64.stars.block.DarknessEdgeBlock;
import co.q64.stars.block.DecayBlock;
import co.q64.stars.block.DecayEdgeBlock;
import co.q64.stars.block.DecayingBlock;
import co.q64.stars.block.DoorBlock;
import co.q64.stars.block.FormedBlock;
import co.q64.stars.block.FormingBlock;
import co.q64.stars.block.GreenFruitBlock;
import co.q64.stars.block.SpecialAirBlock;
import co.q64.stars.block.SpecialDecayBlock;
import co.q64.stars.block.SpecialDecayEdgeBlock;
import co.q64.stars.entity.PickupEntity;
import co.q64.stars.tile.type.DarknessEdgeTileType;
import co.q64.stars.util.DecayManager.SpecialDecayType;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

import javax.inject.Inject;

public class DarknessEdgeTile extends TileEntity implements ITickableTileEntity {
    private static final Direction[] DIRECTIONS = Direction.values();
    private static final long SALT = 0xabcd0123dcba3210L;

    protected @Inject DarknessEdgeBlock darknessEdgeBlock;
    protected @Inject DarknessBlock darknessBlock;
    protected @Inject SpecialAirBlock specialAirBlock;
    protected @Inject DoorBlock doorBlock;
    protected @Inject EntityType<PickupEntity> pickupEntityType;

    @Inject
    protected DarknessEdgeTile(DarknessEdgeTileType type) {
        super(type);
    }

    public void tick() {
        if (!world.isRemote) {
            for (Direction direction : DIRECTIONS) {
                BlockPos target = getPos().offset(direction);
                Block block = world.getBlockState(target).getBlock();
                if (block == Blocks.AIR || block instanceof FormingBlock) {
                    world.setBlockState(target, darknessBlock.getDefaultState());
                } else if (block instanceof SpecialDecayEdgeBlock || block instanceof SpecialDecayBlock || block instanceof GreenFruitBlock) {
                    SpecialDecayType type = SpecialDecayType.HEART;
                    if (block instanceof SpecialDecayBlock) {
                        type = world.getBlockState(target).get(SpecialDecayBlock.TYPE);
                    } else if (block instanceof GreenFruitBlock) {
                        type = SpecialDecayType.HEART;
                    } else {
                        SpecialDecayEdgeTile tile = (SpecialDecayEdgeTile) world.getTileEntity(target);
                        if (tile != null) {
                            type = tile.getDecayType();
                        }
                    }
                    switch (type) {
                        case HEART:
                            PickupEntity heartEntity = pickupEntityType.create(world);
                            heartEntity.setPosition(target.getX() + 0.5, target.getY(), target.getZ() + 0.5);
                            heartEntity.setVariant(0);
                            world.addEntity(heartEntity);
                            world.setBlockState(target, darknessEdgeBlock.getDefaultState());
                            break;
                        case KEY:
                            PickupEntity keyEntity = pickupEntityType.create(world);
                            keyEntity.setPosition(target.getX() + 0.5, target.getY(), target.getZ() + 0.5);
                            keyEntity.setVariant(1);
                            world.addEntity(keyEntity);
                            world.setBlockState(target, darknessEdgeBlock.getDefaultState());
                            break;
                        case DOOR:
                            world.setBlockState(target, doorBlock.getDefaultState());
                            break;
                        case CHALLENGE_DOOR:
                            break;
                    }
                } else if (block instanceof FormedBlock || block instanceof DecayBlock || block instanceof DecayEdgeBlock || block instanceof DecayingBlock
                        || block instanceof DarkAirBlock || block instanceof AirDecayBlock || block instanceof AirDecayEdgeBlock) {
                    world.setBlockState(target, darknessEdgeBlock.getDefaultState());
                }
            }
            world.setBlockState(getPos(), specialAirBlock.getDefaultState());
        }
    }
}
