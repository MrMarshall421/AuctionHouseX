package dev.mrmarshall.auctionhousex.events;

import dev.mrmarshall.auctionhousex.AuctionHouseX;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class InventoryCloseListener implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        Player target;

        try {
            if (AuctionHouseX.getInstance().getTradingManager().getTrading().containsKey(p.getUniqueId()) || AuctionHouseX.getInstance().getTradingManager().getTrading().containsValue(p.getUniqueId())) {
                if (e.getView().getTitle().equals("§2SafeTrade")) {
                    //> Player started Trade
                    target = Bukkit.getPlayer(AuctionHouseX.getInstance().getTradingManager().getTrading().get(p.getUniqueId()));
                    AuctionHouseX.getInstance().getTradingManager().cancelTrade(p.getUniqueId(), target.getUniqueId());
                } else if (e.getView().getTitle().equals(" §2SafeTrade")) {
                    //> Player not started Trade
                    target = Bukkit.getPlayer(AuctionHouseX.getInstance().getTradingManager().getTradingPartner(p.getUniqueId()));
                    AuctionHouseX.getInstance().getTradingManager().cancelTrade(target.getUniqueId(), p.getUniqueId());
                }
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

        if (e.getView().getTitle().equals("§rShulker Box") && p.getInventory().getItemInMainHand().getType().toString().contains("_SHULKER_BOX")) {
            //> Save Shulker Box Contents
            ItemStack[] shulkerBoxContent = e.getInventory().getContents();
            ItemStack shulkerBoxItem = p.getInventory().getItemInMainHand().clone();
            BlockStateMeta shulkerBoxMeta = (BlockStateMeta) shulkerBoxItem.getItemMeta();
            ShulkerBox shulkerBox = (ShulkerBox) shulkerBoxMeta.getBlockState();
            shulkerBox.getInventory().setContents(shulkerBoxContent);
            shulkerBoxMeta.setBlockState(shulkerBox);
            shulkerBoxItem.setItemMeta(shulkerBoxMeta);

            p.getInventory().setItemInMainHand(shulkerBoxItem);
            p.playSound(p.getLocation(), Sound.BLOCK_SHULKER_BOX_CLOSE, 1.0F, 1.0F);
        }

        AuctionHouseX.getInstance().getAuctionhouseManager().getSelling().remove(p.getUniqueId());
        AuctionHouseX.getInstance().getAuctionhouseManager().getCurrentPage().remove(p.getUniqueId());
        AuctionHouseX.getInstance().getAuctionhouseManager().getCurrentCategory().remove(p.getUniqueId());
    }
}
