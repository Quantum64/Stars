package co.q64.stars.tile;

import co.q64.stars.tile.type.DecayingTileType;
import co.q64.stars.type.FormingBlockType;
import co.q64.stars.type.FormingBlockTypes;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;

import javax.inject.Inject;

public class DecayingTile extends TileEntity {
    private static final long SALT = 0x1029adbc3847efefL;

    protected @Inject FormingBlockTypes types;

    private @Getter @Setter boolean calculated;
    private @Getter @Setter FormingBlockType formingBlockType;
    private @Getter @Setter int expectedDecayTime;
    private @Getter @Setter long placed = System.currentTimeMillis();
    private @Getter @Setter boolean primed = false;

    @Inject
    protected DecayingTile(DecayingTileType formingBlockType) {
        super(formingBlockType);
    }

    @Inject
    protected void setupDefault(FormingBlockTypes types) {
        this.formingBlockType = types.getDefault();
    }

    public void read(CompoundNBT compound) {
        super.read(compound);
        expectedDecayTime = compound.getInt("expectedDecayTime");
        formingBlockType = types.get(compound.getInt("formingBlockType"));
        placed = compound.getLong("placed");
        calculated = compound.getBoolean("calculated");
        primed = compound.getBoolean("primed");
    }

    public CompoundNBT write(CompoundNBT compound) {
        CompoundNBT result = super.write(compound);
        result.putInt("expectedDecayTime", expectedDecayTime);
        result.putInt("formingBlockType", formingBlockType.getId());
        result.putLong("placed", placed);
        result.putBoolean("calculated", calculated);
        result.putBoolean("primed", primed);
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

    public long getSeed() {
        return Math.abs(getPos().toLong() ^ SALT);
    }
}
