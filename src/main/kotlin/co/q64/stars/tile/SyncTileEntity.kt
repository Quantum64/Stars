package co.q64.stars.tile

import co.q64.stars.util.load
import kotlinx.serialization.KSerializer
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.NetworkManager
import net.minecraft.network.play.server.SUpdateTileEntityPacket
import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityType
import kotlin.reflect.KMutableProperty0

abstract class SyncTileEntity(type: TileEntityType<*>) : TileEntity(type) {
    private val synced = mutableListOf<SyncedProperty<*>>()

    fun <T> sync(prop: KMutableProperty0<T>, serializer: KSerializer<T>) {
        synced.add(SyncedProperty(prop, serializer))
    }

    final override fun getUpdateTag(): CompoundNBT = super.getUpdateTag().apply { write(this) }
    final override fun handleUpdateTag(tag: CompoundNBT) = run { super.handleUpdateTag(tag) }.run { read(tag) }
    final override fun onDataPacket(net: NetworkManager, packet: SUpdateTileEntityPacket) = read(packet.nbtCompound)

    final override fun getUpdatePacket(): SUpdateTileEntityPacket? {
        with(CompoundNBT()) {
            write(this)
            return SUpdateTileEntityPacket(getPos(), 1, this)
        }
    }

    final override fun read(compound: CompoundNBT) {
        super.read(compound)
        synced.forEach {
            val thing = compound.getCompound(it.prop.name).load(it.serializer)
            (it.prop as KMutableProperty0<Any?>).set(thing)
        }
    }

    final override fun write(compound: CompoundNBT): CompoundNBT {
        return super.write(compound)
    }

    private data class SyncedProperty<T>(val prop: KMutableProperty0<T>, val serializer: KSerializer<T>)
}