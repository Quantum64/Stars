package co.q64.stars.item;

import co.q64.stars.group.StarsGroup;
import co.q64.stars.type.forming.BlueFormingBlockType;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BlueCruxItem extends CruxItem {
    @Inject
    protected BlueCruxItem(BlueFormingBlockType type, StarsGroup group) {
        super("blue_crux", type, group);
    }
}
