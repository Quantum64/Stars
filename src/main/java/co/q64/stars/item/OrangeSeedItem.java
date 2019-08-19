package co.q64.stars.item;

import co.q64.stars.group.StarsGroup;
import co.q64.stars.type.forming.BlueFormingBlockType;
import co.q64.stars.type.forming.OrangeFormingBlockType;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class OrangeSeedItem extends SeedItem {
    @Inject
    protected OrangeSeedItem(OrangeFormingBlockType type, StarsGroup group) {
        super("orange_seed", type, group);
    }

    @Singleton
    public static class OrangeSeedItemRobust extends RobustSeed {
        @Inject
        protected OrangeSeedItemRobust(StarsGroup group) {
            super("orange_seed_robust", group);
        }
    }
}
