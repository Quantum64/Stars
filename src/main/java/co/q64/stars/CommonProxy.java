package co.q64.stars;

import co.q64.stars.loader.CommonLoader;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.inject.Inject;

public abstract class CommonProxy {
    protected @Inject FMLJavaModLoadingContext fmlContext;
    protected @Inject CommonLoader commonLoader;

    public void initialize() {
        fmlContext.getModEventBus().addListener(this::setup);
        commonLoader.load();
    }

    public void setup(FMLCommonSetupEvent event) {

    }
}
