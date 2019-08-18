package co.q64.stars.tile;

import co.q64.stars.tile.type.TrophyTileType;
import co.q64.stars.type.FormingBlockType;
import co.q64.stars.type.FormingBlockTypes;
import co.q64.stars.type.forming.GreyFormingBlockType;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.CompoundNBT;

import javax.inject.Inject;

public class TrophyTile extends SyncTileEntity {
    protected @Inject FormingBlockTypes types;

    private @Setter @Getter FormingBlockType forming;

    @Inject
    public TrophyTile(TrophyTileType type, GreyFormingBlockType defaultType) {
        super(type);
        this.forming = defaultType;
    }

    public void read(CompoundNBT compound) {
        this.forming = types.get(compound.getInt("forming"));
        super.read(compound);
    }

    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("forming", forming.getId());
        return super.write(compound);
    }
}
