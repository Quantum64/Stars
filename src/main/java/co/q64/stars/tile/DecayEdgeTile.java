package co.q64.stars.tile;

import co.q64.stars.block.DecayBlock;
import co.q64.stars.block.DecayEdgeBlock;
import co.q64.stars.block.DecayingBlock;
import co.q64.stars.block.FormedBlock;
import co.q64.stars.tile.type.DecayEdgeTileType;
import co.q64.stars.type.FormingBlockType;
import co.q64.stars.type.FormingBlockTypes;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import net.minecraft.block.Block;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
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

    private Map<Direction, Integer> decayAmount = new HashMap<>();

    private int ticks = 0;

    public DecayEdgeTile(@Provided DecayEdgeTileType type, @Provided DecayEdgeBlock decayEdgeBlock, @Provided DecayBlock decayBlock, @Provided DecayingBlock decayingBlock, @Provided FormingBlockTypes types) {
        super(type);
        this.types = types;
        this.decayBlock = decayBlock;
        this.decayingBlock = decayingBlock;
        this.decayEdgeBlock = decayEdgeBlock;
    }

    public void read(CompoundNBT compound) {
        super.read(compound);
    }

    public CompoundNBT write(CompoundNBT compound) {
        CompoundNBT result = super.write(compound);
        return result;
    }

    public CompoundNBT getUpdateTag() {
        CompoundNBT tag = super.getUpdateTag();
        write(tag);
        return tag;
    }

    public void handleUpdateTag(CompoundNBT tag) {
        super.read(tag);
        read(tag);
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT tag = new CompoundNBT();
        write(tag);
        return new SUpdateTileEntityPacket(getPos(), 1, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet) {
        CompoundNBT tag = packet.getNbtCompound();
        read(tag);
    }

    private long getSeed() {
        return getPos().toLong() ^ SALT;
    }

    public void tick() {
        //if (ticks % 10 == 0) {
        if (!world.isRemote) {
            int counts = 0;
            for (Direction direction : DIRECTIONS) {
                BlockPos target = getPos().offset(direction);
                Block block = world.getBlockState(target).getBlock();
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
