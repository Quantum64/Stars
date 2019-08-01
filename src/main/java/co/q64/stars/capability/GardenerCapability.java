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

    public long getLastJumped();

    public void setLastJumped(long time);

    public int getHubIndex();

    public void setHubIndex(int index);

    public FleetingStage getFleetingStage();

    public void setFleetingStage(FleetingStage fleetingStage);

    public boolean isOpenDoor();

    public void setOpenDoor(boolean openedDoor);

    public boolean isOpenChallengeDoor();

    public void setOpenChallengeDoor(boolean openChallengeDoor);

    public boolean isEnteringHub();

    public void setEnteringHub(boolean enteringHub);

    public FormingBlockType getLastSeed();

    public void setLastSeed(FormingBlockType formingBlockType);

    public Deque<FormingBlockType> getNextSeeds();

    public Set<SoundEvent> getLastSounds();
}
