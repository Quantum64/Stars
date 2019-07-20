package co.q64.stars.tile.type;

import co.q64.stars.block.FormingBlock;
import co.q64.stars.tile.FormingTile;
import co.q64.stars.util.Identifiers;
import net.minecraft.tileentity.TileEntityType;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Collections;

@Singleton
public class FormingTileType extends TileEntityType<FormingTile> {

    protected @Inject FormingTileType(Provider<FormingTile> provider, FormingBlock type, Identifiers identifiers) {
        super(provider::get, Collections.singleton(type), null);
        setRegistryName(identifiers.get("forming"));
    }
}
