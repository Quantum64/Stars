package co.q64.stars.client.render.tile;

import co.q64.stars.tile.FormingTile;
import co.q64.stars.type.FormingBlockType;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.Direction;
import org.lwjgl.opengl.GL11;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.nio.FloatBuffer;

@Singleton
public class FormingBlockRender extends TileEntityRenderer<FormingTile> {
    private static final float width = 0.0625f;
    private FloatBuffer verticalFlip = FloatBuffer.allocate(16);

    protected @Inject FormingBlockRender() {
        verticalFlip.put(new float[]{1, 0, 0, 0});
        verticalFlip.put(new float[]{0, 1, 0, 0});
        verticalFlip.put(new float[]{0, 0, -1, 0});
        verticalFlip.put(new float[]{0, 0, 0, 1});
    }

    public void render(FormingTile tile, double x, double y, double z, float partialTicks, int destroyStage) {
        if (!tile.isCalculated()) {
            return;
        }
        FormingBlockType type = tile.getFormType();
        Direction direction = tile.getDirection();
        int buildTime = tile.getBuildTime();
        float timeSincePlace = System.currentTimeMillis() - tile.getPlaced();
        if (timeSincePlace > buildTime) {
            timeSincePlace = buildTime;
        }
        float progress = timeSincePlace / buildTime;

        float bottomCapProgress = progress * 5;
        float sideProgress = (progress - 0.2f) * 1.666f;
        float topCapProgress = (progress - 0.8f) * 5;

        /*
        float bottomCapProgress = progress * 3;
        float sideProgress = (progress - 0.333f) * 3;
        float topCapProgress = (progress - 0.666f) * 3;
         */

        bottomCapProgress = bottomCapProgress > 1 ? 1 : bottomCapProgress;
        sideProgress = sideProgress > 1 ? 1 : sideProgress;
        topCapProgress = topCapProgress > 1 ? 1 : topCapProgress;

        //GlStateManager.enableDepthTest();
        //GlStateManager.depthFunc(GL11.GL_LEQUAL);
        //GlStateManager.depthMask(true);
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color4f(type.getR() / 256f, type.getG() / 256f, type.getB() / 256f, 1);
        GlStateManager.polygonOffset(-1, -1);
        GlStateManager.enablePolygonOffset();
        GlStateManager.pushMatrix();
        GlStateManager.translated(x, y, z);
        switch (direction) {
            case DOWN:
                GlStateManager.translated(0, 1, 1);
                GlStateManager.rotated(180, 1, 0, 0);
                break;
            case NORTH:
                GlStateManager.translated(0, 0, 1);
                GlStateManager.rotated(90, -1, 0, 0);
                break;
            case SOUTH:
                GlStateManager.translated(0, 1, 0);
                GlStateManager.rotated(90, 1, 0, 0);
                break;
            case WEST:
                GlStateManager.translated(1, 0, 0);
                GlStateManager.rotated(90, 0, 0, 1);
                break;
            case EAST:
                GlStateManager.translated(0, 1, 0);
                GlStateManager.rotated(90, 0, 0, -1);
                break;
            case UP:
                break;
        }

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();
        builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        renderBottomCap(builder, bottomCapProgress);
        if (sideProgress > 0) {
            renderSides(builder, sideProgress);
        }
        if (topCapProgress > 0) {
            renderTopCap(builder, topCapProgress);
        }
        tessellator.draw();

        GlStateManager.popMatrix();
        GlStateManager.enableCull();
        GlStateManager.enableTexture();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.disablePolygonOffset();
    }

    private void renderSides(BufferBuilder builder, float progress) {
        builder.pos(0, 0, 0).endVertex();
        builder.pos(width, 0, 0).endVertex();
        builder.pos(width, progress, 0).endVertex();
        builder.pos(0, progress, 0).endVertex();
        builder.pos(0, 0, 0).endVertex();
        builder.pos(0, 0, width).endVertex();
        builder.pos(0, progress, width).endVertex();
        builder.pos(0, progress, 0).endVertex();

        builder.pos(1, 0, 0).endVertex();
        builder.pos(1 - width, 0, 0).endVertex();
        builder.pos(1 - width, progress, 0).endVertex();
        builder.pos(1, progress, 0).endVertex();
        builder.pos(1, 0, 0).endVertex();
        builder.pos(1, 0, width).endVertex();
        builder.pos(1, progress, width).endVertex();
        builder.pos(1, progress, 0).endVertex();

        builder.pos(0, 0, 1).endVertex();
        builder.pos(width, 0, 1).endVertex();
        builder.pos(width, progress, 1).endVertex();
        builder.pos(0, progress, 1).endVertex();
        builder.pos(0, 0, 1).endVertex();
        builder.pos(0, 0, 1 - width).endVertex();
        builder.pos(0, progress, 1 - width).endVertex();
        builder.pos(0, progress, 1).endVertex();

        builder.pos(1, 0, 1).endVertex();
        builder.pos(1 - width, 0, 1).endVertex();
        builder.pos(1 - width, progress, 1).endVertex();
        builder.pos(1, progress, 1).endVertex();
        builder.pos(1, 0, 1).endVertex();
        builder.pos(1, 0, 1 - width).endVertex();
        builder.pos(1, progress, 1 - width).endVertex();
        builder.pos(1, progress, 1).endVertex();
    }

    private void renderBottomCap(BufferBuilder builder, float progress) {
        builder.pos(0, 0, 0).endVertex();
        builder.pos(width, 0, 0).endVertex();
        builder.pos(width, 0, progress).endVertex();
        builder.pos(0, 0, progress).endVertex();
        builder.pos(0, 0, 0).endVertex();
        builder.pos(0, width, 0).endVertex();
        builder.pos(0, width, progress).endVertex();
        builder.pos(0, 0, progress).endVertex();

        builder.pos(1, 0, 1).endVertex();
        builder.pos(1 - width, 0, 1).endVertex();
        builder.pos(1 - width, 0, 1 - progress).endVertex();
        builder.pos(1, 0, 1 - progress).endVertex();
        builder.pos(1, 0, 1).endVertex();
        builder.pos(1, width, 1).endVertex();
        builder.pos(1, width, 1 - progress).endVertex();
        builder.pos(1, 0, 1 - progress).endVertex();

        builder.pos(0, 0, 1).endVertex();
        builder.pos(0, width, 1).endVertex();
        builder.pos(progress, width, 1).endVertex();
        builder.pos(progress, 0, 1).endVertex();
        builder.pos(0, 0, 1).endVertex();
        builder.pos(0, 0, 1 - width).endVertex();
        builder.pos(progress, 0, 1 - width).endVertex();
        builder.pos(progress, 0, 1).endVertex();

        builder.pos(1, 0, 0).endVertex();
        builder.pos(1, width, 0).endVertex();
        builder.pos(1 - progress, width, 0).endVertex();
        builder.pos(1 - progress, 0, 0).endVertex();
        builder.pos(1, 0, 0).endVertex();
        builder.pos(1, 0, width).endVertex();
        builder.pos(1 - progress, 0, width).endVertex();
        builder.pos(1 - progress, 0, 0).endVertex();
    }

    private void renderTopCap(BufferBuilder builder, float progress) {
        builder.pos(0, 1, 0).endVertex();
        builder.pos(width, 1, 0).endVertex();
        builder.pos(width, 1, progress).endVertex();
        builder.pos(0, 1, progress).endVertex();
        builder.pos(0, 1, 0).endVertex();
        builder.pos(0, 1 - width, 0).endVertex();
        builder.pos(0, 1 - width, progress).endVertex();
        builder.pos(0, 1, progress).endVertex();

        builder.pos(1, 1, 1).endVertex();
        builder.pos(1 - width, 1, 1).endVertex();
        builder.pos(1 - width, 1, 1 - progress).endVertex();
        builder.pos(1, 1, 1 - progress).endVertex();
        builder.pos(1, 1, 1).endVertex();
        builder.pos(1, 1 - width, 1).endVertex();
        builder.pos(1, 1 - width, 1 - progress).endVertex();
        builder.pos(1, 1, 1 - progress).endVertex();

        builder.pos(0, 1, 1).endVertex();
        builder.pos(0, 1 - width, 1).endVertex();
        builder.pos(progress, 1 - width, 1).endVertex();
        builder.pos(progress, 1, 1).endVertex();
        builder.pos(0, 1, 1).endVertex();
        builder.pos(0, 1, 1 - width).endVertex();
        builder.pos(progress, 1, 1 - width).endVertex();
        builder.pos(progress, 1, 1).endVertex();

        builder.pos(1, 1, 0).endVertex();
        builder.pos(1, 1 - width, 0).endVertex();
        builder.pos(1 - progress, 1 - width, 0).endVertex();
        builder.pos(1 - progress, 1, 0).endVertex();
        builder.pos(1, 1, 0).endVertex();
        builder.pos(1, 1, width).endVertex();
        builder.pos(1 - progress, 1, width).endVertex();
        builder.pos(1 - progress, 1, 0).endVertex();
    }
}
