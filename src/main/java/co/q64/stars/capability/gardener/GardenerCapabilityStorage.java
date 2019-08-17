package co.q64.stars.capability.gardener;

import co.q64.stars.capability.GardenerCapability;
import co.q64.stars.level.LevelType;
import co.q64.stars.type.FleetingStage;
import co.q64.stars.type.FormingBlockType;
import co.q64.stars.type.FormingBlockTypes;
import co.q64.stars.util.nbt.ExtendedTag;
import co.q64.stars.util.nbt.NBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Singleton
public class GardenerCapabilityStorage implements IStorage<GardenerCapability> {
    protected @Inject FormingBlockTypes formingBlockTypes;
    protected @Inject NBT nbt;

    protected @Inject GardenerCapabilityStorage() {}

    public INBT writeNBT(Capability<GardenerCapability> capability, GardenerCapability instance, Direction side) {
        ExtendedTag tag = nbt.create();
        tag.putInt("seeds", instance.getSeeds());
        tag.putInt("keys", instance.getKeys());
        tag.putString("fleetingStage", instance.getFleetingStage().name());
        tag.putIntArray("nextSeeds", instance.getNextSeeds().stream().mapToInt(FormingBlockType::getId).toArray());
        tag.putInt("seedVisibility", instance.getSeedVisibility());
        tag.putInt("hubIndex", instance.getHubIndex());
        tag.putString("levelType", instance.getLevelType().name());
        tag.put("hubSpawn", NBTUtil.writeBlockPos(instance.getHubSpawn()));
        return tag.compound();
    }

    public void readNBT(Capability<GardenerCapability> capability, GardenerCapability instance, Direction side, INBT inbt) {
        ExtendedTag tag = nbt.extend(inbt);
        instance.setSeeds(tag.getInt("seeds"));
        instance.setKeys(tag.getInt("keys"));
        tag.ifString("fleetingStage", value -> instance.setFleetingStage(FleetingStage.valueOf(value)));
        IntStream.of(tag.getIntArray("nextSeeds")).mapToObj(formingBlockTypes::get).collect(Collectors.toList()).forEach(type -> instance.getNextSeeds().offer(type));
        tag.ifInt("seedVisibility", instance::setSeedVisibility);
        tag.ifInt("hubIndex", instance::setHubIndex);
        tag.ifString("levelType", value -> instance.setLevelType(LevelType.valueOf(value)));
        tag.ifCompound("hubSpawn", value -> instance.setHubSpawn(NBTUtil.readBlockPos(value)));
    }
}
