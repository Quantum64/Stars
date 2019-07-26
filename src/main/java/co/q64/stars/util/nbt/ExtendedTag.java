package co.q64.stars.util.nbt;

import com.google.auto.factory.AutoFactory;
import lombok.experimental.Delegate;
import net.minecraft.nbt.CompoundNBT;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@AutoFactory
public class ExtendedTag {
    private @Delegate CompoundNBT tag; // TODO delegate manually... this is a sketchy lombok feature

    protected ExtendedTag(CompoundNBT tag) {
        this.tag = tag;
    }

    public CompoundNBT compound() {
        return tag;
    }

    public String getString(String key, String value) {
        return getIfContains(key, this::getString, () -> value);
    }

    public String getString(String key, Supplier<String> value) {
        return getIfContains(key, this::getString, value);
    }

    public void ifString(String key, Consumer<String> action) {
        runIfContains(key, this::getString, action);
    }

    public int getInt(String key, int value) {
        return getIfContains(key, this::getInt, () -> value);
    }

    public int getInt(String key, Supplier<Integer> value) {
        return getIfContains(key, this::getInt, value);
    }

    public void ifInt(String key, Consumer<Integer> action) {
        runIfContains(key, this::getInt, action);
    }

    public long getLong(String key, long value) {
        return getIfContains(key, this::getLong, () -> value);
    }

    public long getLong(String key, Supplier<Long> value) {
        return getIfContains(key, this::getLong, value);
    }

    public void ifLong(String key, Consumer<Long> action) {
        runIfContains(key, this::getLong, action);
    }

    public float getFloat(String key, float value) {
        return getIfContains(key, this::getFloat, () -> value);
    }

    public float getFloat(String key, Supplier<Float> value) {
        return getIfContains(key, this::getFloat, value);
    }

    public void ifFloat(String key, Consumer<Float> action) {
        runIfContains(key, this::getFloat, action);
    }

    public double getDouble(String key, double value) {
        return getIfContains(key, this::getDouble, () -> value);
    }

    public double getDouble(String key, Supplier<Double> value) {
        return getIfContains(key, this::getDouble, value);
    }

    public void ifDouble(String key, Consumer<Double> action) {
        runIfContains(key, this::getDouble, action);
    }

    public short getShort(String key, short value) {
        return getIfContains(key, this::getShort, () -> value);
    }

    public short getShort(String key, Supplier<Short> value) {
        return getIfContains(key, this::getShort, value);
    }

    public void ifShort(String key, Consumer<Short> action) {
        runIfContains(key, this::getShort, action);
    }

    public byte getByte(String key, byte value) {
        return getIfContains(key, this::getByte, () -> value);
    }

    public byte getByte(String key, Supplier<Byte> value) {
        return getIfContains(key, this::getByte, value);
    }

    public void ifByte(String key, Consumer<Byte> action) {
        runIfContains(key, this::getByte, action);
    }

    private <T> T getIfContains(String key, Function<String, T> actual, Supplier<T> missing) {
        return contains(key) ? actual.apply(key) : missing.get();
    }

    private <T> void runIfContains(String key, Function<String, T> supplier, Consumer<T> action) {
        if (contains(key)) {
            action.accept(supplier.apply(key));
        }
    }
}
