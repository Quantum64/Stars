package co.q64.stars.item;

import co.q64.stars.group.StarsGroup;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SeedPouch extends BaseItem {
    @Inject
    protected SeedPouch(StarsGroup group) {
        super("seed_pouch", group);
    }
}
