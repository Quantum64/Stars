package co.q64.stars.util

import co.q64.stars.capability.Gardener
import co.q64.stars.capability.Hub
import kotlinx.serialization.KSerializer
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraft.nbt.INBT
import net.minecraft.util.Direction
import net.minecraft.world.World
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import net.minecraftforge.common.util.LazyOptional

object ForgeBlackMagic {
    @JvmField
    @CapabilityInject(Gardener::class)
    var gardener: Capability<Gardener>? = null

    @JvmField
    @CapabilityInject(Hub::class)
    var hub: Capability<Hub>? = null
}

val gardenerCapability by lazy { ForgeBlackMagic.gardener ?: error("Gardener capability not injected") }
val hubCapability by lazy { ForgeBlackMagic.hub ?: error("Hub capability not injected") }

var gardenerClient = Gardener()

val ServerPlayerEntity.gardener: Gardener
    get() {
        if (!getCapability(gardenerCapability).isPresent)
            error("Could not find capability!")
        return getCapability(gardenerCapability).orElse(Gardener())
    }

val World.hub: Hub
    get() {
        if (!getCapability(hubCapability).isPresent)
            error("Could not find capability!")
        return getCapability(hubCapability).orElse(Hub())
    }

class CapabilityBase<T : Any>(private val capability: Capability<T>, private val serializer: KSerializer<T>) : ICapabilitySerializable<CompoundNBT> {
    private var instance: T = capability.defaultInstance!!

    override fun <T : Any> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        if (cap != capability) return LazyOptional.empty()
        return LazyOptional.of { instance } as LazyOptional<T>
    }

    override fun deserializeNBT(nbt: CompoundNBT?) {
        val bytes = nbt?.getByteArray("data") ?: byteArrayOf()
        if (bytes.isEmpty()) return
        instance = cbor.load(serializer, bytes)
    }

    override fun serializeNBT(): CompoundNBT {
        return CompoundNBT().apply {
            putByteArray("data", cbor.dump(serializer, instance))
        }
    }
}

class NoopStorage<T> : Capability.IStorage<T> {
    override fun readNBT(capability: Capability<T>?, instance: T, side: Direction?, nbt: INBT?) =
            error("This capability does not support storage")

    override fun writeNBT(capability: Capability<T>?, instance: T, side: Direction?): INBT =
            error("This capability does not support storage")
}