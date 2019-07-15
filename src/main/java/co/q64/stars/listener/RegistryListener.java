package co.q64.stars.listener;

import co.q64.stars.block.BaseBlock;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Set;

@Singleton
public class RegistryListener implements Listener {
    protected @Inject Set<BaseBlock> blocks;
    //protected @Inject Set<BaseItem> items;
    protected @Inject Set<TileEntityType<?>> tileEntityTypes;
    //protected @Inject Set<ContainerType<?>> containerTypes;

    protected @Inject RegistryListener() {}

    @SubscribeEvent
    public void onBlockRegistry(Register<Block> event) {
        event.getRegistry().registerAll(blocks.toArray(new Block[0]));
    }

    @SubscribeEvent
    public void onItemRegistry(Register<Item> event) {
        //event.getRegistry().registerAll(items.toArray(new Item[0]));
        for (BaseBlock block : blocks) {
            event.getRegistry().register(new BlockItem(block, new Properties()).setRegistryName(block.getRegistryName()));
        }
    }

    @SubscribeEvent
    public void onTileEntityRegistry(Register<TileEntityType<?>> event) {
        event.getRegistry().registerAll(tileEntityTypes.toArray(new TileEntityType[0]));
    }

    @SubscribeEvent
    public void onContainerRegistry(Register<ContainerType<?>> event) {
        //event.getRegistry().registerAll(containerTypes.toArray(new ContainerType[0]));
    }
}
