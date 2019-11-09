package dev.mrmarshall.auctionhousex.commands;

import dev.mrmarshall.auctionhousex.AuctionHouseX;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class XpBottleCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (p.hasPermission("xpbottle.access")) {
                if (p.getInventory().getItemInMainHand().getType() == Material.GLASS_BOTTLE) {
                    if (AuctionHouseX.getInstance().getLevelManager().getTotalExperience(p) != 0) {
                        if (args.length == 1) {
                            if (args[0].equalsIgnoreCase("all")) {
                                //> Withdraw all EXP
                                int amount = AuctionHouseX.getInstance().getLevelManager().getTotalExperience(p);

                                AuctionHouseX.getInstance().getLevelManager().setTotalExperience(p, 0);
                                p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
                                p.getInventory().addItem(AuctionHouseX.getInstance().getEnchantingBottle().getItem(amount));
                                p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§aSuccessfully withdrawn " + amount + " EXP!");
                            } else {
                                //> Withdraw EXP Amount
                                try {
                                    int currentExp = AuctionHouseX.getInstance().getLevelManager().getTotalExperience(p);
                                    int amount = Integer.parseInt(args[0]);

                                    if (currentExp >= amount) {
                                        AuctionHouseX.getInstance().getLevelManager().setTotalExperience(p, currentExp - amount);
                                        p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
                                        p.getInventory().addItem(AuctionHouseX.getInstance().getEnchantingBottle().getItem(amount));
                                        p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§aSuccessfully withdrawn " + amount + " EXP!");
                                    } else {
                                        p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§cYou don't have " + amount + " EXP.");
                                    }
                                } catch (NumberFormatException ex) {
                                    //> Command Help
                                    p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§cPlease use §e/xpbottle [level] <all | amount>§c.");
                                }
                            }
                        } else if (args.length == 2) {
                            if (args[0].equalsIgnoreCase("level")) {
                                //> Withdraw EXP with Level Amount
                                try {
                                    int currentExp = AuctionHouseX.getInstance().getLevelManager().getTotalExperience(p);
                                    int level = Integer.parseInt(args[1]);

                                    if (p.getLevel() >= level) {
                                        p.setLevel(p.getLevel() - level);
                                        int totalExpBetween = currentExp - AuctionHouseX.getInstance().getLevelManager().getTotalExperience(p);

                                        p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
                                        p.getInventory().addItem(AuctionHouseX.getInstance().getEnchantingBottle().getItem(totalExpBetween));
                                        p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§aSuccessfully withdrawn " + totalExpBetween + " EXP!");
                                    } else {
                                        p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§cYou don't have " + level + " Levels.");
                                    }
                                } catch (NumberFormatException ex) {
                                    //> Command Help
                                    p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§cPlease use §e/xpbottle [level] <all | amount>§c.");
                                }
                            } else {
                                //> Command Help
                                p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§cPlease use §e/xpbottle [level] <all | amount>§c.");
                            }
                        } else {
                            //> Command Help
                            p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§cPlease use §e/xpbottle [level] <all | amount>§c.");
                        }
                    } else {
                        p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§cYou have no EXP.");
                    }
                } else {
                    p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§cYou need to hold a glass bottle in hand to withdraw EXP!");
                }
            } else {
                p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + AuctionHouseX.getInstance().getMessage().noPermission);
            }
        }

        return false;
    }
}
