package co.q64.stars.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.minecraft.util.math.BlockPos;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SpawnpointManager {
    public static final int SPREAD_DISTANCE = 2000;
    public static final int SPAWN_HEIGHT = 100;

    protected @Inject SpawnpointManager() {}

    public BlockPos getSpawnpoint(int index) {
        Point point = spiral(index);
        int x = point.getX() * SPREAD_DISTANCE;
        int y = point.getY() * SPREAD_DISTANCE;
        return new BlockPos(x, SPAWN_HEIGHT, y);
    }

    private static Point spiral(int index) {
        index = index + 1;
        double k = Math.ceil((Math.sqrt(index) - 1) / 2);
        double t = 2 * k + 1;
        double m = t * t;
        t = t - 1;
        double x, y;
        if (index >= m - t) {
            x = k - (m - index);
            y = -k;
        } else {
            m = m - t;
            if (index >= m - t) {
                x = -k;
                y = -k + (m - index);
            } else {
                m = m - t;
                if (index >= m - t) {
                    x = -k + (m - index);
                    y = k;
                } else {
                    x = k;
                    y = k - (m - index - t);
                }
            }
        }
        return new Point((int) x, (int) y);
    }

    @Data
    @AllArgsConstructor
    private static class Point {
        int x, y;
    }
}
