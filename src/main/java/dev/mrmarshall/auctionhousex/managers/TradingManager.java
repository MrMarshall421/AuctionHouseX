package dev.mrmarshall.auctionhousex.managers;

import dev.mrmarshall.auctionhousex.AuctionHouseX;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TradingManager {

    private List<UUID> blockTrading;
    private Map<UUID, UUID> trading;
    private Map<UUID, Inventory> inventoryCache;

    private File instruction;
    private FileConfiguration instructionCfg;

    public TradingManager() {
        instruction = new File("plugins/AuctionHouseX/SafeTrade/instruction.yml");
        instructionCfg = YamlConfiguration.loadConfiguration(instruction);
        loadTradingFiles();

        inventoryCache = new HashMap<>();
        trading = new HashMap<>();
        blockTrading = new ArrayList<>();
    }

    private void loadTradingFiles() {
        File safetradeDir = new File("plugins/AuctionHouseX/SafeTrade");

        if (!safetradeDir.exists()) {
            safetradeDir.mkdir();
        }

        if (!instruction.exists()) {
            try {
                instruction.createNewFile();
                instructionCfg.set("item.name", "&bInstruction");
                ArrayList<String> lore = new ArrayList<>();
                lore.add("&bClick the green button to");
                lore.add("&a&lACCEPT &ba Trade or click");
                lore.add("&bthe red button to &c&lDECLINE&b.");
                instructionCfg.set("item.lore", lore);
                instructionCfg.save(instruction);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ItemStack getInstructionBook() {
        return AuctionHouseX.getInstance().getFileManager().getInstructionBook(instructionCfg);
    }

    public void acceptTrade(UUID targetUUID) {
        ItemStack accept = AuctionHouseX.getInstance().getItemCreator().create(Material.LIME_STAINED_GLASS_PANE, " ", new ArrayList<>(), false);
        Player target = Bukkit.getPlayer(targetUUID);
        Player p = Bukkit.getPlayer(AuctionHouseX.getInstance().getTradingManager().getTradingPartner(targetUUID));

        if (AuctionHouseX.getInstance().getTradingManager().getTrading().containsKey(targetUUID)) {
            target.getOpenInventory().getTopInventory().setItem(50, accept);
            target.getOpenInventory().getTopInventory().setItem(51, accept);
        } else if (AuctionHouseX.getInstance().getTradingManager().getTrading().containsValue(targetUUID)) {
            target.getOpenInventory().getTopInventory().setItem(47, accept);
            target.getOpenInventory().getTopInventory().setItem(48, accept);
        }

        if (!AuctionHouseX.getInstance().getTradingManager().getBlockTrading().contains(targetUUID)) {
            AuctionHouseX.getInstance().getTradingManager().getBlockTrading().add(targetUUID);
            AuctionHouseX.getInstance().getTradingManager().getBlockTrading().add(p.getUniqueId());
        }

        //> Finish trading if both accepted
        if (target.getOpenInventory().getTopInventory().getItem(48).getType() == Material.LIME_STAINED_GLASS_PANE && target.getOpenInventory().getTopInventory().getItem(51).getType() == Material.LIME_STAINED_GLASS_PANE) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (AuctionHouseX.getInstance().getTradingManager().getTrading().containsKey(targetUUID)) {
                        if (target.getOpenInventory().getTopInventory().getItem(14) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(14));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(15) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(15));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(16) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(16));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(17) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(17));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(23) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(23));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(24) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(24));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(25) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(25));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(26) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(26));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(32) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(32));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(33) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(33));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(34) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(34));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(35) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(35));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(41) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(41));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(42) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(42));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(43) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(43));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(44) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(44));
                        }
                        AuctionHouseX.getInstance().getTradingManager().getInventoryCache().remove(targetUUID);
                        AuctionHouseX.getInstance().getTradingManager().getTrading().remove(targetUUID);
                        AuctionHouseX.getInstance().getTradingManager().getBlockTrading().remove(targetUUID);
                        target.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§aTrading with " + p.getName() + " succeed.");

                        if (p.getOpenInventory().getTopInventory().getItem(9) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(9));
                        }
                        if (p.getOpenInventory().getTopInventory().getItem(10) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(10));
                        }
                        if (p.getOpenInventory().getTopInventory().getItem(11) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(11));
                        }
                        if (p.getOpenInventory().getTopInventory().getItem(12) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(12));
                        }
                        if (p.getOpenInventory().getTopInventory().getItem(18) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(18));
                        }
                        if (p.getOpenInventory().getTopInventory().getItem(19) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(19));
                        }
                        if (p.getOpenInventory().getTopInventory().getItem(20) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(20));
                        }
                        if (p.getOpenInventory().getTopInventory().getItem(21) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(21));
                        }
                        if (p.getOpenInventory().getTopInventory().getItem(27) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(27));
                        }
                        if (p.getOpenInventory().getTopInventory().getItem(28) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(28));
                        }
                        if (p.getOpenInventory().getTopInventory().getItem(29) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(29));
                        }
                        if (p.getOpenInventory().getTopInventory().getItem(30) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(30));
                        }
                        if (p.getOpenInventory().getTopInventory().getItem(36) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(36));
                        }
                        if (p.getOpenInventory().getTopInventory().getItem(37) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(37));
                        }
                        if (p.getOpenInventory().getTopInventory().getItem(38) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(38));
                        }
                        if (p.getOpenInventory().getTopInventory().getItem(39) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(39));
                        }
                        AuctionHouseX.getInstance().getTradingManager().getInventoryCache().remove(p.getUniqueId());
                        AuctionHouseX.getInstance().getTradingManager().getTrading().remove(p.getUniqueId());
                        AuctionHouseX.getInstance().getTradingManager().getBlockTrading().remove(p.getUniqueId());
                        p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§aTrading with " + target.getName() + " succeed.");

                        target.closeInventory();
                        p.closeInventory();
                    } else {
                        if (p.getOpenInventory().getTopInventory().getItem(14) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(14));
                        }
                        if (p.getOpenInventory().getTopInventory().getItem(15) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(15));
                        }
                        if (p.getOpenInventory().getTopInventory().getItem(16) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(16));
                        }
                        if (p.getOpenInventory().getTopInventory().getItem(17) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(17));
                        }
                        if (p.getOpenInventory().getTopInventory().getItem(23) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(23));
                        }
                        if (p.getOpenInventory().getTopInventory().getItem(24) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(24));
                        }
                        if (p.getOpenInventory().getTopInventory().getItem(25) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(25));
                        }
                        if (p.getOpenInventory().getTopInventory().getItem(26) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(26));
                        }
                        if (p.getOpenInventory().getTopInventory().getItem(32) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(32));
                        }
                        if (p.getOpenInventory().getTopInventory().getItem(33) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(33));
                        }
                        if (p.getOpenInventory().getTopInventory().getItem(34) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(34));
                        }
                        if (p.getOpenInventory().getTopInventory().getItem(35) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(35));
                        }
                        if (p.getOpenInventory().getTopInventory().getItem(41) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(41));
                        }
                        if (p.getOpenInventory().getTopInventory().getItem(42) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(42));
                        }
                        if (p.getOpenInventory().getTopInventory().getItem(43) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(43));
                        }
                        if (p.getOpenInventory().getTopInventory().getItem(44) != null) {
                            p.getInventory().addItem(p.getOpenInventory().getTopInventory().getItem(44));
                        }
                        AuctionHouseX.getInstance().getTradingManager().getInventoryCache().remove(p.getUniqueId());
                        AuctionHouseX.getInstance().getTradingManager().getTrading().remove(p.getUniqueId());
                        AuctionHouseX.getInstance().getTradingManager().getBlockTrading().remove(p.getUniqueId());
                        p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§aTrading with " + target.getName() + " succeed.");

                        if (target.getOpenInventory().getTopInventory().getItem(9) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(9));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(10) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(10));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(11) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(11));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(12) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(12));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(13) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(13));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(18) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(18));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(19) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(19));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(20) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(20));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(21) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(21));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(27) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(27));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(28) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(28));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(29) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(29));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(30) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(30));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(36) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(36));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(37) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(37));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(38) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(38));
                        }
                        if (target.getOpenInventory().getTopInventory().getItem(39) != null) {
                            target.getInventory().addItem(target.getOpenInventory().getTopInventory().getItem(39));
                        }
                        AuctionHouseX.getInstance().getTradingManager().getInventoryCache().remove(targetUUID);
                        AuctionHouseX.getInstance().getTradingManager().getTrading().remove(targetUUID);
                        AuctionHouseX.getInstance().getTradingManager().getBlockTrading().remove(targetUUID);
                        target.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§aTrading with " + p.getName() + " succeed.");

                        target.closeInventory();
                        p.closeInventory();
                    }
                }
            }.runTaskLater(AuctionHouseX.getInstance(), 20);
        }
    }

    public void declineTrade(UUID targetUUID) {
        ItemStack decline = AuctionHouseX.getInstance().getItemCreator().create(Material.RED_STAINED_GLASS_PANE, " ", new ArrayList<>(), false);
        Player target = Bukkit.getPlayer(targetUUID);
        UUID pUUID = AuctionHouseX.getInstance().getTradingManager().getTradingPartner(targetUUID);

        if (AuctionHouseX.getInstance().getTradingManager().getTrading().containsKey(targetUUID)) {
            target.getOpenInventory().getTopInventory().setItem(50, decline);
            target.getOpenInventory().getTopInventory().setItem(51, decline);
        } else if (AuctionHouseX.getInstance().getTradingManager().getTrading().containsValue(targetUUID)) {
            target.getOpenInventory().getTopInventory().setItem(47, decline);
            target.getOpenInventory().getTopInventory().setItem(48, decline);
        }

        if (!AuctionHouseX.getInstance().getTradingManager().getBlockTrading().contains(targetUUID)) {
            AuctionHouseX.getInstance().getTradingManager().getBlockTrading().add(targetUUID);
            AuctionHouseX.getInstance().getTradingManager().getBlockTrading().add(pUUID);
        }

        //> Cancel Trading after 1 second
        new BukkitRunnable() {
            @Override
            public void run() {
                AuctionHouseX.getInstance().getTradingManager().cancelTrade(pUUID, targetUUID);
            }
        }.runTaskLater(AuctionHouseX.getInstance(), 20);
    }

    public void saveInventory(UUID pUUID) {
        Inventory cacheInventory = Bukkit.createInventory(null, 36);
        Player p = Bukkit.getPlayer(pUUID);

        for (int i = 0; i < cacheInventory.getSize(); i++) {
            cacheInventory.setItem(i, p.getInventory().getItem(i));
        }

        AuctionHouseX.getInstance().getTradingManager().getInventoryCache().put(pUUID, cacheInventory);
    }

    private void restoreInventory(UUID pUUID) {
        Player p = Bukkit.getPlayer(pUUID);

        if (AuctionHouseX.getInstance().getTradingManager().getInventoryCache().containsKey(pUUID)) {
            p.getInventory().setContents(AuctionHouseX.getInstance().getTradingManager().getInventoryCache().get(pUUID).getContents());
            p.updateInventory();

            AuctionHouseX.getInstance().getTradingManager().getInventoryCache().remove(pUUID);
        }
    }

    public void cancelTrade(UUID pUUID, UUID targetUUID) {
        Player p = Bukkit.getPlayer(pUUID);
        Player target = Bukkit.getPlayer(targetUUID);

        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    AuctionHouseX.getInstance().getTradingManager().restoreInventory(pUUID);
                    AuctionHouseX.getInstance().getTradingManager().getTrading().remove(pUUID);
                    AuctionHouseX.getInstance().getTradingManager().getBlockTrading().remove(pUUID);
                    p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§cTrading with " + target.getName() + " cancelled.");

                    AuctionHouseX.getInstance().getTradingManager().restoreInventory(targetUUID);
                    AuctionHouseX.getInstance().getTradingManager().getTrading().remove(targetUUID);
                    AuctionHouseX.getInstance().getTradingManager().getBlockTrading().remove(targetUUID);
                    target.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§cTrading with " + p.getName() + " cancelled.");

                    p.closeInventory();
                    target.closeInventory();
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }
        }.runTaskLater(AuctionHouseX.getInstance(), 20);
    }

    public UUID getTradingPartner(UUID target) {
        if (AuctionHouseX.getInstance().getTradingManager().getTrading().containsKey(target)) {
            return AuctionHouseX.getInstance().getTradingManager().getTrading().get(target);
        } else if (AuctionHouseX.getInstance().getTradingManager().getTrading().containsValue(target)) {
            for (UUID key : AuctionHouseX.getInstance().getTradingManager().getTrading().keySet()) {
                if (AuctionHouseX.getInstance().getTradingManager().getTrading().get(key) == target) {
                    return key;
                }
            }
        }

        return null;
    }

    private Map<UUID, Inventory> getInventoryCache() {
        return inventoryCache;
    }

    public List<UUID> getBlockTrading() {
        return blockTrading;
    }

    public Map<UUID, UUID> getTrading() {
        return trading;
    }
}
