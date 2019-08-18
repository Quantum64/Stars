package co.q64.stars.recipe;

import com.google.gson.JsonObject;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class TrophyRecipe implements IRecipeSerializer<ShapelessRecipe> {
    public ShapelessRecipe read(ResourceLocation recipeId, JsonObject json) {
        return null;
    }

    public ShapelessRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
        return null;
    }

    public void write(PacketBuffer buffer, ShapelessRecipe recipe) {

    }

    public IRecipeSerializer<?> setRegistryName(ResourceLocation name) {
        return null;
    }

    @Nullable public ResourceLocation getRegistryName() {
        return null;
    }

    public Class<IRecipeSerializer<?>> getRegistryType() {
        return null;
    }
}
