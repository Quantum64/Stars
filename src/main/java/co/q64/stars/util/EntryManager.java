package co.q64.stars.util;

import co.q64.stars.block.DecayEdgeBlock;
import co.q64.stars.block.OrangeFormedBlock;
import co.q64.stars.dimension.Dimensions;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EntryManager {
    private static final int SPREAD_DISTANCE = 1000;

    protected @Inject Dimensions dimensions;
    protected @Inject OrangeFormedBlock orangeFormedBlock;
    protected @Inject DecayEdgeBlock decayEdgeBlock;

    private int index = 0;

    protected @Inject EntryManager() {}

    public void enter(ServerPlayerEntity player) {
        BlockPos spawnpoint = getNext();
        ServerWorld world = DimensionManager.getWorld(player.getServer(), dimensions.getAdventureDimensionType(), false, true);
        setupSpawnpoint(world, spawnpoint);
        player.teleport(world, spawnpoint.getX() + 0.5, spawnpoint.getY(), spawnpoint.getZ() + 0.5, player.rotationYaw, player.rotationPitch);
    }

    // TODO set the initial decay according to level
    private void setupSpawnpoint(World world, BlockPos pos) {
        for (int y = pos.getY() - 8; y < pos.getY(); y++) {
            for (int x = pos.getX() - 2; x <= pos.getX() + 2; x++) {
                for (int z = pos.getZ() - 2; z <= pos.getZ() + 2; z++) {
                    world.setBlockState(new BlockPos(x, y, z), orangeFormedBlock.getDefaultState());
                }
            }
        }
        world.setBlockState(new BlockPos(pos.getX(), pos.getY() - 8, pos.getZ()), decayEdgeBlock.getDefaultState());
    }

    public BlockPos getNext() {
        double sidelen = Math.floor(Math.sqrt(index));
        double layer = Math.floor((sidelen + 1) / 2.0);
        double offset = offset = index - sidelen * sidelen;
        int segment = (int) (Math.floor(offset / (sidelen + 1)) + 0.5);
        double offset2 = offset - 4 * segment + 1 - layer;
        double x = 0, y = 0;
        switch (segment) {
            case 0:
                x = layer;
                y = offset2;
            case 1:
                x = -offset2;
                y = layer;
            case 2:
                x = -layer;
                y = -offset2;
            case 3:
                x = offset2;
                y = -layer;
        }
        x *= SPREAD_DISTANCE;
        y *= SPREAD_DISTANCE;
        index++;
        return new BlockPos(x, 64, y);
    }
}
