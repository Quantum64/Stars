package co.q64.stars.tile.type;

import co.q64.stars.block.AirDecayEdgeBlock;
import co.q64.stars.tile.AirDecayEdgeTile;
import co.q64.stars.tile.AirDecayEdgeTileFactory;
import co.q64.stars.util.Identifiers;
import net.minecraft.tileentity.TileEntityType;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;

@Singleton
public class AirDecayEdgeTileType extends TileEntityType<AirDecayEdgeTile> {

    protected @Inject AirDecayEdgeTileType(AirDecayEdgeTileFactory factory, AirDecayEdgeBlock type, Identifiers identifiers) {
        super(factory::create, Collections.singleton(type), null);
        setRegistryName(identifiers.get("air_decay_edge"));
    }
}