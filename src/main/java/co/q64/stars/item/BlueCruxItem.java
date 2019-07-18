package co.q64.stars.item;

import co.q64.stars.type.forming.BlueFormingBlockType;
import co.q64.stars.type.forming.PinkFormingBlockType;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BlueCruxItem extends CruxItem {
    @Inject
    protected BlueCruxItem(BlueFormingBlockType type) {
        super("blue_crux", type);
    }
}
