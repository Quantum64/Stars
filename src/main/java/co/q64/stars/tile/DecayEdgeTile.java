package co.q64.stars.tile;

import co.q64.stars.block.AirDecayEdgeBlock;
import co.q64.stars.block.DarkAirBlock;
import co.q64.stars.block.DecayBlock;
import co.q64.stars.block.DecayEdgeBlock;
import co.q64.stars.block.DecayingBlock;
import co.q64.stars.block.FormedBlock;
import co.q64.stars.block.FormingBlock;
import co.q64.stars.tile.type.DecayEdgeTileType;
import co.q64.stars.type.FormingBlockType;
import co.q64.stars.type.FormingBlockTypes;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorld;

import java.util.HashMap;
import java.util.Map;

@AutoFactory
public class DecayEdgeTile extends TileEntity implements ITickableTileEntity {
    private static final Direction[] DIRECTIONS = Direction.values();
    private static final long SALT = 0xabcd0123dcba3210L;

    private FormingBlockTypes types;
    private DecayEdgeBlock decayEdgeBlock;
    private DecayingBlock decayingBlock;
    private DecayBlock decayBlock;
    private AirDecayEdgeBlock airDecayEdgeBlock;

    private Map<Direction, Integer> decayAmount = new HashMap<>();

    private int ticks = 0;

    public DecayEdgeTile(@Provided DecayEdgeTileType type, @Provided DecayEdgeBlock decayEdgeBlock, @Provided DecayBlock decayBlock, @Provided DecayingBlock decayingBlock, @Provided AirDecayEdgeBlock airDecayEdgeBlock, @Provided FormingBlockTypes types) {
        super(type);
        this.types = types;
        this.decayBlock = decayBlock;
        this.decayingBlock = decayingBlock;
        this.decayEdgeBlock = decayEdgeBlock;
        this.airDecayEdgeBlock = airDecayEdgeBlock;
    }


    public DecayEdgeTile(DecayEdgeBlock decayEdgeBlock, DecayBlock decayBlock, DecayingBlock decayingBlock, AirDecayEdgeBlock airDecayEdgeBlock, FormingBlockTypes types, TileEntityType<?> type) {
        super(type);
        this.types = types;
        this.decayBlock = decayBlock;
        this.decayingBlock = decayingBlock;
        this.decayEdgeBlock = decayEdgeBlock;
        this.airDecayEdgeBlock = airDecayEdgeBlock;
    }

    public void tick() {
        //if (ticks % 10 == 0) {
        if (!world.isRemote) {
            int counts = 0;
            for (Direction direction : DIRECTIONS) {
                BlockPos target = getPos().offset(direction);
                Block block = world.getBlockState(target).getBlock();
                if (block instanceof DarkAirBlock) {
                    //if (ticks > 5) {
                    ((ServerWorld) world).spawnParticle(ParticleTypes.LARGE_SMOKE, target.getX() + 0.5, target.getY() + 0.5, target.getZ() + 0.5, 20, 0.5, 0.5, 0.5, 0.01);
                    world.setBlockState(target, airDecayEdgeBlock.getDefaultState());
                    //}
                    //counts++;
                }
                if (block instanceof FormedBlock) {
                    FormingBlockType type = types.get(block);
                    world.setBlockState(target, decayingBlock.getDefaultState());
                    DecayingTile decayingTile = (DecayingTile) world.getTileEntity(target);
                    decayingTile.setFormingBlockType(type);
                    decayingTile.setExpectedDecayTime(type.getDecayTime(target.toLong() ^ SALT) * 50);
                    decayingTile.setCalculated(true);
                    if (decayingTile == null) {
                        System.out.println("decaying tile null");
                        continue;
                    }
                    counts++;
                } else if (block instanceof DecayingBlock) {
                    int decay = decayAmount.getOrDefault(direction, 0);
                    DecayingTile decayingTile = (DecayingTile) world.getTileEntity(target);
                    if (decayingTile == null) {
                        System.out.println("decaying tile null");
                        continue;
                    }
                    FormingBlockType type = decayingTile.getFormingBlockType();
                    if (decay > type.getDecayTime(Math.abs(target.toLong() ^ SALT))) {
                        ((ServerWorld) world).spawnParticle(ParticleTypes.LARGE_SMOKE, target.getX() + 0.5, target.getY() + 0.5, target.getZ() + 0.5, 20, 0.5, 0.5, 0.5, 0.01);
                        world.setBlockState(target, decayEdgeBlock.getDefaultState());
                    } else {
                        //decayAmount.put(direction, decay + 10);
                        decayAmount.put(direction, decay + 1);
                        counts++;
                    }
                } else if (block instanceof FormingBlock) {
                    world.setBlockState(target, Blocks.AIR.getDefaultState());
                }
            }
            if (counts == 0) {
                world.setBlockState(getPos(), decayBlock.getDefaultState());
            }
        }
        //}
        ticks++;
    }
}
