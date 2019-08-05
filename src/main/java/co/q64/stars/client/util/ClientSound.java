package co.q64.stars.client.util;

import co.q64.stars.client.render.PlayerOverlayRender;
import co.q64.stars.dimension.StarsDimension;
import co.q64.stars.qualifier.SoundQualifiers.AmbientDark;
import co.q64.stars.type.FleetingStage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.util.SoundEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ClientSound {
    protected @Inject PlayerOverlayRender playerOverlayRender;
    protected @Inject @AmbientDark SoundEvent ambientDarkSound;

    private FleetingStage stage;
    private ISound sound;

    protected @Inject ClientSound() {}

    public void tick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.player.world == null || !(mc.player.world.getDimension() instanceof StarsDimension)) {
            if (sound != null) {
                mc.getSoundHandler().stop(sound);
                sound = null;
            }
            return;
        }
        if (stage != playerOverlayRender.getLastStage()) {
            mc.getSoundHandler().stop(sound);
            sound = null;
            stage = playerOverlayRender.getLastStage();
        }
        if (sound == null) {
            if (stage == FleetingStage.DARK) {
                sound = SimpleSound.master(ambientDarkSound, 1f, 1f);
            }
        }
        if (sound != null) {
            if (!mc.getSoundHandler().isPlaying(sound)) {
                mc.getSoundHandler().play(sound);
            }
        }
    }
}
