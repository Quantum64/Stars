package co.q64.stars;

import co.q64.stars.client.DaggerClientComponent;
import co.q64.stars.server.DaggerServerComponent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(ModInformation.ID)
public class Stars {
    private CommonProxy proxy;

    public Stars() {
        long start = System.currentTimeMillis();
        System.out.println("Starting construct...");
        proxy = DistExecutor.runForDist(() -> () -> DaggerClientComponent.create().getProxy(), () -> () -> DaggerServerComponent.create().getProxy());
        proxy.initialize();
        System.out.println("Construct completed (" + (System.currentTimeMillis() - start) + " ms)");
    }
}
