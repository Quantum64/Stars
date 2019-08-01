package co.q64.stars.client.listener;

import co.q64.stars.block.AirDecayBlock;
import co.q64.stars.block.AirDecayEdgeBlock;
import co.q64.stars.block.DarkAirBlock;
import co.q64.stars.block.DarknessBlock;
import co.q64.stars.client.model.DarknessModel;
import co.q64.stars.client.model.ForceRenderCullModelFactory;
import co.q64.stars.item.ArrowItem;
import co.q64.stars.listener.Listener;
import co.q64.stars.util.Logger;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.HashSet;
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
    protected @Inject DarknessModel darknessModel;
    protected @Inject DarknessBlock darknessBlock;
    protected @Inject Logger logger;

    private Set<ResourceLocation> textures = new HashSet<>();

    protected @Inject ClientRegistryListener() {}

    @SubscribeEvent
    public void onModelBake(ModelBakeEvent event) {
        darknessModel.bake(event.getModelLoader());
        Set<String> cullBlocks = Stream.of(darkAirBlock, airDecayBlock, airDecayEdgeBlock).map(block -> block.getRegistryName().getPath()).collect(Collectors.toSet());
        Map<ResourceLocation, IBakedModel> registry = event.getModelRegistry();
        for (ResourceLocation location : registry.keySet()) {
            ModelResourceLocation modelResourceLocation = new ModelResourceLocation(location.toString());
            ResourceLocation block = new ResourceLocation(modelResourceLocation.getNamespace(), modelResourceLocation.getPath());
            if (cullBlocks.contains(block.getPath())) {
                registry.put(location, forceRenderCullModelFactory.create(registry.get(location)));
            }
            if (block.getPath().equals(darknessBlock.getRegistryName().getPath())) {
                registry.put(location, darknessModel);
            }
        }
    }

    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Pre event) {
        textures.addAll(darknessModel.getTextures());
        if (event.getMap().getBasePath().equals("textures")) {
            logger.info("Adding " + textures.size() + " textures to blocks sheet");
            textures.forEach(event::addSprite);
        }
    }
}
