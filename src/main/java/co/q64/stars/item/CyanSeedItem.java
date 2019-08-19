package co.q64.stars.item;

import co.q64.stars.group.StarsGroup;
import co.q64.stars.type.forming.CyanFormingBlockType;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CyanSeedItem extends SeedItem {
    @Inject
    protected CyanSeedItem(CyanFormingBlockType type, StarsGroup group) {
        super("cyan_seed", type, group);
    }

    @Singleton
    public static class CyanSeedItemRobust extends RobustSeed {
        @Inject
        protected CyanSeedItemRobust(StarsGroup group) {
            super("cyan_seed_robust", group);
        }
    }
}
