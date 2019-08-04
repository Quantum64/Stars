package co.q64.stars.client.model;

import co.q64.stars.block.ChallengeDoorBlock;
import co.q64.stars.block.DarknessBlock;
import co.q64.stars.block.DarknessEdgeBlock;
import co.q64.stars.block.DoorBlock;
import co.q64.stars.util.Identifiers;
import co.q64.stars.util.Logger;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.ModelRotation;
import net.minecraft.client.renderer.texture.ISprite;
import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.model.IModelState;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;

@Singleton
public class DarknessModel implements IDynamicBakedModel {
    private static final Direction[] DIRECTIONS = Direction.values();
    private static final EdgeIdentifier[] EDGE_IDENTIFIERS = EdgeIdentifier.values();

    private static final ModelProperty<Boolean> NORTH = new ModelProperty<>();
    private static final ModelProperty<Boolean> SOUTH = new ModelProperty<>();
    private static final ModelProperty<Boolean> EAST = new ModelProperty<>();
    private static final ModelProperty<Boolean> WEST = new ModelProperty<>();
    private static final ModelProperty<Boolean> UP = new ModelProperty<>();
    private static final ModelProperty<Boolean> DOWN = new ModelProperty<>();

    protected @Inject Identifiers identifiers;
    protected @Inject Logger logger;

    private IBakedModel core;
    private Map<Direction, Map<EdgeIdentifier, IBakedModel>> darkEdges = new HashMap<>();
    private Map<Direction, Map<EdgeIdentifier, IBakedModel>> lightEdges = new HashMap<>();

    protected @Inject DarknessModel() {}

    public Set<ResourceLocation> bake(ModelLoader loader) {
        logger.info("Baking darkness model");
        Set<ResourceLocation> textures = new HashSet<>();
        Set<String> errors = new HashSet<>();
        IUnbakedModel coreUnbaked = loader.getUnbakedModel(identifiers.get("block/darkness"));
        coreUnbaked.getTextures(loader::getUnbakedModel, errors);
        IBakedModel coreBaked = coreUnbaked.bake(loader, ModelLoader.defaultTextureGetter(), new ISprite() {}, DefaultVertexFormats.BLOCK);
        this.core = coreBaked;
        for (Direction direction : DIRECTIONS) {
            Map<EdgeIdentifier, IBakedModel> dark = new HashMap<>();
            Map<EdgeIdentifier, IBakedModel> light = new HashMap<>();
            for (EdgeIdentifier edgeIdentifier : EDGE_IDENTIFIERS) {
                IUnbakedModel darkUnbaked = loader.getUnbakedModel(identifiers.get("block/darkness_" + edgeIdentifier.name().toLowerCase()));
                IUnbakedModel lightUnbaked = loader.getUnbakedModel(identifiers.get("block/darkness_edge_" + edgeIdentifier.name().toLowerCase()));
                textures.addAll(darkUnbaked.getTextures(loader::getUnbakedModel, errors));
                textures.addAll(lightUnbaked.getTextures(loader::getUnbakedModel, errors));
                IBakedModel darkBaked = darkUnbaked.bake(loader, ModelLoader.defaultTextureGetter(), new ISprite() {
                    public IModelState getState() {
                        return DarknessModel.this.getRotation(direction);
                    }
                }, DefaultVertexFormats.BLOCK);
                IBakedModel lightBaked = lightUnbaked.bake(loader, ModelLoader.defaultTextureGetter(), new ISprite() {
                    public IModelState getState() {
                        return DarknessModel.this.getRotation(direction);
                    }
                }, DefaultVertexFormats.BLOCK);
                dark.put(edgeIdentifier, darkBaked);
                light.put(edgeIdentifier, lightBaked);
            }
            darkEdges.put(direction, dark);
            lightEdges.put(direction, light);
        }
        errors.forEach(s -> logger.info("Darkness texture error: " + s));
        return textures;
    }

    public Set<ResourceLocation> getTextures() {
        Set<ResourceLocation> result = new HashSet<>();
        for (EdgeIdentifier edgeIdentifier : EDGE_IDENTIFIERS) {
            result.add(identifiers.get("block/darkness_" + edgeIdentifier.name().toLowerCase()));
            result.add(identifiers.get("block/darkness_edge_" + edgeIdentifier.name().toLowerCase()));
        }
        return result;
    }

    public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand, IModelData data) {
        if (side == null || state == null || data == null) {
            return core.getQuads(state, side, rand, data);
        }
        List<BakedQuad> darkQuads = new ArrayList<>();
        List<BakedQuad> lightQuads = new ArrayList<>();
        darkQuads.addAll(core.getQuads(state, side, rand, data));
        Map<EdgeIdentifier, IBakedModel> dark = darkEdges.get(side);
        Map<EdgeIdentifier, IBakedModel> light = lightEdges.get(side);
        BiConsumer<Boolean, EdgeIdentifier> helper = (prop, edge) -> {
            if (prop == null || prop) {
                darkQuads.addAll(dark.get(edge).getQuads(state, side, rand, data));
            } else {
                lightQuads.addAll(light.get(edge).getQuads(state, side, rand, data));
            }
        };

        switch (side) {
            case SOUTH:
                helper.accept(data.getData(WEST), EdgeIdentifier.LEFT);
                helper.accept(data.getData(EAST), EdgeIdentifier.RIGHT);
                helper.accept(data.getData(UP), EdgeIdentifier.TOP);
                helper.accept(data.getData(DOWN), EdgeIdentifier.BOTTOM);
                break;
            case NORTH:
                helper.accept(data.getData(EAST), EdgeIdentifier.LEFT);
                helper.accept(data.getData(WEST), EdgeIdentifier.RIGHT);
                helper.accept(data.getData(UP), EdgeIdentifier.TOP);
                helper.accept(data.getData(DOWN), EdgeIdentifier.BOTTOM);
                break;
            case EAST:
                helper.accept(data.getData(SOUTH), EdgeIdentifier.LEFT);
                helper.accept(data.getData(NORTH), EdgeIdentifier.RIGHT);
                helper.accept(data.getData(UP), EdgeIdentifier.TOP);
                helper.accept(data.getData(DOWN), EdgeIdentifier.BOTTOM);
                break;
            case WEST:
                helper.accept(data.getData(NORTH), EdgeIdentifier.LEFT);
                helper.accept(data.getData(SOUTH), EdgeIdentifier.RIGHT);
                helper.accept(data.getData(UP), EdgeIdentifier.TOP);
                helper.accept(data.getData(DOWN), EdgeIdentifier.BOTTOM);
                break;
            case UP:
                helper.accept(data.getData(EAST), EdgeIdentifier.LEFT);
                helper.accept(data.getData(WEST), EdgeIdentifier.RIGHT);
                helper.accept(data.getData(SOUTH), EdgeIdentifier.TOP);
                helper.accept(data.getData(NORTH), EdgeIdentifier.BOTTOM);
                break;
            case DOWN:
                helper.accept(data.getData(EAST), EdgeIdentifier.LEFT);
                helper.accept(data.getData(WEST), EdgeIdentifier.RIGHT);
                helper.accept(data.getData(NORTH), EdgeIdentifier.TOP);
                helper.accept(data.getData(SOUTH), EdgeIdentifier.BOTTOM);
                break;
            default:
                break;
        }
        darkQuads.addAll(lightQuads);
        return darkQuads;
    }

    public IModelData getModelData(IEnviromentBlockReader world, BlockPos pos, BlockState state, IModelData tileData) {
        ModelDataMap.Builder data = new ModelDataMap.Builder();
        for (Direction direction : DIRECTIONS) {
            Block block = world.getBlockState(pos.offset(direction)).getBlock();
            boolean connected = block instanceof DarknessBlock || block instanceof DarknessEdgeBlock || block instanceof DoorBlock || block instanceof ChallengeDoorBlock;
            data = data.withInitial(getProperty(direction), connected);
        }
        return data.build();
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

    private ModelProperty<Boolean> getProperty(Direction direction) {
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

    private ModelRotation getRotation(Direction direction) {
        if (direction == Direction.EAST) {
            return ModelRotation.X0_Y90;
        } else if (direction == Direction.SOUTH) {
            return ModelRotation.X0_Y180;
        } else if (direction == Direction.WEST) {
            return ModelRotation.X0_Y270;
        } else if (direction == Direction.DOWN) {
            return ModelRotation.X90_Y0;
        } else if (direction == Direction.UP) {
            return ModelRotation.X270_Y0;
        }
        return ModelRotation.X0_Y0;
    }

    private static enum EdgeIdentifier {
        TOP, BOTTOM, RIGHT, LEFT
    }
}
