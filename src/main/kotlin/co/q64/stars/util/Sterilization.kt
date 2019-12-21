package co.q64.stars.util

import kotlinx.serialization.*
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.internal.StringDescriptor
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundEvent
import net.minecraft.util.math.BlockPos

val cbor = Cbor()

@Serializer(ResourceLocation::class)
object ResourceLocationSerializer : KSerializer<ResourceLocation> {
    override val descriptor: SerialDescriptor = StringDescriptor.withName("ResourceLocation")
    override fun serialize(encoder: Encoder, obj: ResourceLocation) = encoder.encodeString(obj.toString())
    override fun deserialize(decoder: Decoder) = ResourceLocation(decoder.decodeString())
}

@Serializer(BlockPos::class)
object BlockPosSerializer : KSerializer<BlockPos> {
    override val descriptor: SerialDescriptor = StringDescriptor.withName("BlockPos")
    override fun serialize(encoder: Encoder, obj: BlockPos) = encoder.encodeLong(obj.toLong())
    override fun deserialize(decoder: Decoder): BlockPos = BlockPos.fromLong(decoder.decodeLong())
}

@Serializer(SoundEvent::class)
object SoundEventSerializer : KSerializer<SoundEvent> {
    override val descriptor: SerialDescriptor = StringDescriptor.withName("SoundEvent")
    override fun serialize(encoder: Encoder, obj: SoundEvent) = encoder.encodeString(obj.name.toString())
    override fun deserialize(decoder: Decoder): SoundEvent = SoundEvent(ResourceLocation(decoder.decodeString()))
}

fun <T> CompoundNBT.load(serializer: KSerializer<T>) =
        cbor.load(serializer, getByteArray("data"))

fun <T> KSerializer<T>.toNBT(data: T, tag: CompoundNBT = CompoundNBT()): CompoundNBT = tag.apply {
    putByteArray("data", cbor.dump(this@toNBT, data))
}