package co.q64.stars.client.render;

import co.q64.stars.capability.GardenerCapability;
import co.q64.stars.type.FleetingStage;
import co.q64.stars.type.FormingBlockType;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class PlayerOverlayRender {
    private static final long ENTRY_EFFECT_TIME = 10000, DARKNESS_EFFECT_TIME = 1000, LOST_EFFECT_TIME = 15000;

    protected @Inject ExtraWorldRender extraWorldRender;

    private long entryEffectTime, darknessEffectTime;
    private Map<FormingBlockType, ItemStack> seedItemCache = new HashMap<>();
    private FleetingStage lastStage = FleetingStage.NONE;
    private GardenerCapability gardenerCapability;

    protected @Inject PlayerOverlayRender() {}

    public void renderOverlay() {
        long now = System.currentTimeMillis();
        long entryEffectMs = entryEffectTime - now + ENTRY_EFFECT_TIME;
        long darknessEffectMs = darknessEffectTime - now + DARKNESS_EFFECT_TIME;
        if (darknessEffectMs > 0) {
            double progress = (darknessEffectMs / Double.valueOf(DARKNESS_EFFECT_TIME));
            if (progress > 1.0) {
                progress = 1.0;
            }
            drawScreenColorOverlay(0, 0, 0, (float) progress);
        }
        if (gardenerCapability == null) {
            return;
        }
        int color = gardenerCapability.getFleetingStage() == FleetingStage.DARK ? 0xFFFFFF : 0x000000;
        String text = "Seeds: " + gardenerCapability.getSeeds() + ", Keys: " + gardenerCapability.getKeys();
        Minecraft.getInstance().fontRenderer.drawString(text, 10, 10, color);
        Minecraft.getInstance().fontRenderer.drawString("Next seeds:", 10, 20, color);
        int x = 10;
        int y = 40;
        RenderHelper.enableGUIStandardItemLighting();
        for (FormingBlockType type : gardenerCapability.getNextSeeds()) {
            ItemStack is = seedItemCache.computeIfAbsent(type, t -> new ItemStack(t.getItemProvider().get()));
            Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(is, x, y);
            y += 20;
        }
        RenderHelper.disableStandardItemLighting();
    }

    public void playEntryEffect() {

    }

    public void playDarknessEffect() {

    }

    private void stageChange(FleetingStage stage) {
        switch (stage) {
            case LIGHT:
                String keyName = Minecraft.getInstance().gameSettings.keyBindSneak.getLocalizedName().toLowerCase();
                if (keyName.contains(" ")) {
                    keyName = keyName.split(" ")[1];
                }
                Minecraft.getInstance().ingameGUI.addChatMessage(ChatType.GAME_INFO, new StringTextComponent(TextFormatting.GRAY + "Touch " + TextFormatting.BOLD + keyName + TextFormatting.GRAY + " to grow."));
                break;
            case DARK:
                extraWorldRender.setAnimationStart(System.currentTimeMillis());
                darknessEffectTime = System.currentTimeMillis();
                break;
        }
    }

    public void setGardenerCapability(GardenerCapability gardenerCapability) {
        this.gardenerCapability = gardenerCapability;
        if (gardenerCapability.getFleetingStage() != lastStage) {
            stageChange(gardenerCapability.getFleetingStage());
            lastStage = gardenerCapability.getFleetingStage();
        }
    }

    private void drawScreenColorOverlay(float r, float g, float b, float a) {
        int width = Minecraft.getInstance().mainWindow.getWidth();
        int height = Minecraft.getInstance().mainWindow.getHeight();
        GlStateManager.disableAlphaTest();
        GlStateManager.disableDepthTest();
        GlStateManager.disableTexture();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color4f(r, g, b, a);
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
