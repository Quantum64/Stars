package co.q64.stars.jei;

import co.q64.stars.ModInformation;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class StarsJEIPlugin implements IModPlugin {
    private static final ResourceLocation ID = new ResourceLocation(ModInformation.ID, "stars_jei_plugin");

    private JEIPluginComponent component;

    public StarsJEIPlugin() {
        this.component = DaggerJEIPluginComponent.create();
    }

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        component.getTrophySubtypeInterpreter().register(registration);
    }
}
