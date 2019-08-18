package co.q64.stars.link.jei;

import co.q64.stars.item.TrophyBlockItem;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.item.ItemStack;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TrophySubtypeInterpreter implements ISubtypeInterpreter {
    //protected @Inject TrophyBlockItem trophyBlockItem;

    protected @Inject TrophySubtypeInterpreter() {}

    public void register(ISubtypeRegistration registration) {
        //registration.registerSubtypeInterpreter(trophyBlockItem, this);
    }

    public String apply(ItemStack stack) {
        return stack.getTranslationKey();
    }
}
