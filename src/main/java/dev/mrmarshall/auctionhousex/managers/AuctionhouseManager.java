package dev.mrmarshall.auctionhousex.managers;

import dev.mrmarshall.auctionhousex.AuctionHouseX;
import dev.mrmarshall.auctionhousex.sorting.Listing;
import dev.mrmarshall.auctionhousex.sorting.SortListingByPrice;
import dev.mrmarshall.auctionhousex.sorting.SortListingByTime;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class AuctionhouseManager {

    private File instruction;
    private FileConfiguration instructionCfg;
    private Map<UUID, Double> selling;
    private Map<UUID, Integer> currentPage;
    private Map<UUID, String> currentCategory;

    public AuctionhouseManager() {
        instruction = new File("plugins/AuctionHouseX/Auctionhouse/instruction.yml");
        instructionCfg = YamlConfiguration.loadConfiguration(instruction);
        selling = new HashMap<>();
        currentPage = new HashMap<>();
        currentCategory = new HashMap<>();
        loadAuctionhouseFiles();
    }

    private void loadAuctionhouseFiles() {
        AuctionHouseX.getInstance().getFileManager().loadAuctionhouseFiles(instruction, instructionCfg);
    }

    public ItemStack getInstructionBook() {
        return AuctionHouseX.getInstance().getFileManager().getInstructionBook(instructionCfg);
    }

    public Map<UUID, Double> getSelling() {
        return selling;
    }

    public Map<UUID, Integer> getCurrentPage() {
        return currentPage;
    }

    public Map<UUID, String> getCurrentCategory() {
        return currentCategory;
    }

    private boolean isBlacklisted(ItemStack item) {
        List<String> blacklisted = AuctionHouseX.getInstance().getConfig().getStringList("blacklist");
        return blacklisted.contains(item.getType().toString().toLowerCase());
    }

    public String getItemCategory(ItemStack item) {
        if (AuctionHouseX.getInstance().getCategoryManager().getBuildingCategory().contains(item.getType())) {
            return "Blocks";
        } else if (AuctionHouseX.getInstance().getCategoryManager().getBrewingCategory().contains(item.getType())) {
            return "Brewing";
        } else if (AuctionHouseX.getInstance().getCategoryManager().getCombatCategory().contains(item.getType())) {
            return "Combat";
        } else if (AuctionHouseX.getInstance().getCategoryManager().getDecorationCategory().contains(item.getType())) {
            return "Decoration";
        } else if (AuctionHouseX.getInstance().getCategoryManager().getFoodCategory().contains(item.getType())) {
            return "Food";
        } else if (AuctionHouseX.getInstance().getCategoryManager().getRtCategory().contains(item.getType())) {
            return "Redstone+Transportation";
        } else if (AuctionHouseX.getInstance().getCategoryManager().getProductiveCategory().contains(item.getType())) {
            return "Productive";
        } else if (AuctionHouseX.getInstance().getCategoryManager().getToolsCategory().contains(item.getType())) {
            return "Tools";
        }

        return null;
    }

    public String getCurrentSortingOrder(Player p) {
        int sortingSlot = 26;

        if (p.getOpenInventory().getTopInventory().getItem(sortingSlot).getType() == Material.ENDER_EYE) {
            //> Sorting order: OLDEST
            return "oldest";
        } else if (p.getOpenInventory().getTopInventory().getItem(sortingSlot).getType() == Material.ENDER_PEARL) {
            //> Sorting order: NEWEST
            return "newest";
        } else {
            //> Sorting order: CHEAPEST
            return "cheapest";
        }
    }

    public void refreshOpenAuctionhouses() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getOpenInventory().getTitle().equals("§3Auctionhouse§6§lX")) {
                String category = getCurrentCategory().get(p.getUniqueId());
                refreshAuctionhouse(p, category, AuctionHouseX.getInstance().getAuctionhouseManager().getCurrentPage().get(p.getUniqueId()));
            }
        }
    }

    public int getItemsOnPage(Player p, int page) {
        if (p.getOpenInventory().getTitle().equals("§3Auctionhouse§6§lX")) {
            switch (page) {
                case 2:
                    return 40;
                case 3:
                    return 80;
                case 4:
                    return 120;
                case 5:
                    return 160;
                case 6:
                    return 200;
                case 7:
                    return 240;
                case 8:
                    return 280;
                case 9:
                    return 320;
                case 10:
                    return 360;
            }
        } else if (p.getOpenInventory().getTitle().equals("§3Your Current Listings") || p.getOpenInventory().getTitle().equals("§2Recently Sold Items") || p.getOpenInventory().getTitle().equals("§6Cancelled/Expired Listings")) {
            switch (page) {
                case 2:
                    return 42;
                case 3:
                    return 84;
                case 4:
                    return 126;
                case 5:
                    return 168;
                case 6:
                    return 210;
                case 7:
                    return 252;
                case 8:
                    return 294;
                case 9:
                    return 336;
                case 10:
                    return 378;
            }
        }

        return 0;
    }

    public void clearCurrentAuctionhouse(Player p) {
        if (p.getOpenInventory().getTitle().equals("§3Auctionhouse§6§lX")) {
            for (int i = 0; i < p.getOpenInventory().getTopInventory().getSize(); i++) {
                if (i != 8 && i != 17 && i != 26 && i != 35 && i != 44 && i != 45 && i != 46 && i != 47 && i != 48 && i != 49 && i != 50 && i != 51 && i != 52 && i != 53) {
                    try {
                        p.getOpenInventory().getTopInventory().getItem(i).setAmount(0);
                    } catch (NullPointerException ex) {
                    }
                }
            }
        } else if (p.getOpenInventory().getTitle().equals("§3Your Current Listings") || p.getOpenInventory().getTitle().equals("§2Recently Sold Items") || p.getOpenInventory().getTitle().equals("§6Cancelled/Expired Listings")) {
            for (int i = 0; i < p.getOpenInventory().getTopInventory().getSize(); i++) {
                if (i != 7 && i != 8 && i != 16 && i != 17 && i != 25 && i != 26 && i != 34 && i != 35 && i != 43 && i != 44 && i != 52 && i != 53) {
                    try {
                        p.getOpenInventory().getTopInventory().getItem(i).setAmount(0);
                    } catch (NullPointerException ex) {
                    }
                }
            }
        }
    }

    public boolean isExpired(File file, FileConfiguration fileCfg, int id) {
        long currentTime = System.currentTimeMillis();
        long listedTime = AuctionHouseX.getInstance().getTimeHandler().convertFormattedToMillis(AuctionHouseX.getInstance().getConfig().getString("auction.listingDuration")) + fileCfg.getLong(id + ".time");

        if (currentTime > listedTime) {
            cancelListing(file, fileCfg, id);
            return true;
        }

        return false;
    }

    public void refreshAuctionhouse(Player p, String category, int page) {
        if (p.getOpenInventory().getTitle().equals("§3Auctionhouse§6§lX")) {
            Inventory auctionhouseGUI = p.getOpenInventory().getTopInventory();
            String sortOrder = getCurrentSortingOrder(p);
            int itemsOnPage = getItemsOnPage(p, page);
            File categoryFile = null;
            FileConfiguration categoryFileCfg = null;
            List<Integer> listings = null;
            if (category != "ALL") {
                categoryFile = new File("plugins/AuctionHouseX/Auctionhouse/" + category + ".yml");
                categoryFileCfg = YamlConfiguration.loadConfiguration(categoryFile);
                listings = AuctionHouseX.getInstance().getFileManager().getListingsFromFile(categoryFileCfg);
            }
            Map<String, List<Integer>> listingsMap = AuctionHouseX.getInstance().getFileManager().getAllListings();

            clearCurrentAuctionhouse(p);

            if (category != "ALL") {
                if (sortOrder.equals("oldest")) {
                    int itemSlot = 0;
                    if (Material.getMaterial(category) == null) {
                        for (int i = itemsOnPage; i < listings.size(); i++) {
                            if (categoryFileCfg.getString(listings.get(i) + ".seller") != null) {
                                if (auctionhouseGUI.getItem(itemSlot) != null) {
                                    if (itemSlot <= 52) {
                                        itemSlot++;
                                    }
                                }

                                if (auctionhouseGUI.getItem(itemSlot) == null) {
                                    String seller = Bukkit.getOfflinePlayer(UUID.fromString(categoryFileCfg.getString(listings.get(i) + ".seller"))).getName();
                                    String time = AuctionHouseX.getInstance().getTimeHandler().convertListingTime(categoryFileCfg.getLong(listings.get(i) + ".time"));
                                    double price = categoryFileCfg.getDouble(listings.get(i) + ".price");

                                    if (!isExpired(categoryFile, categoryFileCfg, listings.get(i))) {
                                        ItemStack item = categoryFileCfg.getItemStack(listings.get(i) + ".item");
                                        ItemMeta itemMeta = item.getItemMeta();
                                        List<String> itemLore = new ArrayList<>();
                                        if (itemMeta.hasLore()) {
                                            itemLore = itemMeta.getLore();
                                        }
                                        itemLore.add("§8------------------------------");
                                        if (AuctionHouseX.getInstance().getEconomyManager().getBalance(Bukkit.getOfflinePlayer(p.getUniqueId())) >= price) {
                                            itemLore.add("§aYou can purchase this item.");
                                        } else {
                                            itemLore.add("§6You can not purchase this item.");
                                        }
                                        itemLore.add(" ");
                                        itemLore.add("§9Price: §e$" + price);
                                        itemLore.add("§9Seller: §e" + seller);
                                        itemLore.add("§9Expire: §e" + time);
                                        itemLore.add(" ");
                                        itemLore.add("§8------------------------------");
                                        itemMeta.setLore(itemLore);
                                        item.setItemMeta(itemMeta);
                                        auctionhouseGUI.setItem(itemSlot, item);
                                    } else {
                                        //> Item expired
                                        itemSlot--;
                                    }
                                }

                                if (itemSlot <= 52) {
                                    itemSlot++;
                                }
                            }
                        }
                    } else {
                        //> /ah search command gui
                        //> Show all listings with material of category
                        Iterator filesIterator = listingsMap.entrySet().iterator();

                        while (filesIterator.hasNext()) {
                            Map.Entry entry = (Map.Entry) filesIterator.next();
                            List<Integer> searchListings = (List<Integer>) entry.getValue();
                            File searchCategoryFile = new File("plugins/AuctionHouseX/Auctionhouse/" + entry.getKey());
                            FileConfiguration searchCategoryFileCfg = YamlConfiguration.loadConfiguration(searchCategoryFile);

                            for (int i = itemsOnPage; i < searchListings.size(); i++) {
                                if (auctionhouseGUI.getItem(itemSlot) != null) {
                                    if (itemSlot <= 52) {
                                        itemSlot++;
                                    }
                                }

                                if (auctionhouseGUI.getItem(itemSlot) == null) {
                                    ItemStack item = searchCategoryFileCfg.getItemStack(searchListings.get(i) + ".item");

                                    if (item.getType() == Material.getMaterial(category)) {
                                        //> Item has correct material
                                        String seller = Bukkit.getOfflinePlayer(UUID.fromString(searchCategoryFileCfg.getString(searchListings.get(i) + ".seller"))).getName();
                                        String time = AuctionHouseX.getInstance().getTimeHandler().convertListingTime(searchCategoryFileCfg.getLong(searchListings.get(i) + ".time"));
                                        double price = searchCategoryFileCfg.getDouble(searchListings.get(i) + ".price");

                                        if (!isExpired(searchCategoryFile, searchCategoryFileCfg, searchListings.get(i))) {
                                            ItemMeta itemMeta = item.getItemMeta();
                                            List<String> itemLore = new ArrayList<>();
                                            if (itemMeta.hasLore()) {
                                                itemLore = itemMeta.getLore();
                                            }
                                            itemLore.add("§8------------------------------");
                                            if (AuctionHouseX.getInstance().getEconomyManager().getBalance(Bukkit.getOfflinePlayer(p.getUniqueId())) >= price) {
                                                itemLore.add("§aYou can purchase this item.");
                                            } else {
                                                itemLore.add("§6You can not purchase this item.");
                                            }
                                            itemLore.add(" ");
                                            itemLore.add("§9Price: §e$" + price);
                                            itemLore.add("§9Seller: §e" + seller);
                                            itemLore.add("§9Expire: §e" + time);
                                            itemLore.add(" ");
                                            itemLore.add("§8------------------------------");
                                            itemMeta.setLore(itemLore);
                                            item.setItemMeta(itemMeta);
                                            auctionhouseGUI.setItem(itemSlot, item);
                                        } else {
                                            //> Item expired
                                            itemSlot--;
                                        }
                                    } else {
                                        //> Item not filtered material
                                        itemSlot--;
                                    }
                                }

                                if (itemSlot <= 52) {
                                    itemSlot++;
                                }
                            }
                        }
                    }
                } else if (sortOrder.equals("newest")) {
                    int itemSlot = 0;
                    if (Material.getMaterial(category) == null) {
                        for (int i = listings.size() - 1; i > itemsOnPage - 1; i--) {
                            if (categoryFileCfg.getString(listings.get(i) + ".seller") != null) {
                                if (auctionhouseGUI.getItem(itemSlot) != null) {
                                    if (itemSlot <= 52) {
                                        itemSlot++;
                                    }
                                }

                                if (auctionhouseGUI.getItem(itemSlot) == null) {
                                    int listing = listings.get(i);
                                    if (page > 1) {
                                        listing = ((listings.size() - 1) - itemsOnPage) - itemSlot;
                                    }
                                    String seller = Bukkit.getOfflinePlayer(UUID.fromString(categoryFileCfg.getString(listing + ".seller"))).getName();
                                    String time = AuctionHouseX.getInstance().getTimeHandler().convertListingTime(categoryFileCfg.getLong(listing + ".time"));
                                    double price = categoryFileCfg.getDouble(listing + ".price");

                                    if (!isExpired(categoryFile, categoryFileCfg, listings.get(i))) {
                                        ItemStack item = categoryFileCfg.getItemStack(listing + ".item");
                                        ItemMeta itemMeta = item.getItemMeta();
                                        List<String> itemLore = new ArrayList<>();
                                        if (itemMeta.hasLore()) {
                                            itemLore = itemMeta.getLore();
                                        }
                                        itemLore.add("§8------------------------------");
                                        if (AuctionHouseX.getInstance().getEconomyManager().getBalance(Bukkit.getOfflinePlayer(p.getUniqueId())) >= price) {
                                            itemLore.add("§aYou can purchase this item.");
                                        } else {
                                            itemLore.add("§6You can not purchase this item.");
                                        }
                                        itemLore.add(" ");
                                        itemLore.add("§9Price: §e$" + price);
                                        itemLore.add("§9Seller: §e" + seller);
                                        itemLore.add("§9Expire: §e" + time);
                                        itemLore.add(" ");
                                        itemLore.add("§8------------------------------");
                                        itemMeta.setLore(itemLore);
                                        item.setItemMeta(itemMeta);
                                        auctionhouseGUI.setItem(itemSlot, item);
                                    } else {
                                        //> Item expired
                                        itemSlot--;
                                    }
                                }

                                if (itemSlot <= 52) {
                                    itemSlot++;
                                }
                            }
                        }
                    } else {
                        //> /ah search command gui
                        //> Show all listings with material of category
                        Iterator filesIterator = listingsMap.entrySet().iterator();

                        while (filesIterator.hasNext()) {
                            Map.Entry entry = (Map.Entry) filesIterator.next();
                            List<Integer> searchListings = (List<Integer>) entry.getValue();
                            File searchCategoryFile = new File("plugins/AuctionHouseX/Auctionhouse/" + entry.getKey());
                            FileConfiguration searchCategoryFileCfg = YamlConfiguration.loadConfiguration(searchCategoryFile);

                            for (int i = searchListings.size() - 1; i > itemsOnPage - 1; i--) {
                                if (auctionhouseGUI.getItem(itemSlot) != null) {
                                    if (itemSlot <= 52) {
                                        itemSlot++;
                                    }
                                }

                                if (auctionhouseGUI.getItem(itemSlot) == null) {
                                    int searchListing = searchListings.get(i);
                                    if (page > 1) {
                                        searchListing = ((searchListings.size() - 1) - itemsOnPage) - itemSlot;
                                    }
                                    ItemStack item = searchCategoryFileCfg.getItemStack(searchListing + ".item");

                                    if (item.getType() == Material.getMaterial(category)) {
                                        //> Item has correct material
                                        String seller = Bukkit.getOfflinePlayer(UUID.fromString(searchCategoryFileCfg.getString(searchListing + ".seller"))).getName();
                                        String time = AuctionHouseX.getInstance().getTimeHandler().convertListingTime(searchCategoryFileCfg.getLong(searchListing + ".time"));
                                        double price = searchCategoryFileCfg.getDouble(searchListing + ".price");

                                        if (!isExpired(searchCategoryFile, searchCategoryFileCfg, searchListing)) {
                                            ItemMeta itemMeta = item.getItemMeta();
                                            List<String> itemLore = new ArrayList<>();
                                            if (itemMeta.hasLore()) {
                                                itemLore = itemMeta.getLore();
                                            }
                                            itemLore.add("§8------------------------------");
                                            if (AuctionHouseX.getInstance().getEconomyManager().getBalance(Bukkit.getOfflinePlayer(p.getUniqueId())) >= price) {
                                                itemLore.add("§aYou can purchase this item.");
                                            } else {
                                                itemLore.add("§6You can not purchase this item.");
                                            }
                                            itemLore.add(" ");
                                            itemLore.add("§9Price: §e$" + price);
                                            itemLore.add("§9Seller: §e" + seller);
                                            itemLore.add("§9Expire: §e" + time);
                                            itemLore.add(" ");
                                            itemLore.add("§8------------------------------");
                                            itemMeta.setLore(itemLore);
                                            item.setItemMeta(itemMeta);
                                            auctionhouseGUI.setItem(itemSlot, item);
                                        } else {
                                            //> Item expired
                                            itemSlot--;
                                        }
                                    } else {
                                        //> Item not filtered material
                                        itemSlot--;
                                    }
                                }

                                if (itemSlot <= 52) {
                                    itemSlot++;
                                }
                            }
                        }
                    }
                } else if (sortOrder.equals("cheapest")) {
                    int itemSlot = 0;

                    //> Sort listings (cheapest)
                    Map<Integer, Double> listingPrices = new HashMap<>();
                    for (int listing : listings) {
                        double price = categoryFileCfg.getDouble(listing + ".price");
                        listingPrices.put(listing, price);
                    }

                    List<Map.Entry<Integer, Double>> list = new ArrayList<>(listingPrices.entrySet());
                    list.sort(Map.Entry.comparingByValue());

                    List<Integer> cheapestListings = new ArrayList<>();
                    for (Map.Entry<Integer, Double> entry : list) {
                        cheapestListings.add(entry.getKey());
                    }

                    if (Material.getMaterial(category) == null) {
                        for (int i = itemsOnPage; i < cheapestListings.size(); i++) {
                            if (categoryFileCfg.getString(cheapestListings.get(i) + ".seller") != null) {
                                if (auctionhouseGUI.getItem(itemSlot) != null) {
                                    if (itemSlot <= 52) {
                                        itemSlot++;
                                    }
                                }

                                if (auctionhouseGUI.getItem(itemSlot) == null) {
                                    int listing = cheapestListings.get(i);
                                    if (page > 1) {
                                        listing = ((cheapestListings.size() - 1) - itemsOnPage) - itemSlot;
                                    }
                                    String seller = Bukkit.getOfflinePlayer(UUID.fromString(categoryFileCfg.getString(listing + ".seller"))).getName();
                                    String time = AuctionHouseX.getInstance().getTimeHandler().convertListingTime(categoryFileCfg.getLong(listing + ".time"));
                                    double price = categoryFileCfg.getDouble(listing + ".price");

                                    if (!isExpired(categoryFile, categoryFileCfg, listing)) {
                                        ItemStack item = categoryFileCfg.getItemStack(listing + ".item");
                                        ItemMeta itemMeta = item.getItemMeta();
                                        List<String> itemLore = new ArrayList<>();
                                        if (itemMeta.hasLore()) {
                                            itemLore = itemMeta.getLore();
                                        }
                                        itemLore.add("§8------------------------------");
                                        if (AuctionHouseX.getInstance().getEconomyManager().getBalance(Bukkit.getOfflinePlayer(p.getUniqueId())) >= price) {
                                            itemLore.add("§aYou can purchase this item.");
                                        } else {
                                            itemLore.add("§6You can not purchase this item.");
                                        }
                                        itemLore.add(" ");
                                        itemLore.add("§9Price: §e$" + price);
                                        itemLore.add("§9Seller: §e" + seller);
                                        itemLore.add("§9Expire: §e" + time);
                                        itemLore.add(" ");
                                        itemLore.add("§8------------------------------");
                                        itemMeta.setLore(itemLore);
                                        item.setItemMeta(itemMeta);
                                        auctionhouseGUI.setItem(itemSlot, item);
                                    } else {
                                        //> Item expired
                                        itemSlot--;
                                    }
                                }

                                if (itemSlot <= 52) {
                                    itemSlot++;
                                }
                            }
                        }
                    } else {
                        //> /ah search command gui
                        //> Show all listings with material of category
                        Iterator filesIterator = listingsMap.entrySet().iterator();

                        while (filesIterator.hasNext()) {
                            Map.Entry entry = (Map.Entry) filesIterator.next();
                            List<Integer> searchListings = (List<Integer>) entry.getValue();
                            File searchCategoryFile = new File("plugins/AuctionHouseX/Auctionhouse/" + entry.getKey());
                            FileConfiguration searchCategoryFileCfg = YamlConfiguration.loadConfiguration(searchCategoryFile);

                            //> Sort listings (cheapest)
                            Map<Integer, Double> searchListingPrices = new HashMap<>();
                            for (int searchListing : searchListings) {
                                double price = searchCategoryFileCfg.getDouble(searchListing + ".price");
                                searchListingPrices.put(searchListing, price);
                            }

                            List<Map.Entry<Integer, Double>> searchList = new ArrayList<>(searchListingPrices.entrySet());
                            searchList.sort(Map.Entry.comparingByValue());

                            List<Integer> searchCheapestListings = new ArrayList<>();
                            for (Map.Entry<Integer, Double> searchEntry : searchList) {
                                searchCheapestListings.add(searchEntry.getKey());
                            }

                            for (int i = itemsOnPage; i < searchCheapestListings.size(); i++) {
                                if (auctionhouseGUI.getItem(itemSlot) != null) {
                                    if (itemSlot <= 52) {
                                        itemSlot++;
                                    }
                                }

                                if (auctionhouseGUI.getItem(itemSlot) == null) {
                                    int searchListing = searchCheapestListings.get(i);
                                    if (page > 1) {
                                        searchListing = ((searchCheapestListings.size() - 1) - itemsOnPage) - itemSlot;
                                    }
                                    ItemStack item = searchCategoryFileCfg.getItemStack(searchListing + ".item");

                                    if (item.getType() == Material.getMaterial(category)) {
                                        //> Item has correct material
                                        String seller = Bukkit.getOfflinePlayer(UUID.fromString(searchCategoryFileCfg.getString(searchListing + ".seller"))).getName();
                                        String time = AuctionHouseX.getInstance().getTimeHandler().convertListingTime(searchCategoryFileCfg.getLong(searchListing + ".time"));
                                        double price = searchCategoryFileCfg.getDouble(searchListing + ".price");

                                        if (!isExpired(searchCategoryFile, searchCategoryFileCfg, searchListing)) {
                                            ItemMeta itemMeta = item.getItemMeta();
                                            List<String> itemLore = new ArrayList<>();
                                            if (itemMeta.hasLore()) {
                                                itemLore = itemMeta.getLore();
                                            }
                                            itemLore.add("§8------------------------------");
                                            if (AuctionHouseX.getInstance().getEconomyManager().getBalance(Bukkit.getOfflinePlayer(p.getUniqueId())) >= price) {
                                                itemLore.add("§aYou can purchase this item.");
                                            } else {
                                                itemLore.add("§6You can not purchase this item.");
                                            }
                                            itemLore.add(" ");
                                            itemLore.add("§9Price: §e$" + price);
                                            itemLore.add("§9Seller: §e" + seller);
                                            itemLore.add("§9Expire: §e" + time);
                                            itemLore.add(" ");
                                            itemLore.add("§8------------------------------");
                                            itemMeta.setLore(itemLore);
                                            item.setItemMeta(itemMeta);
                                            auctionhouseGUI.setItem(itemSlot, item);
                                        } else {
                                            //> Item expired
                                            itemSlot--;
                                        }
                                    } else {
                                        //> Item not filtered material
                                        itemSlot--;
                                    }
                                }

                                if (itemSlot <= 52) {
                                    itemSlot++;
                                }
                            }
                        }
                    }
                }
            } else {
                //> Display all items in auctionhouse
                if (sortOrder.equals("oldest")) {
                    int itemSlot = 0;
                    //> Sorting order (oldest)
                    Iterator filesIterator = listingsMap.entrySet().iterator();
                    List<Listing> sortedList = new ArrayList<>();

                    while (filesIterator.hasNext()) {
                        Map.Entry fileListings = (Map.Entry) filesIterator.next();
                        List<Integer> fileListingsContent = (List<Integer>) fileListings.getValue();
                        File categoryFile2 = new File("plugins/AuctionHouseX/Auctionhouse/" + fileListings.getKey());
                        FileConfiguration categoryFileCfg2 = YamlConfiguration.loadConfiguration(categoryFile2);

                        for (int listingId : fileListingsContent) {
                            long time = categoryFileCfg2.getLong(listingId + ".time");
                            Listing listing = new Listing(categoryFile2, listingId, time);
                            sortedList.add(listing);
                        }
                    }

                    SortListingByTime comparator = new SortListingByTime();
                    Collections.sort(sortedList, comparator);

                    for (int i = itemsOnPage; i < sortedList.size(); i++) {
                        File categoryFile2 = sortedList.get(i).getListingFile();
                        FileConfiguration categoryFileCfg2 = YamlConfiguration.loadConfiguration(categoryFile2);
                        int oldestListing = sortedList.get(i).getListingId();

                        if (auctionhouseGUI.getItem(itemSlot) == null) {
                            String seller = Bukkit.getOfflinePlayer(UUID.fromString(categoryFileCfg2.getString(oldestListing + ".seller"))).getName();
                            String time = AuctionHouseX.getInstance().getTimeHandler().convertListingTime(categoryFileCfg2.getLong(oldestListing + ".time"));
                            double price = categoryFileCfg2.getDouble(oldestListing + ".price");

                            if (!isExpired(categoryFile2, categoryFileCfg2, oldestListing)) {
                                ItemStack item = categoryFileCfg2.getItemStack(oldestListing + ".item");
                                ItemMeta itemMeta = item.getItemMeta();
                                List<String> itemLore = new ArrayList<>();
                                if (itemMeta.hasLore()) {
                                    itemLore = itemMeta.getLore();
                                }
                                itemLore.add("§8------------------------------");
                                if (AuctionHouseX.getInstance().getEconomyManager().getBalance(Bukkit.getOfflinePlayer(p.getUniqueId())) >= price) {
                                    itemLore.add("§aYou can purchase this item.");
                                } else {
                                    itemLore.add("§6You can not purchase this item.");
                                }
                                itemLore.add(" ");
                                itemLore.add("§9Price: §e$" + price);
                                itemLore.add("§9Seller: §e" + seller);
                                itemLore.add("§9Expire: §e" + time);
                                itemLore.add(" ");
                                itemLore.add("§8------------------------------");
                                itemMeta.setLore(itemLore);
                                item.setItemMeta(itemMeta);
                                auctionhouseGUI.setItem(itemSlot, item);
                            } else {
                                //> Item expired
                                itemSlot--;
                            }
                        }

                        if (itemSlot <= 52) {
                            itemSlot++;
                        }
                    }
                } else if (sortOrder.equals("newest")) {
                    int itemSlot = 0;
                    //> Sorting order (newest)
                    Iterator filesIterator = listingsMap.entrySet().iterator();
                    List<Listing> sortedList = new ArrayList<>();

                    while (filesIterator.hasNext()) {
                        Map.Entry fileListings = (Map.Entry) filesIterator.next();
                        List<Integer> fileListingsContent = (List<Integer>) fileListings.getValue();
                        File categoryFile2 = new File("plugins/AuctionHouseX/Auctionhouse/" + fileListings.getKey());
                        FileConfiguration categoryFileCfg2 = YamlConfiguration.loadConfiguration(categoryFile2);

                        for (int listingId : fileListingsContent) {
                            long time = categoryFileCfg2.getLong(listingId + ".time");
                            Listing listing = new Listing(categoryFile2, listingId, time);
                            sortedList.add(listing);
                        }
                    }

                    SortListingByTime comparator = new SortListingByTime();
                    Collections.sort(sortedList, comparator);
                    Collections.reverse(sortedList);

                    for (int i = itemsOnPage; i < sortedList.size(); i++) {
                        File categoryFile2 = sortedList.get(i).getListingFile();
                        FileConfiguration categoryFileCfg2 = YamlConfiguration.loadConfiguration(categoryFile2);
                        int newestListing = sortedList.get(i).getListingId();

                        if (auctionhouseGUI.getItem(itemSlot) == null) {
                            String seller = Bukkit.getOfflinePlayer(UUID.fromString(categoryFileCfg2.getString(newestListing + ".seller"))).getName();
                            String time = AuctionHouseX.getInstance().getTimeHandler().convertListingTime(categoryFileCfg2.getLong(newestListing + ".time"));
                            double price = categoryFileCfg2.getDouble(newestListing + ".price");

                            if (!isExpired(categoryFile2, categoryFileCfg2, newestListing)) {
                                ItemStack item = categoryFileCfg2.getItemStack(newestListing + ".item");
                                ItemMeta itemMeta = item.getItemMeta();
                                List<String> itemLore = new ArrayList<>();
                                if (itemMeta.hasLore()) {
                                    itemLore = itemMeta.getLore();
                                }
                                itemLore.add("§8------------------------------");
                                if (AuctionHouseX.getInstance().getEconomyManager().getBalance(Bukkit.getOfflinePlayer(p.getUniqueId())) >= price) {
                                    itemLore.add("§aYou can purchase this item.");
                                } else {
                                    itemLore.add("§6You can not purchase this item.");
                                }
                                itemLore.add(" ");
                                itemLore.add("§9Price: §e$" + price);
                                itemLore.add("§9Seller: §e" + seller);
                                itemLore.add("§9Expire: §e" + time);
                                itemLore.add(" ");
                                itemLore.add("§8------------------------------");
                                itemMeta.setLore(itemLore);
                                item.setItemMeta(itemMeta);
                                auctionhouseGUI.setItem(itemSlot, item);
                            } else {
                                //> Item expired
                                itemSlot--;
                            }
                        }

                        if (itemSlot <= 52) {
                            itemSlot++;
                        }
                    }
                } else {
                    int itemSlot = 0;
                    //> Sorting order (cheapest)
                    Iterator filesIterator = listingsMap.entrySet().iterator();
                    List<Listing> sortedList = new ArrayList<>();

                    while (filesIterator.hasNext()) {
                        Map.Entry fileListings = (Map.Entry) filesIterator.next();
                        List<Integer> fileListingsContent = (List<Integer>) fileListings.getValue();
                        File categoryFile2 = new File("plugins/AuctionHouseX/Auctionhouse/" + fileListings.getKey());
                        FileConfiguration categoryFileCfg2 = YamlConfiguration.loadConfiguration(categoryFile2);

                        for (int listingId : fileListingsContent) {
                            int price = categoryFileCfg2.getInt(listingId + ".price");
                            Listing listing = new Listing(categoryFile2, listingId, price);
                            sortedList.add(listing);
                        }
                    }

                    SortListingByPrice comparator = new SortListingByPrice();
                    Collections.sort(sortedList, comparator);

                    for (int i = itemsOnPage; i < sortedList.size(); i++) {
                        File categoryFile2 = sortedList.get(i).getListingFile();
                        FileConfiguration categoryFileCfg2 = YamlConfiguration.loadConfiguration(categoryFile2);
                        int cheapestListing = sortedList.get(i).getListingId();

                        if (auctionhouseGUI.getItem(itemSlot) == null) {
                            String seller = Bukkit.getOfflinePlayer(UUID.fromString(categoryFileCfg2.getString(cheapestListing + ".seller"))).getName();
                            String time = AuctionHouseX.getInstance().getTimeHandler().convertListingTime(categoryFileCfg2.getLong(cheapestListing + ".time"));
                            double price = categoryFileCfg2.getDouble(cheapestListing + ".price");

                            if (!isExpired(categoryFile2, categoryFileCfg2, cheapestListing)) {
                                ItemStack item = categoryFileCfg2.getItemStack(cheapestListing + ".item");
                                ItemMeta itemMeta = item.getItemMeta();
                                List<String> itemLore = new ArrayList<>();
                                if (itemMeta.hasLore()) {
                                    itemLore = itemMeta.getLore();
                                }
                                itemLore.add("§8------------------------------");
                                if (AuctionHouseX.getInstance().getEconomyManager().getBalance(Bukkit.getOfflinePlayer(p.getUniqueId())) >= price) {
                                    itemLore.add("§aYou can purchase this item.");
                                } else {
                                    itemLore.add("§6You can not purchase this item.");
                                }
                                itemLore.add(" ");
                                itemLore.add("§9Price: §e$" + price);
                                itemLore.add("§9Seller: §e" + seller);
                                itemLore.add("§9Expire: §e" + time);
                                itemLore.add(" ");
                                itemLore.add("§8------------------------------");
                                itemMeta.setLore(itemLore);
                                item.setItemMeta(itemMeta);
                                auctionhouseGUI.setItem(itemSlot, item);
                            } else {
                                //> Item expired
                                itemSlot--;
                            }
                        }

                        if (itemSlot <= 52) {
                            itemSlot++;
                        }
                    }
                }
            }
            //> Current Listings GUI
        } else if (p.getOpenInventory().getTitle().equals("§3Your Current Listings")) {
            Inventory currentListingsGUI = p.getOpenInventory().getTopInventory();
            String sortOrder = getCurrentSortingOrder(p);
            int itemsOnPage = getItemsOnPage(p, page);
            Map<String, List<Integer>> listingsMap = AuctionHouseX.getInstance().getFileManager().getPlayerListings(p);

            clearCurrentAuctionhouse(p);

            int itemSlot = 0;
            if (sortOrder.equals("oldest")) {
                Iterator filesIterator = listingsMap.entrySet().iterator();
                while (filesIterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) filesIterator.next();
                    List<Integer> listings = (List<Integer>) entry.getValue();
                    File categoryFile = new File("plugins/AuctionHouseX/Auctionhouse/" + entry.getKey());
                    FileConfiguration categoryFileCfg = YamlConfiguration.loadConfiguration(categoryFile);
                    for (int i = itemsOnPage; i < listings.size(); i++) {
                        if (categoryFileCfg.getString(listings.get(i) + ".seller") != null) {
                            if (currentListingsGUI.getItem(itemSlot) != null) {
                                if (itemSlot <= 52) {
                                    itemSlot++;
                                }
                            }

                            if (currentListingsGUI.getItem(itemSlot) == null) {
                                String time = AuctionHouseX.getInstance().getTimeHandler().convertListingTime(categoryFileCfg.getLong(listings.get(i) + ".time"));
                                double price = categoryFileCfg.getDouble(listings.get(i) + ".price");

                                if (!isExpired(categoryFile, categoryFileCfg, listings.get(i))) {
                                    ItemStack item = categoryFileCfg.getItemStack(listings.get(i) + ".item");
                                    ItemMeta itemMeta = item.getItemMeta();
                                    List<String> itemLore = new ArrayList<>();
                                    if (itemMeta.hasLore()) {
                                        itemLore = itemMeta.getLore();
                                    }
                                    itemLore.add("§8------------------------------");
                                    itemLore.add(" ");
                                    itemLore.add("§9Price: §e$" + price);
                                    itemLore.add("§9Expire: §e" + time);
                                    itemLore.add(" ");
                                    itemLore.add("§cShift-Right-Click to cancel.");
                                    itemLore.add("§8------------------------------");
                                    itemMeta.setLore(itemLore);
                                    item.setItemMeta(itemMeta);
                                    currentListingsGUI.setItem(itemSlot, item);
                                } else {
                                    //> Item expired
                                    itemSlot--;
                                }
                            }

                            if (itemSlot <= 52) {
                                itemSlot++;
                            }
                        }
                    }
                }
            } else if (sortOrder.equals("newest")) {
                Iterator filesIterator = listingsMap.entrySet().iterator();
                while (filesIterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) filesIterator.next();
                    List<Integer> listings = (List<Integer>) entry.getValue();
                    File categoryFile = new File("plugins/AuctionHouseX/Auctionhouse/" + entry.getKey());
                    FileConfiguration categoryFileCfg = YamlConfiguration.loadConfiguration(categoryFile);

                    for (int i = listings.size() - 1; i > itemsOnPage - 1; i--) {
                        if (categoryFileCfg.getString(listings.get(i) + ".seller") != null) {
                            if (currentListingsGUI.getItem(itemSlot) != null) {
                                if (itemSlot <= 52) {
                                    itemSlot++;
                                }
                            }

                            if (currentListingsGUI.getItem(itemSlot) == null) {
                                int listing = listings.get(i);
                                if (page > 1) {
                                    listing = ((listings.size() - 1) - itemsOnPage) - itemSlot;
                                }
                                String time = AuctionHouseX.getInstance().getTimeHandler().convertListingTime(categoryFileCfg.getLong(listing + ".time"));
                                double price = categoryFileCfg.getDouble(listing + ".price");

                                if (!isExpired(categoryFile, categoryFileCfg, listings.get(i))) {
                                    ItemStack item = categoryFileCfg.getItemStack(listing + ".item");
                                    ItemMeta itemMeta = item.getItemMeta();
                                    List<String> itemLore = new ArrayList<>();
                                    if (itemMeta.hasLore()) {
                                        itemLore = itemMeta.getLore();
                                    }
                                    itemLore.add("§8------------------------------");
                                    itemLore.add(" ");
                                    itemLore.add("§9Price: §e$" + price);
                                    itemLore.add("§9Expire: §e" + time);
                                    itemLore.add(" ");
                                    itemLore.add("§cShift-Right-Click to cancel.");
                                    itemLore.add("§8------------------------------");
                                    itemMeta.setLore(itemLore);
                                    item.setItemMeta(itemMeta);
                                    currentListingsGUI.setItem(itemSlot, item);
                                } else {
                                    //> Item expired
                                    itemSlot--;
                                }
                            }

                            if (itemSlot <= 52) {
                                itemSlot++;
                            }
                        }
                    }
                }
            } else if (sortOrder.equals("cheapest")) {
                Iterator filesIterator = listingsMap.entrySet().iterator();
                while (filesIterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) filesIterator.next();
                    List<Integer> listings = (List<Integer>) entry.getValue();
                    File categoryFile = new File("plugins/AuctionHouseX/Auctionhouse/" + entry.getKey());
                    FileConfiguration categoryFileCfg = YamlConfiguration.loadConfiguration(categoryFile);

                    //> Sort listings (cheapest)
                    Map<Integer, Double> listingPrices = new HashMap<>();
                    for (int listing : listings) {
                        double price = categoryFileCfg.getDouble(listing + ".price");
                        listingPrices.put(listing, price);
                    }

                    List<Map.Entry<Integer, Double>> list = new ArrayList<>(listingPrices.entrySet());
                    list.sort(Map.Entry.comparingByValue());

                    List<Integer> cheapestListings = new ArrayList<>();
                    for (Map.Entry<Integer, Double> entry2 : list) {
                        cheapestListings.add(entry2.getKey());
                    }

                    for (int i = itemsOnPage; i < cheapestListings.size(); i++) {
                        if (categoryFileCfg.getString(cheapestListings.get(i) + ".seller") != null) {
                            if (currentListingsGUI.getItem(itemSlot) != null) {
                                if (itemSlot <= 52) {
                                    itemSlot++;
                                }
                            }

                            if (currentListingsGUI.getItem(itemSlot) == null) {
                                int listing = cheapestListings.get(i);
                                if (page > 1) {
                                    listing = ((cheapestListings.size() - 1) - itemsOnPage) - itemSlot;
                                }
                                String time = AuctionHouseX.getInstance().getTimeHandler().convertListingTime(categoryFileCfg.getLong(listing + ".time"));
                                double price = categoryFileCfg.getDouble(listing + ".price");

                                if (!isExpired(categoryFile, categoryFileCfg, cheapestListings.get(i))) {
                                    ItemStack item = categoryFileCfg.getItemStack(listing + ".item");
                                    ItemMeta itemMeta = item.getItemMeta();
                                    List<String> itemLore = new ArrayList<>();
                                    if (itemMeta.hasLore()) {
                                        itemLore = itemMeta.getLore();
                                    }
                                    itemLore.add("§8------------------------------");
                                    itemLore.add(" ");
                                    itemLore.add("§9Price: §e$" + price);
                                    itemLore.add("§9Expire: §e" + time);
                                    itemLore.add(" ");
                                    itemLore.add("§cShift-Right-Click to cancel.");
                                    itemLore.add("§8------------------------------");
                                    itemMeta.setLore(itemLore);
                                    item.setItemMeta(itemMeta);
                                    currentListingsGUI.setItem(itemSlot, item);
                                } else {
                                    //> Item expired
                                    itemSlot--;
                                }
                            }

                            if (itemSlot <= 52) {
                                itemSlot++;
                            }
                        }
                    }
                }
            }
            //> Recently Sold Items GUI
        } else if (p.getOpenInventory().getTitle().equals("§2Recently Sold Items")) {
            Inventory recentlySoldGUI = p.getOpenInventory().getTopInventory();
            String sortOrder = getCurrentSortingOrder(p);
            int itemsOnPage = getItemsOnPage(p, page);
            File soldFile = new File("plugins/AuctionHouseX/Auctionhouse/Sold.yml");
            FileConfiguration soldFileCfg = YamlConfiguration.loadConfiguration(soldFile);
            List<Integer> listings = AuctionHouseX.getInstance().getFileManager().getPlayerSoldListings(p);

            clearCurrentAuctionhouse(p);

            int itemSlot = 0;
            if (sortOrder.equals("oldest")) {
                for (int i = itemsOnPage; i < listings.size(); i++) {
                    if (soldFileCfg.getString(listings.get(i) + ".seller") != null) {
                        if (recentlySoldGUI.getItem(itemSlot) != null) {
                            if (itemSlot <= 52) {
                                itemSlot++;
                            }
                        }

                        if (recentlySoldGUI.getItem(itemSlot) == null) {
                            String seller = Bukkit.getOfflinePlayer(UUID.fromString(soldFileCfg.getString(listings.get(i) + ".seller"))).getName();
                            String buyer = Bukkit.getOfflinePlayer(UUID.fromString(soldFileCfg.getString(listings.get(i) + ".buyer"))).getName();
                            String time = AuctionHouseX.getInstance().getTimeHandler().convertSoldTime(soldFileCfg.getLong(listings.get(i) + ".time"));
                            double price = soldFileCfg.getDouble(listings.get(i) + ".price");

                            ItemStack item = soldFileCfg.getItemStack(listings.get(i) + ".item");
                            ItemMeta itemMeta = item.getItemMeta();
                            List<String> itemLore = new ArrayList<>();
                            if (itemMeta.hasLore()) {
                                itemLore = itemMeta.getLore();
                            }
                            itemLore.add("§8------------------------------");
                            itemLore.add(" ");
                            itemLore.add("§9Seller: §e" + seller);
                            itemLore.add("§9Buyer: §e" + buyer);
                            itemLore.add("§9Price: §e$" + price);
                            itemLore.add("§9Sold: §e" + time);
                            itemLore.add(" ");
                            itemLore.add("§8------------------------------");
                            itemMeta.setLore(itemLore);
                            item.setItemMeta(itemMeta);
                            recentlySoldGUI.setItem(itemSlot, item);
                        }

                        if (itemSlot <= 52) {
                            itemSlot++;
                        }
                    }
                }
            } else if (sortOrder.equals("newest")) {
                for (int i = listings.size() - 1; i > itemsOnPage - 1; i--) {
                    if (soldFileCfg.getString(listings.get(i) + ".seller") != null) {
                        if (recentlySoldGUI.getItem(itemSlot) != null) {
                            if (itemSlot <= 52) {
                                itemSlot++;
                            }
                        }

                        if (recentlySoldGUI.getItem(itemSlot) == null) {
                            int listing = listings.get(i);
                            if (page > 1) {
                                listing = ((listings.size() - 1) - itemsOnPage) - itemSlot;
                            }
                            String seller = Bukkit.getOfflinePlayer(UUID.fromString(soldFileCfg.getString(listing + ".seller"))).getName();
                            String buyer = Bukkit.getOfflinePlayer(UUID.fromString(soldFileCfg.getString(listing + ".buyer"))).getName();
                            String time = AuctionHouseX.getInstance().getTimeHandler().convertSoldTime(soldFileCfg.getLong(listing + ".time"));
                            double price = soldFileCfg.getDouble(listing + ".price");

                            ItemStack item = soldFileCfg.getItemStack(listing + ".item");
                            ItemMeta itemMeta = item.getItemMeta();
                            List<String> itemLore = new ArrayList<>();
                            if (itemMeta.hasLore()) {
                                itemLore = itemMeta.getLore();
                            }
                            itemLore.add("§8------------------------------");
                            itemLore.add(" ");
                            itemLore.add("§9Seller: §e" + seller);
                            itemLore.add("§9Buyer: §e" + buyer);
                            itemLore.add("§9Price: §e$" + price);
                            itemLore.add("§9Sold: §e" + time);
                            itemLore.add(" ");
                            itemLore.add("§8------------------------------");
                            itemMeta.setLore(itemLore);
                            item.setItemMeta(itemMeta);
                            recentlySoldGUI.setItem(itemSlot, item);
                        }

                        if (itemSlot <= 52) {
                            itemSlot++;
                        }
                    }
                }
            } else if (sortOrder.equals("cheapest")) {
                //> Sort listings (cheapest)
                Map<Integer, Double> listingPrices = new HashMap<>();
                for (int listing : listings) {
                    double price = soldFileCfg.getDouble(listing + ".price");
                    listingPrices.put(listing, price);
                }

                List<Map.Entry<Integer, Double>> list = new ArrayList<>(listingPrices.entrySet());
                list.sort(Map.Entry.comparingByValue());

                List<Integer> cheapestListings = new ArrayList<>();
                for (Map.Entry<Integer, Double> entry : list) {
                    cheapestListings.add(entry.getKey());
                }

                for (int i = itemsOnPage; i < cheapestListings.size(); i++) {
                    if (soldFileCfg.getString(cheapestListings.get(i) + ".seller") != null) {
                        if (recentlySoldGUI.getItem(itemSlot) != null) {
                            if (itemSlot <= 52) {
                                itemSlot++;
                            }
                        }

                        if (recentlySoldGUI.getItem(itemSlot) == null) {
                            int listing = cheapestListings.get(i);
                            if (page > 1) {
                                listing = ((cheapestListings.size() - 1) - itemsOnPage) - itemSlot;
                            }
                            String seller = Bukkit.getOfflinePlayer(UUID.fromString(soldFileCfg.getString(listing + ".seller"))).getName();
                            String buyer = Bukkit.getOfflinePlayer(UUID.fromString(soldFileCfg.getString(listing + ".buyer"))).getName();
                            String time = AuctionHouseX.getInstance().getTimeHandler().convertSoldTime(soldFileCfg.getLong(listing + ".time"));
                            double price = soldFileCfg.getDouble(listing + ".price");

                            ItemStack item = soldFileCfg.getItemStack(listing + ".item");
                            ItemMeta itemMeta = item.getItemMeta();
                            List<String> itemLore = new ArrayList<>();
                            if (itemMeta.hasLore()) {
                                itemLore = itemMeta.getLore();
                            }
                            itemLore.add("§8------------------------------");
                            itemLore.add(" ");
                            itemLore.add("§9Seller: §e" + seller);
                            itemLore.add("§9Buyer: §e" + buyer);
                            itemLore.add("§9Price: §e$" + price);
                            itemLore.add("§9Sold: §e" + time);
                            itemLore.add(" ");
                            itemLore.add("§8------------------------------");
                            itemMeta.setLore(itemLore);
                            item.setItemMeta(itemMeta);
                            recentlySoldGUI.setItem(itemSlot, item);
                        }

                        if (itemSlot <= 52) {
                            itemSlot++;
                        }
                    }
                }
            }
            //> Cancelled/Expired Listings GUI
        } else if (p.getOpenInventory().getTitle().equals("§6Cancelled/Expired Listings")) {
            Inventory cancelledExpiredGUI = p.getOpenInventory().getTopInventory();
            String sortOrder = getCurrentSortingOrder(p);
            int itemsOnPage = getItemsOnPage(p, page);
            File expired = new File("plugins/AuctionHouseX/Auctionhouse/Expired.yml");
            FileConfiguration expiredCfg = YamlConfiguration.loadConfiguration(expired);
            List<Integer> listings = AuctionHouseX.getInstance().getFileManager().getPlayerExpiredListings(p);

            clearCurrentAuctionhouse(p);

            int itemSlot = 0;
            if (sortOrder.equals("oldest")) {
                for (int i = itemsOnPage; i < listings.size(); i++) {
                    if (expiredCfg.getString(listings.get(i) + ".seller") != null) {
                        if (cancelledExpiredGUI.getItem(itemSlot) != null) {
                            if (itemSlot <= 52) {
                                itemSlot++;
                            }
                        }

                        if (cancelledExpiredGUI.getItem(itemSlot) == null) {
                            String time = AuctionHouseX.getInstance().getTimeHandler().convertSoldTime(expiredCfg.getLong(listings.get(i) + ".time"));
                            double price = expiredCfg.getDouble(listings.get(i) + ".price");

                            ItemStack item = expiredCfg.getItemStack(listings.get(i) + ".item");
                            ItemMeta itemMeta = item.getItemMeta();
                            List<String> itemLore = new ArrayList<>();
                            if (itemMeta.hasLore()) {
                                itemLore = itemMeta.getLore();
                            }
                            itemLore.add("§8------------------------------");
                            itemLore.add(" ");
                            itemLore.add("§9Price: §e$" + price);
                            itemLore.add("§9Cancelled/Expired: §e" + time);
                            itemLore.add(" ");
                            itemLore.add("§8------------------------------");
                            itemMeta.setLore(itemLore);
                            item.setItemMeta(itemMeta);
                            cancelledExpiredGUI.setItem(itemSlot, item);
                        }

                        if (itemSlot <= 52) {
                            itemSlot++;
                        }
                    }
                }
            } else if (sortOrder.equals("newest")) {
                for (int i = listings.size() - 1; i > itemsOnPage - 1; i--) {
                    if (expiredCfg.getString(listings.get(i) + ".seller") != null) {
                        if (cancelledExpiredGUI.getItem(itemSlot) != null) {
                            if (itemSlot <= 52) {
                                itemSlot++;
                            }
                        }

                        if (cancelledExpiredGUI.getItem(itemSlot) == null) {
                            int listing = listings.get(i);
                            if (page > 1) {
                                listing = ((listings.size() - 1) - itemsOnPage) - itemSlot;
                            }
                            String time = AuctionHouseX.getInstance().getTimeHandler().convertSoldTime(expiredCfg.getLong(listing + ".time"));
                            double price = expiredCfg.getDouble(listing + ".price");

                            ItemStack item = expiredCfg.getItemStack(listing + ".item");
                            ItemMeta itemMeta = item.getItemMeta();
                            List<String> itemLore = new ArrayList<>();
                            if (itemMeta.hasLore()) {
                                itemLore = itemMeta.getLore();
                            }
                            itemLore.add("§8------------------------------");
                            itemLore.add(" ");
                            itemLore.add("§9Price: §e$" + price);
                            itemLore.add("§9Cancelled/Expired: §e" + time);
                            itemLore.add(" ");
                            itemLore.add("§8------------------------------");
                            itemMeta.setLore(itemLore);
                            item.setItemMeta(itemMeta);
                            cancelledExpiredGUI.setItem(itemSlot, item);
                        }

                        if (itemSlot <= 52) {
                            itemSlot++;
                        }
                    }
                }
            } else if (sortOrder.equals("cheapest")) {
                //> Sort listings (cheapest)
                Map<Integer, Double> listingPrices = new HashMap<>();
                for (int listing : listings) {
                    double price = expiredCfg.getDouble(listing + ".price");
                    listingPrices.put(listing, price);
                }

                List<Map.Entry<Integer, Double>> list = new ArrayList<>(listingPrices.entrySet());
                list.sort(Map.Entry.comparingByValue());

                List<Integer> cheapestListings = new ArrayList<>();
                for (Map.Entry<Integer, Double> entry : list) {
                    cheapestListings.add(entry.getKey());
                }

                for (int i = itemsOnPage; i < cheapestListings.size(); i++) {
                    if (expiredCfg.getString(cheapestListings.get(i) + ".seller") != null) {
                        if (cancelledExpiredGUI.getItem(itemSlot) != null) {
                            if (itemSlot <= 52) {
                                itemSlot++;
                            }
                        }

                        if (cancelledExpiredGUI.getItem(itemSlot) == null) {
                            int listing = cheapestListings.get(i);
                            if (page > 1) {
                                listing = ((cheapestListings.size() - 1) - itemsOnPage) - itemSlot;
                            }
                            String time = AuctionHouseX.getInstance().getTimeHandler().convertSoldTime(expiredCfg.getLong(listing + ".time"));
                            double price = expiredCfg.getDouble(listing + ".price");

                            ItemStack item = expiredCfg.getItemStack(listing + ".item");
                            ItemMeta itemMeta = item.getItemMeta();
                            List<String> itemLore = new ArrayList<>();
                            if (itemMeta.hasLore()) {
                                itemLore = itemMeta.getLore();
                            }
                            itemLore.add("§8------------------------------");
                            itemLore.add(" ");
                            itemLore.add("§9Price: §e$" + price);
                            itemLore.add("§9Cancelled/Expired: §e" + time);
                            itemLore.add(" ");
                            itemLore.add("§8------------------------------");
                            itemMeta.setLore(itemLore);
                            item.setItemMeta(itemMeta);
                            cancelledExpiredGUI.setItem(itemSlot, item);
                        }

                        if (itemSlot <= 52) {
                            itemSlot++;
                        }
                    }
                }
            }
        }
    }

    public void sellItem(Player p, ItemStack item, double price, double listingPrice) {
        //> Check if item is blacklisted
        if (!isBlacklisted(item)) {
            String category = getItemCategory(item);

            if (category != null) {
                File categoryFile = new File("plugins/AuctionHouseX/Auctionhouse/" + category + ".yml");
                FileConfiguration categoryFileCfg = YamlConfiguration.loadConfiguration(categoryFile);
                int currentPlayerListings = AuctionHouseX.getInstance().getFileManager().getCurrentPlayerListings(p);
                int defaultMaxListings = AuctionHouseX.getInstance().getConfig().getInt("auction.defaultMaxListings");
                int nextListingId = AuctionHouseX.getInstance().getFileManager().getNextListingId(category);

                if (currentPlayerListings < defaultMaxListings) {
                    //> Pay listing price
                    if (listingPrice > 0) {
                        AuctionHouseX.getInstance().getEconomyManager().withdrawPlayer(Bukkit.getOfflinePlayer(p.getUniqueId()), listingPrice);
                        p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§fA fee of §c$" + listingPrice + " §fwas charged");
                    }

                    //> Announce selling
                    String itemName = item.getItemMeta().getDisplayName();
                    if (itemName == "") {
                        itemName = item.getType().name().replaceAll("_", " ");
                    }

                    if (AuctionHouseX.getInstance().getConfig().getBoolean("auction.announce")) {
                        Bukkit.broadcastMessage(AuctionHouseX.getInstance().getMessage().prefix + "§6" + p.getName() + " has listed §e" + item.getAmount() + "x §f" + itemName + " §6for §b$" + price + " §6on the auction house!");
                    } else {
                        p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§6You have listed §e" + item.getAmount() + "x §f" + itemName + " §6for §b$" + price + " §6on the auction house!");
                    }

                    //> Add item to categoryFile
                    categoryFileCfg.set(nextListingId + ".seller", p.getUniqueId().toString());
                    categoryFileCfg.set(nextListingId + ".item", item);
                    categoryFileCfg.set(nextListingId + ".price", price);
                    categoryFileCfg.set(nextListingId + ".time", System.currentTimeMillis());
                    try {
                        categoryFileCfg.save(categoryFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    p.getInventory().getItemInMainHand().setAmount(0);

                    //> Refresh all open Auctionhouses
                    refreshOpenAuctionhouses();
                } else {
                    p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§cYou can't sell more than " + defaultMaxListings + " items at once!");
                }
            } else {
                p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§cThe item you try to sell is not categorized!");
            }
        } else {
            p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§cThe item you try to sell is blacklisted!");
        }
    }

    public void cancelListing(File file, FileConfiguration fileCfg, int id) {
        //> Expired
        File expired = new File("plugins/AuctionHouseX/Auctionhouse/Expired.yml");
        FileConfiguration expiredCfg = YamlConfiguration.loadConfiguration(expired);
        int nextExpiredId = AuctionHouseX.getInstance().getFileManager().getNextListingId("Expired");

        expiredCfg.set(nextExpiredId + ".seller", fileCfg.getString(id + ".seller"));
        expiredCfg.set(nextExpiredId + ".item", fileCfg.getItemStack(id + ".item"));
        expiredCfg.set(nextExpiredId + ".price", fileCfg.getDouble(id + ".price"));
        expiredCfg.set(nextExpiredId + ".time", System.currentTimeMillis());
        try {
            expiredCfg.save(expired);
        } catch (IOException e) {
            e.printStackTrace();
        }

        fileCfg.set(id + "", null);
        try {
            fileCfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //> Refresh all open Auctionhouses
        refreshOpenAuctionhouses();
    }

    public void buyListing(Player p, File file, FileConfiguration fileCfg, int id) {
        //> Sold
        File sold = new File("plugins/AuctionHouseX/Auctionhouse/Sold.yml");
        FileConfiguration soldCfg = YamlConfiguration.loadConfiguration(sold);
        int nextSoldId = AuctionHouseX.getInstance().getFileManager().getNextListingId("Sold");

        soldCfg.set(nextSoldId + ".seller", fileCfg.getString(id + ".seller"));
        soldCfg.set(nextSoldId + ".buyer", p.getUniqueId().toString());
        soldCfg.set(nextSoldId + ".item", fileCfg.getItemStack(id + ".item"));
        soldCfg.set(nextSoldId + ".price", fileCfg.getDouble(id + ".price"));
        soldCfg.set(nextSoldId + ".time", System.currentTimeMillis());
        try {
            soldCfg.save(sold);
        } catch (IOException e) {
            e.printStackTrace();
        }

        fileCfg.set(id + "", null);
        try {
            fileCfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //> Refresh all open Auctionhouses
        refreshOpenAuctionhouses();
    }
}