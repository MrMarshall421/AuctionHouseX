package dev.mrmarshall.auctionhousex.events;

import dev.mrmarshall.auctionhousex.AuctionHouseX;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        Player target;

        if (AuctionHouseX.getInstance().getTradingManager().getTrading().containsKey(p.getUniqueId())) {
            //> Player started Trade
            target = Bukkit.getPlayer(AuctionHouseX.getInstance().getTradingManager().getTrading().get(p.getUniqueId()));
            AuctionHouseX.getInstance().getTradingManager().cancelTrade(p.getUniqueId(), target.getUniqueId());
        } else if (AuctionHouseX.getInstance().getTradingManager().getTrading().containsValue(p.getUniqueId())) {
            //> Player not started Trade
            target = Bukkit.getPlayer(AuctionHouseX.getInstance().getTradingManager().getTradingPartner(p.getUniqueId()));
            AuctionHouseX.getInstance().getTradingManager().cancelTrade(target.getUniqueId(), p.getUniqueId());
        }
    }
}
