package co.q64.stars.block;

import net.minecraft.block.material.Material;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RedPrimedBlock extends FormedBlock {

    protected @Inject RedPrimedBlock() {
        super("red_primed", Properties.create(Material.GLASS).hardnessAndResistance(0f, 0f));
    }
}
