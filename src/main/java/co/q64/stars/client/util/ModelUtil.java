package co.q64.stars.client.util;

import co.q64.stars.block.GreenFruitBlock;
import co.q64.stars.block.RedPrimedBlock;
import co.q64.stars.type.FormingBlockType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public class ModelUtil {
    protected @Inject RedPrimedBlock redPrimedBlock;
    protected @Inject GreenFruitBlock greenFruitBlock;

    private Map<FormingBlockType, IBakedModel> formingModelCache = new HashMap<>();
    private Optional<IBakedModel> fruitModel = Optional.empty(), primedModel = Optional.empty();

    protected @Inject ModelUtil() {}

    public IBakedModel getModel(FormingBlockType type, boolean primed, boolean fruit) {
        if (fruit) {
            return fruitModel.orElseGet(() -> {
                fruitModel = Optional.of(Minecraft.getInstance().getModelManager().getBlockModelShapes().getModel(greenFruitBlock.getDefaultState()).getBakedModel());
                return fruitModel.get();
            });
        } else if (primed) {
            return primedModel.orElseGet(() -> {
                primedModel = Optional.of(Minecraft.getInstance().getModelManager().getBlockModelShapes().getModel(redPrimedBlock.getDefaultState()).getBakedModel());
                return primedModel.get();
            });
        }
        return formingModelCache.computeIfAbsent(type, t -> Minecraft.getInstance().getModelManager().getBlockModelShapes().getModel(t.getFormedBlock().getDefaultState())).getBakedModel();
    }
}
