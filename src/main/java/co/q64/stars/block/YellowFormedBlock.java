package co.q64.stars.block;

import lombok.Getter;
import net.minecraft.block.material.Material;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class YellowFormedBlock extends FormedBlock {

    protected @Inject YellowFormedBlock() {
        super("yellow_formed", Properties.create(Material.IRON).hardnessAndResistance(1.5f, 6.0f));
    }
}
