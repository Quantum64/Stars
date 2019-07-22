package co.q64.stars.client.util;

import co.q64.stars.capability.GardenerCapability;
import co.q64.stars.client.render.PlayerOverlayRender;
import co.q64.stars.util.ClientNetHandler;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ClientNetHandlerImpl implements ClientNetHandler {
    protected @Inject PlayerOverlayRender playerOverlayRender;

    protected @Inject ClientNetHandlerImpl() {}

    public void playDarknessEffect() {
        playerOverlayRender.playDarknessEffect();
    }

    public void playEntryEffect() {

    }

    public void updateOverlay(GardenerCapability gardenerCapability) {
        playerOverlayRender.setGardenerCapability(gardenerCapability);
    }
}
