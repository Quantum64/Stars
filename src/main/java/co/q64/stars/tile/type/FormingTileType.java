package co.q64.stars.tile.type;

import co.q64.stars.block.FormingBlock;
import co.q64.stars.tile.FormingTile;
import co.q64.stars.tile.FormingTileFactory;
import co.q64.stars.util.Identifiers;
import net.minecraft.tileentity.TileEntityType;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class FormingTileType extends TileEntityType<FormingTile> {

    protected @Inject FormingTileType(FormingTileFactory factory, FormingBlock type, Identifiers identifiers) {
        super(factory::create, Collections.singleton(type), null);
        setRegistryName(identifiers.get("forming"));
    }
}
