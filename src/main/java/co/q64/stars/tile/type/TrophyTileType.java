package co.q64.stars.tile.type;

import co.q64.stars.block.TrophyBlock;
import co.q64.stars.tile.TrophyTile;
import co.q64.stars.util.Identifiers;
import net.minecraft.tileentity.TileEntityType;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Collections;

@Singleton
public class TrophyTileType extends TileEntityType<TrophyTile> {

    protected @Inject TrophyTileType(Provider<TrophyTile> provider, TrophyBlock trophyBlock, Identifiers identifiers) {
        super(provider::get, Collections.singleton(trophyBlock), null);
        setRegistryName(identifiers.get("trophy"));
    }
}
