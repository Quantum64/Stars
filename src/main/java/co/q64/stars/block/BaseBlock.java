package co.q64.stars.block;

import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

@Getter
public abstract class BaseBlock extends Block {
    private String id;

    public BaseBlock(String id) {
        this(id, Properties.create(Material.IRON));
    }

    public BaseBlock(String id, Material material) {
        this(id, Properties.create(Material.IRON));
    }

    public BaseBlock(String id, Properties settings) {
        super(settings);
        this.id = id;
        setRegistryName(id);
    }
}
