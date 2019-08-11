package co.q64.stars.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GreenFruitBlock extends FormedBlock {

    protected @Inject GreenFruitBlock() {
        super("green_fruit", Properties.create(Material.EARTH).sound(SoundType.GROUND).hardnessAndResistance(0f, 0f));
    }

    private GreenFruitBlock(String id, Properties properties) {
        super(id, properties);
    }

    @Singleton
    public static class GreenFruitBlockHard extends GreenFruitBlock implements HardBlock {
        protected @Inject GreenFruitBlockHard() {
            super("green_fruit_hard", Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(-1f, 3600000f));
        }
    }
}
