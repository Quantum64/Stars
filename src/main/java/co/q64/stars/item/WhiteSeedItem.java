package co.q64.stars.item;

import co.q64.stars.group.StarsGroup;
import co.q64.stars.type.forming.WhiteFormingBlockType;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WhiteSeedItem extends SeedItem {
    @Inject
    protected WhiteSeedItem(WhiteFormingBlockType type, StarsGroup group) {
        super("white_seed", type, group);
    }

    @Singleton
    public static class WhiteSeedItemRobust extends RobustSeed {
        @Inject
        protected WhiteSeedItemRobust(StarsGroup group) {
            super("white_seed_robust", group);
        }
    }
}
