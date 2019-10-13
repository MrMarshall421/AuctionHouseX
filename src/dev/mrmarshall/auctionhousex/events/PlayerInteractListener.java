package dev.mrmarshall.auctionhousex.events;

import dev.mrmarshall.auctionhousex.AuctionHouseX;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerInteractListener implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if(e.getHand() == EquipmentSlot.HAND) {
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
				if(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§eBottle o' Enchanting")) {
					int amount = Integer.valueOf(p.getInventory().getItemInMainHand().getItemMeta().getLore().get(0).replaceAll("§7", "").replaceAll("[^0-9]", ""));
					AuctionHouseX.getInstance().getEnchantingBottle().addThrownExpBottle(p.getUniqueId(), amount);
				}
			}
		}
	}
}
