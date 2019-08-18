package co.q64.stars.client.render.tile;

import co.q64.stars.client.util.ModelUtil;
import co.q64.stars.tile.TrophyTile;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.chunk.ChunkRenderCache;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.data.EmptyModelData;
import org.lwjgl.opengl.GL11;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Random;

@Singleton
public class TrophyBlockRender extends TileEntityRenderer<TrophyTile> {
    protected @Inject ModelUtil modelUtil;

    private Random random = new Random();

    @Inject
    protected TrophyBlockRender() {
    }

    public void render(TrophyTile tile, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        if (Minecraft.isAmbientOcclusionEnabled()) {
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
        } else {
            GlStateManager.shadeModel(GL11.GL_FLAT);
        }
        RenderHelper.disableStandardItemLighting();
        bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        IBakedModel model = modelUtil.getModel(tile.getForming(), false, false);
        BlockPos pos = tile.getPos();
        ChunkRenderCache world = MinecraftForgeClient.getRegionRenderCache(tile.getWorld(), pos);
        buffer.setTranslation(x - (double) pos.getX(), y - (double) pos.getY(), z - (double) pos.getZ());
        GlStateManager.pushMatrix();
        GlStateManager.scalef(0.5f, 0.5f, 0.5f);
        BlockState state = world.getBlockState(pos);
        Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(world, model, state, pos, buffer, false, random, state.getPositionRandom(pos), EmptyModelData.INSTANCE);
        buffer.setTranslation(0, 0, 0);
        tessellator.draw();
        GlStateManager.popMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableCull();
        GlStateManager.enableLighting();
    }

}
