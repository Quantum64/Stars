package co.q64.stars.client.render.item;

import co.q64.stars.block.TrophyBlock;
import co.q64.stars.tile.TrophyTile;
import co.q64.stars.type.FormingBlockType;
import co.q64.stars.type.FormingBlockTypes;
import co.q64.stars.type.forming.GreyFormingBlockType;
import co.q64.stars.util.nbt.ExtendedTag;
import co.q64.stars.util.nbt.ExtendedTagFactory;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class TrophyItemRenderer extends ItemStackTileEntityRenderer {
    protected @Inject TrophyBlock trophyBlock;
    protected @Inject ExtendedTagFactory extendedTagFactory;
    protected @Inject FormingBlockTypes formingBlockTypes;
    protected @Inject GreyFormingBlockType greyFormingBlockType;
    protected @Inject Provider<TrophyTile> tileProvider;

    private Map<FormingBlockType, TrophyTile> cache = new HashMap<>();

    protected @Inject TrophyItemRenderer() {}

    @Override
    public void renderByItem(ItemStack stack) {
        GlStateManager.pushMatrix();
        GlStateManager.translated(0.5, 0.5, 0.5);
        Minecraft.getInstance().getItemRenderer().renderItem(stack, Minecraft.getInstance().getModelManager().getBlockModelShapes().getModel(trophyBlock.getDefaultState()));
        GlStateManager.popMatrix();
        ExtendedTag tag = extendedTagFactory.create(stack.getOrCreateTag());
        if (tag.getBoolean("hasBlock")) {
            TileEntityRendererDispatcher.instance.renderAsItem(cache.computeIfAbsent(formingBlockTypes.get(tag.getInt("forming", greyFormingBlockType.getId())), key -> {
                TrophyTile result = tileProvider.get();
                result.setForming(key);
                return result;
            }));
        }
    }
}
