package co.q64.stars.level;

import net.minecraft.util.IStringSerializable;

public enum LevelType implements IStringSerializable {
    BLUE,   // High jump
    CYAN,   // Very fast decay, spreading ice
    GREEN,  // Different vine behavior
    ORANGE, // Fast decay, fast forming
    PINK,   // Lower seed visibility, easier harvesting
    PURPLE, // No digging
    RED,    // Annoying block breaking
    TEAL,   // Solid decay
    WHITE,  // Slow decay, slow forming
    YELLOW; // Low jump

    public String getName() {
        return name().toLowerCase();
    }
}
