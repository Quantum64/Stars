package co.q64.stars.tile.type;

import co.q64.stars.block.DarknessEdgeBlock;
import co.q64.stars.tile.DarknessEdgeTile;
import co.q64.stars.util.Identifiers;
import net.minecraft.tileentity.TileEntityType;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Collections;

@Singleton
public class DarknessEdgeTileType extends TileEntityType<DarknessEdgeTile> {

    protected @Inject DarknessEdgeTileType(Provider<DarknessEdgeTile> provider, DarknessEdgeBlock type, Identifiers identifiers) {
        super(provider::get, Collections.singleton(type), null);
        setRegistryName(identifiers.get("darkness_edge"));
    }
}
