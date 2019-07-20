package co.q64.stars.tile.type;

import co.q64.stars.block.AirDecayBlock;
import co.q64.stars.block.DarkAirBlock;
import co.q64.stars.tile.ForceRenderCullTile;
import co.q64.stars.util.Identifiers;
import net.minecraft.tileentity.TileEntityType;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
public class ForceRenderCullTileType extends TileEntityType<ForceRenderCullTile> {

    protected @Inject ForceRenderCullTileType(Provider<ForceRenderCullTile> provider, DarkAirBlock darkAirBlock, AirDecayBlock airDecayBlock, Identifiers identifiers) {
        super(provider::get, Stream.of(darkAirBlock, airDecayBlock).collect(Collectors.toSet()), null);
        setRegistryName(identifiers.get("force_render_cull"));
    }
}
