package co.q64.stars.item;

import co.q64.stars.group.StarsGroup;
import co.q64.stars.type.forming.YellowFormingBlockType;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class YellowSeedItem extends SeedItem {
    @Inject
    protected YellowSeedItem(YellowFormingBlockType type, StarsGroup group) {
        super("yellow_seed", type, group);
    }

    @Singleton
    public static class YellowSeedItemRobust extends BaseItem {
        @Inject
        protected YellowSeedItemRobust(StarsGroup group) {
            super("yellow_seed_robust", group);
        }
    }
}
