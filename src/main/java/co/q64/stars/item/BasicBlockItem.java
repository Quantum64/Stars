package co.q64.stars.item;

import co.q64.stars.block.TrophyBlock;
import co.q64.stars.group.StarsGroup;
import co.q64.stars.qualifier.Qualifiers.TrophyItemProperties;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

@AutoFactory
public class BasicBlockItem extends BlockItem {

    protected BasicBlockItem(Block block, @Provided StarsGroup group) {
        super(block, new Item.Properties().group(group));
        setRegistryName(block.getRegistryName());
    }
}
