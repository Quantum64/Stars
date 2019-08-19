package co.q64.stars.item;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class KeyItem extends BaseItem {
    @Inject
    protected KeyItem() {
        super("key");
        setHideJEI(true);
    }
}
