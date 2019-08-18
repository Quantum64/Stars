package co.q64.stars.link;

import javax.inject.Provider;

public interface LinkInformation {
    public String getModId();

    public Provider<? extends Link> getLinkProvider();
}
