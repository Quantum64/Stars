package co.q64.stars.entity

import co.q64.stars.util.load
import co.q64.stars.util.toNBT
import kotlinx.serialization.Serializable
import net.minecraft.entity.Entity
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.IPacket
import net.minecraft.network.datasync.DataSerializers
import net.minecraft.network.datasync.EntityDataManager
import net.minecraft.world.World
import net.minecraftforge.fml.network.NetworkHooks

class PickupEntity(world: World) : Entity(pickupEntityType, world) {
    var variant: Variant
        get() = Variant.values()[dataManager[variantProp]]
        set(value) = dataManager.set(variantProp, value.ordinal)
    var age: Int = 0
    var location: Long = 0L

    override fun tick() {
        age++
        super.tick()
    }

    override fun canTriggerWalking() = false
    override fun createSpawnPacket(): IPacket<*> = NetworkHooks.getEntitySpawningPacket(this)
    override fun isGlowing() = true
    override fun registerData() = dataManager.register(variantProp, 0)

    override fun readAdditional(compound: CompoundNBT) {
        compound.load(Data.serializer()).let {
            variant = it.variant
            location = it.location
        }
    }

    override fun writeAdditional(compound: CompoundNBT) {
        Data.serializer().toNBT(Data(variant, location), compound)
    }

    enum class Variant {
        HEART, KEY, STAR, ARROW, CHALLENGE
    }

    @Serializable
    private data class Data(val variant: Variant, val location: Long)

    companion object {
        private val variantProp = EntityDataManager.createKey(PickupEntity::class.java, DataSerializers.VARINT)
    }
}