package co.q64.stars.tile.type;

import co.q64.stars.block.DecayEdgeBlock;
import co.q64.stars.block.FormingBlock;
import co.q64.stars.tile.DecayEdgeTile;
import co.q64.stars.tile.DecayEdgeTileFactory;
import co.q64.stars.tile.FormingTile;
import co.q64.stars.tile.FormingTileFactory;
import co.q64.stars.util.Identifiers;
import net.minecraft.tileentity.TileEntityType;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;

@Singleton
public class DecayEdgeTileType extends TileEntityType<DecayEdgeTile> {

    protected @Inject DecayEdgeTileType(DecayEdgeTileFactory factory, DecayEdgeBlock type, Identifiers identifiers) {
        super(factory::create, Collections.singleton(type), null);
        setRegistryName(identifiers.get("decay_edge"));
    }
}
