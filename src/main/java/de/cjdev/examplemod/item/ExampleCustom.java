package de.cjdev.examplemod.item;

import de.cjdev.papermodapi.api.item.CustomItem;
import de.cjdev.papermodapi.api.util.ActionResult;
import de.cjdev.papermodapi.api.util.ItemUsageContext;
import de.cjdev.papermodapi.api.util.Util;
import io.papermc.paper.entity.Shearable;
import io.papermc.paper.math.BlockPosition;
import net.kyori.adventure.text.Component;
import net.minecraft.world.item.context.UseOnContext;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Orientable;
import org.bukkit.entity.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class ExampleCustom extends CustomItem {
    public ExampleCustom(Settings settings) {
        super(settings.maxDamage(100).useCooldown(1));
    }

    @Override
    public void onCraft(ItemStack stack, World world) {
        stack.setDurability((short) 20);
        Bukkit.getServer().broadcast(Component.text("Crafted 11!"));
    }

    @Override
    public void onCraftByPlayer(ItemStack stack, World world, Player player) {
        stack.setDurability((short) 20);
        player.sendMessage(Component.text("Crafted !!!"));
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, Player user, Entity entity, EquipmentSlot hand) {
        if(entity instanceof Shearable shearable && shearable.readyToBeSheared()){
            shearable.shear();
            return ActionResult.SUCCESS;
        }
        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPosition blockPos = context.getBlockPos();
        Block block = blockPos.toLocation(world).getBlock();

        UseOnContext nmsContext = Util.fromItemUsageContext(context);
        nmsContext.getLevel().levelEvent(1038, nmsContext.getClickedPos(), 0);

        if(block.getBlockData() instanceof Orientable orientable){
            orientable.setAxis(Axis.values()[(orientable.getAxis().ordinal() + 1) % Axis.values().length]);
            block.setBlockData(orientable);
            world.playSound(block.getLocation(), block.getBlockSoundGroup().getHitSound(), 1.0f, 0.8f);
            return ActionResult.SUCCESS;
        }
        if(block.getBlockData().getMaterial() == Material.DIAMOND_BLOCK) {
            block.setBlockData(Material.NETHERITE_BLOCK.createBlockData());
            world.playSound(block.getLocation(), Sound.BLOCK_NETHERITE_BLOCK_PLACE, 1.0f, 0.8f);
            return ActionResult.SUCCESS;
        }

        world.spawn(context.getHitPos(), Firework.class);
        return ActionResult.SUCCESS;
    }

    @Override
    public ActionResult use(World world, Player player, EquipmentSlot hand) {
        Block raycastBlock = player.getTargetBlockExact(300, FluidCollisionMode.ALWAYS);
        if (raycastBlock == null)
            return super.use(world, player, hand);
        world.spawn(raycastBlock.getLocation().add(0d, 1d, 0d), LightningStrike.class);
        return ActionResult.SUCCESS;
    }

    @Override
    public void onItemEntityDestroyed(Item entity) {
        entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_ENDER_EYE_DEATH, SoundCategory.NEUTRAL, 1f, 1f);
    }
}
