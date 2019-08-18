package co.q64.stars;

import co.q64.stars.client.DaggerClientComponent;
import co.q64.stars.server.DaggerServerComponent;
import co.q64.stars.util.LinkAPI;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(ModInformation.ID)
public class Stars {
    private static LinkAPI linkAPI;

    private CommonProxy proxy;

    public Stars() {
        long start = System.currentTimeMillis();
        System.out.println("Starting construct...");
        proxy = DistExecutor.runForDist(() -> () -> DaggerClientComponent.create().getProxy(), () -> () -> DaggerServerComponent.create().getProxy());
        linkAPI = proxy.initialize();
        System.out.println("Construct completed (" + (System.currentTimeMillis() - start) + " ms)");
    }

    public static LinkAPI getLinkAPI() {
        if (linkAPI == null) {
            throw new IllegalStateException("Link API is not ready!");
        }
        return linkAPI;
    }
}
