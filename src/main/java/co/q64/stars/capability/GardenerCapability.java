package co.q64.stars.capability;

import co.q64.stars.type.FleetingStage;
import co.q64.stars.type.FormingBlockType;
import net.minecraft.util.SoundEvent;

import java.util.Deque;
import java.util.Set;

public interface GardenerCapability {
    public int getSeeds();

    public void setSeeds(int seeds);

    public int getKeys();

    public void setKeys(int keys);

    public int getSeedVisibility();

    public void setSeedVisibility(int visibility);

    public int getSeedsSincePink();

    public void setSeedsSincePink(int seedsSincePink);

    public int getTotalSeeds();

    public void setTotalSeeds(int totalSeeds);

    public long getLastPlayed(SoundEvent event);

    public void setLastPlayed(SoundEvent event, long time);

    public FleetingStage getFleetingStage();

    public void setFleetingStage(FleetingStage fleetingStage);

    public FormingBlockType getLastSeed();

    public void setLastSeed(FormingBlockType formingBlockType);

    public Deque<FormingBlockType> getNextSeeds();

    public Set<SoundEvent> getLastSounds();
}