package co.q64.stars.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class OrangeFormedBlock extends FormedBlock {

    protected @Inject OrangeFormedBlock() {
        super("orange_formed", Properties.create(Material.EARTH).sound(SoundType.GROUND).hardnessAndResistance(0f, 0f));
    }

    private OrangeFormedBlock(String id, Properties properties) {
        super(id, properties);
    }

    @Singleton
    public static class OrangeFormedBlockHard extends OrangeFormedBlock implements HardBlock {
        protected @Inject OrangeFormedBlockHard() {
            super("orange_formed_hard", Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(-1f, 3600000f));
        }
    }
}
