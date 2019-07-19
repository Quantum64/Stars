package co.q64.stars.client.listener;

import co.q64.stars.block.AirDecayBlock;
import co.q64.stars.block.AirDecayEdgeBlock;
import co.q64.stars.block.DarkAirBlock;
import co.q64.stars.client.model.ForceRenderCullModelFactory;
import co.q64.stars.listener.Listener;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.inject.Inject;
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
}
