package co.q64.stars.client;

import co.q64.stars.client.listener.ClientPlayerListener;
import co.q64.stars.client.listener.ClientRegistryListener;
import co.q64.stars.client.util.ClientNetHandlerImpl;
import co.q64.stars.listener.Listener;
import co.q64.stars.net.ClientNetHandler;
import co.q64.stars.qualifier.SoundQualifiers.AmbientDark;
import co.q64.stars.qualifier.SoundQualifiers.Misc;
import co.q64.stars.util.Identifiers;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import net.minecraft.util.SoundEvent;

import javax.inject.Singleton;

@Module
public interface ClientModule {
    // @formatter:off
    @Binds @IntoSet Listener bindClientRegistryListener(ClientRegistryListener clientRegistryListener);
    @Binds @IntoSet Listener bindClientPlayerListener(ClientPlayerListener clientPlayerListener);

    @Binds ClientNetHandler bindClientEffects(ClientNetHandlerImpl clientEffects);

    static @Provides @Singleton @AmbientDark SoundEvent provideAmbientDarkSound(Identifiers identifiers) { return new SoundEvent(identifiers.get("ambient_dark")); }

    @Binds @IntoSet @Misc SoundEvent bindAmbientDarkSound(@AmbientDark SoundEvent event);
    // @formatter:on
}
