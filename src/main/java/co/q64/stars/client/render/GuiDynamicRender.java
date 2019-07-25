package co.q64.stars.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GuiDynamicRender {
    protected @Inject GuiDynamicRender() {}

    public void rect(ResourceLocation texture, int left, int top, int width, int height, int color) {
        rect(texture, left, top, width, height, 0, 0, 1, 1, color, 0);
    }

    public void rect(ResourceLocation texture, int left, int top, int width, int height, float u1, float v1, float u2, float v2, int color) {
        rect(texture, left, top, width, height, u1, v1, u2, v2, 0xFFFFFFFF, 0);
    }

    public void rect(ResourceLocation texture, int left, int top, int width, int height, float u1, float v1, float u2, float v2, int color, int z) {
        Minecraft.getInstance().getTextureManager().bindTexture(texture);
        if (width <= 0) {
            width = 1;
        }
        if (height <= 0) {
            height = 1;
        }
        float a = (color >> 24 & 255) / 255.0F;
        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color4f(r, g, b, 1.0f);
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(left, top + height, z).tex(u1, v2).endVertex();
        buffer.pos(left + width, top + height, z).tex(u2, v2).endVertex();
        buffer.pos(left + width, top, z).tex(u2, v1).endVertex();
        buffer.pos(left, top, z).tex(u1, v1).endVertex();
        tessellator.draw();
        GlStateManager.disableBlend();
    }

    public void rect(int left, int top, int width, int height, int color) {
        if (width <= 0) {
            width = 1;
        }
        if (height <= 0) {
            height = 1;
        }
        float a = (color >> 24 & 255) / 255.0F;
        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color4f(r, g, b, a);
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        buffer.pos(left, top + height, 0.0D).endVertex();
        buffer.pos(left + width, top + height, 0.0D).endVertex();
        buffer.pos(left + width, top, 0.0D).endVertex();
        buffer.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture();
        GlStateManager.disableBlend();
    }

    public void maskedRect(ResourceLocation mask, ResourceLocation texture, int left, int top, int width, int height) {
        rect(mask, left, top, width, height, 0, 0, 1, 1, 0xFFFFFFFF, 7);
        GlStateManager.enableDepthTest();
        GlStateManager.depthFunc(GL11.GL_EQUAL);
        rect(texture, left, top, width, height, 0, 0, 1, 1, 0xFFFFFFFF, 7);
        GlStateManager.depthFunc(GL11.GL_LESS);
        GlStateManager.disableDepthTest();
    }

    public void drawGuiPanel(int x, int y, int width, int height) {
        drawGuiPanel(x, y, width, height, 0xFF555555, 0xFFC6C6C6, 0xFFFFFFFF, 0xFF000000);
    }

    public void drawGuiPanel(int x, int y, int width, int height, int panelColor) {
        int shadowColor = multiplyColor(panelColor, 0.50f);
        int hilightColor = multiplyColor(panelColor, 1.25f);
        drawGuiPanel(x, y, width, height, shadowColor, panelColor, hilightColor, 0xFF000000);
    }

    public void drawGuiPanel(int x, int y, int width, int height, int shadow, int panel, int hilight, int outline) {
        rect(x + 3, y + 3, width - 6, height - 6, panel); //Main panel area

        rect(x + 2, y + 1, width - 4, 2, hilight); //Top hilight
        rect(x + 2, y + height - 3, width - 4, 2, shadow); //Bottom shadow
        rect(x + 1, y + 2, 2, height - 4, hilight); //Left hilight
        rect(x + width - 3, y + 2, 2, height - 4, shadow); //Right shadow
        rect(x + width - 3, y + 2, 1, 1, panel); //Topright non-hilight/non-shadow transition pixel
        rect(x + 2, y + height - 3, 1, 1, panel); //Bottomleft non-hilight/non-shadow transition pixel
        rect(x + 3, y + 3, 1, 1, hilight); //Topleft round hilight pixel
        rect(x + width - 4, y + height - 4, 1, 1, shadow); //Bottomright round shadow pixel

        rect(x + 2, y, width - 4, 1, outline); //Top outline
        rect(x, y + 2, 1, height - 4, outline); //Left outline
        rect(x + width - 1, y + 2, 1, height - 4, outline); //Right outline
        rect(x + 2, y + height - 1, width - 4, 1, outline); //Bottom outline
        rect(x + 1, y + 1, 1, 1, outline); //Topleft round pixel
        rect(x + 1, y + height - 2, 1, 1, outline); //Bottomleft round pixel
        rect(x + width - 2, y + 1, 1, 1, outline); //Topright round pixel
        rect(x + width - 2, y + height - 2, 1, 1, outline); //Bottomright round pixel
    }

    public void drawItemSlot(int x, int y) {
        drawBeveledPanel(x, y, 18, 18, 0xFF373737, 0xFF8b8b8b, 0xFFFFFFFF);
    }

    public void drawItemSlot(int x, int y, int color) {
        int shadowColor = multiplyColor(color, 0.50f);
        int hilightColor = multiplyColor(color, 1.25f);
        drawBeveledPanel(x, y, 18, 18, shadowColor, color, hilightColor);
    }

    public void drawBeveledPanel(int x, int y, int width, int height) {
        drawBeveledPanel(x, y, width, height, 0xFF373737, 0xFF8b8b8b, 0xFFFFFFFF);
    }

    public void drawBeveledPanel(int x, int y, int width, int height, int topleft, int panel, int bottomright) {
        rect(x, y, width, height, panel); //Center panel
        rect(x, y, width - 1, 1, topleft); //Top shadow
        rect(x, y + 1, 1, height - 2, topleft); //Left shadow
        rect(x + width - 1, y + 1, 1, height - 1, bottomright); //Right hilight
        rect(x + 1, y + height - 1, width - 1, 1, bottomright); //Bottom hilight
    }

    public void drawString(String s, int x, int y, int color) {
        Minecraft.getInstance().getFontResourceManager().getFontRenderer(Minecraft.DEFAULT_FONT_RENDERER_NAME).drawString(s, x, y, color);
    }

    public int colorAtOpacity(int opaque, float opacity) {
        if (opacity < 0.0f) opacity = 0.0f;
        if (opacity > 1.0f) opacity = 1.0f;

        int a = (int) (opacity * 255.0f);

        return (opaque & 0xFFFFFF) | (a << 24);
    }

    public int multiplyColor(int color, float amount) {
        int a = color & 0xFF000000;
        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;

        r = Math.min(r * amount, 1.0f);
        g = Math.min(g * amount, 1.0f);
        b = Math.min(b * amount, 1.0f);

        int ir = (int) (r * 255);
        int ig = (int) (g * 255);
        int ib = (int) (b * 255);

        return a | (ir << 16) | (ig << 8) | ib;
    }
}