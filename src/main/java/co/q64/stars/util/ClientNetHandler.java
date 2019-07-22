package co.q64.stars.util;

import co.q64.stars.capability.GardenerCapability;

public interface ClientNetHandler {
    public void playDarknessEffect();

    public void playEntryEffect();

    public void updateOverlay(GardenerCapability gardenerCapability);
}
