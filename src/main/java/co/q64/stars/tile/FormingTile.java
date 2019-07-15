package co.q64.stars.tile;

import co.q64.stars.block.FormingBlock;
import co.q64.stars.tile.type.FormingTileType;
import co.q64.stars.type.FormingBlockType;
import co.q64.stars.type.FormingBlockTypes;
import co.q64.stars.type.forming.YellowFormingBlockType;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import javax.annotation.Nullable;
import java.util.concurrent.ThreadLocalRandom;

@AutoFactory
public class FormingTile extends TileEntity implements ITickableTileEntity {
    private FormingBlockTypes types;
    private FormingBlock formingBlock;

    private @Setter @Getter int iterationsRemaining = 5;
    private @Setter @Getter Direction direction = Direction.values()[ThreadLocalRandom.current().nextInt(Direction.values().length)];
    private @Setter @Getter FormingBlockType formType;
    private @Getter long placed = System.currentTimeMillis();

    private int ticks = 0;
    private int formTicks;

    public FormingTile(@Provided FormingTileType type, @Provided FormingBlock formingBlock, @Provided FormingBlockTypes types, @Provided YellowFormingBlockType formType) {
        super(type);
        this.types = types;
        this.formingBlock = formingBlock;
        setup(formType);
    }

    private void setup(FormingBlockType type) {
        this.formType = type;
        this.formTicks = formType.getBuildTime() / 50; // 50 ms per tick
    }

    public void setWorld(World worldIn) {
        super.setWorld(worldIn);
        if (world.isRemote) {
            Minecraft.getInstance().player.sendChatMessage(direction.name());
        }
    }

    public void read(CompoundNBT compound) {
        if (compound.contains("direction")) {
            direction = Direction.valueOf(compound.getString("direction"));
        }
        if (compound.contains("formType")) {
            formType = types.get(compound.getInt("formType"));
        }
        if (compound.contains("iterationsRemaining")) {
            iterationsRemaining = compound.getInt("iterationsRemaining");
        }
        super.read(compound);
    }

    public CompoundNBT write(CompoundNBT compound) {
        CompoundNBT result = super.write(compound);
        result.putString("direction", direction.name());
        result.putInt("formType", formType.getId());
        result.putInt("iterationsRemaining", iterationsRemaining);
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

    public void tick() {
        if (!world.isRemote) {
            if (ticks == formTicks) {
                world.setBlockState(getPos(), types.yellowFormingBlockType.getFormedBlock().getDefaultState());
                if (iterationsRemaining > 0) {
                    BlockPos placed = getPos().add(direction.getXOffset(), direction.getYOffset(), direction.getZOffset());
                    world.setBlockState(placed, formingBlock.getDefaultState());
                    FormingTile spawned = (FormingTile) world.getTileEntity(placed);
                    if (spawned != null) {
                        spawned.setIterationsRemaining(iterationsRemaining - 1);
                        spawned.setDirection(direction);
                    } else {
                        System.out.println("Problem");
                    }
                }
            }
            ticks++;
        }
    }
}
