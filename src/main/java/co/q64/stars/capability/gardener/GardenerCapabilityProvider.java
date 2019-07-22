package co.q64.stars.capability.gardener;

import co.q64.stars.capability.GardenerCapability;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.inject.Inject;

public class GardenerCapabilityProvider implements ICapabilityProvider {
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
}
