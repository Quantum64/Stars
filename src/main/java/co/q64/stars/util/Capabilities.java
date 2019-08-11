package co.q64.stars.util;

import co.q64.stars.capability.GardenerCapability;
import co.q64.stars.capability.HubCapability;
import dagger.Lazy;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import org.lwjgl.system.CallbackI.P;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.function.Consumer;

@Singleton
public class Capabilities {
    protected @Inject Provider<Capability<GardenerCapability>> gardener;
    protected @Inject Provider<Capability<HubCapability>> hub;

    protected @Inject Capabilities() {}

    public void gardener(PlayerEntity player, Consumer<GardenerCapability> task) {
        player.getCapability(gardener.get()).ifPresent(task::accept);
    }

    public void hub(World world, Consumer<HubCapability> task) {
        world.getCapability(hub.get()).ifPresent(task::accept);
    }
}
