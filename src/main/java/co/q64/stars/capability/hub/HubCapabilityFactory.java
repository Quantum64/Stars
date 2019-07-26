package co.q64.stars.capability.hub;

import co.q64.stars.capability.HubCapability;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.concurrent.Callable;

@Singleton
public class HubCapabilityFactory implements Callable<HubCapability> {
    protected @Inject Provider<HubCapability> capabilityProvider;

    protected @Inject HubCapabilityFactory() {}

    public HubCapability call() throws Exception {
        return capabilityProvider.get();
    }
}
