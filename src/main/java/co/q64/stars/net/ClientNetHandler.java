package co.q64.stars.net;

import co.q64.stars.capability.GardenerCapability;
import co.q64.stars.net.packets.ClientFadePacket.FadeMode;

public interface ClientNetHandler {
    public void fade(FadeMode mode, long time);

    public void updateOverlay(GardenerCapability gardenerCapability);
}
