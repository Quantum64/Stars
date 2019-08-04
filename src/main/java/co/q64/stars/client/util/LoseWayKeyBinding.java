package co.q64.stars.client.util;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.glfw.GLFW;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LoseWayKeyBinding extends KeyBinding {
    @Inject
    protected LoseWayKeyBinding() {
        super("Lose your way", GLFW.GLFW_KEY_H, "Stars");
    }
}
