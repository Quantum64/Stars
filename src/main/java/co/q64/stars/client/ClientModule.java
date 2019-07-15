package co.q64.stars.client;

import co.q64.stars.client.listener.ClientRegistryListener;
import co.q64.stars.listener.Listener;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoSet;

@Module
public interface ClientModule {
    // @formatter:off
    @Binds @IntoSet Listener bindClientRegistryListener(ClientRegistryListener clientRegistryListener);
    //@Binds @IntoSet Listener bindClientInitializationListener(ClientInitializationListener clientInitializationListener );
    // @formatter:on
}
