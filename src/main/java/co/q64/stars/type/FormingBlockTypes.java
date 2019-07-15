package co.q64.stars.type;

import co.q64.stars.type.forming.YellowFormingBlockType;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Singleton
public class FormingBlockTypes {
    private Map<Integer, FormingBlockType> cache = new HashMap<>();

    public @Inject YellowFormingBlockType yellowFormingBlockType;

    @Inject
    protected FormingBlockTypes(Set<FormingBlockType> types) {
        for (FormingBlockType type : types) {
            cache.put(type.getId(), type);
        }
    }

    public FormingBlockType get(int id) {
        return cache.getOrDefault(id, yellowFormingBlockType);
    }
}
