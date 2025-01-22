package de.cjdev.examplemod.init;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class CustomRecipeType extends ShapelessRecipe {
    final ItemStack result;
    final List<CustomIngredient> ingredients;

    public CustomRecipeType(String group, CraftingBookCategory category, ItemStack result, List<CustomIngredient> ingredients) {
        super(group, category, result, ingredients.stream().map(CustomIngredient::getVanillaCopy).toList());

        this.result = result;
        this.ingredients = ingredients;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.ingredientCount() != this.ingredients.size()) {
            return false;
        } else if (input.size() == 1 && this.ingredients.size() == 1) {
            return this.ingredients.getFirst().test(input.getItem(0));
        } else {
            input.stackedContents().initializeExtras(this, input);
            boolean canCraft = input.stackedContents().canCraft(this, null);
            input.stackedContents().resetExtras();
            return canCraft;
        }
    }
}
