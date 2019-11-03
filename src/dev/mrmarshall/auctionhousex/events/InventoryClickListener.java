package dev.mrmarshall.auctionhousex.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();

		if (e.getView().getTitle().equals("§rShulker Box")) {
			if (e.getAction() == InventoryAction.HOTBAR_SWAP || e.getClick() == ClickType.NUMBER_KEY) {
				e.setCancelled(true);
			}

			if (e.getCurrentItem().getType().toString().contains("_SHULKER_BOX")) {
				e.setCancelled(true);
			}
		}
	}
}
