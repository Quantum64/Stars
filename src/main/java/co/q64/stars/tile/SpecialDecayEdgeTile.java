package co.q64.stars.tile;

import co.q64.stars.block.DecayBlock;
import co.q64.stars.block.SpecialDecayBlock;
import co.q64.stars.tile.type.SpecialDecayEdgeTileType;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.IStringSerializable;

import javax.inject.Inject;

public class SpecialDecayEdgeTile extends DecayEdgeTile implements ITickableTileEntity {
    private @Setter @Getter SpecialDecayType decayType = SpecialDecayType.HEART;

    @Inject
    protected SpecialDecayEdgeTile(SpecialDecayEdgeTileType type) {
        super(type);
    }

    @Inject
    protected void completeSetup(SpecialDecayBlock block) {
        this.decayBlock = block;
    }

    public void read(CompoundNBT compound) {
        super.read(compound);
        this.decayType = SpecialDecayType.valueOf(compound.getString("decayType"));
    }

    public CompoundNBT write(CompoundNBT compound) {
        compound.putString("decayType", decayType.name());
        return super.write(compound);
    }

    protected BlockState getDecayState(DecayBlock block) {
        return block.getDefaultState().with(SpecialDecayBlock.TYPE, decayType);
    }

    public static enum SpecialDecayType implements IStringSerializable {
        HEART, DOOR, CHALLENGE_DOOR;

        public String getName() {
            return name().toLowerCase();
        }
    }
}
