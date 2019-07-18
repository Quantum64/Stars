package co.q64.stars.client.loader;

import co.q64.stars.client.render.DecayingBlockRender;
import co.q64.stars.client.render.FormingBlockRender;
import co.q64.stars.loader.CommonLoader;
import co.q64.stars.tile.DecayingTile;
import co.q64.stars.tile.FormingTile;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ClientLoader {
    protected @Inject CommonLoader commonLoader;

    protected @Inject FormingBlockRender formingBlockRender;
    protected @Inject DecayingBlockRender decayingBlockRender;

    protected @Inject ClientLoader() {}

    public void load() {
        ClientRegistry.bindTileEntitySpecialRenderer(FormingTile.class, formingBlockRender);
        ClientRegistry.bindTileEntitySpecialRenderer(DecayingTile.class, decayingBlockRender);
    }
}
