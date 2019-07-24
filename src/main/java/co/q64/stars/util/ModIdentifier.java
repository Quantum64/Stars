package co.q64.stars.util;

import co.q64.stars.qualifier.ConstantQualifiers.ModId;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import net.minecraft.util.ResourceLocation;

@AutoFactory
public class ModIdentifier extends ResourceLocation {

    public ModIdentifier(@Provided @ModId String modId, String path) {
        super(modId, path);
    }
}
