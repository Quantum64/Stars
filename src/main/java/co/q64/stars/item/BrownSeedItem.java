package co.q64.stars.item;

import co.q64.stars.group.StarsGroup;
import co.q64.stars.type.forming.BrownFormingBlockType;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BrownSeedItem extends SeedItem {
    @Inject
    protected BrownSeedItem(BrownFormingBlockType type, StarsGroup group) {
        super("brown_seed", type, group);
    }
}
