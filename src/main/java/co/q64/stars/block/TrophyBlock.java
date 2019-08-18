package co.q64.stars.block;

import co.q64.stars.tile.TrophyTile;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class TrophyBlock extends BaseBlock {
    protected @Inject Provider<TrophyTile> tileFactory;

    protected @Inject TrophyBlock() {
        super("trophy", Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(5f, 6f));
    }

    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return tileFactory.get();
    }
}
