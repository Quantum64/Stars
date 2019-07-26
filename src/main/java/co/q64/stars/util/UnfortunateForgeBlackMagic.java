package co.q64.stars.util;

import co.q64.stars.capability.GardenerCapability;
import co.q64.stars.capability.HubCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UnfortunateForgeBlackMagic {
    @CapabilityInject(GardenerCapability.class)
    private static Capability<GardenerCapability> gardenerCapability;

    @CapabilityInject(HubCapability.class)
    private static Capability<HubCapability> hubCapability;

    protected @Inject UnfortunateForgeBlackMagic() {}

    public Capability<GardenerCapability> getGardenerCapability() {
        return gardenerCapability;
    }

    public Capability<HubCapability> getHubCapability() {
        return hubCapability;
    }
}
