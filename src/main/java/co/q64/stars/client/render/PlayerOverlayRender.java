package co.q64.stars.client.render;

import co.q64.stars.capability.GardenerCapability;
import co.q64.stars.item.KeyItem;
import co.q64.stars.type.FleetingStage;
import co.q64.stars.type.FormingBlockType;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
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
    protected @Inject GuiDynamicRender guiDynamicRender;

    private long entryEffectTime, darknessEffectTime;
    private ItemStack keyStack;
    private Map<FormingBlockType, ItemStack> seedItemCache = new HashMap<>();
    private FleetingStage lastStage = FleetingStage.NONE;
    private GardenerCapability gardenerCapability;

    @Inject
    protected PlayerOverlayRender(KeyItem keyItem) {
        this.keyStack = new ItemStack(keyItem);
    }

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
        Minecraft mc = Minecraft.getInstance();
        int windowWidth = mc.mainWindow.getScaledWidth(), windowHeight = mc.mainWindow.getScaledHeight();
        int centerX = windowWidth / 2;
        int color = gardenerCapability.getFleetingStage() == FleetingStage.DARK ? 0xFFFFFF : 0x000000;

        int width = (18 * 3) + 6;
        int height = 4 + 18;
        int startX = centerX - (width / 2);
        int x = startX;
        guiDynamicRender.drawGuiPanel(x, 0, width, height);
        int y = 2;
        x += 2;
        RenderHelper.enableGUIStandardItemLighting();
        for (int i = 0; i < 3; i++) {
            guiDynamicRender.drawItemSlot(x, y);
            if (gardenerCapability.getFleetingStage() == FleetingStage.LIGHT) {
                if (gardenerCapability.getNextSeeds().size() > 2 - i) {
                    FormingBlockType type = gardenerCapability.getNextSeeds().stream().skip(2 - i).findFirst().get();
                    ItemStack is = seedItemCache.computeIfAbsent(type, t -> new ItemStack(t.getItemProvider().get()));
                    Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(is, x + 1, y + 1);
                }
            } else {
                if (gardenerCapability.getKeys() > i) {
                    Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(keyStack, x + 1, y + 1);
                }
            }
            x = x + 18 + 1;
        }
        RenderHelper.disableStandardItemLighting();
        Minecraft.getInstance().getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
        String number = String.valueOf(gardenerCapability.getSeeds());
        float scale = 2.4f;
        GlStateManager.pushMatrix();
        GlStateManager.scalef(scale, scale, scale);
        mc.fontRenderer.drawString(number, (startX / scale) - mc.fontRenderer.getStringWidth(number) - 1, 1, color);
        GlStateManager.popMatrix();
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
