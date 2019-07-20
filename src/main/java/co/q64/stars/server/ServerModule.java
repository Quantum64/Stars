package co.q64.stars.server;

import co.q64.stars.util.ClientEffects;
import dagger.Binds;
import dagger.Module;

@Module
public interface ServerModule {
    // @formatter:off
    @Binds ClientEffects bindClientEffects(MockClientEffects mockClientEffects);
    // @formatter:on
}
