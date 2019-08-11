package co.q64.stars.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PurpleFormedBlock extends FormedBlock {

    protected @Inject PurpleFormedBlock() {
        super("purple_formed", Properties.create(Material.EARTH).sound(SoundType.GROUND).hardnessAndResistance(0f, 0f));
    }

    private PurpleFormedBlock(String id, Properties properties) {
        super(id, properties);
    }

    @Singleton
    public static class PurpleFormedBlockHard extends PurpleFormedBlock implements HardBlock {
        protected @Inject PurpleFormedBlockHard() {
            super("purple_formed_hard", Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(-1f, 3600000f));
        }
    }
}
