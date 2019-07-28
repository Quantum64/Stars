package co.q64.stars.client.render;

import co.q64.stars.capability.GardenerCapability;
import co.q64.stars.dimension.StarsDimension;
import co.q64.stars.item.KeyItem;
import co.q64.stars.net.packets.ClientFadePacket.FadeMode;
import co.q64.stars.type.FleetingStage;
import co.q64.stars.type.FormingBlockType;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class PlayerOverlayRender {
    private static final long LOST_EFFECT_TIME = 15000;

    protected @Inject ExtraWorldRender extraWorldRender;
    protected @Inject GuiDynamicRender guiDynamicRender;

    private long fadeStart, fadeTime;
    private FadeMode fadeMode = FadeMode.FADE_FROM_BLACK;
    private ItemStack keyStack;
    private Map<FormingBlockType, ItemStack> seedItemCache = new HashMap<>();
    private FleetingStage lastStage = FleetingStage.NONE;
    private GardenerCapability gardenerCapability;
    private int lastNextSeeds, lastSeeds, tick;
    private int animationSlot, animationTicks, animationIndex;
    private List<FormingBlockType> types;
    private boolean planted = false;

    @Inject
    protected PlayerOverlayRender(KeyItem keyItem, Set<FormingBlockType> types) {
        this.keyStack = new ItemStack(keyItem);
        this.types = types.stream().filter(FormingBlockType::canGrow).collect(Collectors.toList());
    }

    public void renderOverlay() {
        renderHud();
        renderFadeEffect();
    }

    private void renderFadeEffect() {
        long now = System.currentTimeMillis();
        long currentFadeTime = fadeStart - now + fadeTime;
        if (currentFadeTime > 0 || fadeMode == FadeMode.FADE_TO_BLACK || fadeMode == FadeMode.FADE_TO_WHITE) {
            float progress = (float) (currentFadeTime / Double.valueOf(fadeTime));
            if (progress > 1.0) {
                progress = 1.0f;
            }
            float r = 0, b = 0, g = 0, a = progress;
            switch (fadeMode) {
                case FADE_TO_WHITE:
                    a = 1 - progress;
                    // fall-through
                case FADE_FROM_WHITE:
                    r = 1;
                    b = 1;
                    g = 1;
                    break;
                case FADE_FROM_BLACK:
                    // fall-through
                case FADE_TO_BLACK:
                    a = 1 - progress;
                    break;
            }
            drawScreenColorOverlay(r, g, b, a * a);
        }
    }

    private void renderHud() {
        PlayerEntity player = Minecraft.getInstance().player;
        if (gardenerCapability == null || player == null || player.getEntityWorld() == null || !(player.getEntityWorld().getDimension() instanceof StarsDimension)) {
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
        for (int i = 0; i < 3; i++) {
            guiDynamicRender.drawItemSlot(x, y);
            RenderHelper.enableGUIStandardItemLighting();
            if (gardenerCapability.getFleetingStage() == FleetingStage.LIGHT) {
                if (i > animationSlot && gardenerCapability.getNextSeeds().size() > 2 - i) {
                    FormingBlockType type = gardenerCapability.getNextSeeds().stream().skip(2 - i).findFirst().get();
                    ItemStack is = seedItemCache.computeIfAbsent(type, t -> new ItemStack(t.getItemProvider().get()));
                    Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(is, x + 1, y + 1);
                } else if (i == animationSlot && gardenerCapability.getNextSeeds().size() > 2 - i) {
                    FormingBlockType type = types.stream().skip(animationIndex % types.size()).findFirst().get();
                    ItemStack is = seedItemCache.computeIfAbsent(type, t -> new ItemStack(t.getItemProvider().get()));
                    Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(is, x + 1, y + 1);
                }
            } else {
                if (gardenerCapability.getKeys() > i) {
                    Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(keyStack, x + 1, y + 1);
                }
            }
            x = x + 18 + 1;
            RenderHelper.disableStandardItemLighting();
        }
        Minecraft.getInstance().getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
        String number = String.valueOf(gardenerCapability.getSeeds());
        float scale = 2.4f;
        GlStateManager.pushMatrix();
        GlStateManager.scalef(scale, scale, scale);
        mc.fontRenderer.drawString(number, (startX / scale) - mc.fontRenderer.getStringWidth(number) - 1, 1, color);
        GlStateManager.popMatrix();
    }

    public void fade(FadeMode mode, long time) {
        this.fadeMode = mode;
        this.fadeStart = System.currentTimeMillis();
        this.fadeTime = time;
    }

    private void stageChange(FleetingStage stage) {
        switch (stage) {
            case LIGHT:
                animationSlot = 2;
                lastNextSeeds = 3;
                lastSeeds = 0;
                planted = false;
                break;
            case DARK:
                extraWorldRender.setAnimationStart(System.currentTimeMillis());
                fade(FadeMode.FADE_FROM_BLACK, 1000);
                break;
        }
    }

    public void setGardenerCapability(GardenerCapability gardenerCapability) {
        this.gardenerCapability = gardenerCapability;
        if (gardenerCapability.getFleetingStage() != lastStage) {
            stageChange(gardenerCapability.getFleetingStage());
            lastStage = gardenerCapability.getFleetingStage();
        } else {
            int nextSeeds = gardenerCapability.getNextSeeds().size();
            int seeds = gardenerCapability.getSeeds();
            if (nextSeeds > lastNextSeeds) {
                if (nextSeeds == 1) {
                    animationSlot = 2;
                } else if (nextSeeds == 2) {
                    animationSlot = 1;
                } else if (nextSeeds == 3) {
                    animationSlot = 0;
                }
            } else {
                if (seeds < lastSeeds) {
                    animationSlot++;
                    planted = true;
                }
            }
            lastNextSeeds = nextSeeds;
            lastSeeds = seeds;
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

    private static final int TICKS_PER_ITEM = 3;
    private static final int ITERATIONS_PER_ANIMATION = 20;

    public void tick() {
        if (!planted && tick % 10 == 0) {
            String keyName = Minecraft.getInstance().gameSettings.keyBindSneak.getLocalizedName().toLowerCase();
            if (keyName.contains(" ")) {
                keyName = keyName.split(" ")[1];
            }
            Minecraft.getInstance().ingameGUI.addChatMessage(ChatType.GAME_INFO, new StringTextComponent(TextFormatting.GRAY + "Touch " + TextFormatting.BOLD + keyName + TextFormatting.GRAY + " to grow."));
        }
        if (animationTicks > 0) {
            animationTicks--;
            if (animationTicks == 0) {
                animationIndex--;
                if (animationIndex > 0) {
                    animationTicks = Math.max(15 - (animationIndex / 2), 4);
                } else {
                    if (animationSlot >= 0) {
                        animationSlot--;
                    }
                }
            }
        } else {
            if (animationSlot >= 0) {
                animationTicks = TICKS_PER_ITEM;
                animationIndex = ITERATIONS_PER_ANIMATION;
            }
        }
        tick++;
    }
}
