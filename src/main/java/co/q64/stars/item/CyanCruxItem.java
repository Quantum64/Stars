package co.q64.stars.item;

import co.q64.stars.type.forming.CyanFormingBlockType;
import co.q64.stars.type.forming.YellowFormingBlockType;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CyanCruxItem extends CruxItem {
    @Inject
    protected CyanCruxItem(CyanFormingBlockType type) {
        super("cyan_crux", type);
    }
}
