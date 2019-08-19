package co.q64.stars.item;

import co.q64.stars.group.StarsGroup;
import co.q64.stars.type.forming.PinkFormingBlockType;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PinkSeedItem extends SeedItem {
    @Inject
    protected PinkSeedItem(PinkFormingBlockType type, StarsGroup group) {
        super("pink_seed", type, group);
    }

    @Singleton
    public static class PinkSeedItemRobust extends RobustSeed {
        @Inject
        protected PinkSeedItemRobust(StarsGroup group) {
            super("pink_seed_robust", group);
        }
    }
}
