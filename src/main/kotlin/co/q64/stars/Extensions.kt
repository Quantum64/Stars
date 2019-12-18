package co.q64.stars

import net.minecraft.util.Direction
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.Vec3d
import net.minecraftforge.registries.IForgeRegistryEntry

var <T> IForgeRegistryEntry<T>.id: String
    get() = registryName?.path ?: "null"
    set(value) {
        registryName = value.id
    }

val String.id: ResourceLocation
    get() = ResourceLocation(modId, this)

val directions = Direction.values()
val one = Vec3d(1.0, 1.0, 1.0)