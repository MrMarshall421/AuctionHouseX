package dev.mrmarshall.auctionhousex.gui;

import dev.mrmarshall.auctionhousex.AuctionHouseX;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AuctionhouseGUI implements Listener {

	public void open(Player p ) {
		Inventory auctionhouseGUI = Bukkit.createInventory(null, 54, "§bAuctionhouse§6§lX");

		//> Items
		ItemStack nextPage = AuctionHouseX.getInstance().getItemCreator().create(Material.SPECTRAL_ARROW, "§aNext Page", new ArrayList<>(), false);
		ItemStack previousPage = AuctionHouseX.getInstance().getItemCreator().create(Material.TIPPED_ARROW, "§cPrevious Page", new ArrayList<>(), false);
		List<String> sortingLore = new ArrayList<>();
		sortingLore.add("§9Order: §eOldest First");
		ItemStack sorting = AuctionHouseX.getInstance().getItemCreator().create(Material.ENDER_EYE, "§6Sort Listings", sortingLore, false);
		ItemStack instruction = AuctionHouseX.getInstance().getAuctionhouseManager().getInstructionBook();
		List<String> sellingLore = new ArrayList<>();
		sellingLore.add("§cYou have not listed any items."); //> TODO: Change if player has listed items
		ItemStack selling = AuctionHouseX.getInstance().getItemCreator().create(Material.CHEST, "§6Items You're Selling", new ArrayList<>(), false);
		ItemStack placeholder = AuctionHouseX.getInstance().getItemCreator().create(Material.BLUE_STAINED_GLASS_PANE, "", new ArrayList<>(), false);

	}
}
