package co.q64.stars.jei;

import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = JEIPluginModule.class)
public interface JEIPluginComponent {
    public TrophySubtypeInterpreter getTrophySubtypeInterpreter();
}
