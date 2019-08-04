package co.q64.stars.tile.type;

import co.q64.stars.block.TubeAirBlock;
import co.q64.stars.block.TubeDarknessBlock;
import co.q64.stars.tile.TubeTile;
import co.q64.stars.util.Identifiers;
import net.minecraft.tileentity.TileEntityType;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
public class TubeTileType extends TileEntityType<TubeTile> {

    protected @Inject TubeTileType(Provider<TubeTile> provider, TubeDarknessBlock tubeDarknessBlock, TubeAirBlock tubeAirBlock, Identifiers identifiers) {
        super(provider::get, Stream.of(tubeDarknessBlock, tubeAirBlock).collect(Collectors.toSet()), null);
        setRegistryName(identifiers.get("tube"));
    }
}
