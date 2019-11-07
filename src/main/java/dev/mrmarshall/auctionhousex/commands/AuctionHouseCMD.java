package dev.mrmarshall.auctionhousex.commands;

import dev.mrmarshall.auctionhousex.AuctionHouseX;
import dev.mrmarshall.auctionhousex.gui.ListingPriceConfirmationGUI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AuctionHouseCMD implements CommandExecutor {

    ListingPriceConfirmationGUI listingPriceConfirmationGUI;

    public AuctionHouseCMD() {
        listingPriceConfirmationGUI = new ListingPriceConfirmationGUI();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            //> Prevent Spectator & Creative from using /ah
            if (AuctionHouseX.getInstance().getConfig().getBoolean("auction.preventCreative") && p.getGameMode() == GameMode.CREATIVE) {
                return false;
            }
            if (AuctionHouseX.getInstance().getConfig().getBoolean("auction.preventSpectator") && p.getGameMode() == GameMode.SPECTATOR) {
                return false;
            }

            //> Prevent using Commands in disabled worlds
            List<String> disabledWorlds = AuctionHouseX.getInstance().getConfig().getStringList("disabled-worlds");
            for (String world : disabledWorlds) {
                if (p.getWorld().getName() == world) {
                    return false;
                }
            }

            if (args.length == 0) {
                //> Open Auctionhouse
                if (p.hasPermission("auctionhouse.access")) {
                    AuctionHouseX.getInstance().getAuctionhouseGUI().open(p, "Blocks", 1, "newest");
                } else {
                    p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + AuctionHouseX.getInstance().getMessage().noPermission);
                }
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    //> Reload Command
                    if (p.hasPermission("auctionhouse.reload")) {
                        AuctionHouseX.getInstance().getPluginLoader().disablePlugin(AuctionHouseX.getInstance());
                        AuctionHouseX.getInstance().getPluginLoader().enablePlugin(AuctionHouseX.getInstance());
                        AuctionHouseX.getInstance().reloadConfig();

                        p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§aPlugin reloaded successfully.");
                    } else {
                        p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + AuctionHouseX.getInstance().getMessage().noPermission);
                    }
                } else if (args[0].equalsIgnoreCase("help")) {
                    //> Help Command
                    if (p.hasPermission("auctionhouse.access")) {
                        sendHelp(p, 1);
                    } else {
                        p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + AuctionHouseX.getInstance().getMessage().noPermission);
                    }
                } else if (args[0].equalsIgnoreCase("selling")) {
                    //> Show the items player is selling
                    if (p.hasPermission("auctionhouse.sell")) {
                        AuctionHouseX.getInstance().getCurrentListingsGUI().open(p, "newest", 1);
                    } else {
                        p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + AuctionHouseX.getInstance().getMessage().noPermission);
                    }
                } else if (args[0].equalsIgnoreCase("sold")) {
                    //> Show the items player has sold
                    if (p.hasPermission("auctionhouse.sell")) {
                        AuctionHouseX.getInstance().getRecentlySoldGUI().open(p, "newest", 1);
                    } else {
                        p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + AuctionHouseX.getInstance().getMessage().noPermission);
                    }
                } else if (args[0].equalsIgnoreCase("expired")) {
                    //> Show the expired items of a player
                    if (p.hasPermission("auctionhouse.sell")) {
                        AuctionHouseX.getInstance().getCancelledExpiredGUI().open(p, "newest", 1);
                    } else {
                        p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + AuctionHouseX.getInstance().getMessage().noPermission);
                    }
                } else if (args[0].equalsIgnoreCase("cancel")) {
                    //> Cancels all auctions of a player
                    if (p.hasPermission("auctionhouse.cancel")) {
                        Map<String, List<Integer>> listingsMap = AuctionHouseX.getInstance().getFileManager().getPlayerListings(p);
                        Iterator filesIterator = listingsMap.entrySet().iterator();

                        while (filesIterator.hasNext()) {
                            Map.Entry entry = (Map.Entry) filesIterator.next();
                            List<Integer> listings = (List<Integer>) entry.getValue();
                            File categoryFile = new File("plugins/AuctionHouseX/Auctionhouse/" + entry.getKey());
                            FileConfiguration categoryFileCfg = YamlConfiguration.loadConfiguration(categoryFile);
                            for (int i = 0; i < listings.size(); i++) {
                                if (categoryFileCfg.getString(listings.get(i) + ".seller") != null) {
                                    //> Cancel item
                                    AuctionHouseX.getInstance().getAuctionhouseManager().cancelListing(categoryFile, categoryFileCfg, listings.get(i));
                                }
                            }
                        }

                        p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§aSuccessfully cancelled all listings!");
                    } else {
                        p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + AuctionHouseX.getInstance().getMessage().noPermission);
                    }
                } else if (args[0].equalsIgnoreCase("return")) {
                    //> Returns all expired/cancelled items to Auctionhouse
                    if (p.hasPermission("auctionhouse.return")) {
                        AuctionHouseX.getInstance().getCancelledExpiredGUI().returnCancelledExpired(p, 0);
                    } else {
                        p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + AuctionHouseX.getInstance().getMessage().noPermission);
                    }
                } else if (args[0].equalsIgnoreCase("search")) {
                    //> Show /ah search usage
                    if (p.hasPermission("auctionhouse.access")) {
                        p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§cPlease use /ah search <item>");
                    } else {
                        p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + AuctionHouseX.getInstance().getMessage().noPermission);
                    }
                } else {
                    //> Show Help
                    if (p.hasPermission("auctionhouse.access")) {
                        sendHelp(p, 1);
                    } else {
                        p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + AuctionHouseX.getInstance().getMessage().noPermission);
                    }
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("help") && args[1].equals("2")) {
                    if (p.hasPermission("auctionhouse.access")) {
                        sendHelp(p, 2);
                    } else {
                        p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + AuctionHouseX.getInstance().getMessage().noPermission);
                    }
                } else if (args[0].equalsIgnoreCase("sell")) {
                    if (p.hasPermission("auctionhouse.sell")) {
                        //> Sells item in hand
                        try {
                            double price = Float.valueOf(args[1]);
                            double listingPriceFinal = 0.0;
                            double listingPrice = AuctionHouseX.getInstance().getConfig().getDouble("auction.listingPrice");
                            double listingRate = AuctionHouseX.getInstance().getConfig().getDouble("auction.listingRate");

                            boolean damaged = false;
                            if (p.getInventory().getItemInMainHand().getItemMeta() instanceof Damageable) {
                                Damageable damageable = (Damageable) p.getInventory().getItemInMainHand();

                                if (damageable.getAbsorptionAmount() > 0) {
                                    if (!AuctionHouseX.getInstance().getConfig().getBoolean("auction.allowDamagedItems")) {
                                        damaged = true;
                                    }
                                }
                            }

                            if (!damaged) {
                                if (price <= AuctionHouseX.getInstance().getConfig().getDouble("auction.maxSellPrice")) {
                                    if (AuctionHouseX.getInstance().getConfig().getDouble("auction.listingPrice") != 0 || AuctionHouseX.getInstance().getConfig().getDouble("auction.listingRate") != 0) {
                                        //> Open Confirmation GUI for listing price
                                        if (listingPrice != 0.0) {
                                            listingPriceFinal += listingPrice;
                                        }
                                        if (listingRate != 0.0) {
                                            listingPriceFinal = (price / 100 * listingRate) + listingPriceFinal;
                                        }

                                        if (AuctionHouseX.getInstance().getEconomyManager().getBalance(Bukkit.getOfflinePlayer(p.getUniqueId())) >= listingPriceFinal) {
                                            AuctionHouseX.getInstance().getAuctionhouseManager().getSelling().put(p.getUniqueId(), price);
                                            listingPriceConfirmationGUI.open(p, p.getInventory().getItemInMainHand(), listingPriceFinal);
                                        } else {
                                            p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§cYou have not enough money to pay the listing fee!");
                                        }
                                    } else {
                                        //> Sell item & skip Confirmation GUI
                                        AuctionHouseX.getInstance().getAuctionhouseManager().sellItem(p, p.getInventory().getItemInMainHand(), price, 0);
                                    }
                                } else {
                                    p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§cYou can't sell for more than " + AuctionHouseX.getInstance().getConfig().getDouble("auction.maxSellPrice") + "$");
                                }
                            } else {
                                p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§cYou can't sell damaged items!");
                            }
                        } catch (NumberFormatException ex) {
                            p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§cThe Price must be a number!");
                        }
                    } else {
                        p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + AuctionHouseX.getInstance().getMessage().noPermission);
                    }
                } else if (args[0].equalsIgnoreCase("search")) {
                    if (p.hasPermission("auctionhouse.access")) {
                        //> Search for item with material filter
                        Material material = Material.getMaterial(args[1].toUpperCase());

                        if (material != null) {
                            //> Open filtered Auctionhouse
                            AuctionHouseX.getInstance().getAuctionhouseGUI().open(p, args[1].toUpperCase(), 1, "newest");
                        } else {
                            p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§cThere is no item called " + args[1].toUpperCase());
                        }
                    } else {
                        p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + AuctionHouseX.getInstance().getMessage().noPermission);
                    }
                } else if (args[0].equalsIgnoreCase("cancel")) {
                    //> Cancels all listings from another player
                    if (p.hasPermission("auctionhouse.cancel.others")) {
                        Player target = Bukkit.getPlayer(UUID.fromString(args[1]));

                        if (target != null) {
                            AuctionHouseX.getInstance().getCancelledExpiredGUI().returnCancelledExpired(target, 0);
                            p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§aSuccessfully cancelled listings from " + target.getName() + "!");
                        } else {
                            p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§cPlayer " + target.getName() + " is not online!");
                        }
                    } else {
                        p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + AuctionHouseX.getInstance().getMessage().noPermission);
                    }
                } else {
                    //> Show Help
                    if (p.hasPermission("auctionhouse.access")) {
                        sendHelp(p, 1);
                    } else {
                        p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + AuctionHouseX.getInstance().getMessage().noPermission);
                    }
                }
            } else {
                //> Show Help
                if (p.hasPermission("auctionhouse.access")) {
                    sendHelp(p, 1);
                } else {
                    p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + AuctionHouseX.getInstance().getMessage().noPermission);
                }
            }
        }

        return false;
    }

    private void sendHelp(Player p, int page) {
        switch (page) {
            case 1:
                p.sendMessage(" ");
                p.sendMessage("§6<§3=================§6< §bAuctionHouse§6§lX §6>§3================§6>");
                p.sendMessage("§6<§3=====================§6< §bv" + AuctionHouseX.getInstance().getDescription().getVersion() + " §6>§3====================§6>");
                p.sendMessage("§6<§3============§6< §b§oby https://mrmarshall.dev/ §6>§3===========§6>");
                p.sendMessage(" ");
                p.sendMessage("§6<§3== §b/ah           §6- §bOpens the Auctionhouse");
                p.sendMessage("§6<§3== §b/ah search §6- §bSearches for specific items");
                p.sendMessage("§6<§3== §b/ah help     §6- §bShows a list of all commands");
                p.sendMessage("§6<§3== §b/ah sell     §6- §bSells the item you are holding");
                p.sendMessage(" ");
                p.sendMessage("§6<§3== §7§oUse /ah help 2 to see more commands!");
                break;
            case 2:
                p.sendMessage(" ");
                p.sendMessage("§6<§3=================§6< §bAuctionHouse§6§lX §6>§3================§6>");
                p.sendMessage(" ");
                p.sendMessage("§6<§3== §b/ah selling    §6- §bView the items you are selling");
                p.sendMessage("§6<§3== §b/ah sold   §6- §bView the items you have sold");
                p.sendMessage("§6<§3== §b/ah expired  §6- §bView your expired items");
                p.sendMessage("§6<§3== §b/ah cancel   §6- §bCancel all your auctions");
                p.sendMessage("§6<§3== §b/ah return   §6- §bReturn all your cancelled/expired items");
                p.sendMessage(" ");
                break;
        }
    }
}
