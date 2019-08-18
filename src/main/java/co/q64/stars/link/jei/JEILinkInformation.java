package co.q64.stars.link.jei;

import co.q64.stars.link.LinkInformation;
import lombok.Getter;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class JEILinkInformation implements LinkInformation {
    private final @Getter String modId = "jei";
    protected @Getter @Inject Provider<JEILink> linkProvider;

    protected @Inject JEILinkInformation() {}
}
