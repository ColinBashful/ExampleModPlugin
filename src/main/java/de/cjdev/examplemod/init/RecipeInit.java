package de.cjdev.examplemod.init;

import de.cjdev.examplemod.ExampleMod;
import de.cjdev.papermodapi.PaperModAPI;
import de.cjdev.recipeapi.RecipeAPI;
import de.cjdev.recipeapi.api.recipe.*;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.entity.CraftPlayer;

public class RecipeInit {
    public static void load(){
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.sendMessage(ItemStack.fromBukkitCopy(ItemInit.EXAMPLE_ITEM.getDefaultStack()).getItemName().getString());
            ((CraftPlayer)player).getHandle().getInventory().add(ItemStack.fromBukkitCopy(ItemInit.EXAMPLE_ITEM.getDefaultStack()));
        });

        CustomShapelessRecipe shapelessRecipe = new CustomShapelessRecipe(ItemInit.EXAMPLE_CUSTOM);
        shapelessRecipe.addIngredient(3, ItemInit.EXAMPLE_FUEL);
        shapelessRecipe.addIngredient(2, Material.DIRT);
        RecipeAPI.addRecipe(NamespacedKey.fromString("test", ExampleMod.getPlugin()), shapelessRecipe);

        CustomFurnaceRecipe furnaceRecipe = new CustomFurnaceRecipe(ItemInit.EXAMPLE_EATING, new CustomIngredient(ItemInit.EXAMPLE_ITEM), 20);
        RecipeAPI.addRecipe(NamespacedKey.fromString("test_furnace", ExampleMod.getPlugin()), furnaceRecipe);
    }
}
