package co.q64.stars.block;

import net.minecraft.block.material.Material;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BrownFormedBlock extends FormedBlock {

    protected @Inject BrownFormedBlock() {
        super("brown_formed", Properties.create(Material.GLASS).hardnessAndResistance(0f, 0f));
    }
}
