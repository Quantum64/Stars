package co.q64.stars.client.listener;

import co.q64.stars.dimension.FleetingDimension;
import co.q64.stars.listener.Listener;
import co.q64.stars.net.PacketManager;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.ForgeIngameGui;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ClientPlayerListener implements Listener {
    private static final long ENTRY_EFFECT_TIME = 10000, DARKNESS_EFFECT_TIME = 5000;
    protected @Inject PacketManager packetManager;

    private long entryEffectTime, darknessEffectTime;
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
        }
    }

    @SubscribeEvent
    public void onOverlayRender(RenderGameOverlayEvent.Post event) {
        long now = System.currentTimeMillis();
        long entryEffectMs = entryEffectTime - now + ENTRY_EFFECT_TIME;
        long darknessEffectMs = darknessEffectTime - now + DARKNESS_EFFECT_TIME;
        if (darknessEffectMs > 0 || entryEffectMs > 0) {
            int width = Minecraft.getInstance().mainWindow.getWidth();
            int height = Minecraft.getInstance().mainWindow.getHeight();
            if (darknessEffectMs > 0) {
                double progress = (darknessEffectMs / Double.valueOf(DARKNESS_EFFECT_TIME));
                if (progress > 1.0) {
                    progress = 1.0;
                }
                GlStateManager.disableAlphaTest();
                GlStateManager.disableDepthTest();
                GlStateManager.disableTexture();
                GlStateManager.depthMask(false);
                GlStateManager.enableBlend();
                GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                GlStateManager.color4f(0f, 0f, 0f, (float) progress);
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder bufferbuilder = tessellator.getBuffer();
                bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
                bufferbuilder.pos(0, height, -90).endVertex();
                bufferbuilder.pos(width, height, -90).endVertex();
                bufferbuilder.pos(width, 0, -90).endVertex();
                bufferbuilder.pos(0, 0, -90).endVertex();
                tessellator.draw();
                GlStateManager.depthMask(true);
                GlStateManager.enableDepthTest();
                GlStateManager.enableAlphaTest();
                GlStateManager.enableTexture();
                GlStateManager.disableBlend();
                GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }

    public void playEntryEffect() {

    }

    public void playDarknessEffect() {
        darknessEffectTime = System.currentTimeMillis();
    }
}
