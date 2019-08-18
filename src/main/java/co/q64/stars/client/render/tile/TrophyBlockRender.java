package co.q64.stars.client.render.tile;

import co.q64.stars.client.util.ModelUtil;
import co.q64.stars.tile.TrophyTile;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.model.data.EmptyModelData;
import org.lwjgl.opengl.GL11;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Random;

@Singleton
public class TrophyBlockRender extends TileEntityRenderer<TrophyTile> {
    private static final double ROTATE_OFFSET = 0.1;
    private static final double TRANSLATE_OFFSET = 0.4;

    protected @Inject ModelUtil modelUtil;

    private Random random = new Random();

    @Inject
    protected TrophyBlockRender() {
    }

    public void render(TrophyTile tile, double x, double y, double z, float partialTicks, int destroyStage) {
        if (!tile.isHasBlock()) {
            return;
        }
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        if (Minecraft.isAmbientOcclusionEnabled()) {
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
        } else {
            GlStateManager.shadeModel(GL11.GL_FLAT);
        }
        GlStateManager.pushMatrix();
        GlStateManager.translated(x + TRANSLATE_OFFSET, y + 0.45, z + TRANSLATE_OFFSET);
        GlStateManager.translated(ROTATE_OFFSET, 0, ROTATE_OFFSET);
        GlStateManager.rotated((Minecraft.getInstance().world.getGameTime() + partialTicks) * 3, 0, 1, 0);
        GlStateManager.translated(-ROTATE_OFFSET, 0, -ROTATE_OFFSET);
        GlStateManager.scalef(0.2f, 0.2f, 0.2f);
        RenderHelper.disableStandardItemLighting();
        bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        IBakedModel model = modelUtil.getModel(tile.getForming(), false, false);
        // TODO what
        //ChunkRenderCache world = MinecraftForgeClient.getRegionRenderCache(tile.getWorld(), pos);
        //ChunkRenderCache world = MinecraftForgeClient.getRegionRenderCache(tile.getWorld(), BlockPos.ZERO);
        //buffer.setTranslation(x - (double) pos.getX(), y - (double) pos.getY(), z - (double) pos.getZ());
        buffer.setTranslation(0, 0, 0);
        Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(Minecraft.getInstance().world, model, Blocks.STONE.getDefaultState(), BlockPos.ZERO, buffer, false, random, Blocks.STONE.getDefaultState().getPositionRandom(new BlockPos(x, y, z)), EmptyModelData.INSTANCE);
        buffer.setTranslation(0, 0, 0);
        tessellator.draw();
        GlStateManager.popMatrix();
        if(tile.isSunbeams()) {
            GlStateManager.enableCull();
            GlStateManager.enableLighting();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            float time = ((float) Minecraft.getInstance().world.getGameTime() + partialTicks) / 200.0F;
            Random random = new Random(MathHelper.getPositionRandom(tile.getPos()));
            GlStateManager.disableTexture();
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
            GlStateManager.disableAlphaTest();
            GlStateManager.enableCull();
            GlStateManager.depthMask(false);
            GlStateManager.pushMatrix();
            GlStateManager.translated(x + 0.5, y + 0.5, z + 0.5);
            GlStateManager.scaled(0.05, 0.05, 0.05);
            for (int i = 0; i < 10; ++i) {
                GlStateManager.rotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotatef(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
                GlStateManager.rotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotatef(random.nextFloat() * 360.0F + time * 90.0F, 0.0F, 0.0F, 1.0F);
                float f2 = random.nextFloat() * 20.0F + 5.0F;
                float f3 = random.nextFloat() * 2.0F + 1.0F;
                bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
                bufferbuilder.pos(0.0D, 0.0D, 0.0D).color(255, 255, 255, (int) (255.0F * (1.0F))).endVertex();
                bufferbuilder.pos(-0.866D * (double) f3, (double) f2, (double) (-0.5F * f3)).color(255, 0, 255, 0).endVertex();
                bufferbuilder.pos(0.866D * (double) f3, (double) f2, (double) (-0.5F * f3)).color(255, 0, 255, 0).endVertex();
                bufferbuilder.pos(0.0D, (double) f2, (double) (1.0F * f3)).color(255, 0, 255, 0).endVertex();
                bufferbuilder.pos(-0.866D * (double) f3, (double) f2, (double) (-0.5F * f3)).color(255, 0, 255, 0).endVertex();
                tessellator.draw();
            }
            GlStateManager.popMatrix();
            GlStateManager.depthMask(true);
            GlStateManager.disableCull();
            GlStateManager.disableBlend();
            GlStateManager.shadeModel(7424);
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableTexture();
            GlStateManager.enableAlphaTest();
        }
        RenderHelper.enableStandardItemLighting();
    }
}
