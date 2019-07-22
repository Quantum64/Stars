package co.q64.stars.server;

import co.q64.stars.util.ClientNetHandler;
import dagger.Binds;
import dagger.Module;

@Module
public interface ServerModule {
    // @formatter:off
    @Binds ClientNetHandler bindClientEffects(MockClientNetHandler mockClientEffects);
    // @formatter:on
}
