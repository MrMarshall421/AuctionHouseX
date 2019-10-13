package dev.mrmarshall.auctionhousex.plugin;

import dev.mrmarshall.auctionhousex.AuctionHouseX;
import dev.mrmarshall.auctionhousex.commands.XpBottleCMD;
import dev.mrmarshall.auctionhousex.events.*;
import dev.mrmarshall.auctionhousex.gui.TradingGUI;
import org.bukkit.Bukkit;

public class PluginManager {

	private void createFiles() {
		AuctionHouseX.getInstance().saveDefaultConfig();
	}

	private void registerEvents() {
		Bukkit.getPluginManager().registerEvents(new PlayerInteractEntityListener(), AuctionHouseX.getInstance());
		Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), AuctionHouseX.getInstance());
		Bukkit.getPluginManager().registerEvents(new InventoryCloseListener(), AuctionHouseX.getInstance());
		Bukkit.getPluginManager().registerEvents(new InventoryDragListener(), AuctionHouseX.getInstance());
		Bukkit.getPluginManager().registerEvents(new AsyncPlayerChatListener(), AuctionHouseX.getInstance());
		Bukkit.getPluginManager().registerEvents(new ExpBottleListener(), AuctionHouseX.getInstance());
		Bukkit.getPluginManager().registerEvents(new ProjectileLaunchListener(), AuctionHouseX.getInstance());
		Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), AuctionHouseX.getInstance());

		//> GUIs
		Bukkit.getPluginManager().registerEvents(new TradingGUI(), AuctionHouseX.getInstance());
	}

	private void loadCommands() {
		XpBottleCMD cXpBottleCMD = new XpBottleCMD();
		AuctionHouseX.getInstance().getCommand("xpbottle").setExecutor(cXpBottleCMD);
	}

	public PluginManager() {
		createFiles();
		registerEvents();
		loadCommands();
	}
}
