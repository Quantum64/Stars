package co.q64.stars.capability.gardener;

import co.q64.stars.capability.GardenerCapability;
import co.q64.stars.type.FleetingStage;
import co.q64.stars.type.FormingBlockType;
import co.q64.stars.type.FormingBlockTypes;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Singleton
public class GardenerCapabilityStorage implements IStorage<GardenerCapability> {
    protected @Inject FormingBlockTypes formingBlockTypes;

    protected @Inject GardenerCapabilityStorage() {}

    public INBT writeNBT(Capability<GardenerCapability> capability, GardenerCapability instance, Direction side) {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("seeds", instance.getSeeds());
        tag.putInt("keys", instance.getKeys());
        tag.putString("fleetingStage", instance.getFleetingStage().name());
        tag.putIntArray("nextSeeds", instance.getNextSeeds().stream().mapToInt(FormingBlockType::getId).toArray());
        tag.putInt("seedVisibility", instance.getSeedVisibility());
        return tag;
    }

    public void readNBT(Capability<GardenerCapability> capability, GardenerCapability instance, Direction side, INBT nbt) {
        CompoundNBT tag = (CompoundNBT) nbt;
        instance.setSeeds(tag.getInt("seeds"));
        instance.setKeys(tag.getInt("keys"));
        Stream.of(tag.getString("fleetingStage")).filter(s -> !s.equals("")).map(FleetingStage::valueOf).forEach(instance::setFleetingStage);
        IntStream.of(tag.getIntArray("nextSeeds")).mapToObj(formingBlockTypes::get).collect(Collectors.toList()).forEach(type -> instance.getNextSeeds().offer(type));
        if (tag.contains("seedVisibility")) {
            instance.setSeedVisibility(tag.getInt("seedVisibility"));
        }
    }
}
