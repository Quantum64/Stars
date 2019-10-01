package co.q64.stars.util;


import lombok.Getter;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class NamedSoundEvent extends SoundEvent {
    private @Getter ResourceLocation loc;

    public NamedSoundEvent(ResourceLocation name) {
        super(name);
        this.loc = name;
    }
}
