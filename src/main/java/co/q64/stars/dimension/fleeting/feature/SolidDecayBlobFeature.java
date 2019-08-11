package co.q64.stars.dimension.fleeting.feature;

import co.q64.stars.block.DecayBlock.DecayBlockSolid;
import co.q64.stars.util.Identifiers;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SolidDecayBlobFeature extends DecayBlobFeature {
    protected @Inject SolidDecayBlobFeature(Identifiers identifiers, DecayBlockSolid decayBlockSolid) {
        super(decayBlockSolid);
        setRegistryName(identifiers.get("solid_decay_blob"));
    }
}
