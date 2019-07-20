package co.q64.stars.tile.type;

import co.q64.stars.block.DoorBlock;
import co.q64.stars.tile.DoorTile;
import co.q64.stars.util.Identifiers;
import net.minecraft.tileentity.TileEntityType;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Collections;

@Singleton
public class DoorTileType extends TileEntityType<DoorTile> {

    protected @Inject DoorTileType(Provider<DoorTile> provider, DoorBlock type, Identifiers identifiers) {
        super(provider::get, Collections.singleton(type), null);
        setRegistryName(identifiers.get("door"));
    }
}
