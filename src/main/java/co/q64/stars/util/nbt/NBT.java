package co.q64.stars.util.nbt;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NBT {
    protected @Inject ExtendedTagFactory tagFactory;

    protected @Inject NBT() {}

    public ExtendedTag extend(INBT tag) {
        if (!(tag instanceof CompoundNBT)) {
            throw new IllegalStateException("Invalid NBT tag passed to NBT extender: " + tag.toString());
        }
        return extend((CompoundNBT) tag);
    }

    public ExtendedTag extend(CompoundNBT tag) {
        return tagFactory.create(tag);
    }

    public ExtendedTag create() {
        return tagFactory.create(new CompoundNBT());
    }
}
