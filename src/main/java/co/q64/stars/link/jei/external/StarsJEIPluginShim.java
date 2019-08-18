package co.q64.stars.link.jei.external;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IAdvancedRegistration;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IModIngredientRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.util.ResourceLocation;

import java.util.Optional;


// JEI has this crazy reflection based plugin loading system which is
// not very object oriented is just pretty terrible overall, so this class
// is the only one that can actually interact JEI, so we need to redirect
// all plugin calls through a static hack object into our proper injected
// environment
@JeiPlugin
public class StarsJEIPluginShim implements IModPlugin {
    public static Optional<IModPlugin> delegate = Optional.empty();

    public ResourceLocation getPluginUid() {
        return getPlugin().getPluginUid();
    }

    public void registerItemSubtypes(ISubtypeRegistration registration) {
        getPlugin().registerItemSubtypes(registration);
    }

    public void registerIngredients(IModIngredientRegistration registration) {
        getPlugin().registerIngredients(registration);
    }

    public void registerCategories(IRecipeCategoryRegistration registration) {
        getPlugin().registerCategories(registration);
    }

    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
        getPlugin().registerVanillaCategoryExtensions(registration);
    }

    public void registerRecipes(IRecipeRegistration registration) {
        getPlugin().registerRecipes(registration);
    }

    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        getPlugin().registerRecipeTransferHandlers(registration);
    }

    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        getPlugin().registerRecipeCatalysts(registration);
    }

    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        getPlugin().registerGuiHandlers(registration);
    }

    public void registerAdvanced(IAdvancedRegistration registration) {
        getPlugin().registerAdvanced(registration);
    }

    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        getPlugin().onRuntimeAvailable(jeiRuntime);
    }

    private static IModPlugin getPlugin() {
        return delegate.orElseThrow(() -> new IllegalStateException("JEI plugin accessed before link initialization"));
    }
}
