package co.q64.stars.tile.type;

import co.q64.stars.block.DecayEdgeBlock;
import co.q64.stars.block.DecayEdgeBlock.DecayEdgeBlockSolid;
import co.q64.stars.tile.DecayEdgeTile;
import co.q64.stars.util.Identifiers;
import net.minecraft.tileentity.TileEntityType;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
public class DecayEdgeTileType extends TileEntityType<DecayEdgeTile> {

    protected @Inject DecayEdgeTileType(Provider<DecayEdgeTile> provider, DecayEdgeBlock decayEdgeBlock, DecayEdgeBlockSolid decayEdgeBlockSolid, Identifiers identifiers) {
        super(provider::get, Stream.of(decayEdgeBlock, decayEdgeBlockSolid).collect(Collectors.toSet()), null);
        setRegistryName(identifiers.get("decay_edge"));
    }
}
