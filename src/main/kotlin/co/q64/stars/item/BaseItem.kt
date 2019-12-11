package co.q64.stars.item

import net.minecraft.item.Item
import net.minecraft.item.ItemGroup

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
abstract class BaseItem(id: String, group: ItemGroup? = null, val hideJEI: Boolean = false) : Item(Properties().group(group)) {
    init {
        setRegistryName(id)
    }
}