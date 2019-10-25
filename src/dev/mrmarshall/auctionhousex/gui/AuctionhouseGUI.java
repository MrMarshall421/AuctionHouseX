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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AuctionhouseGUI implements Listener {

	public void open(Player p, String category, int page, String sortOrder) {
		Inventory auctionhouseGUI = Bukkit.createInventory(null, 54, "§bAuctionhouse§6§lX");

		//> Items
		ItemStack nextPage = AuctionHouseX.getInstance().getItemCreator().create(Material.TIPPED_ARROW, "§aNext Page", new ArrayList<>(), true);
		PotionMeta nextPageMeta = (PotionMeta) nextPage.getItemMeta();
		nextPageMeta.setBasePotionData(new PotionData(PotionType.JUMP));
		nextPageMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		nextPage.setItemMeta(nextPageMeta);
		ItemStack previousPage = AuctionHouseX.getInstance().getItemCreator().create(Material.TIPPED_ARROW, "§cPrevious Page", new ArrayList<>(), true);
		PotionMeta previousPageMeta = (PotionMeta) previousPage.getItemMeta();
		previousPageMeta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
		previousPageMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		previousPage.setItemMeta(previousPageMeta);
		List<String> sortingLore = new ArrayList<>();
		ItemStack sorting;
		if (sortOrder.equals("oldest")) {
			sortingLore.add("§9Order: §eOldest First");
			sorting = AuctionHouseX.getInstance().getItemCreator().create(Material.ENDER_EYE, "§6Sort Listings", sortingLore, true);
		} else {
			sortingLore.add("§9Order: §eNewest First");
			sorting = AuctionHouseX.getInstance().getItemCreator().create(Material.ENDER_PEARL, "§6Sort Listings", sortingLore, true);
		}
		ItemStack instruction = AuctionHouseX.getInstance().getAuctionhouseManager().getInstructionBook();
		List<String> sellingLore = new ArrayList<>();
		if (AuctionHouseX.getInstance().getFileManager().getCurrentPlayerListings(p) == 0) {
			sellingLore.add("§cYou have not listed any items.");
		}
		ItemStack selling = AuctionHouseX.getInstance().getItemCreator().create(Material.DIAMOND, "§6Items You're Selling", new ArrayList<>(), true);
		ItemStack placeholder = AuctionHouseX.getInstance().getItemCreator().create(Material.BLUE_STAINED_GLASS_PANE, " ", new ArrayList<>(), true);
		ItemStack brewingCategory = AuctionHouseX.getInstance().getItemCreator().create(Material.DRAGON_BREATH, "§dBrewing", new ArrayList<>(), true);
		ItemStack combatCategory = AuctionHouseX.getInstance().getItemCreator().create(Material.DIAMOND_SWORD, "§cCombat", new ArrayList<>(), true);
		ItemMeta combatCategoryMeta = combatCategory.getItemMeta();
		combatCategoryMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		combatCategory.setItemMeta(combatCategoryMeta);
		ItemStack toolsCategory = AuctionHouseX.getInstance().getItemCreator().create(Material.GOLDEN_PICKAXE, "§bTools", new ArrayList<>(), true);
		ItemMeta toolsCategoryMeta = toolsCategory.getItemMeta();
		toolsCategoryMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		toolsCategory.setItemMeta(toolsCategoryMeta);
		ItemStack foodCategory = AuctionHouseX.getInstance().getItemCreator().create(Material.BREAD, "§eFood", new ArrayList<>(), true);
		ItemStack productiveCategory = AuctionHouseX.getInstance().getItemCreator().create(Material.IRON_INGOT, "§9Productive", new ArrayList<>(), true);
		ItemStack rtCategory = AuctionHouseX.getInstance().getItemCreator().create(Material.REDSTONE, "§4Redstone & Transportation", new ArrayList<>(), true);
		ItemStack decorationCategory = AuctionHouseX.getInstance().getItemCreator().create(Material.LANTERN, "§aDecoration", new ArrayList<>(), true);
		ItemStack blocksCategory = AuctionHouseX.getInstance().getItemCreator().create(Material.BRICKS, "§3Blocks", new ArrayList<>(), true);

		//> Set items
		auctionhouseGUI.setItem(8, nextPage);
		auctionhouseGUI.setItem(17, previousPage);
		auctionhouseGUI.setItem(26, sorting);
		auctionhouseGUI.setItem(35, instruction);
		auctionhouseGUI.setItem(44, selling);
		auctionhouseGUI.setItem(45, blocksCategory);
		auctionhouseGUI.setItem(46, decorationCategory);
		auctionhouseGUI.setItem(47, rtCategory);
		auctionhouseGUI.setItem(48, productiveCategory);
		auctionhouseGUI.setItem(49, foodCategory);
		auctionhouseGUI.setItem(50, toolsCategory);
		auctionhouseGUI.setItem(51, combatCategory);
		auctionhouseGUI.setItem(52, brewingCategory);
		auctionhouseGUI.setItem(53, placeholder);

		AuctionHouseX.getInstance().getAuctionhouseManager().getCurrentPage().remove(p.getUniqueId());
		AuctionHouseX.getInstance().getAuctionhouseManager().getCurrentPage().put(p.getUniqueId(), page);

		//> Open GUI
		if (!p.getOpenInventory().getTitle().equals("§bAuctionhouse§6§lX")) {
			p.openInventory(auctionhouseGUI);
		} else {
			//> Change sorting items
			p.getOpenInventory().getTopInventory().setItem(26, sorting);
		}

		AuctionHouseX.getInstance().getAuctionhouseManager().refreshAuctionhouse(p, category, page);
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();

		if (e.getView().getTitle().equals("§bAuctionhouse§6§lX")) {
			e.setCancelled(true);

			String currentCategory = AuctionHouseX.getInstance().getAuctionhouseManager().getCurrentCategory(e.getInventory());
			String currentSortingOrder = AuctionHouseX.getInstance().getAuctionhouseManager().getCurrentSortingOrder(p);
			int currentPage = AuctionHouseX.getInstance().getAuctionhouseManager().getCurrentPage().get(p.getUniqueId());
			if (e.getSlot() == 8) {
				//> Next Page
				if (currentPage <= 9) {
					open(p, currentCategory, currentPage + 1, currentSortingOrder);
				}
			} else if (e.getSlot() == 17) {
				//> Previous Page
				if (currentPage >= 2) {
					open(p, currentCategory, currentPage - 1, currentSortingOrder);
				}
			} else if (e.getSlot() == 26) {
				//> Change Sorting Order
				if (currentSortingOrder.equals("oldest")) {
					//> Change to newest
					open(p, currentCategory, currentPage, "newest");
				} else {
					//> Change to oldest
					open(p, currentCategory, currentPage, "oldest");
				}
			} else if (e.getSlot() == 45) {
				//> Change Category to "Blocks"
				open(p, "Blocks", currentPage, currentSortingOrder);
			} else if (e.getSlot() == 46) {
				//> Change Category to "Decoration"
				open(p, "Decoration", currentPage, currentSortingOrder);
			} else if (e.getSlot() == 47) {
				//> Change Category to "Redstone & Transportation"
				open(p, "Redstone+Transportation", currentPage, currentSortingOrder);
			} else if (e.getSlot() == 48) {
				//> Change Category to "Productive"
				open(p, "Productive", currentPage, currentSortingOrder);
			} else if (e.getSlot() == 49) {
				//> Change Category to "Food"
				open(p, "Food", currentPage, currentSortingOrder);
			} else if (e.getSlot() == 50) {
				//> Change Category to "Tools"
				open(p, "Tools", currentPage, currentSortingOrder);
			} else if (e.getSlot() == 51) {
				//> Change Category to "Combat"
				open(p, "Combat", currentPage, currentSortingOrder);
			} else if (e.getSlot() == 52) {
				//> Change Category to "Brewing"
				open(p, "Brewing", currentPage, currentSortingOrder);
			} else if (e.getSlot() == 44) {
				//> Open current listings gui
				p.closeInventory();
				AuctionHouseX.getInstance().getCurrentListingsGUI().open(p, currentSortingOrder, 1);
			} else if (e.getSlot() != 53) {
				//> Player buys item
				int clickedSlot = e.getSlot();

				Inventory auctionhouseGUI = p.getOpenInventory().getTopInventory();
				int itemsOnPage = AuctionHouseX.getInstance().getAuctionhouseManager().getItemsOnPage(p, AuctionHouseX.getInstance().getAuctionhouseManager().getCurrentPage().get(p.getUniqueId()));
				File categoryFile = new File("plugins/AuctionHouseX/Auctionhouse/" + currentCategory + ".yml");
				FileConfiguration categoryFileCfg = YamlConfiguration.loadConfiguration(categoryFile);
				List<Integer> listings = AuctionHouseX.getInstance().getFileManager().getListingsFromFile(categoryFileCfg);

				AuctionHouseX.getInstance().getAuctionhouseManager().clearCurrentAuctionhouse(p);

				int itemSlot = 0;
				if (currentSortingOrder.equals("oldest")) {
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

								boolean itemBought = false;
								if (!AuctionHouseX.getInstance().getAuctionhouseManager().isExpired(categoryFile, categoryFileCfg, listings.get(i))) {
									if (itemSlot == clickedSlot) {
										//> Buy Item
										if (seller != p.getName()) {
											if (AuctionHouseX.getInstance().getEconomyManager().getBalance(Bukkit.getOfflinePlayer(p.getUniqueId())) >= price) {
												// TODO: SALES TAXES
												AuctionHouseX.getInstance().getEconomyManager().withdrawPlayer(Bukkit.getOfflinePlayer(p.getUniqueId()), price);
												p.closeInventory();
												p.getInventory().addItem(categoryFileCfg.getItemStack(listings.get(i) + ".item"));
												p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§aSuccessfully bought an item from " + seller + "!");
												AuctionHouseX.getInstance().getEconomyManager().depositPlayer(Bukkit.getOfflinePlayer(UUID.fromString(categoryFileCfg.getString(listings.get(i) + ".seller"))), price);
												itemBought = true;
											} else {
												p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§cYou don't have enough money!");
											}
										} else {
											p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§cYou can't buy your own items!");
										}
									}

									if (!itemBought) {
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
										//> Remove bought item
										AuctionHouseX.getInstance().getAuctionhouseManager().buyListing(p, categoryFile, categoryFileCfg, listings.get(i));
										itemSlot--;
									}

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
					for (int i = listings.size() - 1; i > itemsOnPage - 1; i--) {
						if (categoryFileCfg.getString(listings.get(i) + ".seller") != null) {
							if (auctionhouseGUI.getItem(itemSlot) != null) {
								if (itemSlot <= 52) {
									itemSlot++;
								}
							}

							if (auctionhouseGUI.getItem(itemSlot) == null) {
								int listing = listings.get(i);
								if (currentPage > 1) {
									listing = ((listings.size() - 1) - itemsOnPage) - itemSlot;
								}
								String seller = Bukkit.getOfflinePlayer(UUID.fromString(categoryFileCfg.getString(listing + ".seller"))).getName();
								String time = AuctionHouseX.getInstance().getTimeHandler().convertListingTime(categoryFileCfg.getLong(listing + ".time"));
								double price = categoryFileCfg.getDouble(listing + ".price");

								boolean itemBought = false;
								if (!AuctionHouseX.getInstance().getAuctionhouseManager().isExpired(categoryFile, categoryFileCfg, listings.get(i))) {
									if (itemSlot == clickedSlot) {
										//> Buy Item
										if (seller != p.getName()) {
											if (AuctionHouseX.getInstance().getEconomyManager().getBalance(Bukkit.getOfflinePlayer(p.getUniqueId())) >= price) {
												// TODO: SALES TAXES
												AuctionHouseX.getInstance().getEconomyManager().withdrawPlayer(Bukkit.getOfflinePlayer(p.getUniqueId()), price);
												p.closeInventory();
												p.getInventory().addItem(categoryFileCfg.getItemStack(listings.get(i) + ".item"));
												p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§aSuccessfully bought an item from " + seller + "!");
												AuctionHouseX.getInstance().getEconomyManager().depositPlayer(Bukkit.getOfflinePlayer(UUID.fromString(categoryFileCfg.getString(listings.get(i) + ".seller"))), price);
												itemBought = true;
											} else {
												p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§cYou don't have enough money!");
											}
										} else {
											p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§cYou can't buy your own items!");
										}
									}

									if (!itemBought) {
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
										//> Remove bought item
										AuctionHouseX.getInstance().getAuctionhouseManager().buyListing(p, categoryFile, categoryFileCfg, listings.get(i));
										itemSlot--;
									}
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
		}
	}
}
