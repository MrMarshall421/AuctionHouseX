package dev.mrmarshall.auctionhousex;

import dev.mrmarshall.auctionhousex.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AuctionHouseX extends JavaPlugin {

	private static AuctionHouseX instance;

	@Override
	public void onEnable() {
		instance = this;

		new PluginManager();
	}

	@Override
	public void onDisable() {
		instance = null;
	}

	public static AuctionHouseX getInstance() { return instance; }
}