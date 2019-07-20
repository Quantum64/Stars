package co.q64.stars.group;

import co.q64.stars.item.PinkSeedItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
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
}
