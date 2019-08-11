package co.q64.stars.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CyanFormedBlock extends FormedBlock {

    protected @Inject CyanFormedBlock() {
        super("cyan_formed", Properties.create(Material.EARTH).sound(SoundType.GROUND).hardnessAndResistance(0f, 0f));
    }

    private CyanFormedBlock(String id, Properties properties) {
        super(id, properties);
    }

    @Singleton
    public static class CyanFormedBlockHard extends CyanFormedBlock implements HardBlock {
        protected @Inject CyanFormedBlockHard() {
            super("cyan_formed_hard", Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(-1f, 3600000f));
        }
    }
}
