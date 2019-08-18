package co.q64.stars.server;

import co.q64.stars.CommonProxy;
import co.q64.stars.util.LinkAPI;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ServerProxy extends CommonProxy {
    protected @Inject ServerProxy() {}

    @Override
    public LinkAPI initialize() {
        return super.initialize();
    }
}
