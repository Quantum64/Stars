package co.q64.stars.block;

import net.minecraft.block.material.Material;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DarknessBlock extends BaseBlock {

    protected DarknessBlock(String id, Properties settings) {
        super(id, settings);
    }

    protected @Inject DarknessBlock() {
        super("darkness", Properties.create(Material.IRON).hardnessAndResistance(1.5f, 6.0f));
    }
}
