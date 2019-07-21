package co.q64.stars.item;

import co.q64.stars.type.FormingBlockType;
import co.q64.stars.util.SeedManager;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.inject.Inject;

public abstract class SeedItem extends BaseItem {
    protected @Inject SeedManager seedManager;

    private FormingBlockType type;

    public SeedItem(String id, FormingBlockType type, ItemGroup group) {
        super(id, group);
        this.type = type;
    }

    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        if (seedManager.tryGrow(world, pos, type)) {
            context.getItem().shrink(1);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }
}
