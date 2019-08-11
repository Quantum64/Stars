package co.q64.stars.client.listener;

import co.q64.stars.client.render.ExtraWorldRender;
import co.q64.stars.client.render.PlayerOverlayRender;
import co.q64.stars.client.util.ClientSound;
import co.q64.stars.client.util.LoseWayKeyBinding;
import co.q64.stars.dimension.StarsDimension;
import co.q64.stars.dimension.fleeting.FleetingDimension;
import co.q64.stars.dimension.hub.HubDimension;
import co.q64.stars.listener.Listener;
import co.q64.stars.net.PacketManager;
import co.q64.stars.type.FleetingStage;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ForgeIngameGui;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ClientPlayerListener implements Listener {
    protected @Inject PacketManager packetManager;
    protected @Inject PlayerOverlayRender playerOverlayRender;
    protected @Inject ExtraWorldRender extraWorldRender;
    protected @Inject LoseWayKeyBinding loseWayKeyBinding;
    protected @Inject ClientSound clientSound;

    private Boolean autoJump;
    private Integer renderDistance;
    private boolean pressingJump;

    protected @Inject ClientPlayerListener() {}

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.getEntity() == Minecraft.getInstance().player) {
            if (event.getWorld().getDimension() instanceof StarsDimension) {
                ForgeIngameGui.renderVignette = false;
                autoJump = Minecraft.getInstance().gameSettings.autoJump;
                renderDistance = Minecraft.getInstance().gameSettings.renderDistanceChunks;
                Minecraft.getInstance().gameSettings.autoJump = false;
                Minecraft.getInstance().gameSettings.renderDistanceChunks = 2;
            } else {
                ForgeIngameGui.renderVignette = true;
                if (autoJump != null) {
                    Minecraft.getInstance().gameSettings.autoJump = autoJump;
                    autoJump = null;
                }
                if (renderDistance != null) {
                    Minecraft.getInstance().gameSettings.renderDistanceChunks = renderDistance;
                    renderDistance = null;
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent event) {
        if (event.phase == Phase.END && event.player == Minecraft.getInstance().player) {
            if (event.player.world.getDimension() instanceof HubDimension || (event.player.world.getDimension() instanceof FleetingDimension && playerOverlayRender.getLastStage() == FleetingStage.LIGHT)) {
                Minecraft.getInstance().gameSettings.renderDistanceChunks = 2; // No cheating
            } else if ((event.player.world.getDimension() instanceof FleetingDimension)) {
                Minecraft.getInstance().gameSettings.renderDistanceChunks = 6;
            }
        }
    }

    @SubscribeEvent
    public void onClientTick(ClientTickEvent event) {
        clientSound.tick();
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.getEntityWorld().getDimension() instanceof StarsDimension) {
            playerOverlayRender.tick();
        }
    }

    @SubscribeEvent
    public void onKeyInput(KeyInputEvent event) {
        if (Minecraft.getInstance().player == null || Minecraft.getInstance().player.getEntityWorld() == null || Minecraft.getInstance().gameSettings == null) {
            return;
        }
        if (Minecraft.getInstance().player.getEntityWorld().getDimension() instanceof StarsDimension) {
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
            if (playerOverlayRender.getLastStage() == FleetingStage.DARK && loseWayKeyBinding.isKeyDown()) {
                long now = System.currentTimeMillis();
                long time = playerOverlayRender.getLostTime();
                if (time - now > 70000) {
                    time = now + 70000;
                }
                time -= 500;
                playerOverlayRender.setLostTime(time);
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
