package co.q64.stars.client.render.entity;

import co.q64.stars.entity.PickupEntity;
import co.q64.stars.item.ArrowItem;
import co.q64.stars.item.HeartItem;
import co.q64.stars.item.KeyItem;
import co.q64.stars.item.StarItem;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.pipeline.LightUtil;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Random;

@AutoFactory
public class PickupEntityRender extends EntityRenderer<PickupEntity> {
    private ItemStack heart, key, star, arrow;

    protected PickupEntityRender(EntityRendererManager renderManager, @Provided HeartItem heartItem, @Provided KeyItem keyItem, @Provided StarItem starItem, @Provided ArrowItem arrowItem) {
        super(renderManager);
        this.heart = new ItemStack(heartItem);
        this.key = new ItemStack(keyItem);
        this.star = new ItemStack(starItem);
        this.arrow = new ItemStack(arrowItem);
    }

    public void doRender(PickupEntity entity, double x, double y, double z, float yaw, float partialTicks) {
        GlStateManager.enableRescaleNormal();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
        GlStateManager.enableBlend();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
        GlStateManager.pushMatrix();
        GlStateManager.translated(x, y + 0.5, z);
        GlStateManager.rotatef((entity.getAge() + partialTicks) * 5, 0.0F, 1.0F, 0.0F);
        ItemStack stack = heart;
        switch (entity.getVariant()) {
            case PickupEntity.VARIANT_KEY:
                stack = key;
                break;
            case PickupEntity.VARIANT_STAR:
                stack = star;
                break;
            case PickupEntity.VARIANT_ARROW:
                stack = arrow;
                break;
        }
        if (stack != arrow) {
            Minecraft.getInstance().getItemRenderer().renderItem(stack, TransformType.NONE);
        } else {
            PlayerEntity player = Minecraft.getInstance().player;
            double dx = player.posX, dy = player.posY, dz = player.posZ;
            double dist = Math.sqrt(((entity.posX - dx) * (entity.posX - dx)) + ((entity.posY - dy) * (entity.posY - dy) + ((entity.posZ - dz) * (entity.posZ - dz)))) - 1;
            int color = (int) (Math.min((Math.max(dist, 0) / 10.0), 1) * 255);
            int result = 0xFFFFFF + (color << 24);

            IBakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getModelWithOverrides(stack);
            Minecraft.getInstance().getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getInstance().getTextureManager().getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableRescaleNormal();
            GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
            GlStateManager.enableBlend();
            GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.pushMatrix();
            bakedmodel = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(bakedmodel, TransformType.NONE, false);
            GlStateManager.pushMatrix();
            GlStateManager.translatef(-0.5F, -0.5F, -0.5F);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.ITEM);
            Random random = new Random();
            long i = 42L;
            for (Direction direction : Direction.values()) {
                random.setSeed(42L);
                this.renderQuads(bufferbuilder, bakedmodel.getQuads(null, direction, random, EmptyModelData.INSTANCE), result, stack);
            }
            random.setSeed(42L);
            this.renderQuads(bufferbuilder, bakedmodel.getQuads(null, null, random, EmptyModelData.INSTANCE), result, stack);
            tessellator.draw();
            GlStateManager.popMatrix();
            GlStateManager.cullFace(GlStateManager.CullFace.BACK);
            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
            Minecraft.getInstance().getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getInstance().getTextureManager().getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
        }
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
    }

    private void renderQuads(BufferBuilder renderer, List<BakedQuad> quads, int color, ItemStack stack) {
        int i = 0;
        for (int j = quads.size(); i < j; ++i) {
            BakedQuad bakedquad = quads.get(i);
            LightUtil.renderQuadColor(renderer, bakedquad, color);
        }
    }

    protected ResourceLocation getEntityTexture(PickupEntity entity) {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }
}
