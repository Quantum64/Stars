package co.q64.stars.block;

import co.q64.stars.tile.DoorTile;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class DoorBlock extends BaseBlock {
    protected @Inject Provider<DoorTile> tileFactory;

    protected @Inject DoorBlock() {
        super("door", Properties.create(Material.GLASS).hardnessAndResistance(-1f, 3600000f));
    }

    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return tileFactory.get();
    }
}
