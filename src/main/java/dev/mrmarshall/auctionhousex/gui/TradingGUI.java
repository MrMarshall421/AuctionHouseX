package dev.mrmarshall.auctionhousex.gui;

import dev.mrmarshall.auctionhousex.AuctionHouseX;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class TradingGUI implements Listener {

    public void open(Player p, Player target) {
        Inventory pTradingGUI = Bukkit.createInventory(null, 54, "§2SafeTrade");
        Inventory targetTradingGUI = Bukkit.createInventory(null, 54, " §2SafeTrade");

        //> Items
        ItemStack placeholder = AuctionHouseX.getInstance().getItemCreator().create(Material.LIGHT_BLUE_STAINED_GLASS_PANE, " ", new ArrayList<>(), false);
        ItemStack instruction = AuctionHouseX.getInstance().getTradingManager().getInstructionBook();
        ItemStack neutral = AuctionHouseX.getInstance().getItemCreator().create(Material.YELLOW_STAINED_GLASS_PANE, " ", new ArrayList<>(), false);
        ItemStack accept = AuctionHouseX.getInstance().getItemCreator().create(Material.GREEN_STAINED_GLASS_PANE, " ", new ArrayList<>(), false);
        ItemStack decline = AuctionHouseX.getInstance().getItemCreator().create(Material.RED_STAINED_GLASS_PANE, " ", new ArrayList<>(), false);

        //> Skulls
        ItemStack playerSkull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta playerSkullMeta = (SkullMeta) playerSkull.getItemMeta();
        playerSkullMeta.setDisplayName("§b" + p.getName());
        playerSkullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(p.getUniqueId()));
        playerSkull.setItemMeta(playerSkullMeta);

        ItemStack targetSkull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta targetSkullMeta = (SkullMeta) playerSkull.getItemMeta();
        targetSkullMeta.setDisplayName("§b" + target.getName());
        targetSkullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(target.getUniqueId()));
        targetSkull.setItemMeta(targetSkullMeta);

        //> Set items
        pTradingGUI.setItem(0, placeholder);
        pTradingGUI.setItem(1, placeholder);
        pTradingGUI.setItem(2, placeholder);
        pTradingGUI.setItem(3, placeholder);
        pTradingGUI.setItem(4, instruction);
        pTradingGUI.setItem(5, placeholder);
        pTradingGUI.setItem(6, placeholder);
        pTradingGUI.setItem(7, placeholder);
        pTradingGUI.setItem(8, placeholder);
        pTradingGUI.setItem(13, placeholder);
        pTradingGUI.setItem(22, placeholder);
        pTradingGUI.setItem(31, placeholder);
        pTradingGUI.setItem(40, placeholder);
        pTradingGUI.setItem(45, playerSkull);
        pTradingGUI.setItem(46, placeholder);
        pTradingGUI.setItem(47, accept);
        pTradingGUI.setItem(48, decline);
        pTradingGUI.setItem(49, placeholder);
        pTradingGUI.setItem(50, neutral);
        pTradingGUI.setItem(51, neutral);
        pTradingGUI.setItem(52, placeholder);
        pTradingGUI.setItem(53, targetSkull);

        targetTradingGUI.setItem(0, placeholder);
        targetTradingGUI.setItem(1, placeholder);
        targetTradingGUI.setItem(2, placeholder);
        targetTradingGUI.setItem(3, placeholder);
        targetTradingGUI.setItem(4, instruction);
        targetTradingGUI.setItem(5, placeholder);
        targetTradingGUI.setItem(6, placeholder);
        targetTradingGUI.setItem(7, placeholder);
        targetTradingGUI.setItem(8, placeholder);
        targetTradingGUI.setItem(13, placeholder);
        targetTradingGUI.setItem(22, placeholder);
        targetTradingGUI.setItem(31, placeholder);
        targetTradingGUI.setItem(40, placeholder);
        targetTradingGUI.setItem(45, playerSkull);
        targetTradingGUI.setItem(46, placeholder);
        targetTradingGUI.setItem(47, neutral);
        targetTradingGUI.setItem(48, neutral);
        targetTradingGUI.setItem(49, placeholder);
        targetTradingGUI.setItem(50, accept);
        targetTradingGUI.setItem(51, decline);
        targetTradingGUI.setItem(52, placeholder);
        targetTradingGUI.setItem(53, targetSkull);

        //> Open Trading GUIs
        p.openInventory(pTradingGUI);
        target.openInventory(targetTradingGUI);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Player target = Bukkit.getPlayer(AuctionHouseX.getInstance().getTradingManager().getTradingPartner(p.getUniqueId()));

        //> Safe Trade
        if (e.getView().getTitle().contains("§2SafeTrade")) {
            if (!(e.isShiftClick() && e.getClickedInventory() == p.getOpenInventory().getBottomInventory())) {
                if (e.getClickedInventory().getType() != InventoryType.PLAYER) {
                    e.setCancelled(true);

                    //> Player Trading GUI
                    if (e.getView().getTitle().equals("§2SafeTrade")) {
                        if (e.getSlot() == 9 || e.getSlot() == 10 || e.getSlot() == 11 || e.getSlot() == 12 || e.getSlot() == 18 || e.getSlot() == 19 || e.getSlot() == 20 || e.getSlot() == 21 || e.getSlot() == 27 || e.getSlot() == 28 || e.getSlot() == 29 || e.getSlot() == 30 || e.getSlot() == 36 || e.getSlot() == 37 || e.getSlot() == 38 || e.getSlot() == 39 || e.getSlot() == 47 || e.getSlot() == 48) {
                            e.setCancelled(false);
                        }
                        //> Target Trading GUI
                    } else if (e.getView().getTitle().equals(" §2SafeTrade")) {
                        if (e.getSlot() == 14 || e.getSlot() == 15 || e.getSlot() == 16 || e.getSlot() == 17 || e.getSlot() == 23 || e.getSlot() == 24 || e.getSlot() == 25 || e.getSlot() == 26 || e.getSlot() == 32 || e.getSlot() == 33 || e.getSlot() == 34 || e.getSlot() == 35 || e.getSlot() == 41 || e.getSlot() == 42 || e.getSlot() == 43 || e.getSlot() == 44 || e.getSlot() == 50 || e.getSlot() == 51) {
                            e.setCancelled(false);
                        }
                    }

                    if (!e.isCancelled()) {
                        if (e.getCurrentItem() != null) {
                            if (e.getCurrentItem().getType() == Material.GREEN_STAINED_GLASS_PANE) {
                                //> Accept Trade
                                ItemStack accept = AuctionHouseX.getInstance().getItemCreator().create(Material.GREEN_STAINED_GLASS_PANE, " ", new ArrayList<>(), false);

                                e.setCancelled(true);

                                if (AuctionHouseX.getInstance().getTradingManager().getTrading().containsKey(p.getUniqueId())) {
                                    e.getInventory().setItem(48, accept);
                                } else {
                                    e.getInventory().setItem(51, accept);
                                }

                                AuctionHouseX.getInstance().getTradingManager().acceptTrade(target.getUniqueId());
                            } else if (e.getCurrentItem().getType() == Material.RED_STAINED_GLASS_PANE) {
                                //> Decline Trade
                                ItemStack decline = AuctionHouseX.getInstance().getItemCreator().create(Material.RED_STAINED_GLASS_PANE, " ", new ArrayList<>(), false);

                                e.setCancelled(true);

                                if (AuctionHouseX.getInstance().getTradingManager().getTrading().containsKey(p.getUniqueId())) {
                                    e.getInventory().setItem(47, decline);
                                } else {
                                    e.getInventory().setItem(50, decline);
                                }

                                AuctionHouseX.getInstance().getTradingManager().declineTrade(target.getUniqueId());
                            } else {
                                //> Synchronize item to other player
                                if (e.getClickedInventory() == e.getView().getTopInventory()) {
                                    if (e.getCurrentItem() != null) {
                                        if (!AuctionHouseX.getInstance().getTradingManager().getBlockTrading().contains(p.getUniqueId())) {
                                            //> Item removed from Trading Field
                                            new BukkitRunnable() {
                                                @Override
                                                public void run() {
                                                    target.getOpenInventory().getTopInventory().setItem(e.getSlot(), e.getClickedInventory().getItem(e.getSlot()));
                                                }
                                            }.runTaskLater(AuctionHouseX.getInstance(), 5);
                                        } else {
                                            e.setCancelled(true);
                                        }
                                    }
                                }
                            }
                        } else {
                            if (!AuctionHouseX.getInstance().getTradingManager().getBlockTrading().contains(p.getUniqueId())) {
                                if (e.getCursor() != null && e.getCursor().getType() != Material.AIR) {
                                    //> New Item added to Trading Field
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            target.getOpenInventory().getTopInventory().setItem(e.getSlot(), e.getClickedInventory().getItem(e.getSlot()));
                                        }
                                    }.runTaskLater(AuctionHouseX.getInstance(), 5);
                                }
                            } else {
                                e.setCancelled(true);
                            }
                        }
                    }
                }
            } else {
                e.setCancelled(true);
            }
        }
    }
}
