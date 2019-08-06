package co.q64.stars.util;

import co.q64.stars.capability.GardenerCapability;
import co.q64.stars.qualifier.SoundQualifiers.Dark;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Singleton
public class Sounds {
    protected @Inject Provider<Capability<GardenerCapability>> gardenerCapability;

    private Map<SoundEvent, Long> playLimit = new HashMap<>();

    protected @Inject Sounds() {}

    public void playRangedSound(ServerWorld world, BlockPos pos, SoundEvent sound, int range, float volume) {
        playRangedSound(world, pos, Collections.singleton(sound), range, volume);
    }

    public void playRangedSound(ServerWorld world, BlockPos pos, Set<SoundEvent> sounds, int range, float volume) {
        for (ServerPlayerEntity player : world.getPlayers()) {
            if (player.getPosition().distanceSq(pos) < range * range) {
                player.getCapability(gardenerCapability.get()).ifPresent(cap -> {
                    SoundEvent event = getSound(cap, sounds);
                    float total = (1 - (float) Math.sqrt(player.getPosition().distanceSq(pos)) / range) * volume;
                    if (System.currentTimeMillis() > cap.getLastPlayed(event) + playLimit.getOrDefault(event, 0L)) {
                        world.playSound(null, pos, event, SoundCategory.MASTER, total, 1f);
                        cap.setLastPlayed(event, System.currentTimeMillis());
                    }
                });
            }
        }
    }

    public void playSound(ServerWorld world, BlockPos pos, SoundEvent sound, float volume) {
        playSound(world, pos, Collections.singleton(sound), volume);
    }

    public void playSound(ServerWorld world, BlockPos pos, Set<SoundEvent> sounds, float volume) {
        playSound(world, pos, sounds, volume, 1f);
    }

    public void playSound(ServerWorld world, BlockPos pos, Set<SoundEvent> sounds, float volume, float pitch) {
        for (ServerPlayerEntity player : world.getPlayers()) {
            player.getCapability(gardenerCapability.get()).ifPresent(cap -> {
                SoundEvent event = getSound(cap, sounds);
                if (System.currentTimeMillis() > cap.getLastPlayed(event) + playLimit.getOrDefault(event, 0L)) {
                    world.playSound(null, pos, event, SoundCategory.MASTER, volume, pitch);
                    cap.setLastPlayed(event, System.currentTimeMillis());
                }
            });
        }
    }

    private SoundEvent getSound(GardenerCapability capability, Set<SoundEvent> sounds) {
        SoundEvent event = sounds.iterator().next();
        List<SoundEvent> unplayed = sounds.stream().filter(sound -> !capability.getLastSounds().contains(sound)).collect(Collectors.toList());
        if (unplayed.size() > 0) {
            event = unplayed.get(ThreadLocalRandom.current().nextInt(unplayed.size()));
        }
        capability.getLastSounds().removeAll(sounds);
        capability.getLastSounds().add(event);
        return event;
    }

    @Inject
    protected void limitDarkSounds(@Dark Set<SoundEvent> sounds) {
        sounds.forEach(sound -> playLimit.put(sound, 500L));
    }
}
