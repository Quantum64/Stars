package co.q64.stars.item;

import lombok.Getter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

@Getter
public abstract class BaseItem extends Item {
    private String id;

    public BaseItem(String id) {
        this(id, new Properties());
    }

    public BaseItem(String id, ItemGroup group) {
        this(id, new Properties().group(group));
    }

    public BaseItem(String id, Properties settings) {
        super(settings);
        this.id = id;
        setRegistryName(id);
    }
}
