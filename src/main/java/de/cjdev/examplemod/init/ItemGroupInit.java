package de.cjdev.examplemod.init;

import de.cjdev.examplemod.ExampleMod;
import de.cjdev.papermodapi.api.itemgroup.CustomItemGroup;
import de.cjdev.papermodapi.api.itemgroup.CustomItemGroups;
import net.kyori.adventure.text.Component;

public class ItemGroupInit {
    public static final CustomItemGroup GROUP = CustomItemGroups.register(ExampleMod.key("itemgroup"), CustomItemGroup.builder()
            .displayName(Component.translatable("itemGroup.examplemod"))
            .icon(() -> ItemInit.EXAMPLE_CUSTOM.getDisplayStack())
            .entries((hasOp, entries) -> {
                entries.add(ItemInit.EXAMPLE_ITEM);
                entries.add(ItemInit.EXAMPLE_TOOL);
                entries.add(ItemInit.EXAMPLE_FUEL);
                entries.add(ItemInit.EXAMPLE_EATING);
                entries.add(ItemInit.EXAMPLE_CUSTOM);
            })
            .build());

    public static void load(){
    }
}
