package co.q64.stars.item;

import co.q64.stars.group.StarsGroup;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SeedPouchItem extends BaseItem {
    @Inject
    protected SeedPouchItem(StarsGroup group) {
        super("seed_pouch", group);
    }
}
