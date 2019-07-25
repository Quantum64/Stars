package co.q64.stars.server;

import co.q64.stars.capability.GardenerCapability;
import co.q64.stars.net.ClientNetHandler;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MockClientNetHandler implements ClientNetHandler {
    protected @Inject MockClientNetHandler() {}

    public void playDarknessEffect() {}

    public void playEntryEffect() {}

    public void updateOverlay(GardenerCapability gardenerCapability) {}
}
