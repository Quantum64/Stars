package co.q64.stars.item;

import co.q64.stars.group.StarsGroup;
import co.q64.stars.type.forming.GreenFormingBlockType;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GreenSeedItem extends SeedItem {
    @Inject
    protected GreenSeedItem(GreenFormingBlockType type, StarsGroup group) {
        super("green_seed", type, group);
    }

    @Singleton
    public static class GreenSeedItemRobust extends RobustSeed {
        @Inject
        protected GreenSeedItemRobust(StarsGroup group) {
            super("green_seed_robust", group);
        }
    }
}
