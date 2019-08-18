package co.q64.stars.item;

import co.q64.stars.group.StarsGroup;
import co.q64.stars.type.forming.BlueFormingBlockType;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BlueSeedItem extends SeedItem {
    @Inject
    protected BlueSeedItem(BlueFormingBlockType type, StarsGroup group) {
        super("blue_seed", type, group);
    }

    @Singleton
    public static class BlueSeedItemRobust extends BaseItem {
        @Inject
        protected BlueSeedItemRobust(StarsGroup group) {
            super("blue_seed_robust", group);
        }
    }
}
