package co.q64.stars.type;

import co.q64.stars.type.forming.PurpleFormingBlockType;
import co.q64.stars.type.forming.YellowFormingBlockType;
import net.minecraft.block.Block;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Singleton
public class FormingBlockTypes {
    private Map<Integer, FormingBlockType> cache = new HashMap<>();
    private Map<Block, FormingBlockType> formed = new HashMap<>();

    public @Inject YellowFormingBlockType yellowFormingBlockType;
    public @Inject PurpleFormingBlockType purpleFormingBlockType;

    @Inject
    protected FormingBlockTypes(Set<FormingBlockType> types) {
        for (FormingBlockType type : types) {
            cache.put(type.getId(), type);
            formed.put(type.getFormedBlock(), type);
        }
    }

    public FormingBlockType get(int id) {
        return cache.getOrDefault(id, yellowFormingBlockType);
    }

    public FormingBlockType get(Block block) {
        return formed.getOrDefault(block, yellowFormingBlockType);
    }
}
