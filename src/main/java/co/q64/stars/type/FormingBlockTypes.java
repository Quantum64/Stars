package co.q64.stars.type;

import co.q64.stars.block.RedFormedBlock;
import co.q64.stars.type.forming.RedFormingBlockType;
import co.q64.stars.type.forming.YellowFormingBlockType;
import co.q64.stars.util.Identifiers;
import net.minecraft.block.Block;
import net.minecraft.util.SoundEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Singleton
public class FormingBlockTypes {
    private Map<Integer, FormingBlockType> cache = new HashMap<>();
    private Map<Block, FormingBlockType> formed = new HashMap<>();

    protected @Inject YellowFormingBlockType yellowFormingBlockType;
    protected @Inject RedFormingBlockType redFormingBlockType;
    protected @Inject RedFormedBlock redFormedBlock;
    protected @Inject Identifiers identifiers;

    @Inject
    protected FormingBlockTypes(Set<FormingBlockType> types) {
        for (FormingBlockType type : types) {
            cache.put(type.getId(), type);
            formed.put(type.getFormedBlock(), type);
        }
    }

    @Inject
    protected void setup() {
        formed.put(redFormedBlock, redFormingBlockType);
    }

    public FormingBlockType get(int id) {
        return cache.getOrDefault(id, yellowFormingBlockType);
    }

    public FormingBlockType get(Block block) {
        return formed.getOrDefault(block, yellowFormingBlockType);
    }

    public FormingBlockType getDefault() {
        return yellowFormingBlockType;
    }
}
