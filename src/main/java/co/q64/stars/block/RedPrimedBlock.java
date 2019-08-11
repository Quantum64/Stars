package co.q64.stars.block;

import co.q64.stars.util.NoSound;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RedPrimedBlock extends FormedBlock {

    protected @Inject RedPrimedBlock(NoSound noSound) {
        super("red_primed", Properties.create(Material.EARTH).sound(noSound).hardnessAndResistance(0f, 0f));
    }

    private RedPrimedBlock(String id, Properties properties) {
        super(id, properties);
    }

    @Singleton
    public static class RedPrimedBlockHard extends RedPrimedBlock implements HardBlock {
        protected @Inject RedPrimedBlockHard() {
            super("red_primed_hard", Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(-1f, 3600000f));
        }
    }
}
