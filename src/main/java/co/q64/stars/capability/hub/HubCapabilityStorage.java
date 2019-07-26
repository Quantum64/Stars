package co.q64.stars.capability.hub;

import co.q64.stars.capability.HubCapability;
import co.q64.stars.type.FormingBlockTypes;
import co.q64.stars.util.nbt.ExtendedTag;
import co.q64.stars.util.nbt.NBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HubCapabilityStorage implements IStorage<HubCapability> {
    protected @Inject FormingBlockTypes formingBlockTypes;
    protected @Inject NBT nbt;

    protected @Inject HubCapabilityStorage() {}

    public INBT writeNBT(Capability<HubCapability> capability, HubCapability instance, Direction side) {
        ExtendedTag tag = nbt.create();
        tag.putInt("nextIndex", instance.getNextIndex());
        return tag.compound();
    }

    public void readNBT(Capability<HubCapability> capability, HubCapability instance, Direction side, INBT inbt) {
        ExtendedTag tag = nbt.extend(inbt);
        tag.ifInt("nextIndex", instance::setNextIndex);
    }
}
