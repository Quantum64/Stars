package co.q64.stars.block

import co.q64.stars.id
import net.minecraft.block.Block
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material

val earth = Block.Properties.create(Material.EARTH).sound(SoundType.GROUND).hardnessAndResistance(0f, 0f)
val hard = Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(-1f, 3600000f)

abstract class BaseBlock(blockId: String, properties: Properties = Properties.create(Material.IRON)) : Block(properties) {
    init {
        id = blockId
    }
}

interface HardBlock {}