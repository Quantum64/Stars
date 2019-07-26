package co.q64.stars.util;

import net.minecraft.block.SoundType;
import net.minecraft.util.SoundEvents;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NoSound extends SoundType {

    @Inject
    protected NoSound() {
        super(0f, 1f, SoundEvents.UI_BUTTON_CLICK, SoundEvents.UI_BUTTON_CLICK, SoundEvents.UI_BUTTON_CLICK, SoundEvents.UI_BUTTON_CLICK, SoundEvents.UI_BUTTON_CLICK);
    }
}
