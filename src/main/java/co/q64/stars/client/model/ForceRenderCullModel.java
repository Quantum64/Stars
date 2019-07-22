package co.q64.stars.client.model;

import com.google.auto.factory.AutoFactory;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelProperty;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@AutoFactory
public class ForceRenderCullModel implements IDynamicBakedModel {
    private static final Direction[] DIRECTIONS = Direction.values();

    private IBakedModel model;

    protected ForceRenderCullModel(IBakedModel model) {
        this.model = model;
    }

    public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand, IModelData extraData) {
        if ((side != null && extraData != null) && Optional.ofNullable(extraData.getData(CullProperties.get(side))).orElse(false)) {
            return Collections.emptyList();
        }
        return model.getQuads(state, side, rand, extraData);
    }

    public IModelData getModelData(IEnviromentBlockReader world, BlockPos pos, BlockState state, IModelData data) {
        for (Direction direction : DIRECTIONS) {
            data.setData(CullProperties.get(direction), !world.getBlockState(pos.offset(direction)).isAir(world, pos.offset(direction)));
        }
        return data;
    }

    public boolean isAmbientOcclusion() {
        return false;
    }

    public boolean isGui3d() {
        return true;
    }

    public boolean isBuiltInRenderer() {
        return false;
    }

    public TextureAtlasSprite getParticleTexture() {
        return MissingTextureSprite.func_217790_a();
    }

    public ItemOverrideList getOverrides() {
        return ItemOverrideList.EMPTY;
    }

    private static interface CullProperties {
        public static ModelProperty<Boolean> NORTH = new ModelProperty<>();
        public static ModelProperty<Boolean> SOUTH = new ModelProperty<>();
        public static ModelProperty<Boolean> EAST = new ModelProperty<>();
        public static ModelProperty<Boolean> WEST = new ModelProperty<>();
        public static ModelProperty<Boolean> UP = new ModelProperty<>();
        public static ModelProperty<Boolean> DOWN = new ModelProperty<>();

        public static ModelProperty<Boolean> get(Direction direction) {
            switch (direction) {
                case DOWN:
                    return DOWN;
                case UP:
                    return UP;
                case NORTH:
                    return NORTH;
                case SOUTH:
                    return SOUTH;
                case WEST:
                    return WEST;
                default:
                    return EAST;
            }
        }
    }
}
