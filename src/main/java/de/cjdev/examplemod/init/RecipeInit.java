package de.cjdev.examplemod.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftPlayer;

import java.util.List;

public class RecipeInit {
    public static void load(){
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.sendMessage(ItemStack.fromBukkitCopy(ItemInit.EXAMPLE_ITEM.getDefaultStack()).getItemName().getString());
            ((CraftPlayer)player).getHandle().getInventory().add(ItemStack.fromBukkitCopy(ItemInit.EXAMPLE_ITEM.getDefaultStack()));
        });
        CustomRecipeType recipe = new CustomRecipeType("", CraftingBookCategory.MISC, ItemStack.fromBukkitCopy(ItemInit.EXAMPLE_CUSTOM.getDefaultStack()), List.of(CustomIngredient.ofStacks(List.of(ItemStack.fromBukkitCopy(ItemInit.EXAMPLE_ITEM.getDefaultStack())))));
        //ShapelessRecipe recipe = new ShapelessRecipe(NamespacedKey.fromString("example_recipe", ExampleMod.getPlugin()), ItemInit.EXAMPLE_CUSTOM.getDefaultStack());
        ((CraftServer)Bukkit.getServer()).getHandle().getServer().getRecipeManager().addRecipe(new RecipeHolder<>(ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath("example-mod", "example-recipe")), recipe));
        //recipe.addIngredient(Material.STONE);

        //Bukkit.addRecipe(recipe);
    }
}
