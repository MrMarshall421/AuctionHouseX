package dev.mrmarshall.auctionhousex.events;

import dev.mrmarshall.auctionhousex.AuctionHouseX;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		Player target;

		try {
			if(AuctionHouseX.getInstance().getTradingManager().getTrading().containsKey(p.getUniqueId()) || AuctionHouseX.getInstance().getTradingManager().getTrading().containsValue(p.getUniqueId())) {
				if(e.getView().getTitle().equals("§2SafeTrade")) {
					//> Player started Trade
					target = Bukkit.getPlayer(AuctionHouseX.getInstance().getTradingManager().getTrading().get(p.getUniqueId()));
					AuctionHouseX.getInstance().getTradingManager().cancelTrade(p.getUniqueId(), target.getUniqueId());
				} else if(e.getView().getTitle().equals(" §2SafeTrade")) {
					//> Player not started Trade
					target = Bukkit.getPlayer(AuctionHouseX.getInstance().getTradingManager().getTradingPartner(p.getUniqueId()));
					AuctionHouseX.getInstance().getTradingManager().cancelTrade(target.getUniqueId(), p.getUniqueId());
				}
			}
		} catch(NullPointerException ex) {
			ex.printStackTrace();
		}
	}
}
