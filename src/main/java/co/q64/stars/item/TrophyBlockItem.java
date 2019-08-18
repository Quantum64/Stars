package co.q64.stars.item;

import co.q64.stars.block.TrophyBlock;
import co.q64.stars.qualifier.Qualifiers.TrophyItemProperties;
import net.minecraft.item.BlockItem;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TrophyBlockItem extends BlockItem {
    @Inject
    protected TrophyBlockItem(TrophyBlock block, @TrophyItemProperties Properties properties) {
        super(block, properties);
        setRegistryName("trophy");
    }
}
