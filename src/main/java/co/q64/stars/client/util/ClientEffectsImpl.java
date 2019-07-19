package co.q64.stars.client.util;

import co.q64.stars.client.listener.ClientPlayerListener;
import co.q64.stars.util.ClientEffects;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ClientEffectsImpl implements ClientEffects {
    protected @Inject ClientPlayerListener clientPlayerListener;

    protected @Inject ClientEffectsImpl() {}

    public void playDarknessEffect() {
        clientPlayerListener.playDarknessEffect();
    }

    public void playEntryEffect() {

    }
}
