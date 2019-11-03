package dev.mrmarshall.auctionhousex.events;

import dev.mrmarshall.auctionhousex.AuctionHouseX;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (e.getHand() == EquipmentSlot.HAND) {
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
                if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§eBottle o' Enchanting")) {
                    int amount = Integer.valueOf(p.getInventory().getItemInMainHand().getItemMeta().getLore().get(0).replaceAll("§7", "").replaceAll("[^0-9]", ""));
                    AuctionHouseX.getInstance().getEnchantingBottle().addThrownExpBottle(p.getUniqueId(), amount);
                } else if (p.getInventory().getItemInMainHand().getType().toString().contains("_SHULKER_BOX") && p.isSneaking()) {
                    //> Open Shulker Box
                    BlockStateMeta shulkerBoxMeta = (BlockStateMeta) p.getInventory().getItemInMainHand().getItemMeta();
                    ShulkerBox shulkerBox = (ShulkerBox) shulkerBoxMeta.getBlockState();
                    ItemStack[] shulkerBoxContent = shulkerBox.getInventory().getContents();

                    Inventory shulkerBoxGUI = Bukkit.createInventory(null, 27, "§rShulker Box");
                    shulkerBoxGUI.setContents(shulkerBoxContent);

                    p.openInventory(shulkerBoxGUI);
                    p.playSound(p.getLocation(), Sound.BLOCK_SHULKER_BOX_OPEN, 1.0F, 1.0F);
                }
            }
        }
    }
}
