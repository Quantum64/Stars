package co.q64.stars.item;

import co.q64.stars.block.TrophyBlock;
import co.q64.stars.level.Level;
import co.q64.stars.level.LevelManager;
import co.q64.stars.level.LevelType;
import co.q64.stars.qualifier.Qualifiers.TrophyItemProperties;
import co.q64.stars.type.FormingBlockTypes;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TrophyBlockItem extends BlockItem {
    private static final String BASE_TRANSLATION_KEY = "block.stars.";

    protected @Inject FormingBlockTypes types;
    protected @Inject LevelManager levelManager;

    @Inject
    protected TrophyBlockItem(TrophyBlock block, @TrophyItemProperties Properties properties) {
        super(block, properties);
        setRegistryName("trophy");
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public String getTranslationKey(ItemStack stack) {
        CompoundNBT tag = stack.getOrCreateChildTag("BlockEntityTag");
        if (tag.getBoolean("hasBlock")) {
            return BASE_TRANSLATION_KEY + "trophy_" + types.get(tag.getInt("forming")).getName().toLowerCase();
        }
        return BASE_TRANSLATION_KEY + "trophy_base";
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            items.add(new ItemStack(this));
            for (LevelType type : LevelType.values()) {
                Level level = levelManager.getLevel(type);
                ItemStack stack = new ItemStack(this);
                CompoundNBT tag = stack.getOrCreateChildTag("BlockEntityTag");
                tag.putBoolean("hasBlock", true);
                tag.putInt("forming", level.getSymbolicBlock().getId());
                items.add(stack);
            }
        }
    }
}
