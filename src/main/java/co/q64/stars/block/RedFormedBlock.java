package co.q64.stars.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RedFormedBlock extends FormedBlock {

    protected @Inject RedFormedBlock() {
        super("red_formed", Properties.create(Material.EARTH).sound(SoundType.GROUND).hardnessAndResistance(0f, 0f));
    }

    private RedFormedBlock(String id, Properties properties) {
        super(id, properties);
    }

    @Singleton
    public static class RedFormedBlockHard extends RedFormedBlock implements HardBlock {
        protected @Inject RedFormedBlockHard() {
            super("red_formed_hard", Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(-1f, 3600000f));
        }
    }
}
