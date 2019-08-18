package co.q64.stars.client.render.item;

import co.q64.stars.block.TrophyBlock;
import co.q64.stars.block.TrophyBlock.TrophyVariant;
import co.q64.stars.item.TrophyBlockItem;
import co.q64.stars.level.LevelManager;
import co.q64.stars.tile.TrophyTile;
import co.q64.stars.type.FormingBlockType;
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
import java.util.Set;

@Singleton
public class TrophyItemRenderer extends ItemStackTileEntityRenderer {
    protected @Inject Set<TrophyBlock> trophyBlocks;
    protected @Inject LevelManager levelManager;
    protected @Inject Provider<TrophyTile> tileProvider;

    private TrophyBlock base;
    private Map<FormingBlockType, TrophyTile> cache = new HashMap<>();

    protected @Inject TrophyItemRenderer() {}

    @Inject
    protected void setup() {
        this.base = trophyBlocks.stream().filter(block -> block.getVariant() == TrophyVariant.BASE).findFirst().get();
    }

    @Override
    public void renderByItem(ItemStack stack) {
        GlStateManager.pushMatrix();
        GlStateManager.translated(0.5, 0.5, 0.5);
        Minecraft.getInstance().getItemRenderer().renderItem(stack, Minecraft.getInstance().getModelManager().getBlockModelShapes().getModel(base.getDefaultState()));
        GlStateManager.popMatrix();
        if (stack.getItem() instanceof TrophyBlockItem) {
            TrophyBlockItem item = (TrophyBlockItem) stack.getItem();
            item.getTrophyBlock().getVariant().getType().ifPresent(level -> {
                TileEntityRendererDispatcher.instance.renderAsItem(cache.computeIfAbsent(levelManager.getLevel(level).getSymbolicBlock(), key -> {
                    TrophyTile result = tileProvider.get();
                    result.setHasBlock(true);
                    result.setForming(key);
                    return result;
                }));
            });
        }
    }
}
