package co.q64.stars.capability.gardener;

import co.q64.stars.capability.GardenerCapability;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.concurrent.Callable;

@Singleton
public class GardenerCapabilityFactory implements Callable<GardenerCapability> {
    protected @Inject Provider<GardenerCapability> capabilityProvider;

    protected @Inject GardenerCapabilityFactory() {}

    public GardenerCapability call() throws Exception {
        return capabilityProvider.get();
    }
}
