package co.q64.stars.client.render.entity;

import co.q64.stars.entity.PickupEntity;
import co.q64.stars.item.HeartItem;
import co.q64.stars.item.KeyItem;
import co.q64.stars.item.StarItem;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@AutoFactory
public class PickupEntityRender extends EntityRenderer<PickupEntity> {
    private ItemStack heart, key, star;

    protected PickupEntityRender(EntityRendererManager renderManager, @Provided HeartItem heartItem, @Provided KeyItem keyItem, @Provided StarItem starItem) {
        super(renderManager);
        this.heart = new ItemStack(heartItem);
        this.key = new ItemStack(keyItem);
        this.star = new ItemStack(starItem);
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
        Minecraft.getInstance().getItemRenderer().renderItem(entity.getVariant() == 0 ? heart : entity.getVariant() == 1 ? key : star, TransformType.NONE);
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
    }

    protected ResourceLocation getEntityTexture(PickupEntity entity) {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }
}
