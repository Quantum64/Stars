package co.q64.stars.item;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ArrowItem extends BaseItem {
    @Inject
    protected ArrowItem() {
        super("arrow");
        setHideJEI(true);
    }
}
