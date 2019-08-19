package co.q64.stars.item;

import co.q64.stars.group.StarsGroup;
import co.q64.stars.type.forming.RedFormingBlockType;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RedSeedItem extends SeedItem {
    @Inject
    protected RedSeedItem(RedFormingBlockType type, StarsGroup group) {
        super("red_seed", type, group);
    }

    @Singleton
    public static class RedSeedItemRobust extends RobustSeed {
        @Inject
        protected RedSeedItemRobust(StarsGroup group) {
            super("red_seed_robust", group);
        }
    }
}
