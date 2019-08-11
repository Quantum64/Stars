package co.q64.stars.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GreenFormedBlock extends FormedBlock {

    protected @Inject GreenFormedBlock() {
        super("green_formed", Properties.create(Material.EARTH).sound(SoundType.GROUND).hardnessAndResistance(0f, 0f));
    }

    private GreenFormedBlock(String id, Properties properties) {
        super(id, properties);
    }

    @Singleton
    public static class GreenFormedBlockHard extends GreenFormedBlock implements HardBlock {
        protected @Inject GreenFormedBlockHard() {
            super("green_formed_hard", Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(-1f, 3600000f));
        }
    }
}
