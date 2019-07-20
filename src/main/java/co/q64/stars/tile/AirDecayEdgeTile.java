package co.q64.stars.tile;

import co.q64.stars.block.AirDecayBlock;
import co.q64.stars.tile.type.AirDecayEdgeTileType;
import net.minecraft.tileentity.ITickableTileEntity;

import javax.inject.Inject;

public class AirDecayEdgeTile extends DecayEdgeTile implements ITickableTileEntity {

    @Inject
    protected AirDecayEdgeTile(AirDecayEdgeTileType type) {
        super(type);
    }

    @Inject
    protected void completeSetup(AirDecayBlock block) {
        this.decayBlock = block;
    }
}
