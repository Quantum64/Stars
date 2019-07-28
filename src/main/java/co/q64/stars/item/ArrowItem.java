package co.q64.stars.item;

import co.q64.stars.group.StarsGroup;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ArrowItem extends BaseItem {
    @Inject
    protected ArrowItem(StarsGroup group) {
        super("arrow", group);
    }
}
