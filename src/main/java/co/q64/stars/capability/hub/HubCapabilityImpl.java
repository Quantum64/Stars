package co.q64.stars.capability.hub;

import co.q64.stars.capability.HubCapability;
import lombok.Getter;
import lombok.Setter;

import javax.inject.Inject;

public class HubCapabilityImpl implements HubCapability {
    private @Setter @Getter int nextIndex;

    protected @Inject HubCapabilityImpl() {}
}
