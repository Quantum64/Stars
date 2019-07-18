package co.q64.stars.util;

import net.minecraft.util.math.BlockPos;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SpawnpointManager {
    private static final int SPREAD_DISTANCE = 1000;

    private int index = 0;

    protected @Inject SpawnpointManager() {}

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
