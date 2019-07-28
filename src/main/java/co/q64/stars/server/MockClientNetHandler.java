package co.q64.stars.server;

import co.q64.stars.capability.GardenerCapability;
import co.q64.stars.net.ClientNetHandler;
import co.q64.stars.net.packets.ClientFadePacket.FadeMode;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MockClientNetHandler implements ClientNetHandler {
    protected @Inject MockClientNetHandler() {}

    public void fade(FadeMode mode, long time) {}

    public void updateOverlay(GardenerCapability gardenerCapability) {}
}
