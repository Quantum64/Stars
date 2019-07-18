package co.q64.stars.tile.type;

import co.q64.stars.block.DarknessEdgeBlock;
import co.q64.stars.tile.DarknessEdgeTile;
import co.q64.stars.tile.DarknessEdgeTileFactory;
import co.q64.stars.util.Identifiers;
import net.minecraft.tileentity.TileEntityType;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;

@Singleton
public class DarknessEdgeTileType extends TileEntityType<DarknessEdgeTile> {

    protected @Inject DarknessEdgeTileType(DarknessEdgeTileFactory factory, DarknessEdgeBlock type, Identifiers identifiers) {
        super(factory::create, Collections.singleton(type), null);
        setRegistryName(identifiers.get("darkness_edge"));
    }
}
