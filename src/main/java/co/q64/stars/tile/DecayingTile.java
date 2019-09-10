package co.q64.stars.tile;

import co.q64.stars.type.FormingBlockType;
import co.q64.stars.type.FormingBlockTypes;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;

import javax.inject.Inject;

public class DecayingTile extends SeedTile {
    protected @Inject FormingBlockTypes types;

    private @Getter @Setter boolean calculated;
    private @Getter @Setter boolean hasSeed;
    private @Getter @Setter int expectedDecayTime;
    private @Getter @Setter long placed = System.currentTimeMillis();

    @Inject
    protected DecayingTile(TileEntityType<DecayingTile> type) {
        super(type);
    }

    public void read(CompoundNBT compound) {
        super.read(compound);
        expectedDecayTime = compound.getInt("expectedDecayTime");
        placed = compound.getLong("placed");
        calculated = compound.getBoolean("calculated");
        hasSeed = compound.getBoolean("hasSeed");
    }

    public CompoundNBT write(CompoundNBT compound) {
        CompoundNBT result = super.write(compound);
        result.putInt("expectedDecayTime", expectedDecayTime);
        result.putLong("placed", placed);
        result.putBoolean("calculated", calculated);
        result.putBoolean("hasSeed", hasSeed);
        return result;
    }

    protected boolean hasSeed() {
        return hasSeed;
    }

    public void grow(FormingBlockType type) {
        setSeedType(type);
        setGrowTicks(getInitialGrowTicks());
        setHasSeed(true);
        world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 3);
    }
}
