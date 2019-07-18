package co.q64.stars.item;

import co.q64.stars.block.BrownFormedBlock;
import co.q64.stars.type.forming.BrownFormingBlockType;
import co.q64.stars.type.forming.RedFormingBlockType;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BrownCruxItem extends CruxItem {
    @Inject
    protected BrownCruxItem(BrownFormingBlockType type) {
        super("brown_crux", type);
    }
}
