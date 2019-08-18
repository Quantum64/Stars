package co.q64.stars.tile;

import co.q64.stars.block.TrophyBlock;
import co.q64.stars.block.TrophyBlock.TrophyVariant;
import co.q64.stars.level.LevelManager;
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
    protected @Inject LevelManager levelManager;

    private @Setter @Getter boolean hasBlock = false;
    private @Setter @Getter boolean sunbeams = true;
    private @Setter @Getter FormingBlockType forming;

    @Inject
    public TrophyTile(TrophyTileType type, GreyFormingBlockType defaultType) {
        super(type);
        this.forming = defaultType;
    }

    public void setup(TrophyBlock trophyBlock) {
        this.hasBlock = trophyBlock.getVariant() != TrophyVariant.BASE;
        if (hasBlock) {
            this.forming = levelManager.getLevel(trophyBlock.getVariant().getType().get()).getSymbolicBlock();
        }
    }

    public void read(CompoundNBT compound) {
        this.forming = types.get(compound.getInt("forming"));
        this.hasBlock = compound.getBoolean("hasBlock");
        this.sunbeams = compound.getBoolean("sunbeams");
        super.read(compound);
    }

    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("forming", forming.getId());
        compound.putBoolean("hasBlock", hasBlock);
        compound.putBoolean("sunbeams", sunbeams);
        return super.write(compound);
    }


}
