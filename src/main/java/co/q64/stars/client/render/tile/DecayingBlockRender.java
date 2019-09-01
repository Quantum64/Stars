package co.q64.stars.client.render.tile;

import co.q64.stars.client.util.ModelUtil;
import co.q64.stars.tile.DecayingTile;
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
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

@Singleton
public class DecayingBlockRender extends TileEntityRenderer<DecayingTile> {
    private static final boolean RENDER_OPTIMIZATON = false;

    private static final Direction[] DIRECTIONS = Direction.values();
    private static final int COUNTS_PER_SIDE = 20;
    private static final int ANIMATION_TIME = 150;

    protected @Inject SeedBlockRender seedBlockRender;
    protected @Inject ModelUtil modelUtil;

    private Map<Direction, long[]> salts = new HashMap<>();
    private Random random = new Random();
    private float[] cutoffs = new float[COUNTS_PER_SIDE];

    @Inject
    protected DecayingBlockRender() {
        for (Direction direction : DIRECTIONS) {
            salts.put(direction, LongStream.range(0, COUNTS_PER_SIDE * 2).map(l -> Math.abs(ThreadLocalRandom.current().nextLong())).toArray());
        }
        for (int i = 0; i < cutoffs.length; i++) {
            cutoffs[i] = (Float.valueOf(i + 1) / COUNTS_PER_SIDE); // - (1f / COUNTS_PER_SIDE);
        }
    }

    public void render(DecayingTile tile, double x, double y, double z, float partialTicks, int destroyStage) {
        Direction[] renderable = new Direction[DIRECTIONS.length];
        if (RENDER_OPTIMIZATON) {
            for (int index = 0; index < DIRECTIONS.length; index++) {
                if (!tile.getWorld().getBlockState(tile.getPos().offset(DIRECTIONS[index])).isOpaqueCube(tile.getWorld(), tile.getPos())) {
                    renderable[index] = DIRECTIONS[index];
                }
            }
        }
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

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
        BlockState state = world.getBlockState(pos);
        Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(world, model, state, pos, buffer, false, random, state.getPositionRandom(pos), EmptyModelData.INSTANCE);
        buffer.setTranslation(0, 0, 0);
        tessellator.draw();
        RenderHelper.enableStandardItemLighting();

        if (tile.isHasSeed()) {
            seedBlockRender.drawItems(x, y, z, tile.getSeedType());
        }

        GlStateManager.disableTexture();
        GlStateManager.color4f(0, 0, 0, 1);
        GlStateManager.polygonOffset(-0.5f, -0.5f);
        GlStateManager.enablePolygonOffset();
        GlStateManager.pushMatrix();
        GlStateManager.translated(x, y, z);

        if (tile.isCalculated()) {
            long position = tile.getPos().toLong();
            long now = System.currentTimeMillis();
            float progress = (now - tile.getPlaced()) / Float.valueOf(tile.getExpectedDecayTime());
            if (progress > 1.0f) {
                progress = 1.0f;
            }
            BufferBuilder builder = tessellator.getBuffer();
            builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);

            for (int index = 0; index < COUNTS_PER_SIDE; index++) {
                float cutoff = cutoffs[index];
                if (progress > cutoff) {
                    long timeAtCutoff = (tile.getPlaced() + (long) (cutoff * tile.getExpectedDecayTime()));
                    long msAnimationRemaining = timeAtCutoff - noWow + ANIMATION_TIME;
                    if (msAnimationRemaining < 0) {
                        msAnimationRemaining = 0;
                    }
                    double animationScale = msAnimationRemaining / Double.valueOf(ANIMATION_TIME);
                    animationScale *= 0.1;
                    for (Direction direction : RENDER_OPTIMIZATON ? renderable : DIRECTIONS) {
                        if (direction == null) {
                            continue;
                        }
                        long seed = Math.abs(position) ^ salts.get(direction)[index];
                        long seed2 = Math.abs(position) ^ salts.get(direction)[COUNTS_PER_SIDE + index];
                        double decaySize = 0.1 + (((Math.abs(position) ^ (seed ^ seed2)) % 1000) / 12000.0);
                        double decayX = ((seed % 1000) / 1000.0) * (1 - (decaySize));
                        double decayY = (seed2 % 1000) / 1000.0 * (1 - (decaySize));
                        double xStart = decayX - animationScale;
                        double yStart = decayY - animationScale;
                        double xEnd = decayX + decaySize + animationScale;
                        double yEnd = decayY + decaySize + animationScale;

                        switch (direction) {
                            case DOWN:
                                builder.pos(xStart, 0, yStart).endVertex();
                                builder.pos(xEnd, 0, yStart).endVertex();
                                builder.pos(xEnd, 0, yEnd).endVertex();
                                builder.pos(xStart, 0, yEnd).endVertex();
                                break;
                            case UP:
                                builder.pos(xStart, 1, yStart).endVertex();
                                builder.pos(xEnd, 1, yStart).endVertex();
                                builder.pos(xEnd, 1, yEnd).endVertex();
                                builder.pos(xStart, 1, yEnd).endVertex();
                                break;
                            case NORTH:
                                builder.pos(xStart, yStart, 0).endVertex();
                                builder.pos(xEnd, yStart, 0).endVertex();
                                builder.pos(xEnd, yEnd, 0).endVertex();
                                builder.pos(xStart, yEnd, 0).endVertex();
                                break;
                            case SOUTH:
                                builder.pos(xStart, yStart, 1).endVertex();
                                builder.pos(xEnd, yStart, 1).endVertex();
                                builder.pos(xEnd, yEnd, 1).endVertex();
                                builder.pos(xStart, yEnd, 1).endVertex();
                                break;
                            case WEST:
                                builder.pos(0, yStart, xStart).endVertex();
                                builder.pos(0, yStart, xEnd).endVertex();
                                builder.pos(0, yEnd, xEnd).endVertex();
                                builder.pos(0, yEnd, xStart).endVertex();
                                break;
                            case EAST:
                                builder.pos(1, yStart, xStart).endVertex();
                                builder.pos(1, yStart, xEnd).endVertex();
                                builder.pos(1, yEnd, xEnd).endVertex();
                                builder.pos(1, yEnd, xStart).endVertex();
                                break;
                        }
                    }
                }
            }

            tessellator.draw();
        }

        GlStateManager.popMatrix();
        GlStateManager.enableCull();
        GlStateManager.enableTexture();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.disablePolygonOffset();
    }
}
