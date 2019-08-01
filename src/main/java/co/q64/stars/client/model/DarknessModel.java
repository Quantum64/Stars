package co.q64.stars.client.model;

import co.q64.stars.state.DarknessState;
import co.q64.stars.util.Identifiers;
import co.q64.stars.util.Logger;
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
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;
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

    public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand, IModelData extraData) {
        if (side == null || state == null) {
            return core.getQuads(state, side, rand, extraData);
        }
        List<BakedQuad> result = new ArrayList<>();
        result.addAll(core.getQuads(state, side, rand, extraData));
        Map<EdgeIdentifier, IBakedModel> dark = darkEdges.get(side);
        Map<EdgeIdentifier, IBakedModel> light = lightEdges.get(side);
        BiConsumer<BooleanProperty, EdgeIdentifier> helper = (prop, edge) -> result.addAll(state.get(prop) ? dark.get(edge).getQuads(state, side, rand, extraData) : light.get(edge).getQuads(state, side, rand, extraData));

        switch (side) {
            case SOUTH:
                helper.accept(DarknessState.CONNECT_WEST, EdgeIdentifier.LEFT);
                helper.accept(DarknessState.CONNECT_EAST, EdgeIdentifier.RIGHT);
                helper.accept(DarknessState.CONNECT_UP, EdgeIdentifier.TOP);
                helper.accept(DarknessState.CONNECT_DOWN, EdgeIdentifier.BOTTOM);
                break;
            case NORTH:
                helper.accept(DarknessState.CONNECT_EAST, EdgeIdentifier.LEFT);
                helper.accept(DarknessState.CONNECT_WEST, EdgeIdentifier.RIGHT);
                helper.accept(DarknessState.CONNECT_UP, EdgeIdentifier.TOP);
                helper.accept(DarknessState.CONNECT_DOWN, EdgeIdentifier.BOTTOM);
                break;
            case EAST:
                helper.accept(DarknessState.CONNECT_SOUTH, EdgeIdentifier.LEFT);
                helper.accept(DarknessState.CONNECT_NORTH, EdgeIdentifier.RIGHT);
                helper.accept(DarknessState.CONNECT_UP, EdgeIdentifier.TOP);
                helper.accept(DarknessState.CONNECT_DOWN, EdgeIdentifier.BOTTOM);
                break;
            case WEST:
                helper.accept(DarknessState.CONNECT_NORTH, EdgeIdentifier.LEFT);
                helper.accept(DarknessState.CONNECT_SOUTH, EdgeIdentifier.RIGHT);
                helper.accept(DarknessState.CONNECT_UP, EdgeIdentifier.TOP);
                helper.accept(DarknessState.CONNECT_DOWN, EdgeIdentifier.BOTTOM);
                break;
            case UP:
                helper.accept(DarknessState.CONNECT_EAST, EdgeIdentifier.LEFT);
                helper.accept(DarknessState.CONNECT_WEST, EdgeIdentifier.RIGHT);
                helper.accept(DarknessState.CONNECT_SOUTH, EdgeIdentifier.TOP);
                helper.accept(DarknessState.CONNECT_NORTH, EdgeIdentifier.BOTTOM);
                break;
            case DOWN:
                helper.accept(DarknessState.CONNECT_EAST, EdgeIdentifier.LEFT);
                helper.accept(DarknessState.CONNECT_WEST, EdgeIdentifier.RIGHT);
                helper.accept(DarknessState.CONNECT_NORTH, EdgeIdentifier.TOP);
                helper.accept(DarknessState.CONNECT_SOUTH, EdgeIdentifier.BOTTOM);
                break;
            default:
                break;
        }

        return result;
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
