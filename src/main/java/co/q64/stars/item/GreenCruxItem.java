package co.q64.stars.item;

import co.q64.stars.type.forming.GreenFormingBlockType;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GreenCruxItem extends CruxItem {
    @Inject
    protected GreenCruxItem(GreenFormingBlockType type) {
        super("green_crux", type);
    }
}
