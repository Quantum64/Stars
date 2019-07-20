package co.q64.stars.item;

import co.q64.stars.group.StarsGroup;
import co.q64.stars.type.forming.PurpleFormingBlockType;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PurpleSeedItem extends SeedItem {
    @Inject
    protected PurpleSeedItem(PurpleFormingBlockType type, StarsGroup group) {
        super("purple_seed", type, group);
    }
}
