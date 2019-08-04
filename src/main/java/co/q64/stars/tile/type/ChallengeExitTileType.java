package co.q64.stars.tile.type;

import co.q64.stars.block.ChallengeExitBlock;
import co.q64.stars.tile.ChallengeExitTile;
import co.q64.stars.util.Identifiers;
import net.minecraft.tileentity.TileEntityType;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Collections;

@Singleton
public class ChallengeExitTileType extends TileEntityType<ChallengeExitTile> {

    protected @Inject ChallengeExitTileType(Provider<ChallengeExitTile> provider, ChallengeExitBlock block, Identifiers identifiers) {
        super(provider::get, Collections.singleton(block), null);
        setRegistryName(identifiers.get("challenge_exit"));
    }
}
