package co.q64.stars.loader;

import co.q64.stars.listener.Listener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Set;

@Singleton
public class CommonLoader {
    protected @Inject Set<Listener> listeners;

    protected @Inject CommonLoader() {}

    public void load() {
        for (Listener listener : listeners) {
            MinecraftForge.EVENT_BUS.register(listener);
            FMLJavaModLoadingContext.get().getModEventBus().register(listener);
        }
    }
}
