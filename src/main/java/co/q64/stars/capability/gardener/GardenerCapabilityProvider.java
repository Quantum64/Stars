package co.q64.stars.capability.gardener;

import co.q64.stars.capability.GardenerCapability;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.inject.Inject;

public class GardenerCapabilityProvider implements ICapabilitySerializable<INBT> {
    private Capability<GardenerCapability> gardenerCapability;
    private GardenerCapability instance;

    @Inject
    protected GardenerCapabilityProvider(Capability<GardenerCapability> gardenerCapability) {
        this.gardenerCapability = gardenerCapability;
        this.instance = gardenerCapability.getDefaultInstance();
    }

    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap != gardenerCapability) {
            return LazyOptional.empty();
        }
        return (LazyOptional<T>) LazyOptional.of(() -> instance);
    }

    public INBT serializeNBT() {
        return gardenerCapability.getStorage().writeNBT(gardenerCapability, instance, null);
    }

    public void deserializeNBT(INBT nbt) {
        gardenerCapability.getStorage().readNBT(gardenerCapability, instance, null, nbt);
    }
}
