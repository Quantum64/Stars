package co.q64.stars.client.listener;

import co.q64.stars.client.render.ExtraWorldRender;
import co.q64.stars.client.render.PlayerOverlayRender;
import co.q64.stars.dimension.FleetingDimension;
import co.q64.stars.listener.Listener;
import co.q64.stars.net.PacketManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ForgeIngameGui;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ClientPlayerListener implements Listener {
    protected @Inject PacketManager packetManager;
    protected @Inject PlayerOverlayRender playerOverlayRender;
    protected @Inject ExtraWorldRender extraWorldRender;

    private Boolean autoJump;
    private boolean pressingJump;

    protected @Inject ClientPlayerListener() {}

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.getEntity() == Minecraft.getInstance().player) {
            if (event.getWorld().getDimension() instanceof FleetingDimension) {
                ForgeIngameGui.renderVignette = false;
                autoJump = Minecraft.getInstance().gameSettings.autoJump;
                Minecraft.getInstance().gameSettings.autoJump = false;
            } else {
                ForgeIngameGui.renderVignette = true;
                if (autoJump != null) {
                    Minecraft.getInstance().gameSettings.autoJump = autoJump;
                    autoJump = null;
                }
            }
        }
    }

    @SubscribeEvent
    public void onKeyInput(KeyInputEvent event) {
        if (Minecraft.getInstance().player == null || Minecraft.getInstance().player.getEntityWorld() == null || Minecraft.getInstance().gameSettings == null) {
            return;
        }
        if (Minecraft.getInstance().player.getEntityWorld().getDimension() instanceof FleetingDimension) {
            if (Minecraft.getInstance().gameSettings.keyBindJump.isKeyDown()) {
                if (!pressingJump) {
                    pressingJump = true;
                    packetManager.getChannel().sendToServer(packetManager.getUpdateJumpPacketFactory().create(true));
                }
            } else {
                if (pressingJump) {
                    pressingJump = false;
                    packetManager.getChannel().sendToServer(packetManager.getUpdateJumpPacketFactory().create(false));
                }
            }
            if (Minecraft.getInstance().gameSettings.keyBindSneak.isPressed()) {
                packetManager.getChannel().sendToServer(packetManager.getPlantSeedPacketFactory().create());
            }
        }
    }

    @SubscribeEvent
    public void onOverlayRender(RenderGameOverlayEvent.Post event) {
        playerOverlayRender.renderOverlay();
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        extraWorldRender.render();
    }
}
