package de.cjdev.examplemod.init;

import de.cjdev.examplemod.ExampleMod;
import de.cjdev.examplemod.item.ExampleCustom;
import de.cjdev.papermodapi.api.item.CustomItem;
import de.cjdev.papermodapi.api.item.CustomItems;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.FoodProperties;
import io.papermc.paper.datacomponent.item.Tool;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import java.util.function.Function;

public class ItemInit {
    public static CustomItem EXAMPLE_ITEM;
    public static CustomItem EXAMPLE_TOOL;
    public static CustomItem EXAMPLE_FUEL;
    public static CustomItem EXAMPLE_EATING;
    public static CustomItem EXAMPLE_CUSTOM;

    private static NamespacedKey createItemId(String id){
        return ExampleMod.key(id);
    }

    public static void load(){
        EXAMPLE_ITEM = register("example_item");
        EXAMPLE_TOOL = register("example_tool", new CustomItem.Settings().maxDamage(69).component(DataComponentTypes.TOOL, Tool.tool().build()));
        EXAMPLE_FUEL = register("example_fuel", new CustomItem.Settings().baseMaterial(Material.COAL));
        EXAMPLE_EATING = register("example_eating", new CustomItem.Settings().food(FoodProperties.food().nutrition(10).saturation(10).build()));
        EXAMPLE_CUSTOM = register("example_custom", ExampleCustom::new);
    }

    private static CustomItem register(String id, Function<CustomItem.Settings, CustomItem> factory, CustomItem.Settings settings){
        CustomItem item = CustomItems.register(createItemId(id), factory, settings);
        ExampleMod.getPlugin().getLogger().info("\u001B[38;2;85;85;85m" + createItemId(id) + " registered\u001B[0m");
        return item;
    }

    private static CustomItem register(String id, Function<CustomItem.Settings, CustomItem> factory){
        return register(id, factory, new CustomItem.Settings());
    }

    private static CustomItem register(String id, CustomItem.Settings settings){
        return register(id, CustomItem::new, settings);
    }

    private static CustomItem register(String id){
        return register(id, new CustomItem.Settings());
    }
}
