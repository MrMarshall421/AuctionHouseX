package dev.mrmarshall.auctionhousex.gui;

import dev.mrmarshall.auctionhousex.AuctionHouseX;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ListingPriceConfirmationGUI implements Listener {

	public void open(Player p, ItemStack item, double listingPrice) {
		Inventory listingPriceConfirmationGUI = Bukkit.createInventory(null, 9, "Pay $" + listingPrice + " Listing Fee?");

		//> Items
		ItemStack confirm = AuctionHouseX.getInstance().getItemCreator().create(Material.GREEN_STAINED_GLASS_PANE, "§aConfirm", new ArrayList<>(), false);
		ItemStack cancel = AuctionHouseX.getInstance().getItemCreator().create(Material.RED_STAINED_GLASS_PANE, "§cCancel", new ArrayList<>(), false);

		//> Set Items
		listingPriceConfirmationGUI.setItem(0, confirm);
		listingPriceConfirmationGUI.setItem(1, confirm);
		listingPriceConfirmationGUI.setItem(2, confirm);
		listingPriceConfirmationGUI.setItem(3, confirm);
		listingPriceConfirmationGUI.setItem(4, item);
		listingPriceConfirmationGUI.setItem(5, cancel);
		listingPriceConfirmationGUI.setItem(6, cancel);
		listingPriceConfirmationGUI.setItem(7, cancel);
		listingPriceConfirmationGUI.setItem(8, cancel);

		//> Open GUI
		p.openInventory(listingPriceConfirmationGUI);
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();

		if (e.getView().getTitle().contains(" Listing Fee?")) {
			try {
				double listingFee = Integer.parseInt(e.getView().getTitle().replaceAll("Pay $", "").replaceAll(" Listing Fee?", ""));
				e.setCancelled(true);

				if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§aConfirm")) {
					//> Sell item at slot 4
					AuctionHouseX.getInstance().getAuctionhouseManager().sellItem(p, p.getOpenInventory().getTopInventory().getItem(4), listingFee);
					AuctionHouseX.getInstance().getEconomyManager().withdrawPlayer(Bukkit.getOfflinePlayer(p.getUniqueId()), listingFee);

					p.closeInventory();
					p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§fA fee of §c$" + listingFee + " §fwas charged");
				} else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§cCancel")) {
					p.closeInventory();
					p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§cYou have cancelled the selling");
				}
			} catch (NullPointerException ex) {
			}
		}
	}
}
