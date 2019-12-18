package co.q64.stars.util

import net.minecraft.nbt.CompoundNBT

fun CompoundNBT.getString(key: String, default: String) = getString(key) { default }
fun CompoundNBT.getString(key: String, default: () -> String): String = orGet(key, ::getString, default)
fun CompoundNBT.ifString(key: String, action: (String) -> Unit) = ifPresent(key, ::getString, action)
fun CompoundNBT.getInt(key: String, default: Int) = getInt(key) { default }
fun CompoundNBT.getInt(key: String, default: () -> Int) = orGet(key, ::getInt, default)
fun CompoundNBT.ifInt(key: String, action: (Int) -> Unit) = ifPresent(key, ::getInt, action)
fun CompoundNBT.getLong(key: String, default: Long) = getLong(key) { default }
fun CompoundNBT.getLong(key: String, default: () -> Long) = orGet(key, ::getLong, default)
fun CompoundNBT.ifLong(key: String, action: (Long) -> Unit) = ifPresent(key, ::getLong, action)
fun CompoundNBT.getDouble(key: String, default: Double) = getDouble(key) { default }
fun CompoundNBT.getDouble(key: String, default: () -> Double) = orGet(key, ::getDouble, default)
fun CompoundNBT.ifDouble(key: String, action: (Double) -> Unit) = ifPresent(key, ::getDouble, action)
fun CompoundNBT.getFloat(key: String, default: Float) = getFloat(key) { default }
fun CompoundNBT.getFloat(key: String, default: () -> Float) = orGet(key, ::getFloat, default)
fun CompoundNBT.ifFloat(key: String, action: (Float) -> Unit) = ifPresent(key, ::getFloat, action)
fun CompoundNBT.getShort(key: String, default: Short) = getShort(key) { default }
fun CompoundNBT.getShort(key: String, default: () -> Short) = orGet(key, ::getShort, default)
fun CompoundNBT.ifShort(key: String, action: (Short) -> Unit) = ifPresent(key, ::getShort, action)
fun CompoundNBT.getByte(key: String, default: Byte) = getByte(key) { default }
fun CompoundNBT.getByte(key: String, default: () -> Byte) = orGet(key, ::getByte, default)
fun CompoundNBT.ifByte(key: String, action: (Byte) -> Unit) = ifPresent(key, ::getByte, action)
fun CompoundNBT.getCompound(key: String, default: CompoundNBT) = getCompound(key) { default }
fun CompoundNBT.getCompound(key: String, default: () -> CompoundNBT): CompoundNBT = orGet(key, ::getCompound, default)
fun CompoundNBT.ifCompound(key: String, action: (CompoundNBT) -> Unit) = ifPresent(key, ::getCompound, action)

fun <T> CompoundNBT.orGet(key: String, actual: (String) -> T, default: () -> T): T =
        if (contains(key)) actual(key) else default()

fun <T> CompoundNBT.ifPresent(key: String, supplier: (String) -> T, action: (T) -> Unit) {
    if (contains(key)) {
        action(supplier(key))
    }
}