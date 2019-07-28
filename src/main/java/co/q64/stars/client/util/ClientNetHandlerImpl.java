package co.q64.stars.client.util;

import co.q64.stars.capability.GardenerCapability;
import co.q64.stars.client.render.PlayerOverlayRender;
import co.q64.stars.net.ClientNetHandler;
import co.q64.stars.net.packets.ClientFadePacket.FadeMode;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ClientNetHandlerImpl implements ClientNetHandler {
    protected @Inject PlayerOverlayRender playerOverlayRender;

    protected @Inject ClientNetHandlerImpl() {}

    public void fade(FadeMode mode, long time) {
        playerOverlayRender.fade(mode, time);
    }

    public void updateOverlay(GardenerCapability gardenerCapability) {
        playerOverlayRender.setGardenerCapability(gardenerCapability);
    }
}
