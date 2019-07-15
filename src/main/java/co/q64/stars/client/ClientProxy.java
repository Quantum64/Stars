package co.q64.stars.client;

import co.q64.stars.CommonProxy;
import co.q64.stars.client.loader.ClientLoader;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ClientProxy extends CommonProxy {
    protected @Inject ClientLoader clientLoader;

    protected @Inject ClientProxy() {}

    @Override
    public void initialize() {
        super.initialize();
        clientLoader.load();
    }
}
