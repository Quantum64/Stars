package co.q64.stars.client.render;

import co.q64.stars.block.GatewayBlock;
import co.q64.stars.capability.GardenerCapability;
import co.q64.stars.dimension.StarsDimension;
import co.q64.stars.dimension.hub.HubDimension;
import co.q64.stars.item.KeyItem;
import co.q64.stars.level.LevelType;
import co.q64.stars.net.PacketManager;
import co.q64.stars.net.packets.ClientFadePacket.FadeMode;
import co.q64.stars.type.FleetingStage;
import co.q64.stars.type.FormingBlockType;
import com.mojang.blaze3d.platform.GlStateManager;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Singleton
public class PlayerOverlayRender {
    private static final long LOST_TIME = 80000;
    private static final long LOST_TIME_FIRST_BONUS = 5000;

    protected @Inject ExtraWorldRender extraWorldRender;
    protected @Inject GuiDynamicRender guiDynamicRender;
    protected @Inject PacketManager packetManager;

    private long fadeStart, fadeTime;
    private FadeMode fadeMode = FadeMode.FADE_FROM_BLACK;
    private ItemStack keyStack;
    private Map<FormingBlockType, ItemStack> seedItemCache = new HashMap<>();
    private @Getter FleetingStage lastStage = FleetingStage.NONE;
    private GardenerCapability gardenerCapability;
    private int lastNextSeeds, lastSeeds, tick;
    private @Getter @Setter long lostTime;
    private int animationSlot, animationTicks, animationIndex;
    private List<FormingBlockType> types;
    private boolean planted = false, sentLost = false;

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
                    r = 1;
                    b = 1;
                    g = 1;
                    break;
                case FADE_FROM_WHITE:
                    r = 1;
                    b = 1;
                    g = 1;
                    break;
                case FADE_TO_BLACK:
                    a = 1 - progress;
                    break;
                case FADE_FROM_BLACK:
                    break;

            }
            drawScreenColorOverlay(r, g, b, a * a);
        }
        if (lostTime > 0) {
            double progress = (lostTime - now + 10000) / Double.valueOf(LOST_TIME);
            if (progress > 1.0f) {
                progress = 1.0f;
            }
            if (progress < 0f) {
                progress = 0f;
            }
            double a = 1f - progress;
            a = a * 0.75;
            a = a * a;
            drawScreenColorOverlay(1, 1, 1, a);
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

        boolean pink = gardenerCapability.getFleetingStage() == FleetingStage.LIGHT && gardenerCapability.getLevelType() == LevelType.PINK;
        int width = (18 * 3) + 6;
        width -= pink ? (18 * 2) + 2 : 0;
        int height = 4 + 18;
        int startX = centerX - (width / 2);
        int x = startX;
        guiDynamicRender.drawGuiPanel(x, 0, width, height);
        int y = 2;
        x += 2;
        for (int i = pink ? 2 : 0; i < 3; i++) {
            guiDynamicRender.drawItemSlot(x, y);
            RenderHelper.enableGUIStandardItemLighting();
            if (gardenerCapability.getFleetingStage() == FleetingStage.LIGHT || gardenerCapability.getFleetingStage() == FleetingStage.NONE) {
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
        String number = String.valueOf(gardenerCapability.getSeeds());
        float scale = 2.4f;
        GlStateManager.pushMatrix();
        GlStateManager.scalef(scale, scale, scale);
        mc.fontRenderer.drawString(number, (startX / scale) - mc.fontRenderer.getStringWidth(number) - 1, 1, color);
        GlStateManager.popMatrix();
        mc.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
    }

    public void fade(FadeMode mode, long time) {
        this.fadeMode = mode;
        this.fadeStart = System.currentTimeMillis();
        this.fadeTime = time;
    }

    private void stageChange(FleetingStage stage) {
        switch (stage) {
            case NONE:
            case LIGHT:
                animationSlot = 2;
                lastNextSeeds = 3;
                lastSeeds = 0;
                planted = false;
                sentLost = false;
                break;
            case DARK:
                //extraWorldRender.setAnimationStart(System.currentTimeMillis());
                fade(FadeMode.FADE_FROM_BLACK, 4000);
                break;
        }
    }

    public void setGardenerCapability(GardenerCapability gardenerCapability) {
        this.gardenerCapability = gardenerCapability;
        if (gardenerCapability.getFleetingStage() != lastStage) {
            stageChange(gardenerCapability.getFleetingStage());
            lastStage = gardenerCapability.getFleetingStage();
            lostTime = lastStage == FleetingStage.DARK ? System.currentTimeMillis() + LOST_TIME + LOST_TIME_FIRST_BONUS : 0;
        }
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
        if (lastStage == FleetingStage.DARK) {
            if (seeds > lastSeeds) {
                lostTime = System.currentTimeMillis() + LOST_TIME;
            }
        }
        lastNextSeeds = nextSeeds;
        lastSeeds = seeds;
    }

    private void drawScreenColorOverlay(double r, double g, double b, double a) {
        int width = Minecraft.getInstance().mainWindow.getWidth();
        int height = Minecraft.getInstance().mainWindow.getHeight();
        GlStateManager.disableAlphaTest();
        GlStateManager.disableDepthTest();
        GlStateManager.disableTexture();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color4f((float) r, (float) g, (float) b, (float) a);
        GL11.glColor4d(r, g, b, a);
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
        if (tick % 10 == 0) {
            Supplier<String> keyName = () -> {
                String result = Minecraft.getInstance().gameSettings.keyBindSneak.getLocalizedName().toLowerCase();
                if (result.contains(" ")) {
                    result = result.split(" ")[1];
                }
                return result;
            };
            if (lastStage == FleetingStage.LIGHT && !planted) {
                Minecraft.getInstance().ingameGUI.addChatMessage(ChatType.GAME_INFO, new StringTextComponent(TextFormatting.GRAY + "Touch " + TextFormatting.BOLD + keyName.get() + TextFormatting.GRAY + " to grow."));
            } else if (lastStage == FleetingStage.NONE) {
                if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.getEntityWorld() != null) {
                    World world = Minecraft.getInstance().player.getEntityWorld();
                    if (world.getDimension() instanceof HubDimension) {
                        BlockState state = world.getBlockState(Minecraft.getInstance().player.getPosition().offset(Direction.DOWN));
                        if (state.getBlock() instanceof GatewayBlock && !state.get(GatewayBlock.COMPLETE)) {
                            Minecraft.getInstance().ingameGUI.addChatMessage(ChatType.GAME_INFO, new StringTextComponent(TextFormatting.GRAY + "Touch " + keyName.get()));
                        }
                    }
                }
            }
        }
        if (animationTicks > 0) {
            animationTicks -= 1;
            if (animationTicks <= 0) {
                animationIndex--;
                if (animationIndex > 0) {
                    animationTicks = Math.max(15 - (animationIndex / 1), 4);
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
        if (lastStage == FleetingStage.DARK) {
            long now = System.currentTimeMillis();
            if (now > lostTime && !sentLost) {
                packetManager.getChannel().sendToServer(packetManager.getLostPacketFactory().create());
                sentLost = true;
            }
        }
        tick++;
    }
}
