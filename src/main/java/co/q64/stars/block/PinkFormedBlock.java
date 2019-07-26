package co.q64.stars.block;

import co.q64.stars.util.NoSound;
import net.minecraft.block.material.Material;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PinkFormedBlock extends FormedBlock {

    protected @Inject PinkFormedBlock(NoSound noSound) {
        super("pink_formed", Properties.create(Material.EARTH).sound(noSound).hardnessAndResistance(0f, 0f));
    }
}
