package co.q64.stars.item;

import co.q64.stars.group.StarsGroup;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class StarItem extends BaseItem {
    @Inject
    protected StarItem() {
        super("star");
        setHideJEI(true);
    }
}
