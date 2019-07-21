package co.q64.stars.block;

import net.minecraft.block.material.Material;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GreenFruitBlock extends FormedBlock {

    protected @Inject GreenFruitBlock() {
        super("green_fruit", Properties.create(Material.GLASS).hardnessAndResistance(0f, 0f));
    }
}
