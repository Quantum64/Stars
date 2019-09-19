package co.q64.stars.client.sound;

import co.q64.stars.client.render.PlayerOverlayRender;
import co.q64.stars.dimension.StarsDimension;
import co.q64.stars.qualifier.SoundQualifiers.AmbientDark;
import co.q64.stars.qualifier.SoundQualifiers.AmbientHub;
import co.q64.stars.qualifier.SoundQualifiers.AmbientLight;
import co.q64.stars.qualifier.SoundQualifiers.Lost;
import co.q64.stars.type.FleetingStage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.SoundEvent;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class ClientSound {
    protected @Inject PlayerOverlayRender playerOverlayRender;
    protected @Inject @AmbientDark SoundEvent ambientDarkEvent;
    protected @Inject @AmbientLight SoundEvent ambientLightEvent;
    protected @Inject @AmbientHub SoundEvent ambientHubEvent;
    protected @Inject @Lost SoundEvent lostEvent;
    protected @Inject Provider<LostSound> lostSoundProvider;

    private Map<FleetingStage, ISound> ambient = new HashMap<>();
    private FleetingStage stage;
    private ISound lostSound;

    protected @Inject ClientSound() {}

    public void tick() {
        Minecraft mc = Minecraft.getInstance();
        SoundHandler sh = mc.getSoundHandler();
        if (mc.player == null || mc.player.world == null || !(mc.player.world.getDimension() instanceof StarsDimension)) {
            resetAmbient();
            if (lostSound != null) {
                sh.stop(lostSound);
                lostSound = null;
            }
            return;
        }
        if (stage != playerOverlayRender.getLastStage()) {
            resetAmbient();
            sh.stop(lostSound);
            lostSound = null;
            stage = playerOverlayRender.getLastStage();
        }

        if (!ambient.containsKey(stage)) {
            ambient.put(stage, SimpleSound.master(getEvent(stage), 1f, 1f));
        }
        if (stage == FleetingStage.DARK) {
            if (lostSound == null) {
                lostSound = lostSoundProvider.get();
            }
        }
        for (ISound sound : ambient.values()) {
            if (!sh.isPlaying(sound)) {
                sh.play(sound);
            }
        }
        if (lostSound != null) {
            if (!sh.isPlaying(lostSound)) {
                sh.play(lostSound);
            }
        }
    }

    private void resetAmbient() {
        for (ISound sound : ambient.values()) {
            Minecraft.getInstance().getSoundHandler().stop(sound);
        }
        ambient.clear();
    }

    private SoundEvent getEvent(FleetingStage stage) {
        switch (stage) {
            case LIGHT:
                return ambientLightEvent;
            case DARK:
                return ambientDarkEvent;
            default:
                return ambientHubEvent;
        }
    }
}
