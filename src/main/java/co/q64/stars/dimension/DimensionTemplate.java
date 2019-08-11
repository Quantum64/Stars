package co.q64.stars.dimension;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.dimension.DimensionType;

public interface DimensionTemplate {
    public ResourceLocation getId();

    public default DimensionType getType() {
        return DimensionType.byName(getId());
    }
}
