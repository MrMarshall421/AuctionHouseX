package dev.mrmarshall.auctionhousex.events;

import dev.mrmarshall.auctionhousex.AuctionHouseX;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener {

    @EventHandler
    public void dropItem(PlayerDropItemEvent e) {
        Player p = e.getPlayer();

        if (AuctionHouseX.getInstance().getTradingManager().getBlockTrading().contains(p.getUniqueId())) {
            e.getItemDrop().getItemStack().setAmount(0);
        }
    }
}
