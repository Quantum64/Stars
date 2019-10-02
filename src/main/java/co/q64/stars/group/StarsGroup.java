package co.q64.stars.group;

import co.q64.stars.item.PinkSeedItem;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class StarsGroup extends ItemGroup {
    protected @Inject Provider<PinkSeedItem> itemProvider;

    protected @Inject StarsGroup() {
        super("stars");
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack createIcon() {
        return new ItemStack(itemProvider.get());
    }

    public void fill(NonNullList<ItemStack> items) {
        super.fill(items);
        System.out.println("STARS GROUP: ");
        items.forEach(i -> System.out.println(i.getItem().getRegistryName().toString()));
    }
}
