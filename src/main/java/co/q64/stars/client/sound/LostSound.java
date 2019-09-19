package co.q64.stars.client.sound;

import co.q64.stars.client.render.PlayerOverlayRender;
import co.q64.stars.qualifier.SoundQualifiers.Lost;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

import javax.inject.Inject;

public class LostSound extends TickableSound {
    private PlayerOverlayRender playerOverlayRender;

    @Inject
    protected LostSound(@Lost SoundEvent event, PlayerOverlayRender playerOverlayRender) {
        super(event, SoundCategory.MASTER);
        this.playerOverlayRender = playerOverlayRender;
        this.repeat = true;
        this.attenuationType = AttenuationType.NONE;
        this.volume = 0.0F;
    }

    @Override
    public boolean canBeSilent() {
        return true;
    }

    @Override
    public void tick() {
        this.volume = (float) playerOverlayRender.getLostOverlayProgress();
    }
}