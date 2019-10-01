package co.q64.stars.listener;

import co.q64.stars.util.NamedSoundEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Optional;
import java.util.Set;

@Singleton
public class RegistryListener implements Listener {
    protected @Inject Provider<Set<Block>> blocks;
    protected @Inject Provider<Set<Item>> items;
    protected @Inject Provider<Set<TileEntityType<?>>> tileEntityTypes;
    protected @Inject Provider<Set<EntityType<?>>> entityTypes;
    protected @Inject Provider<Set<Feature<?>>> features;
    protected @Inject Provider<Set<Biome>> biomes;
    protected @Inject Provider<Set<Placement<?>>> placements;
    protected @Inject Provider<Set<SoundEvent>> soundEvents;
    protected @Inject Provider<Set<ModDimension>> dimensions;

    protected @Inject RegistryListener() {}

    @SubscribeEvent
    public void onBlockRegistry(Register<Block> event) {
        event.getRegistry().registerAll(blocks.get().toArray(new Block[0]));
    }

    @SubscribeEvent
    public void onItemRegistry(Register<Item> event) {
        //event.getRegistry().registerAll(items.get().toArray(new Item[0]));
        event.getRegistry().registerAll(items.get().stream().sorted(((o1, o2) -> o1.getRegistryName().compareTo(o2.getRegistryName()))).toArray(Item[]::new));
    }

    @SubscribeEvent
    public void onTileEntityRegistry(Register<TileEntityType<?>> event) {
        event.getRegistry().registerAll(tileEntityTypes.get().toArray(new TileEntityType[0]));
    }

    @SubscribeEvent
    public void onEntityRegistry(Register<EntityType<?>> event) {
        event.getRegistry().registerAll(entityTypes.get().toArray(new EntityType[0]));
    }

    @SubscribeEvent
    public void onFeatureRegistry(Register<Feature<?>> event) {
        event.getRegistry().registerAll(features.get().toArray(new Feature[0]));
    }

    @SubscribeEvent
    public void onBiomeRegistry(Register<Biome> event) {
        event.getRegistry().registerAll(biomes.get().toArray(new Biome[0]));
    }

    @SubscribeEvent
    public void onPlacementRegistry(Register<Placement<?>> event) {
        event.getRegistry().registerAll(placements.get().toArray(new Placement[0]));
    }

    @SubscribeEvent
    public void onSoundEventRegistry(Register<SoundEvent> event) {
        event.getRegistry().registerAll(soundEvents.get().stream().map(sound -> sound.setRegistryName(((NamedSoundEvent) sound).getLoc())).toArray(SoundEvent[]::new));
    }

    @SubscribeEvent
    public void onDimensionsRegistry(Register<ModDimension> event) {
        event.getRegistry().registerAll(dimensions.get().toArray(new ModDimension[0]));
    }

    @SubscribeEvent
    public void onRegisterDimensions(RegisterDimensionsEvent event) {
        // Crazy stupid thing where you have to "manually" register the dimensions if they
        // haven't been previously saved because for some reason the above event isn't
        // able to handle that for you
        // https://gyazo.com/83e7d02c650aef81ce03ed342f760a5e
        for (ModDimension dimension : dimensions.get()) {
            if (!Optional.ofNullable(DimensionType.byName(dimension.getRegistryName())).isPresent()) {
                // The dimension hasn't been saved in this world before so register it manually
                DimensionManager.registerDimension(dimension.getRegistryName(), dimension, null, false);
            }
        }
    }
}
