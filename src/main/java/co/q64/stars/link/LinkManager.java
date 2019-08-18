package co.q64.stars.link;

import net.minecraftforge.fml.ModList;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Singleton
public class LinkManager {
    protected @Inject Set<LinkInformation> linkTemplates;
    private List<Link> links = new ArrayList<>();

    protected @Inject LinkManager() {}

    public void init() {
        for (LinkInformation linkInformation : linkTemplates) {
            if (ModList.get().isLoaded(linkInformation.getModId())) {
                links.add(linkInformation.getLinkProvider().get());
            }
        }
        for (Link link : links) {
            link.init();
        }
    }
}
