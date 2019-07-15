package co.q64.stars.client.loader;

import co.q64.stars.client.render.FormingBlockRender;
import co.q64.stars.loader.CommonLoader;
import co.q64.stars.tile.FormingTile;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ClientLoader {
    protected @Inject CommonLoader commonLoader;

    protected @Inject FormingBlockRender formingBlockRender;

    protected @Inject ClientLoader() {}

    public void load() {
        TileEntityRendererDispatcher.instance.setSpecialRenderer(FormingTile.class, formingBlockRender);
    }
}
