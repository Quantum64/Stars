package co.q64.stars.tile

import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.NetworkManager
import net.minecraft.network.play.server.SUpdateTileEntityPacket
import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityType

abstract class SyncTileEntity(type: TileEntityType<*>) : TileEntity(type) {
    override fun getUpdateTag(): CompoundNBT = super.getUpdateTag().apply { write(this) }
    override fun handleUpdateTag(tag: CompoundNBT) = run { super.handleUpdateTag(tag) }.run { read(tag) }
    override fun onDataPacket(net: NetworkManager, packet: SUpdateTileEntityPacket) = read(packet.nbtCompound)

    override fun getUpdatePacket(): SUpdateTileEntityPacket? {
        with(CompoundNBT()) {
            write(this)
            return SUpdateTileEntityPacket(getPos(), 1, this)
        }
    }
}