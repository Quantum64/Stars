package co.q64.stars.link.jei;

import co.q64.stars.item.BaseItem;
import co.q64.stars.item.HasDescription;
import co.q64.stars.util.Identifiers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IModIngredientRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class StarsJEIPlugin implements IModPlugin {
    protected @Inject Provider<Set<Item>> itemsProvider;
    protected @Inject Provider<Set<BaseItem>> baseItemProvider;

    private final ResourceLocation pluginId;

    @Inject
    protected StarsJEIPlugin(Identifiers identifiers) {
        this.pluginId = identifiers.get("stars_jei_plugin");
    }

    @Override
    public ResourceLocation getPluginUid() {
        return pluginId;
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        for (Item item : itemsProvider.get()) {
            if (item instanceof HasDescription) {
                registration.addIngredientInfo(new ItemStack(item), VanillaTypes.ITEM, ((HasDescription) item).getDescriptionLines());
            }
        }
        registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM, baseItemProvider.get().stream().filter(BaseItem::isHideJEI).map(ItemStack::new).collect(Collectors.toList()));
    }
}
