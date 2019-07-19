package co.q64.stars.item;

import co.q64.stars.group.StarsGroup;
import co.q64.stars.type.forming.YellowFormingBlockType;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class YellowCruxItem extends CruxItem {
    @Inject
    protected YellowCruxItem(YellowFormingBlockType type, StarsGroup group) {
        super("yellow_crux", type, group);
    }
}
