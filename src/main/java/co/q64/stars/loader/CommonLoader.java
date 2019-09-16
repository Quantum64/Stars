package co.q64.stars.loader;

import co.q64.stars.dimension.overworld.feature.GatewayFeature;
import co.q64.stars.dimension.overworld.placement.GatewayPlacement;
import co.q64.stars.link.LinkManager;
import co.q64.stars.listener.Listener;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
public class CommonLoader {
    private static final Set<Category> blacklist = Stream.of(Category.NONE, Category.NETHER, Category.THEEND).collect(Collectors.toSet());

    protected @Inject Set<Listener> listeners;
    protected @Inject LinkManager linkManager;
    protected @Inject GatewayFeature gatewayFeature;
    protected @Inject GatewayPlacement gatewayPlacement;

    protected @Inject CommonLoader() {}

    public void load() {
        for (Listener listener : listeners) {
            MinecraftForge.EVENT_BUS.register(listener);
            FMLJavaModLoadingContext.get().getModEventBus().register(listener);
        }
        linkManager.init();

        for (Biome biome : ForgeRegistries.BIOMES.getValues()) {
            if (blacklist.contains(biome.getCategory())) {
                continue;
            }
            biome.addFeature(Decoration.TOP_LAYER_MODIFICATION, Biome.createDecoratedFeature(gatewayFeature, IFeatureConfig.NO_FEATURE_CONFIG, gatewayPlacement, IPlacementConfig.NO_PLACEMENT_CONFIG));
        }
    }
}
