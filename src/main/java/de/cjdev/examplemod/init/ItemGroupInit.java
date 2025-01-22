package de.cjdev.examplemod.init;

import de.cjdev.papermodapi.api.itemgroup.CustomItemGroup;
import net.kyori.adventure.text.Component;

public class ItemGroupInit {
    public static void load(){
        CustomItemGroup.builder()
                .displayName(Component.translatable("itemGroup.examplemod"))
                .icon(() -> ItemInit.EXAMPLE_CUSTOM.getDisplayStack())
                .entries((hasOp, entries) -> {
                    entries.add(ItemInit.EXAMPLE_ITEM);
                    entries.add(ItemInit.EXAMPLE_FUEL);
                    entries.add(ItemInit.EXAMPLE_EATING);
                    entries.add(ItemInit.EXAMPLE_CUSTOM);
                })
                .build();
    }
}
