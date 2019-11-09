package dev.mrmarshall.auctionhousex.gui;

import dev.mrmarshall.auctionhousex.AuctionHouseX;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RecentlySoldGUI implements Listener {

    public void open(Player p, String sortOrder, int page) {
        Inventory recentlySoldGUI = Bukkit.createInventory(null, 54, "§2Recently Sold Items");

        //> Items
        ItemStack placeholder = AuctionHouseX.getInstance().getItemCreator().create(Material.LIME_STAINED_GLASS_PANE, " ", new ArrayList<>(), false);
        ItemStack nextPage = AuctionHouseX.getInstance().getItemCreator().create(Material.TIPPED_ARROW, "§aNext Page", new ArrayList<>(), false);
        PotionMeta nextPageMeta = (PotionMeta) nextPage.getItemMeta();
        nextPageMeta.setBasePotionData(new PotionData(PotionType.JUMP));
        nextPageMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        nextPage.setItemMeta(nextPageMeta);
        ItemStack previousPage = AuctionHouseX.getInstance().getItemCreator().create(Material.TIPPED_ARROW, "§cPrevious Page", new ArrayList<>(), false);
        PotionMeta previousPageMeta = (PotionMeta) previousPage.getItemMeta();
        previousPageMeta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
        previousPageMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        previousPage.setItemMeta(previousPageMeta);
        List<String> sortingLore = new ArrayList<>();
        ItemStack sorting;
        if (sortOrder.equals("oldest")) {
            sortingLore.add("§2Currently looking at:");
            sortingLore.add("§a§lOldest first");
            sorting = AuctionHouseX.getInstance().getItemCreator().create(Material.ENDER_EYE, "§6Sorting Order", sortingLore, false);
        } else if (sortOrder.equals("newest")) {
            sortingLore.add("§2Currently looking at:");
            sortingLore.add("§a§lNewest first");
            sorting = AuctionHouseX.getInstance().getItemCreator().create(Material.ENDER_PEARL, "§6Sorting Order", sortingLore, false);
        } else {
            sortingLore.add("§2Currently looking at:");
            sortingLore.add("§a§lCheapest first");
            sorting = AuctionHouseX.getInstance().getItemCreator().create(Material.GLOWSTONE_DUST, "§6Sorting Order", sortingLore, false);
        }
        List<String> clearSoldItemsLore = new ArrayList<>();
        clearSoldItemsLore.add("§aClick here to clear all sold items.");
        clearSoldItemsLore.add("§cItems will be permanently removed!");
        ItemStack clearSoldItems = AuctionHouseX.getInstance().getItemCreator().create(Material.MAP, "§6Clear Sold Items", clearSoldItemsLore, false);
        ItemStack back = AuctionHouseX.getInstance().getItemCreator().create(Material.IRON_DOOR, "§9Back", new ArrayList<>(), false);

        //> Set Items
        recentlySoldGUI.setItem(7, placeholder);
        recentlySoldGUI.setItem(8, nextPage);
        recentlySoldGUI.setItem(16, placeholder);
        recentlySoldGUI.setItem(17, previousPage);
        recentlySoldGUI.setItem(25, placeholder);
        recentlySoldGUI.setItem(26, sorting);
        recentlySoldGUI.setItem(34, placeholder);
        recentlySoldGUI.setItem(35, clearSoldItems);
        recentlySoldGUI.setItem(43, placeholder);
        recentlySoldGUI.setItem(44, placeholder);
        recentlySoldGUI.setItem(52, placeholder);
        recentlySoldGUI.setItem(53, back);

        AuctionHouseX.getInstance().getAuctionhouseManager().getCurrentPage().remove(p.getUniqueId());
        AuctionHouseX.getInstance().getAuctionhouseManager().getCurrentPage().put(p.getUniqueId(), page);

        //> Open GUI
        if (!p.getOpenInventory().getTitle().equals("§2Recently Sold Items")) {
            p.openInventory(recentlySoldGUI);
        } else {
            //> Change sorting items
            p.getOpenInventory().getTopInventory().setItem(26, sorting);
        }

        AuctionHouseX.getInstance().getAuctionhouseManager().refreshAuctionhouse(p, "", page);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        try {
            if (e.getView().getTitle().equals("§2Recently Sold Items")) {
                e.setCancelled(true);

                String currentSortingOrder = AuctionHouseX.getInstance().getAuctionhouseManager().getCurrentSortingOrder(p);
                int currentPage = AuctionHouseX.getInstance().getAuctionhouseManager().getCurrentPage().get(p.getUniqueId());

                if (e.getSlot() == 8) {
                    //> Next Page
                    if (currentPage <= 9) {
                        if (e.getInventory().getItem(51) != null && e.getInventory().getItem(51).getType() != Material.AIR) {
                            open(p, currentSortingOrder, currentPage + 1);
                        }
                    }
                } else if (e.getSlot() == 17) {
                    //> Previous Page
                    if (currentPage >= 2) {
                        open(p, currentSortingOrder, currentPage - 1);
                    }
                } else if (e.getSlot() == 26) {
                    //> Change Sorting Order
                    if (currentSortingOrder.equals("oldest")) {
                        //> Change to newest
                        open(p, "newest", currentPage);
                    } else if (currentSortingOrder.equals("newest")) {
                        //> Change to cheapest
                        open(p, "cheapest", currentPage);
                    } else {
                        //> Change to oldest
                        open(p, "oldest", currentPage);
                    }
                } else if (e.getSlot() == 35) {
                    //> Clear all sold items
                    File sold = new File("plugins/AuctionHouseX/Auctionhouse/Sold.yml");
                    FileConfiguration soldCfg = YamlConfiguration.loadConfiguration(sold);

                    Map<String, Object> listings = soldCfg.getValues(false);
                    Iterator iterator = listings.entrySet().iterator();

                    while (iterator.hasNext()) {
                        Map.Entry entry = (Map.Entry) iterator.next();
                        int listingId = Integer.parseInt(entry.getKey().toString());

                        if (soldCfg.getString(listingId + ".seller").contains(p.getUniqueId().toString())) {
                            soldCfg.set(listingId + "", null);
                        }
                    }

                    try {
                        soldCfg.save(sold);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    AuctionHouseX.getInstance().getAuctionhouseManager().refreshAuctionhouse(p, "", currentPage);
                    p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§aSuccessfully cleared sold items!");
                } else if (e.getSlot() == 53) {
                    //> Back
                    p.closeInventory();
                    AuctionHouseX.getInstance().getCurrentListingsGUI().open(p, currentSortingOrder, currentPage);
                }
            }
        } catch (NullPointerException ex) {
        }
    }
}
