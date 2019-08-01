package co.q64.stars.state;

import net.minecraft.client.renderer.model.ModelRotation;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface DarknessState {
    public static final Direction[] DIRECTIONS = Direction.values();

    public static BooleanProperty CONNECT_UP = BooleanProperty.create("connect_up");
    public static BooleanProperty CONNECT_DOWN = BooleanProperty.create("connect_down");
    public static BooleanProperty CONNECT_NORTH = BooleanProperty.create("connect_north");
    public static BooleanProperty CONNECT_SOUTH = BooleanProperty.create("connect_south");
    public static BooleanProperty CONNECT_EAST = BooleanProperty.create("connect_east");
    public static BooleanProperty CONNECT_WEST = BooleanProperty.create("connect_west");

    public static BooleanProperty get(Direction direction) {
        switch (direction) {
            case DOWN:
                return CONNECT_DOWN;
            case UP:
                return CONNECT_UP;
            case NORTH:
                return CONNECT_NORTH;
            case SOUTH:
                return CONNECT_SOUTH;
            case WEST:
                return CONNECT_WEST;
            default:
                return CONNECT_EAST;
        }
    }
}
