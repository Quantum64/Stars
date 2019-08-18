package co.q64.stars.jei;

import co.q64.stars.Stars;
import co.q64.stars.item.TrophyBlockItem;
import co.q64.stars.util.LinkAPI;
import dagger.Module;
import dagger.Provides;

@Module
public interface JEIPluginModule {
    // @formatter:off
    static @Provides LinkAPI provideLinkAPI() { return Stars.getLinkAPI(); }
    static @Provides TrophyBlockItem provideTrophyBlockItem(LinkAPI linkAPI) { return linkAPI.getTrophyBlockItem(); }
    // @formatter:on
}
