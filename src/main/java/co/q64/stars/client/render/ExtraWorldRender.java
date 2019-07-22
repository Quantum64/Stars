package co.q64.stars.client.render;

import co.q64.stars.util.Identifiers;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.platform.GlStateManager;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;

@Singleton
public class ExtraWorldRender {
    private static final int ANIMATION_TIME = 6000;

    protected @Inject Identifiers identifiers;

    private @Setter long animationStart;
    private ShaderGroup entityOutlineShader;
    private Framebuffer entityOutlineFramebuffer;

    protected @Inject ExtraWorldRender() {}

    public void render() {
        long now = System.currentTimeMillis();
        long ticks = animationStart - now + ANIMATION_TIME;
        if (ticks <= 0) {
            return;
        }
        float progress = ticks / Float.valueOf(ANIMATION_TIME);
        Minecraft mc = Minecraft.getInstance();
        if (mc.world == null || mc.skipRenderWorld) {
            return;
        }

        if (entityOutlineShader == null) {
            if (ShaderLinkHelper.getStaticShaderLinkHelper() == null) {
                ShaderLinkHelper.setNewStaticShaderLinkHelper();
            }

            if (this.entityOutlineShader != null) {
                this.entityOutlineShader.close();
            }

            ResourceLocation resourcelocation = identifiers.get("shaders/post/block_outline.json");

            try {
                this.entityOutlineShader = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), resourcelocation);
                this.entityOutlineShader.createBindFramebuffers(mc.mainWindow.getFramebufferWidth(), mc.mainWindow.getFramebufferHeight());
                this.entityOutlineFramebuffer = this.entityOutlineShader.getFramebufferRaw("final");
            } catch (IOException ioexception) {
                ioexception.printStackTrace();
            } catch (JsonSyntaxException jsonsyntaxexception) {
                jsonsyntaxexception.printStackTrace();
            }
        }

        this.entityOutlineFramebuffer.framebufferClear(Minecraft.IS_RUNNING_ON_MAC);
        //GlStateManager.depthFunc(519);
        //GlStateManager.disableFog();
        this.entityOutlineFramebuffer.bindFramebuffer(false);
        RenderHelper.disableStandardItemLighting();

        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.pushMatrix();
        GlStateManager.disableAlphaTest();
        mc.worldRenderer.renderBlockLayer(BlockRenderLayer.SOLID, mc.gameRenderer.getActiveRenderInfo());
        GlStateManager.enableAlphaTest();
        GlStateManager.popMatrix();

        //GlStateManager.depthMask(false);
        entityOutlineShader.render(0);
        //GlStateManager.depthMask(true);
        //GlStateManager.enableFog();
        mc.getFramebuffer().bindFramebuffer(false);
        RenderHelper.enableStandardItemLighting();

        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);

        int width = mc.mainWindow.getFramebufferWidth();
        int height = mc.mainWindow.getFramebufferHeight();

        GlStateManager.colorMask(true, true, true, false);
        GlStateManager.disableDepthTest();
        GlStateManager.depthMask(false);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0D, (double) width, (double) height, 0.0D, 1000.0D, 3000.0D);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        GlStateManager.translatef(0.0F, 0.0F, -2000.0F);
        GlStateManager.viewport(0, 0, width, height);
        GlStateManager.enableTexture();
        GlStateManager.disableLighting();
        GlStateManager.disableAlphaTest();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, progress);
        entityOutlineFramebuffer.bindFramebufferTexture();
        float w = (float) width;
        float h = (float) height;
        float u = (float) entityOutlineFramebuffer.framebufferWidth / (float) entityOutlineFramebuffer.framebufferTextureWidth;
        float v = (float) entityOutlineFramebuffer.framebufferHeight / (float) entityOutlineFramebuffer.framebufferTextureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos(0.0D, (double) h, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
        bufferbuilder.pos((double) w, (double) h, 0.0D).tex((double) u, 0.0D).color(255, 255, 255, 255).endVertex();
        bufferbuilder.pos((double) w, 0.0D, 0.0D).tex((double) u, (double) v).color(255, 255, 255, 255).endVertex();
        bufferbuilder.pos(0.0D, 0.0D, 0.0D).tex(0.0D, (double) v).color(255, 255, 255, 255).endVertex();
        tessellator.draw();
        entityOutlineFramebuffer.unbindFramebufferTexture();
        GlStateManager.depthMask(true);
        GlStateManager.colorMask(true, true, true, true);

        GlStateManager.disableBlend();
    }
}
