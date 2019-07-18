package co.q64.stars.tile.type;

import co.q64.stars.block.DecayingBlock;
import co.q64.stars.tile.DecayingTile;
import co.q64.stars.tile.DecayingTileFactory;
import co.q64.stars.util.Identifiers;
import net.minecraft.tileentity.TileEntityType;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;

@Singleton
public class DecayingTileType extends TileEntityType<DecayingTile> {

    protected @Inject DecayingTileType(DecayingTileFactory factory, DecayingBlock type, Identifiers identifiers) {
        super(factory::create, Collections.singleton(type), null);
        setRegistryName(identifiers.get("decaying"));
    }
}
