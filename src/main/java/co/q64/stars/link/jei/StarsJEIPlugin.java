package co.q64.stars.link.jei;

import co.q64.stars.util.Identifiers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.util.ResourceLocation;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class StarsJEIPlugin implements IModPlugin {
    protected @Inject TrophySubtypeInterpreter trophySubtypeInterpreter;

    private final ResourceLocation pluginId;

    @Inject
    protected StarsJEIPlugin(Identifiers identifiers) {
        this.pluginId = identifiers.get("stars_jei_plugin");
    }

    @Override
    public ResourceLocation getPluginUid() {
        return pluginId;
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        //trophySubtypeInterpreter.register(registration);
    }
}
