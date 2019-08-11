package co.q64.stars.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BrownFormedBlock extends FormedBlock {

    protected @Inject BrownFormedBlock() {
        super("brown_formed", Properties.create(Material.EARTH).sound(SoundType.GROUND).hardnessAndResistance(0f, 0f));
    }

    private BrownFormedBlock(String id, Properties properties) {
        super(id, properties);
    }

    @Singleton
    public static class BrownFormedBlockHard extends BrownFormedBlock implements HardBlock {
        protected @Inject BrownFormedBlockHard() {
            super("brown_formed_hard", Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(-1f, 3600000f));
        }
    }
}
