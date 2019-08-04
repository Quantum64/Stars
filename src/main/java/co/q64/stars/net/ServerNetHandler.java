package co.q64.stars.net;

import co.q64.stars.capability.GardenerCapability;
import co.q64.stars.util.FleetingManager;
import co.q64.stars.util.HubManager;
import co.q64.stars.util.PlayerManager;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.capabilities.Capability;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ServerNetHandler {
    protected @Inject Capability<GardenerCapability> gardenerCapability;
    protected @Inject PlayerManager playerManager;
    protected @Inject FleetingManager fleetingManager;
    protected @Inject HubManager hubManager;

    protected @Inject ServerNetHandler() {}

    public void updateJumpStatus(ServerPlayerEntity player, boolean jumping) {
        fleetingManager.updateJumpStatus(player, jumping);
    }

    public void grow(ServerPlayerEntity player) {
        fleetingManager.tryEnter(player);
        playerManager.grow(player);
    }

    public void lost(ServerPlayerEntity player) {
        hubManager.lost(player);
    }
}
