package co.q64.stars.item;

import co.q64.stars.block.FormingBlock;
import co.q64.stars.dimension.StarsDimension;
import co.q64.stars.tile.FormingTile;
import co.q64.stars.type.FormingBlockType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.inject.Inject;
import java.util.Optional;

public abstract class SeedItem extends BaseItem {
    protected @Inject FormingBlock formingBlock;

    private FormingBlockType type;

    public SeedItem(String id, FormingBlockType type, ItemGroup group) {
        super(id, group);
        this.type = type;
    }

    public ActionResultType onItemUse(ItemUseContext context) {
        BlockPos pos = context.getPos();
        World world = context.getWorld();
        if (world.getDimension() instanceof StarsDimension) {
            return ActionResultType.FAIL;
        }
        Direction first = type.getInitialDirection(world, pos);
        if (first != null) {
            BlockPos target = pos.offset(first);
            world.setBlockState(target, formingBlock.getDefaultState());
            Optional.ofNullable((FormingTile) world.getTileEntity(target)).ifPresent(tile -> {
                tile.setFirst(true);
                tile.setDirection(first);
                tile.setup(type);
                tile.setCalculated(true);
            });
            context.getItem().shrink(1);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }
}
