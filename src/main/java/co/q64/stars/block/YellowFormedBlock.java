package co.q64.stars.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class YellowFormedBlock extends FormedBlock {

    protected @Inject YellowFormedBlock() {
        super("yellow_formed", Properties.create(Material.EARTH).sound(SoundType.GROUND).hardnessAndResistance(0f, 0f));
    }

    private YellowFormedBlock(String id, Properties properties) {
        super(id, properties);
    }

    @Singleton
    public static class YellowFormedBlockHard extends YellowFormedBlock implements HardBlock {
        protected @Inject YellowFormedBlockHard() {
            super("yellow_formed_hard", Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(-1f, 3600000f));
        }
    }
}
