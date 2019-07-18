package co.q64.stars.tile;

import co.q64.stars.block.AirDecayBlock;
import co.q64.stars.block.AirDecayEdgeBlock;
import co.q64.stars.block.DecayBlock;
import co.q64.stars.block.DecayEdgeBlock;
import co.q64.stars.block.FormingBlock;
import co.q64.stars.tile.type.FormingTileType;
import co.q64.stars.type.FormingBlockType;
import co.q64.stars.type.FormingBlockTypes;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPlayerPositionLookPacket.Flags;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorld;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@AutoFactory
public class FormingTile extends TileEntity implements ITickableTileEntity {
    private static final Direction[] DIRECTIONS = Direction.values();
    private static final long SALT = 0x1029adbc3847efefL;

    private FormingBlockTypes types;
    private FormingBlock formingBlock;
    private DecayEdgeBlock decayEdgeBlock;
    private AirDecayEdgeBlock airDecayEdgeBlock;

    private @Setter @Getter boolean calculated = false;
    private @Setter @Getter boolean first = true;
    private @Setter @Getter int iterationsRemaining = 0;
    private @Setter @Getter Direction direction = Direction.UP;
    private @Setter @Getter FormingBlockType formType;
    private @Getter long placed = System.currentTimeMillis();
    private @Getter int buildTime = 0;

    private int ticks = 0;
    private int formTicks;

    public FormingTile(@Provided FormingTileType type, @Provided FormingBlock formingBlock, @Provided FormingBlockTypes types, @Provided DecayEdgeBlock decayEdgeBlock, @Provided AirDecayEdgeBlock airDecayEdgeBlock) {
        super(type);
        this.types = types;
        this.formingBlock = formingBlock;
        this.decayEdgeBlock = decayEdgeBlock;
        this.airDecayEdgeBlock = airDecayEdgeBlock;
        setup(ThreadLocalRandom.current().nextBoolean() ? types.purpleFormingBlockType : types.yellowFormingBlockType);
    }

    public void setup(FormingBlockType type) {
        this.formType = type;
        this.buildTime = formType.getBuildTime();
        if (formType.getBuildTimeOffset() > 0) {
            buildTime += ThreadLocalRandom.current().nextInt(formType.getBuildTimeOffset() * 2) - formType.getBuildTimeOffset();
        }
        this.formTicks = buildTime / 50; // 50 ms per tick
        if (first) {
            this.iterationsRemaining = formType.getIterations(getSeed());
        }
    }

    public void read(CompoundNBT compound) {
        direction = Direction.valueOf(compound.getString("direction"));
        formType = types.get(compound.getInt("formType"));
        iterationsRemaining = compound.getInt("iterationsRemaining");
        placed = compound.getLong("placed");
        calculated = compound.getBoolean("calculated");
        buildTime = compound.getInt("buildTime");
        super.read(compound);
    }

    public CompoundNBT write(CompoundNBT compound) {
        CompoundNBT result = super.write(compound);
        result.putString("direction", direction.name());
        result.putInt("formType", formType.getId());
        result.putInt("iterationsRemaining", iterationsRemaining);
        result.putLong("placed", placed);
        result.putBoolean("calculated", calculated);
        result.putInt("buildTime", buildTime);
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
        return Math.abs(getPos().toLong() ^ SALT);
    }

    public void tick() {
        if (!world.isRemote) {
            if (first && !calculated) {
                direction = formType.getInitialDirection(world, pos);
                calculated = true;
                world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 2);
            }
            if (ticks == formTicks) {
                ((ServerWorld) world).spawnParticle(ParticleTypes.CLOUD, getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5, 20, 0.4, 0.4, 0.4, 0.01);
                world.setBlockState(getPos(), formType.getFormedBlock().getDefaultState());

                // Move nearby
                if (!world.isRemote) {
                    for (ServerPlayerEntity player : ((ServerWorld) world).getPlayers()) {
                        if (player.posX > getPos().getX() - 0.3 && player.posX < getPos().getX() + 1.3
                                && player.posZ > getPos().getZ() - 0.3 && player.posZ < getPos().getZ() + 1.3
                                && player.posY > getPos().getY() - 0.01 && player.posY < getPos().getY() + 1.01) {
                            player.connection.setPlayerLocation(player.posX, player.posY + 1, player.posZ, player.rotationYaw, player.rotationPitch, Arrays.asList(Flags.X, Flags.Y, Flags.Z, Flags.X_ROT, Flags.Y_ROT).stream().collect(Collectors.toSet()));
                        }
                    }
                }

                // Check if decay needs to be reactivated
                for (Direction direction : DIRECTIONS) {
                    BlockPos target = getPos().offset(direction);
                    if (world.getBlockState(target).getBlock() instanceof AirDecayBlock) {
                        world.setBlockState(target, airDecayEdgeBlock.getDefaultState());
                    } else if (world.getBlockState(target).getBlock() instanceof DecayBlock) {
                        world.setBlockState(target, decayEdgeBlock.getDefaultState());
                    }
                }

                if (iterationsRemaining > 0) {
                    for (Direction next : formType.getNextDirections(world, pos, direction, iterationsRemaining - 1)) {
                        BlockPos placed = getPos().add(next.getXOffset(), next.getYOffset(), next.getZOffset());
                        world.setBlockState(placed, formingBlock.getDefaultState());
                        FormingTile spawned = (FormingTile) world.getTileEntity(placed);
                        if (spawned != null) {
                            spawned.setFirst(false);
                            spawned.setIterationsRemaining(iterationsRemaining - 1);
                            spawned.setDirection(next);
                            spawned.setup(formType);
                            spawned.setCalculated(true);
                        } else {
                            System.out.println("Problem");
                        }
                    }
                }
            }
            ticks++;
        }
    }
}
