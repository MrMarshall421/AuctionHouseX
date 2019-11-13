package dev.mrmarshall.auctionhousex.plugin;

import dev.mrmarshall.auctionhousex.AuctionHouseX;
import dev.mrmarshall.auctionhousex.commands.AuctionHouseCMD;
import dev.mrmarshall.auctionhousex.commands.XpBottleCMD;
import dev.mrmarshall.auctionhousex.events.*;
import dev.mrmarshall.auctionhousex.gui.*;
import org.bukkit.Bukkit;

public class PluginManager {

    public PluginManager() {
        createFiles();
        registerEvents();
        loadCommands();
    }

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
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), AuctionHouseX.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerDropItemListener(), AuctionHouseX.getInstance());

        //> GUIs
        Bukkit.getPluginManager().registerEvents(new TradingGUI(), AuctionHouseX.getInstance());
        Bukkit.getPluginManager().registerEvents(new ListingPriceConfirmationGUI(), AuctionHouseX.getInstance());
        Bukkit.getPluginManager().registerEvents(new AuctionhouseGUI(), AuctionHouseX.getInstance());
        Bukkit.getPluginManager().registerEvents(new CurrentListingsGUI(), AuctionHouseX.getInstance());
        Bukkit.getPluginManager().registerEvents(new RecentlySoldGUI(), AuctionHouseX.getInstance());
        Bukkit.getPluginManager().registerEvents(new CancelledExpiredGUI(), AuctionHouseX.getInstance());
    }

    private void loadCommands() {
        XpBottleCMD cXpBottleCMD = new XpBottleCMD();
        AuctionHouseX.getInstance().getCommand("xpbottle").setExecutor(cXpBottleCMD);
        AuctionHouseCMD cAuctionHouseCMD = new AuctionHouseCMD();
        AuctionHouseX.getInstance().getCommand("auctionhouse").setExecutor(cAuctionHouseCMD);
    }
}
