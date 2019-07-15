package co.q64.stars.server;

import co.q64.stars.CommonProxy;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ServerProxy extends CommonProxy {
    protected @Inject ServerProxy() {}

    @Override
    public void initialize() {
        super.initialize();
    }
}
