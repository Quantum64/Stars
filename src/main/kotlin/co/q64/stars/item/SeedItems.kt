package co.q64.stars.item

import co.q64.stars.block.FormingBlock
import co.q64.stars.dimension.StarsDimension
import co.q64.stars.group.StarsGroup
import co.q64.stars.type.BlueFormingBlockType
import co.q64.stars.type.FormingBlockType
import net.minecraft.item.ItemUseContext
import net.minecraft.util.ActionResultType

sealed class SeedItem(id: String, private val type: FormingBlockType) : BaseItem(id, StarsGroup) {
    override fun onItemUse(context: ItemUseContext): ActionResultType = with(context) {
        if (world.dimension is StarsDimension) return ActionResultType.FAIL
        type.firstDirection(world, pos)?.let {
            world.setBlockState(pos.offset(it), FormingBlock.defaultState)
            world.getTileEntity(pos.offset(it))?.apply {

            }
            item.shrink(1)
            return ActionResultType.SUCCESS
        }
        return ActionResultType.FAIL
    }
}

object BlueSeedItem : SeedItem("blue_seed", BlueFormingBlockType)

sealed class RobustSeedItem(id: String) : BaseItem(id, StarsGroup)
object BlueRobustSeedItem : RobustSeedItem("blue_seed_robust")