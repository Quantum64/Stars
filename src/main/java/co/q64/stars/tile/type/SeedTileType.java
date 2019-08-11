package co.q64.stars.tile.type;

import co.q64.stars.block.SeedBlock;
import co.q64.stars.block.SeedBlock.SeedBlockHard;
import co.q64.stars.tile.SeedTile;
import co.q64.stars.util.Identifiers;
import net.minecraft.tileentity.TileEntityType;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
public class SeedTileType extends TileEntityType<SeedTile> {

    protected @Inject SeedTileType(Provider<SeedTile> provider, SeedBlock seedBlock, SeedBlockHard seedBlockHard, Identifiers identifiers) {
        super(provider::get, Stream.of(seedBlock, seedBlockHard).collect(Collectors.toSet()), null);
        setRegistryName(identifiers.get("seed"));
    }
}
