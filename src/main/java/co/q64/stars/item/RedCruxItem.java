package co.q64.stars.item;

import co.q64.stars.group.StarsGroup;
import co.q64.stars.type.forming.RedFormingBlockType;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RedCruxItem extends CruxItem {
    @Inject
    protected RedCruxItem(RedFormingBlockType type, StarsGroup group) {
        super("red_crux", type, group);
    }
}
