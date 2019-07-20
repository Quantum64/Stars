package co.q64.stars.tile.type;

import co.q64.stars.block.DecayEdgeBlock;
import co.q64.stars.tile.DecayEdgeTile;
import co.q64.stars.util.Identifiers;
import net.minecraft.tileentity.TileEntityType;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Collections;

@Singleton
public class DecayEdgeTileType extends TileEntityType<DecayEdgeTile> {

    protected @Inject DecayEdgeTileType(Provider<DecayEdgeTile> provider, DecayEdgeBlock type, Identifiers identifiers) {
        super(provider::get, Collections.singleton(type), null);
        setRegistryName(identifiers.get("decay_edge"));
    }
}
