package co.q64.stars.level;

import co.q64.stars.level.levels.RedLevel;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LevelManager {
    protected @Inject RedLevel redLevel;

    protected @Inject LevelManager() {}

    public Level getLevel(LevelType type) {
        switch (type) {
            default:
                return redLevel;
        }
    }
}
