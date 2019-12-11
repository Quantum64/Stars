package co.q64.stars.block

import net.minecraft.block.Block
import net.minecraft.block.material.Material

abstract class BaseBlock(id: String, properties: Properties = Properties.create(Material.IRON)) : Block(properties) {
    init {
        setRegistryName(id)
    }
}