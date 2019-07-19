package co.q64.stars.server;

import co.q64.stars.client.listener.ClientPlayerListener;
import co.q64.stars.client.listener.ClientRegistryListener;
import co.q64.stars.listener.Listener;
import co.q64.stars.util.ClientEffects;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoSet;

@Module
public interface ServerModule {
    // @formatter:off
    @Binds ClientEffects bindClientEffects(MockClientEffects mockClientEffects);
    // @formatter:on
}
