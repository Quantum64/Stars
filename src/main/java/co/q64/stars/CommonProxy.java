package co.q64.stars;

import co.q64.stars.loader.CommonLoader;
import co.q64.stars.util.LinkAPI;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.inject.Inject;

public abstract class CommonProxy {
    protected @Inject FMLJavaModLoadingContext fmlContext;
    protected @Inject CommonLoader commonLoader;
    protected @Inject LinkAPI linkAPI;

    public LinkAPI initialize() {
        fmlContext.getModEventBus().addListener(this::setup);
        commonLoader.load();
        return linkAPI;
    }

    public void setup(FMLCommonSetupEvent event) {

    }
}
