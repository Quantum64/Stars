package co.q64.stars.listener;

import co.q64.stars.block.BaseBlock;
import co.q64.stars.item.BaseItem;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Set;

@Singleton
public class RegistryListener implements Listener {
    protected @Inject Provider<Set<BaseBlock>> blocks;
    protected @Inject Provider<Set<BaseItem>> items;
    protected @Inject Provider<Set<TileEntityType<?>>> tileEntityTypes;
    protected @Inject Provider<Set<EntityType<?>>> entityTypes;
    protected @Inject Provider<Set<Feature<?>>> features;
    protected @Inject Provider<Set<Biome>> biomes;
    protected @Inject Provider<Set<Placement<?>>> placements;
    protected @Inject Provider<Set<Set<SoundEvent>>> soundEvents;

    protected @Inject RegistryListener() {}

    @SubscribeEvent
    public void onBlockRegistry(Register<Block> event) {
        event.getRegistry().registerAll(blocks.get().toArray(new Block[0]));
    }

    @SubscribeEvent
    public void onItemRegistry(Register<Item> event) {
        event.getRegistry().registerAll(items.get().toArray(new Item[0]));
        for (BaseBlock block : blocks.get()) { // TODO remove
            event.getRegistry().register(new BlockItem(block, new Properties()).setRegistryName(block.getRegistryName()));
        }
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
        event.getRegistry().registerAll(soundEvents.get().stream().flatMap(Set::stream).map(sound -> sound.setRegistryName(sound.getName())).toArray(SoundEvent[]::new));
    }
}
