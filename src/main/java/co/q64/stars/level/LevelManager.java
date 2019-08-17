package co.q64.stars.level;

import co.q64.stars.level.levels.RedLevel;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Singleton
public class LevelManager {
    protected @Inject Set<Level> levels;
    protected @Inject RedLevel defaultLevel;

    private Map<LevelType, Level> cache = new HashMap<>();

    protected @Inject LevelManager() {}

    public Level getLevel(LevelType type) {
        return cache.computeIfAbsent(type, key -> {
            for (Level level : levels) {
                if (level.getType() == key) {
                    return level;
                }
            }
            return defaultLevel;
        });
    }
}
