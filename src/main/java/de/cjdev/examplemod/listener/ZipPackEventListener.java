package de.cjdev.examplemod.listener;

import de.cjdev.dynamicrp.api.event.ZipPackEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.nio.charset.StandardCharsets;

public class ZipPackEventListener implements Listener {
    @EventHandler
    public void onZipPack(ZipPackEvent event){
        event.accept("test/pack.mcmeta", "test1234, works!!!!".getBytes(StandardCharsets.UTF_8));
    }
}
