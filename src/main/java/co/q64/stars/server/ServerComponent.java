package co.q64.stars.server;

import co.q64.stars.CommonModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {CommonModule.class})
public interface ServerComponent {
    public ServerProxy getProxy();
}
