package co.q64.stars.tile.type;

import co.q64.stars.block.DecayingBlock;
import co.q64.stars.block.DecayingBlock.DecayingBlockHard;
import co.q64.stars.tile.DecayingTile;
import co.q64.stars.util.Identifiers;
import net.minecraft.tileentity.TileEntityType;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
public class DecayingTileType extends TileEntityType<DecayingTile> {

    protected @Inject DecayingTileType(Provider<DecayingTile> provider, DecayingBlock decayingBlock, DecayingBlockHard decayingBlockHard, Identifiers identifiers) {
        super(provider::get, Stream.of(decayingBlock, decayingBlockHard).collect(Collectors.toSet()), null);
        setRegistryName(identifiers.get("decaying"));
    }
}
