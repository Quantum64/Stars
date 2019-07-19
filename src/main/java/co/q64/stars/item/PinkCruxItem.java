package co.q64.stars.item;

import co.q64.stars.group.StarsGroup;
import co.q64.stars.type.forming.PinkFormingBlockType;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PinkCruxItem extends CruxItem {
    @Inject
    protected PinkCruxItem(PinkFormingBlockType type, StarsGroup group) {
        super("pink_crux", type, group);
    }
}
