package co.q64.stars.util;

import co.q64.stars.qualifier.SoundQualifiers.Empty;
import net.minecraft.block.SoundType;
import net.minecraft.util.SoundEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NoSound extends SoundType {

    @Inject
    protected NoSound(@Empty SoundEvent sound) {
        super(0f, 1f, sound, sound, sound, sound, sound);
    }
}
