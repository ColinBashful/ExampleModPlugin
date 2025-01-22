package de.cjdev.examplemod.listener;

import de.cjdev.examplemod.init.ItemInit;
import de.cjdev.papermodapi.api.event.FuelRegistryEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FuelRegistryEventListener implements Listener {
    @EventHandler
    public void onFuelRegistryEvent(FuelRegistryEvent event){
        event.addFuel(ItemInit.EXAMPLE_ITEM, 200);
    }
}
