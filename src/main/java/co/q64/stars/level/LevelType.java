package co.q64.stars.level;

import net.minecraft.util.IStringSerializable;

public enum LevelType implements IStringSerializable {
    BLUE, CYAN, GREEN, ORANGE, PINK, PURPLE, RED, TEAL, WHITE, YELLOW;

    public String getName() {
        return name().toLowerCase();
    }
}
