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
import java.util.*;

public class CurrentListingsGUI implements Listener {

    public void open(Player p, String sortOrder, int page) {
        Inventory currentListingsGUI = Bukkit.createInventory(null, 54, "§bYour Current Listings");

        //> Items
        ItemStack back = AuctionHouseX.getInstance().getItemCreator().create(Material.IRON_DOOR, "§9Back", new ArrayList<>(), false);
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
        ItemStack recentlySold = AuctionHouseX.getInstance().getItemCreator().create(Material.EMERALD, "§6Recently Sold Items", new ArrayList<>(), false);
        ItemStack expiredCancelled = AuctionHouseX.getInstance().getItemCreator().create(Material.POISONOUS_POTATO, "§6Expired/Cancelled Listings", new ArrayList<>(), false);
        ItemStack placeholder = AuctionHouseX.getInstance().getItemCreator().create(Material.YELLOW_STAINED_GLASS_PANE, " ", new ArrayList<>(), false);

        //> Set items
        currentListingsGUI.setItem(7, placeholder);
        currentListingsGUI.setItem(8, nextPage);
        currentListingsGUI.setItem(16, placeholder);
        currentListingsGUI.setItem(17, previousPage);
        currentListingsGUI.setItem(25, placeholder);
        currentListingsGUI.setItem(26, sorting);
        currentListingsGUI.setItem(34, placeholder);
        currentListingsGUI.setItem(35, recentlySold);
        currentListingsGUI.setItem(43, placeholder);
        currentListingsGUI.setItem(44, expiredCancelled);
        currentListingsGUI.setItem(52, placeholder);
        currentListingsGUI.setItem(53, back);

        AuctionHouseX.getInstance().getAuctionhouseManager().getCurrentPage().remove(p.getUniqueId());
        AuctionHouseX.getInstance().getAuctionhouseManager().getCurrentPage().put(p.getUniqueId(), page);

        //> Open GUI
        if (!p.getOpenInventory().getTitle().equals("§bYour Current Listings")) {
            p.openInventory(currentListingsGUI);
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
            if (e.getView().getTitle().equals("§bYour Current Listings")) {
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
                    p.closeInventory();
                    AuctionHouseX.getInstance().getRecentlySoldGUI().open(p, currentSortingOrder, currentPage);
                } else if (e.getSlot() == 44) {
                    p.closeInventory();
                    AuctionHouseX.getInstance().getCancelledExpiredGUI().open(p, currentSortingOrder, currentPage);
                } else if (e.getSlot() == 53) {
                    //> Back
                    p.closeInventory();
                    AuctionHouseX.getInstance().getAuctionhouseGUI().open(p, "ALL", 1, currentSortingOrder);
                } else {
                    if (e.isShiftClick() && e.isRightClick()) {
                        //> Cancel Listing
                        int clickedSlot = e.getSlot();

                        int itemsOnPage = AuctionHouseX.getInstance().getAuctionhouseManager().getItemsOnPage(p, currentPage);
                        Inventory currentListingsGUI = p.getOpenInventory().getTopInventory();
                        Map<String, List<Integer>> listingsMap = AuctionHouseX.getInstance().getFileManager().getPlayerListings(p);

                        AuctionHouseX.getInstance().getAuctionhouseManager().clearCurrentAuctionhouse(p);

                        int itemSlot = 0;
                        if (currentSortingOrder.equals("oldest")) {
                            boolean cancelled = false;
                            Iterator filesIterator = listingsMap.entrySet().iterator();
                            while (filesIterator.hasNext()) {
                                if (!cancelled) {
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
                                                if (itemSlot == clickedSlot) {
                                                    //> Cancel item
                                                    AuctionHouseX.getInstance().getAuctionhouseManager().cancelListing(categoryFile, categoryFileCfg, listings.get(i));
                                                    itemSlot--;
                                                    cancelled = true;
                                                    break;
                                                }
                                            }

                                            if (itemSlot <= 52) {
                                                itemSlot++;
                                            }
                                        }
                                    }
                                } else {
                                    AuctionHouseX.getInstance().getAuctionhouseManager().refreshAuctionhouse(p, "", currentPage);
                                    break;
                                }
                            }
                        } else if (currentSortingOrder.equals("newest")) {
                            boolean cancelled = false;
                            Iterator filesIterator = listingsMap.entrySet().iterator();
                            while (filesIterator.hasNext()) {
                                if (!cancelled) {
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
                                                if (itemSlot == clickedSlot) {
                                                    //> Cancel item
                                                    AuctionHouseX.getInstance().getAuctionhouseManager().cancelListing(categoryFile, categoryFileCfg, listings.get(i));
                                                    itemSlot--;
                                                    cancelled = true;
                                                    break;
                                                }
                                            }

                                            if (itemSlot <= 52) {
                                                itemSlot++;
                                            }
                                        }
                                    }
                                } else {
                                    AuctionHouseX.getInstance().getAuctionhouseManager().refreshAuctionhouse(p, "", currentPage);
                                    break;
                                }
                            }
                        } else {
                            boolean cancelled = false;
                            Iterator filesIterator = listingsMap.entrySet().iterator();
                            while (filesIterator.hasNext()) {
                                if (!cancelled) {
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
                                                if (itemSlot == clickedSlot) {
                                                    //> Cancel item
                                                    AuctionHouseX.getInstance().getAuctionhouseManager().cancelListing(categoryFile, categoryFileCfg, cheapestListings.get(i));
                                                    itemSlot--;
                                                    cancelled = true;
                                                    break;
                                                }
                                            }

                                            if (itemSlot <= 52) {
                                                itemSlot++;
                                            }
                                        }
                                    }
                                } else {
                                    AuctionHouseX.getInstance().getAuctionhouseManager().refreshAuctionhouse(p, "", currentPage);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } catch (NullPointerException ex) {
        }
    }
}
