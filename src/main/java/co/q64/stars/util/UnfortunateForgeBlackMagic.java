package co.q64.stars.util;

import co.q64.stars.capability.GardenerCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UnfortunateForgeBlackMagic {
    @Deprecated
    @CapabilityInject(GardenerCapability.class)
    public static Capability<GardenerCapability> gardenerCapability;

    protected @Inject UnfortunateForgeBlackMagic() {}

    public Capability<GardenerCapability> getGardenerCapability() {
        return gardenerCapability;
    }
}
