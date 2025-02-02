package de.cjdev.examplemod.listener;

import de.cjdev.dynamicrp.DynamicRP;
import de.cjdev.dynamicrp.api.event.ZipPackEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.nio.charset.StandardCharsets;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;

public class ZipPackEventListener implements Listener {
    @EventHandler
    public void onZipPack(ZipPackEvent event){
        ZipEntry zipEntry = new ZipEntry("test/pack.mcmeta");
        byte[] data = "test1234, works!!!!".getBytes(StandardCharsets.UTF_8);
        zipEntry.setSize(data.length);
        CRC32 crc = new CRC32();
        crc.update(data);
        zipEntry.setCrc(crc.getValue());
        zipEntry.setMethod(ZipEntry.STORED);
        event.accept(new DynamicRP.ZipEntryData(zipEntry, data));
    }
}
