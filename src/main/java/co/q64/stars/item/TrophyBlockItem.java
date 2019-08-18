package co.q64.stars.item;

import co.q64.stars.block.TrophyBlock;
import co.q64.stars.qualifier.Qualifiers.TrophyItemProperties;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import lombok.Getter;
import net.minecraft.item.BlockItem;

@AutoFactory
public class TrophyBlockItem extends BlockItem {
    private @Getter TrophyBlock trophyBlock;

    protected TrophyBlockItem(TrophyBlock block, @Provided @TrophyItemProperties Properties properties) {
        super(block, properties);
        this.trophyBlock = block;
        setRegistryName(block.getRegistryName());
    }
}
