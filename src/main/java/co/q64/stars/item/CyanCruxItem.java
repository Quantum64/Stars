package co.q64.stars.item;

import co.q64.stars.group.StarsGroup;
import co.q64.stars.type.forming.CyanFormingBlockType;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CyanCruxItem extends CruxItem {
    @Inject
    protected CyanCruxItem(CyanFormingBlockType type, StarsGroup group) {
        super("cyan_crux", type, group);
    }
}
