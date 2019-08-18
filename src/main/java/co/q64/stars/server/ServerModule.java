package co.q64.stars.server;

import co.q64.stars.group.StarsGroup;
import co.q64.stars.net.ClientNetHandler;
import co.q64.stars.qualifier.Qualifiers.TrophyItemProperties;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import net.minecraft.item.Item.Properties;

@Module
public interface ServerModule {
    // @formatter:off
    @Binds ClientNetHandler bindClientEffects(MockClientNetHandler mockClientEffects);

    static @Provides @TrophyItemProperties Properties provideTrophyItemProperties(StarsGroup starsGroup) { return new Properties().group(starsGroup); }
    // @formatter:on
}
