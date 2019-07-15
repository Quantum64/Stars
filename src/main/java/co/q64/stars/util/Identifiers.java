package co.q64.stars.util;

import co.q64.stars.item.BaseItem;
import net.minecraft.util.ResourceLocation;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Identifiers {
    protected @Inject ModIdentifierFactory identifierUtil;

    protected @Inject Identifiers() {}

    public ResourceLocation getIdentifier(BaseItem item) {
        return identifierUtil.create(item.getId());
    }

    public ResourceLocation get(String path) {
        return identifierUtil.create(path);
    }
}
