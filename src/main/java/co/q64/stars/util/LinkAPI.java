package co.q64.stars.util;

import co.q64.stars.item.TrophyBlockItem;
import lombok.Getter;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LinkAPI {
    protected @Inject @Getter TrophyBlockItem trophyBlockItem;

    protected @Inject LinkAPI() {}

}
