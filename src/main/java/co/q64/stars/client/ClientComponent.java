package co.q64.stars.client;

import co.q64.stars.CommonComponent;
import co.q64.stars.CommonModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {CommonModule.class, ClientModule.class})
public interface ClientComponent extends CommonComponent {
    public ClientProxy getProxy();
}
