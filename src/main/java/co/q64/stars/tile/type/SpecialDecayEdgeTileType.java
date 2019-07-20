package co.q64.stars.tile.type;

import co.q64.stars.block.SpecialDecayEdgeBlock;
import co.q64.stars.tile.SpecialDecayEdgeTile;
import co.q64.stars.util.Identifiers;
import net.minecraft.tileentity.TileEntityType;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Collections;

@Singleton
public class SpecialDecayEdgeTileType extends TileEntityType<SpecialDecayEdgeTile> {

    protected @Inject SpecialDecayEdgeTileType(Provider<SpecialDecayEdgeTile> provider, SpecialDecayEdgeBlock type, Identifiers identifiers) {
        super(provider::get, Collections.singleton(type), null);
        setRegistryName(identifiers.get("special_decay_edge"));
    }
}
