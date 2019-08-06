package co.q64.stars.client.render.tile;

import co.q64.stars.tile.DoorTile;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DoorBlockRender extends TileEntityRenderer<DoorTile> {
    private static final String LINE1 = "You are always home-bound.";
    private static final String LINE2 = "Hold 'H' to lose your way.";

    protected @Inject DoorBlockRender() {}

    public void render(DoorTile tile, double x, double y, double z, float partialTicks, int destroyStage) {
        if (!tile.isFallen() || tile.isChallenge()) {
            return;
        }

        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.polygonOffset(-20f, -20f);
        GlStateManager.enablePolygonOffset();
        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        GlStateManager.translated(x + 0.5, y + 1, z);
        GlStateManager.pushMatrix();
        GlStateManager.rotated(90, 1, 0, 0);
        GlStateManager.scalef(-0.02f, -0.02f, 0.02f);

        getFontRenderer().drawString(LINE2, -getFontRenderer().getStringWidth(LINE2) / 2, 1, 0xFFFFFFFF);
        GlStateManager.popMatrix();
        GlStateManager.translated(0, 0, 1);
        GlStateManager.pushMatrix();
        GlStateManager.rotated(90, 1, 0, 0);
        GlStateManager.scalef(-0.02f, -0.02f, 0.02f);
        getFontRenderer().drawString(LINE1, -getFontRenderer().getStringWidth(LINE1) / 2, -10, 0xFFFFFFFF);

        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
        GlStateManager.enableCull();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.disablePolygonOffset();
    }
}
