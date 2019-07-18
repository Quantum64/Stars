package co.q64.stars.item;

import co.q64.stars.block.DecayingBlock;
import co.q64.stars.block.FormedBlock;
import co.q64.stars.block.FormingBlock;
import co.q64.stars.tile.FormingTile;
import co.q64.stars.type.FormingBlockType;
import net.minecraft.block.Block;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.inject.Inject;

public abstract class CruxItem extends BaseItem {
    protected @Inject FormingBlock formingBlock;

    private FormingBlockType type;

    public CruxItem(String id, FormingBlockType type) {
        super(id);
        this.type = type;
    }

    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        Block block = world.getBlockState(pos).getBlock();
        if (block instanceof FormedBlock || block instanceof DecayingBlock) {
            Direction direction = type.getInitialDirection(world, pos);
            if (direction == null) {
                return ActionResultType.FAIL;
            }
            BlockPos target = pos.offset(direction);
            world.setBlockState(target, formingBlock.getDefaultState());
            if (!world.isRemote) {
                FormingTile tile = (FormingTile) world.getTileEntity(target);
                if (tile == null) {
                    System.out.println("null tile on place " + target.toString());
                } else {
                    tile.setFirst(true);
                    tile.setDirection(direction);
                    tile.setup(type);
                    tile.setCalculated(true);
                }
            }
            //TODO sound
            context.getItem().shrink(1);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }
}
