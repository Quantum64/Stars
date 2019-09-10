package co.q64.stars.item;

import co.q64.stars.block.TrophyBlock;
import co.q64.stars.group.StarsGroup;
import co.q64.stars.qualifier.Qualifiers.TrophyItemProperties;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import lombok.Getter;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;

@AutoFactory
public class BasicBlockItem extends BlockItem {
    private @Getter TrophyBlock trophyBlock;

    protected BasicBlockItem(Block block, ) {
        super(block, properties);
        this.trophyBlock = block;
        setRegistryName(block.getRegistryName());
    }
}
