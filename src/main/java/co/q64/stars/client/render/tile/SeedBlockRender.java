package co.q64.stars.client.render.tile;

import co.q64.stars.client.util.ModelUtil;
import co.q64.stars.tile.SeedTile;
import co.q64.stars.type.FormingBlockType;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.chunk.ChunkRenderCache;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.data.EmptyModelData;
import org.lwjgl.opengl.GL11;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Singleton
public class SeedBlockRender extends TileEntityRenderer<SeedTile> {
    private static final Direction[] DIRECTIONS = Direction.values();

    protected @Inject ModelUtil modelUtil;

    private Map<FormingBlockType, ItemStack> stackCache = new HashMap<>();
    private Random random = new Random();

    @Inject
    protected SeedBlockRender() {
    }

    public void render(SeedTile tile, double x, double y, double z, float partialTicks, int destroyStage) {
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
        IBakedModel model = modelUtil.getModel(tile.getFormingBlockType(), tile.isPrimed(), tile.isFruit());
        BlockPos pos = tile.getPos();
        ChunkRenderCache world = MinecraftForgeClient.getRegionRenderCache(tile.getWorld(), pos);
        buffer.setTranslation(x - (double) pos.getX(), y - (double) pos.getY(), z - (double) pos.getZ());
        Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(world, model, world.getBlockState(pos), pos, buffer, false, random, 42L, EmptyModelData.INSTANCE);
        buffer.setTranslation(0, 0, 0);
        tessellator.draw();
        RenderHelper.enableStandardItemLighting();
        drawItems(x, y, z, tile.getSeedType());
        GlStateManager.enableCull();
        GlStateManager.enableLighting();
    }

    public void drawItems(double x, double y, double z, FormingBlockType type) {
        ItemStack stack = stackCache.computeIfAbsent(type, t -> new ItemStack(t.getItemProvider().get()));
        GlStateManager.polygonOffset(-1, -1);
        GlStateManager.enablePolygonOffset();
        for (Direction direction : DIRECTIONS) {
            GlStateManager.pushMatrix();
            GlStateManager.translated(x, y, z);
            switch (direction) {
                case NORTH:
                    GlStateManager.translated(0.5, 0.5, 0);
                    break;
                case SOUTH:
                    GlStateManager.translated(0.5, 0.5, 1);
                    break;
                case EAST:
                    GlStateManager.translated(1, 0.5, 0.5);
                    GlStateManager.rotated(-90, 0, 1, 0);
                    break;
                case WEST:
                    GlStateManager.translated(0, 0.5, 0.5);
                    GlStateManager.rotated(90, 0, 1, 0);
                    break;
                case UP:
                    GlStateManager.translated(0.5, 1, 0.5);
                    GlStateManager.rotated(90, 1, 0, 0);
                    break;
                case DOWN:
                    GlStateManager.translated(0.5, 0, 0.5);
                    GlStateManager.rotated(-90, 1, 0, 0);
                    break;
            }
            Minecraft.getInstance().getItemRenderer().renderItem(stack, TransformType.NONE);
            GlStateManager.popMatrix();
        }
        GlStateManager.disablePolygonOffset();
    }
}
