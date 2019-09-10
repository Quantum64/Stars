package co.q64.stars.tile;

import co.q64.stars.block.AirDecayBlock;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.inject.Inject;

public class AirDecayEdgeTile extends DecayEdgeTile implements ITickableTileEntity {

    @Inject
    protected AirDecayEdgeTile(TileEntityType<AirDecayEdgeTile> type) {
        super(type);
    }

    @Inject
    protected void completeSetup(AirDecayBlock block) {
        this.decayBlock = block;
    }
}
