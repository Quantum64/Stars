package co.q64.stars.capability.hub;

import co.q64.stars.capability.HubCapability;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.inject.Inject;

public class HubCapabilityProvider implements ICapabilityProvider {
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
}
