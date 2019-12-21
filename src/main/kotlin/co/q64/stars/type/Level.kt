package co.q64.stars.type

import net.minecraft.util.IStringSerializable

enum class Level(val id: String) : IStringSerializable {
    RED("red"),
    PURPLE("purple"),
    WHITE("white"),
    ORANGE("orange"),
    TEAL("teal"),
    CYAN("cyan");

    override fun getName(): String = id
}
