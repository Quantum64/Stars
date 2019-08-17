package co.q64.stars.util;

import co.q64.stars.block.DecayingBlock;
import co.q64.stars.block.FormedBlock;
import co.q64.stars.block.GreenFruitBlock;
import co.q64.stars.block.RedPrimedBlock;
import co.q64.stars.block.SeedBlock;
import co.q64.stars.block.SeedBlock.SeedBlockHard;
import co.q64.stars.level.LevelType;
import co.q64.stars.tile.DecayingTile;
import co.q64.stars.tile.SeedTile;
import co.q64.stars.type.FleetingStage;
import co.q64.stars.type.FormingBlockType;
import co.q64.stars.type.FormingBlockTypes;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class SeedManager {
    protected @Inject FormingBlockTypes types;
    protected @Inject SeedBlock seedBlock;
    protected @Inject SeedBlockHard seedBlockHard;
    protected @Inject Capabilities capabilities;

    protected @Inject SeedManager() {}

    public boolean tryGrow(PlayerEntity player, BlockPos pos, FormingBlockType type) {
        World world = player.getEntityWorld();
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
            capabilities.gardener(player, gardener -> {
                world.setBlockState(pos, gardener.getLevelType() == LevelType.PURPLE ? seedBlockHard.getDefaultState() : seedBlock.getDefaultState(), 3);
                if (world.isRemote) {
                    Optional.ofNullable((SeedTile) world.getTileEntity(pos)).ifPresent(tile -> {
                        tile.setFormingBlockType(types.get(block));
                        tile.setPrimed(primed);
                        tile.setSeedType(type);
                        tile.setCalculated(true);
                    });
                } else {
                    Optional.ofNullable((SeedTile) world.getTileEntity(pos)).ifPresent(tile -> {
                        tile.setFormingBlockType(types.get(block));
                        tile.setPrimed(primed);
                        tile.setSeedType(type);
                        tile.setCalculated(true);
                        if (gardener.getFleetingStage() == FleetingStage.LIGHT) {
                            if (gardener.getLevelType() == LevelType.WHITE) {
                                tile.setMultiplier(1.5);
                            } else if (gardener.getLevelType() == LevelType.ORANGE) {
                                tile.setMultiplier(0.5);
                            }
                        }
                    });
                }
            });
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
