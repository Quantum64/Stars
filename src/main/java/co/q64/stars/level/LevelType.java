package co.q64.stars.level;

import net.minecraft.util.IStringSerializable;

public enum LevelType implements IStringSerializable {
    BLUE,   // High jump
    CYAN,
    GREEN,  // Different vine behavior
    ORANGE, // Fast decay, fast forming
    PINK,
    PURPLE, // No digging
    RED,    // Annoying block breaking
    TEAL,   // Solid decay
    WHITE,  // Slow decay, slow forming
    YELLOW; // Low jump

    public String getName() {
        return name().toLowerCase();
    }
}
