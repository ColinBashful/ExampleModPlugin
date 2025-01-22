package de.cjdev.examplemod.init;

import de.cjdev.papermodapi.api.item.CustomItem;
import de.cjdev.papermodapi.api.item.CustomItems;
import io.papermc.paper.inventory.recipe.ItemOrExact;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackLinkedSet;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.bukkit.Bukkit;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CustomIngredient implements Predicate<ItemStack> {
    private final HolderSet<Item> values;
    @Nullable
    private Set<ItemStack> itemStacks;

    public boolean isExact() {
        return this.itemStacks != null;
    }

    @Nullable
    public Set<ItemStack> itemStacks() {
        return this.itemStacks;
    }

    public Ingredient getVanillaCopy(){
        return isExact() ? Ingredient.ofStacks(itemStacks.stream().toList()) : Ingredient.of(values);
    }

    public static CustomIngredient ofStacks(List<ItemStack> stacks) {
        CustomIngredient recipe = of(stacks.stream().map(ItemStack::getItem));
        recipe.itemStacks = ItemStackLinkedSet.createTypeAndComponentsSet();
        recipe.itemStacks.addAll(stacks);
        recipe.itemStacks = Collections.unmodifiableSet(recipe.itemStacks);
        return recipe;
    }

    public static CustomIngredient ofCustomItems(List<CustomItem> customItems) {
        List<ItemStack> stacks = customItems.stream().map(customItem -> ItemStack.fromBukkitCopy(customItem.getDefaultStack())).toList();
        CustomIngredient recipe = of(stacks.stream().map(ItemStack::getItem));
        recipe.itemStacks = ItemStackLinkedSet.createTypeAndComponentsSet();
        recipe.itemStacks.addAll(stacks);
        recipe.itemStacks = Collections.unmodifiableSet(recipe.itemStacks);
        return recipe;
    }

    private CustomIngredient(HolderSet<Item> values) {
        values.unwrap().ifRight((list) -> {
            if (list.isEmpty()) {
                throw new UnsupportedOperationException("Ingredients can't be empty");
            } else if (list.contains(Items.AIR.builtInRegistryHolder())) {
                throw new UnsupportedOperationException("Ingredient can't contain air");
            }
        });
        this.values = values;
    }

    public static CustomIngredient of(ItemLike item) {
        return new CustomIngredient(HolderSet.direct(item.asItem().builtInRegistryHolder()));
    }

    public static CustomIngredient of(ItemLike... items) {
        return of(Arrays.stream(items));
    }

    public static CustomIngredient of(Stream<? extends ItemLike> items) {
        return new CustomIngredient(HolderSet.direct(items.map((item) -> item.asItem().builtInRegistryHolder()).toList()));
    }

    public static CustomIngredient of(HolderSet<Item> items) {
        return new CustomIngredient(items);
    }

    @Override
    public boolean test(ItemStack stack) {
        //return this.isExact() ? this.itemStacks.contains(stack) : stack.is(this.values);
        if (this.isExact()) {
            Key matchKey = CustomItems.getKeyByStack(stack.getBukkitStack());
            if (matchKey == null)
                return this.itemStacks.contains(stack);
            return this.itemStacks.stream().anyMatch(stack1 -> {
                Key key = CustomItems.getKeyByStack(stack1.getBukkitStack());
                CustomData customData = stack1.getComponents().get(DataComponents.CUSTOM_DATA);
                if(customData != null){
                    Bukkit.getServer().broadcast(Component.text(customData.toString()));
                }
                if(key == null)
                    return false;
                return key.equals(matchKey);
            });
        }
        return stack.is(this.values);
    }

    public boolean acceptsItem(ItemOrExact itemOrExact) {
        return switch (itemOrExact) {
            case ItemOrExact.Item(Holder item) -> !this.isExact() && this.values.contains(item);
            case ItemOrExact.Exact(ItemStack exact) -> this.isExact() && this.itemStacks.contains(exact);
        };
    }

    public boolean equals(Object other) {
        if (other instanceof CustomIngredient ingredient) {
            return Objects.equals(this.values, ingredient.values) && Objects.equals(this.itemStacks, ingredient.itemStacks);
        }
        return false;
    }
}
