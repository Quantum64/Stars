package co.q64.stars.server;

import co.q64.stars.util.ClientEffects;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MockClientEffects implements ClientEffects {
    protected @Inject MockClientEffects() {}

    public void playDarknessEffect() {}

    public void playEntryEffect() {}
}
