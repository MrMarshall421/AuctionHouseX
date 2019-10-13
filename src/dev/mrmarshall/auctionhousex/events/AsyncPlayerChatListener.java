package dev.mrmarshall.auctionhousex.events;

import dev.mrmarshall.auctionhousex.AuctionHouseX;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		if(e.getMessage().contains("*item")) {
			if(e.getPlayer().hasPermission("itemdisplay.access")) {
				String message = ChatColor.translateAlternateColorCodes('&', AuctionHouseX.getInstance().getConfig().getString("item-show.format")
						.replaceAll("%prefix%", AuctionHouseX.getInstance().getChatManager().getGroupPrefix(e.getPlayer().getWorld(), AuctionHouseX.getInstance().getPermissionManager().getPrimaryGroup(e.getPlayer()))))
						.replaceAll("%player%", e.getPlayer().getName())
						.replaceAll("%suffix%", AuctionHouseX.getInstance().getChatManager().getGroupSuffix(e.getPlayer().getWorld(), AuctionHouseX.getInstance().getPermissionManager().getPrimaryGroup(e.getPlayer())))
						.replaceAll("%message%", e.getMessage());

				AuctionHouseX.getInstance().getMessage().replaceItem(e.getPlayer(), message);
				e.setCancelled(true);
			}
		}
	}
}