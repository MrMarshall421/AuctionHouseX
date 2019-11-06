package dev.mrmarshall.auctionhousex;

import dev.mrmarshall.auctionhousex.gui.AuctionhouseGUI;
import dev.mrmarshall.auctionhousex.gui.CurrentListingsGUI;
import dev.mrmarshall.auctionhousex.gui.RecentlySoldGUI;
import dev.mrmarshall.auctionhousex.items.EnchantingBottle;
import dev.mrmarshall.auctionhousex.managers.*;
import dev.mrmarshall.auctionhousex.plugin.PluginManager;
import dev.mrmarshall.auctionhousex.util.ItemCreator;
import dev.mrmarshall.auctionhousex.util.Message;
import dev.mrmarshall.auctionhousex.util.TimeHandler;
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
    private TimeHandler timeHandler;
    private AuctionhouseGUI auctionhouseGUI;
    private CurrentListingsGUI currentListingsGUI;
    private RecentlySoldGUI recentlySoldGUI;

    public static AuctionHouseX getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        new PluginManager();

        itemCreator = new ItemCreator();
        message = new Message();
        enchantingBottle = new EnchantingBottle();
        levelManager = new LevelManager();
        fileManager = new FileManager();
        AhM = new AuctionhouseManager();
        categoryManager = new CategoryManager();
        setupChatManager();
        setupPermissionManager();
        setupEconomyManager();
        tradingManager = new TradingManager();
        timeHandler = new TimeHandler();
        auctionhouseGUI = new AuctionhouseGUI();
        currentListingsGUI = new CurrentListingsGUI();
        recentlySoldGUI = new RecentlySoldGUI();
    }

    public TradingManager getTradingManager() {
        return tradingManager;
    }

    public ItemCreator getItemCreator() {
        return itemCreator;
    }

    public Chat getChatManager() {
        return chatManager;
    }

    private void setupChatManager() {
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
            try {
                chatManager = rsp.getProvider();
            } catch (NullPointerException ex) {
            }
        }
    }

    public Permission getPermissionManager() {
        return permissionManager;
    }

    private void setupPermissionManager() {
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
            permissionManager = rsp.getProvider();
        }
    }

    public Economy getEconomyManager() {
        return economyManager;
    }

    private void setupEconomyManager() {
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
            economyManager = rsp.getProvider();
        }
    }

    public Message getMessage() {
        return message;
    }

    public EnchantingBottle getEnchantingBottle() {
        return enchantingBottle;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public AuctionhouseManager getAuctionhouseManager() {
        return AhM;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public CategoryManager getCategoryManager() {
        return categoryManager;
    }

    public TimeHandler getTimeHandler() {
        return timeHandler;
    }

    public AuctionhouseGUI getAuctionhouseGUI() {
        return auctionhouseGUI;
    }

    public CurrentListingsGUI getCurrentListingsGUI() {
        return currentListingsGUI;
    }

    public RecentlySoldGUI getRecentlySoldGUI() {
        return recentlySoldGUI;
    }
}