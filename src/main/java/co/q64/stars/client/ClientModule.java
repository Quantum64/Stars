package co.q64.stars.client;

import co.q64.stars.client.listener.ClientPlayerListener;
import co.q64.stars.client.listener.ClientRegistryListener;
import co.q64.stars.client.util.ClientNetHandlerImpl;
import co.q64.stars.listener.Listener;
import co.q64.stars.net.ClientNetHandler;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoSet;

@Module
public interface ClientModule {
    // @formatter:off
    @Binds @IntoSet Listener bindClientRegistryListener(ClientRegistryListener clientRegistryListener);
    @Binds @IntoSet Listener bindClientPlayerListener(ClientPlayerListener clientPlayerListener);

    @Binds ClientNetHandler bindClientEffects(ClientNetHandlerImpl clientEffects);
    // @formatter:on
}
