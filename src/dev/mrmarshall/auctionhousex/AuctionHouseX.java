package dev.mrmarshall.auctionhousex;

import dev.mrmarshall.auctionhousex.items.EnchantingBottle;
import dev.mrmarshall.auctionhousex.managers.*;
import dev.mrmarshall.auctionhousex.plugin.PluginManager;
import dev.mrmarshall.auctionhousex.util.ItemCreator;
import dev.mrmarshall.auctionhousex.util.Message;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class AuctionHouseX extends JavaPlugin {

	private static AuctionHouseX instance;
	private TradingManager tradingManager;
	private ItemCreator itemCreator;
	private Chat chatManager;
	private Permission permissionManager;
	private Economy economyManager;
	private Message message;
	private EnchantingBottle enchantingBottle;
	private LevelManager levelManager;
	private AuctionhouseManager AhM;
	private FileManager fileManager;
	private CategoryManager categoryManager;

	@Override
	public void onEnable() {
		instance = this;
		itemCreator = new ItemCreator();
		message = new Message();
		enchantingBottle = new EnchantingBottle();
		levelManager = new LevelManager();
		AhM = new AuctionhouseManager();
		fileManager = new FileManager();
		categoryManager = new CategoryManager();
		setupChatManager();
		setupPermissionManager();
		setupEconomyManager();

		new PluginManager();
		tradingManager = new TradingManager();
	}

	public static AuctionHouseX getInstance() { return instance; }
	public TradingManager getTradingManager() { return tradingManager; }
	public ItemCreator getItemCreator() { return itemCreator; }
	public Chat getChatManager() { return chatManager; }
	private void setupChatManager() {
		if(getServer().getPluginManager().getPlugin("Vault") != null) {
			RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
			chatManager = rsp.getProvider();
		}
	}
	public Permission getPermissionManager() { return permissionManager; }
	private void setupPermissionManager() {
		if(getServer().getPluginManager().getPlugin("Vault") != null) {
			RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
			permissionManager = rsp.getProvider();
		}
	}
	public Economy getEconomyManager() { return economyManager; }
	private void setupEconomyManager() {
		if(getServer().getPluginManager().getPlugin("Vault") != null) {
			RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
			economyManager = rsp.getProvider();
		}
	}
	public Message getMessage() { return message; }
	public EnchantingBottle getEnchantingBottle() { return enchantingBottle; }
	public LevelManager getLevelManager() { return levelManager; }
	public AuctionhouseManager getAuctionhouseManager() { return AhM; }
	public FileManager getFileManager() { return fileManager; }
	public CategoryManager getCategoryManager() { return categoryManager; }
}