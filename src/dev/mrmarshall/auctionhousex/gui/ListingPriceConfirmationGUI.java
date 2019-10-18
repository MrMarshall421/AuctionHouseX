package dev.mrmarshall.auctionhousex.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class ListingPriceConfirmationGUI implements Listener {

	public void open(Player p, double listingPrice) {
		Inventory listingPriceConfirmationGUI = Bukkit.createInventory(null, 9, "Pay");



	}
}
