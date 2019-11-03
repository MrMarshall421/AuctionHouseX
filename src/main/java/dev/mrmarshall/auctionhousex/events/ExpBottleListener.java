package dev.mrmarshall.auctionhousex.events;

import dev.mrmarshall.auctionhousex.AuctionHouseX;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExpBottleEvent;

public class ExpBottleListener implements Listener {

    @EventHandler
    public void onExpBottle(ExpBottleEvent e) {
        if (AuctionHouseX.getInstance().getEnchantingBottle().getThrownExpBottle().containsKey(e.getEntity().getUniqueId())) {
            e.setExperience(AuctionHouseX.getInstance().getEnchantingBottle().getThrownExpBottle().get(e.getEntity().getUniqueId()));
            AuctionHouseX.getInstance().getEnchantingBottle().getThrownExpBottle().remove(e.getEntity().getUniqueId());
        }
    }
}
