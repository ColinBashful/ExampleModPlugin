package de.cjdev.examplemod;

import de.cjdev.examplemod.init.ItemGroupInit;
import de.cjdev.examplemod.init.ItemInit;
import de.cjdev.examplemod.init.RecipeInit;
import de.cjdev.papermodapi.PaperModAPI;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class ExampleMod extends JavaPlugin {
    private static ExampleMod plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        ItemInit.load();
        ItemGroupInit.load();
        RecipeInit.load();

        // Registering Fuels
        PaperModAPI.registerFuel(ItemInit.EXAMPLE_FUEL, 20);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static ExampleMod getPlugin(){
        return plugin;
    }

    public static NamespacedKey key(String id){
        return NamespacedKey.fromString(id, getPlugin());
    }
}
