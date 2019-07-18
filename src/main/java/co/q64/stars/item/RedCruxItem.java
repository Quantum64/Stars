package co.q64.stars.item;

import co.q64.stars.type.forming.RedFormingBlockType;
import co.q64.stars.type.forming.YellowFormingBlockType;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RedCruxItem extends CruxItem {
    @Inject
    protected RedCruxItem(RedFormingBlockType type) {
        super("red_crux", type);
    }
}
