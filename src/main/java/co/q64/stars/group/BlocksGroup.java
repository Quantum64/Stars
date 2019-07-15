package co.q64.stars.group;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BlocksGroup extends ItemGroup {

    protected @Inject BlocksGroup() {
        super("blocks");
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack createIcon() {
        return new ItemStack(Items.PUMPKIN);
    }
}
