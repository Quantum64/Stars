package co.q64.stars.tile

import co.q64.stars.block.FormingBlock
import co.q64.stars.block.HardBlock
import co.q64.stars.block.RedFormedBlock
import co.q64.stars.block.RedFormedBlockHard
import co.q64.stars.type.BlueFormingBlockType
import co.q64.stars.type.FormingBlockType
import co.q64.stars.type.RedFormingBlockType
import net.minecraft.nbt.CompoundNBT
import net.minecraft.tileentity.ITickableTileEntity
import net.minecraft.tileentity.TileEntityType
import net.minecraft.util.Direction
import net.minecraft.world.World
import net.minecraft.world.server.ServerWorld

open class SeedTile(type: TileEntityType<*> = seedTileType) : SyncTileEntity(type), ITickableTileEntity {
    var direction: Direction? = null
    var ticks = 20
    var active = false
    var ready = false
    var type: FormingBlockType = BlueFormingBlockType
    var seed: FormingBlockType = BlueFormingBlockType
    var primed = false
    var fruit = false
    var multiplier = 1.0

    override fun read(compound: CompoundNBT) = with(compound) {
        super.read(this)
        type = FormingBlockType.fromId(getString("type"))
        seed = FormingBlockType.fromId(getString("seed"))
        ready = getBoolean("ready")
        primed = getBoolean("primed")
        fruit = getBoolean("fruit")
        ticks = getInt("ticks")
        active = getBoolean("active")
    }

    override fun write(compound: CompoundNBT): CompoundNBT = with(compound) {
        putString("type", type.id)
        putString("seed", seed.id)
        putBoolean("ready", ready)
        putBoolean("primed", primed)
        putBoolean("fruit", fruit)
        putBoolean("active", active)
        return super.write(compound)
    }


    override fun tick() {
        world?.let { world ->
            if (world.isRemote && !active) return
            if (ticks == 0) {
                (world as? ServerWorld)?.getClosestPlayer(pos.x.toDouble(), pos.z.toDouble(), 1000.0)?.let {
                    // TODO level type
                }
            }
            var count = 0
            if (ticks == 0) {
                grow(world)
            } else if (ticks < 0) {
                check(world)
            }
            ticks--
        }
    }

    private fun grow(world: World) {
        if (primed) {
            RedFormingBlockType.explode(world as ServerWorld, pos, false)
            return
        }
        direction = seed.firstDirection(world, pos)
        direction?.let {
            val target = pos.offset(it)
            world.setBlockState(target, FormingBlock.defaultState)
            (world.getTileEntity(target) as? FormingTile)?.apply {
                first = true
                direction = it
                multiplier = this@SeedTile.multiplier
                hard = blockState.block is HardBlock
                type = seed
                ready = true
            }
        }
    }

    private fun check(world: World) {
        direction?.let {
            val hard = blockState.block is HardBlock
            if (world.getBlockState(pos.offset(it)).block != FormingBlock) {
                if (type is RedFormingBlockType) {
                    world.setBlockState(pos, if (hard) RedFormedBlockHard.defaultState else RedFormedBlock.defaultState)
                } else {
                    world.setBlockState(pos, if (hard) type.formedHard.defaultState else type.formed.defaultState)
                }
            }
        }
    }
}