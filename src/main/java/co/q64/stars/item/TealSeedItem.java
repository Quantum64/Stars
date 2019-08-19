package co.q64.stars.item;

import co.q64.stars.group.StarsGroup;
import co.q64.stars.type.forming.TealFormingBlockType;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TealSeedItem extends SeedItem {
    @Inject
    protected TealSeedItem(TealFormingBlockType type, StarsGroup group) {
        super("teal_seed", type, group);
    }
    
    @Singleton
    public static class TealSeedItemRobust extends RobustSeed {
        @Inject
        protected TealSeedItemRobust(StarsGroup group) {
            super("teal_seed_robust", group);
        }
    }
}
