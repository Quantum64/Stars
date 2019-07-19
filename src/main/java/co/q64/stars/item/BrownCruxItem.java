package co.q64.stars.item;

import co.q64.stars.group.StarsGroup;
import co.q64.stars.type.forming.BrownFormingBlockType;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BrownCruxItem extends CruxItem {
    @Inject
    protected BrownCruxItem(BrownFormingBlockType type, StarsGroup group) {
        super("brown_crux", type, group);
    }
}
