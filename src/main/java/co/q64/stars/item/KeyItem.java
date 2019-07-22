package co.q64.stars.item;

import co.q64.stars.group.StarsGroup;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class KeyItem extends BaseItem {
    @Inject
    protected KeyItem(StarsGroup group) {
        super("key", group);
    }
}
