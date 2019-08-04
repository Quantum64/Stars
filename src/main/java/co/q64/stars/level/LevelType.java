package co.q64.stars.level;

import net.minecraft.util.IStringSerializable;

public enum LevelType implements IStringSerializable {
    RED, BLUE;

    public String getName() {
        return name().toLowerCase();
    }
}
