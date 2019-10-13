package dev.mrmarshall.auctionhousex.events;

import dev.mrmarshall.auctionhousex.AuctionHouseX;
import dev.mrmarshall.auctionhousex.gui.TradingGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerInteractEntityListener implements Listener {

	private TradingGUI tradingGUI = new TradingGUI();

	@EventHandler
	public void onInteractEntity(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();

		if(e.getHand() == EquipmentSlot.HAND) {
			if(p.isSneaking()) {
				if(e.getRightClicked() instanceof Player) {
					if(p.hasPermission("safetrade.access")) {
						Player target = (Player) e.getRightClicked();

						//> check if target player is not already trading
						if(!(AuctionHouseX.getInstance().getTradingManager().getTrading().containsKey(target.getUniqueId()) || AuctionHouseX.getInstance().getTradingManager().getTrading().containsValue(target.getUniqueId()))) {
							AuctionHouseX.getInstance().getTradingManager().getTrading().put(p.getUniqueId(), target.getUniqueId());
							AuctionHouseX.getInstance().getTradingManager().saveInventory(p.getUniqueId());
							AuctionHouseX.getInstance().getTradingManager().saveInventory(target.getUniqueId());
							tradingGUI.open(p, target);
						}
					}
				}
			}
		}
	}
}
