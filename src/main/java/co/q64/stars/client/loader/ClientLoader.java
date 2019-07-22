package co.q64.stars.client.loader;

import co.q64.stars.client.render.entity.PickupEntityRenderFactory;
import co.q64.stars.client.render.tile.DecayingBlockRender;
import co.q64.stars.client.render.tile.DoorBlockRender;
import co.q64.stars.client.render.tile.FormingBlockRender;
import co.q64.stars.client.render.tile.SeedBlockRender;
import co.q64.stars.entity.PickupEntity;
import co.q64.stars.loader.CommonLoader;
import co.q64.stars.tile.DecayingTile;
import co.q64.stars.tile.DoorTile;
import co.q64.stars.tile.FormingTile;
import co.q64.stars.tile.SeedTile;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ClientLoader {
    protected @Inject CommonLoader commonLoader;

    protected @Inject FormingBlockRender formingBlockRender;
    protected @Inject DecayingBlockRender decayingBlockRender;
    protected @Inject DoorBlockRender doorBlockRender;
    protected @Inject SeedBlockRender seedBlockRender;

    protected @Inject PickupEntityRenderFactory pickupEntityRenderFactory;

    protected @Inject ClientLoader() {}

    public void load() {
        ClientRegistry.bindTileEntitySpecialRenderer(FormingTile.class, formingBlockRender);
        ClientRegistry.bindTileEntitySpecialRenderer(DecayingTile.class, decayingBlockRender);
        ClientRegistry.bindTileEntitySpecialRenderer(DoorTile.class, doorBlockRender);
        ClientRegistry.bindTileEntitySpecialRenderer(SeedTile.class, seedBlockRender);

        RenderingRegistry.registerEntityRenderingHandler(PickupEntity.class, pickupEntityRenderFactory::create);
    }
}
