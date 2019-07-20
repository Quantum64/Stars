package co.q64.stars.tile;

import co.q64.stars.tile.type.ForceRenderCullTileType;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;

import javax.inject.Inject;

public class ForceRenderCullTile extends TileEntity {
    @Inject
    protected ForceRenderCullTile(ForceRenderCullTileType type) {
        super(type);
    }

    public IModelData getModelData() {
        return new ModelDataMap.Builder().build();
    }
}
