package co.q64.stars.tile.type;

import co.q64.stars.block.SeedBlock;
import co.q64.stars.tile.SeedTile;
import co.q64.stars.util.Identifiers;
import net.minecraft.tileentity.TileEntityType;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Collections;

@Singleton
public class SeedTileType extends TileEntityType<SeedTile> {

    protected @Inject SeedTileType(Provider<SeedTile> provider, SeedBlock type, Identifiers identifiers) {
        super(provider::get, Collections.singleton(type), null);
        setRegistryName(identifiers.get("seed"));
    }
}
