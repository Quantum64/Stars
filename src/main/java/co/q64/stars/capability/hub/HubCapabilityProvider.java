package co.q64.stars.capability.hub;

import co.q64.stars.capability.HubCapability;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.inject.Inject;

public class HubCapabilityProvider implements ICapabilitySerializable<INBT> {
    private Capability<HubCapability> hubCapability;
    private HubCapability instance;

    @Inject
    protected HubCapabilityProvider(Capability<HubCapability> hubCapability) {
        this.hubCapability = hubCapability;
        this.instance = hubCapability.getDefaultInstance();
    }

    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap != hubCapability) {
            return LazyOptional.empty();
        }
        return (LazyOptional<T>) LazyOptional.of(() -> instance);
    }

    public INBT serializeNBT() {
        return hubCapability.getStorage().writeNBT(hubCapability, instance, null);
    }

    public void deserializeNBT(INBT nbt) {
        hubCapability.getStorage().readNBT(hubCapability, instance, null, nbt);
    }
}
