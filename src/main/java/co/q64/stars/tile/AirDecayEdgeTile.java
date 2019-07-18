package co.q64.stars.tile;

import co.q64.stars.block.AirDecayBlock;
import co.q64.stars.block.AirDecayEdgeBlock;
import co.q64.stars.block.DecayEdgeBlock;
import co.q64.stars.block.DecayingBlock;
import co.q64.stars.tile.type.AirDecayEdgeTileType;
import co.q64.stars.type.FormingBlockTypes;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import net.minecraft.tileentity.ITickableTileEntity;

@AutoFactory
public class AirDecayEdgeTile extends DecayEdgeTile implements ITickableTileEntity {
    public AirDecayEdgeTile(@Provided AirDecayEdgeTileType type, @Provided DecayEdgeBlock decayEdgeBlock, @Provided AirDecayBlock decayBlock, @Provided DecayingBlock decayingBlock, @Provided FormingBlockTypes types, @Provided AirDecayEdgeBlock airDecayEdgeBlock) {
        super(decayEdgeBlock, decayBlock, decayingBlock, airDecayEdgeBlock, types, type);
    }
}
