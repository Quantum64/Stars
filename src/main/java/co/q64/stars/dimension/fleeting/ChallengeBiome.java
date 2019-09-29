package co.q64.stars.dimension.fleeting;

import co.q64.stars.util.Identifiers;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ChallengeBiome extends FleetingBiome {

    protected @Inject ChallengeBiome(Identifiers identifiers) {
        super();
        setRegistryName(identifiers.get("challenge"));
    }
}
