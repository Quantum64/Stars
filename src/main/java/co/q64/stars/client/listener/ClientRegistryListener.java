package co.q64.stars.client.listener;

import co.q64.stars.block.AirDecayBlock;
import co.q64.stars.block.AirDecayEdgeBlock;
import co.q64.stars.block.DarkAirBlock;
import co.q64.stars.client.model.ForceRenderCullModelFactory;
import co.q64.stars.item.ArrowItem;
import co.q64.stars.listener.Listener;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
public class ClientRegistryListener implements Listener {
    protected @Inject ForceRenderCullModelFactory forceRenderCullModelFactory;
    protected @Inject DarkAirBlock darkAirBlock;
    protected @Inject AirDecayBlock airDecayBlock;
    protected @Inject AirDecayEdgeBlock airDecayEdgeBlock;
    protected @Inject Provider<ArrowItem> arrowItemProvider;

    protected @Inject ClientRegistryListener() {}

    @SubscribeEvent
    public void modelBakeEvent(ModelBakeEvent event) {
        Set<String> replace = Stream.of(darkAirBlock, airDecayBlock, airDecayEdgeBlock).map(block -> block.getRegistryName().getPath()).collect(Collectors.toSet());
        Map<ResourceLocation, IBakedModel> registry = event.getModelRegistry();
        for (ResourceLocation location : registry.keySet()) {
            ModelResourceLocation modelResourceLocation = new ModelResourceLocation(location.toString());
            ResourceLocation block = new ResourceLocation(modelResourceLocation.getNamespace(), modelResourceLocation.getPath());
            if (replace.contains(block.getPath())) {
                registry.put(location, forceRenderCullModelFactory.create(registry.get(location)));
            }
        }
    }

    /*
    @SubscribeEvent
    public void onItemColorHandler(ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, layer) -> {
            // TODO Investigate performance
            PlayerEntity player = Minecraft.getInstance().player;
            if (player.getEntityWorld() != null) {
                List<PickupEntity> entities = player.getEntityWorld().getEntitiesWithinAABB(PickupEntity.class, player.getBoundingBox()
                                .expand(10, 10, 10).expand(-10, -10, -10),
                        entity -> entity.getVariant() == PickupEntity.VARIANT_ARROW);
                if (!entities.isEmpty()) {
                    double x = entities.get(0).posX, y = entities.get(0).posY, z = entities.get(0).posZ, dx = player.posX, dy = player.posY, dz = player.posZ;
                    double distsq = ((x - dx) * (x - dx)) + ((y - dy) * (y - dy) + ((z - dz) * (z - dz)));
                    int color = (int) (Math.min((distsq / 10.0), 1) * 255);
                    int result = (color) + (color << 8) + (color << 16) + (color << 24);
                    return result;
                }
            }
            return 0xFFFFFFFF;
        }, arrowItemProvider::get);
    }
     */
}
