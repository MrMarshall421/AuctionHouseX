package dev.mrmarshall.auctionhousex.events;

import dev.mrmarshall.auctionhousex.AuctionHouseX;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class InventoryDragListener implements Listener {

	@EventHandler
	public void onDrag(InventoryDragEvent e) {
		Player p = (Player) e.getWhoClicked();
		Player target = Bukkit.getPlayer(AuctionHouseX.getInstance().getTradingManager().getTradingPartner(p.getUniqueId()));

		//> Player Trading GUI
		if(e.getView().getTitle().equals("§2SafeTrade")) {
			for(Map.Entry<Integer, ItemStack> dragged : e.getNewItems().entrySet()) {
				if(dragged.getKey() == 14 || dragged.getKey() == 15 || dragged.getKey() == 16 || dragged.getKey() == 17 || dragged.getKey() == 23 || dragged.getKey() == 24 || dragged.getKey() == 25 || dragged.getKey() == 26 || dragged.getKey() == 32 || dragged.getKey() == 33 || dragged.getKey() == 34 || dragged.getKey() == 35 || dragged.getKey() == 41 || dragged.getKey() == 42 || dragged.getKey() == 43 || dragged.getKey() == 44 || dragged.getKey() == 50 || dragged.getKey() == 51) {
					e.setCancelled(true);
				}
			}
		//> Target Trading GUI
		} else if(e.getView().getTitle().equals(" §2SafeTrade")) {
			for(Map.Entry<Integer, ItemStack> dragged : e.getNewItems().entrySet()) {
				if(dragged.getKey() == 9 || dragged.getKey() == 10 || dragged.getKey() == 11 || dragged.getKey() == 12 || dragged.getKey() == 18 || dragged.getKey() == 19 || dragged.getKey() == 20 || dragged.getKey() == 21 || dragged.getKey() == 27 || dragged.getKey() == 28 || dragged.getKey() == 29 || dragged.getKey() == 30 || dragged.getKey() == 36 || dragged.getKey() == 37 || dragged.getKey() == 38 || dragged.getKey() == 39 || dragged.getKey() == 47 || dragged.getKey() == 48) {
					e.setCancelled(true);
				}
			}
		}

		if(!e.isCancelled()) {
			if(!AuctionHouseX.getInstance().getTradingManager().getBlockTrading().contains(p.getUniqueId())) {
				if(e.getInventory() == p.getOpenInventory().getTopInventory()) {
					//> New Item added to Trading Field
					new BukkitRunnable() {
						@Override
						public void run() {
							for(Integer slot : e.getNewItems().keySet()) {
								try {
									target.getOpenInventory().getTopInventory().setItem(slot, e.getInventory().getItem(slot));
								} catch(NullPointerException ex) {
								}
							}
						}
					}.runTaskLater(AuctionHouseX.getInstance(), 5);
				}
			} else {
				e.setCancelled(true);
			}
		}
	}
}
