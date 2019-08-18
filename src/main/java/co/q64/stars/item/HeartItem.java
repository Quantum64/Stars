package co.q64.stars.item;

import co.q64.stars.group.StarsGroup;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HeartItem extends BaseItem {
    @Inject
    protected HeartItem() {
        super("heart");
    }
}
