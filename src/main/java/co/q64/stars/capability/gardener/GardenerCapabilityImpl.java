package co.q64.stars.capability.gardener;

import co.q64.stars.capability.GardenerCapability;
import co.q64.stars.type.FleetingStage;
import co.q64.stars.type.FormingBlockType;
import co.q64.stars.type.forming.BrownFormingBlockType;
import lombok.Getter;
import lombok.Setter;

import javax.inject.Inject;
import java.util.ArrayDeque;
import java.util.Deque;

public class GardenerCapabilityImpl implements GardenerCapability {
    private @Setter @Getter int seeds = 0, keys = 0, seedVisibility = 3, seedsSincePink = 0, totalSeeds = 0;
    private @Setter @Getter FleetingStage fleetingStage = FleetingStage.NONE;
    private @Setter @Getter FormingBlockType lastSeed;
    private @Getter Deque<FormingBlockType> nextSeeds = new ArrayDeque<>();

    @Inject
    protected GardenerCapabilityImpl(BrownFormingBlockType brownFormingBlockType) {
        this.lastSeed = brownFormingBlockType;
    }
}
