package co.q64.stars.link.jei;

import co.q64.stars.link.Link;
import co.q64.stars.link.jei.external.StarsJEIPluginShim;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class JEILink extends Link {
    protected @Inject StarsJEIPlugin starsJEIPlugin;

    protected @Inject JEILink() {}

    @Override
    public void init() {
        StarsJEIPluginShim.delegate = Optional.of(starsJEIPlugin);
    }
}
