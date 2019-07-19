package co.q64.stars.tile;

import co.q64.stars.tile.type.ForceRenderCullTileType;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;

@AutoFactory
public class ForceRenderCullTile extends TileEntity {
    public ForceRenderCullTile(@Provided ForceRenderCullTileType type) {
        super(type);
    }

    public IModelData getModelData() {
        return new ModelDataMap.Builder().build();
    }
}
