package dev.mrmarshall.auctionhousex.events;

import dev.mrmarshall.auctionhousex.AuctionHouseX;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class ProjectileLaunchListener implements Listener {

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent e) {

        if (e.getEntity().getShooter() instanceof Player) {
            Player p = (Player) e.getEntity().getShooter();

            if (AuctionHouseX.getInstance().getEnchantingBottle().getThrownExpBottle().containsKey(p.getUniqueId())) {
                int amount = AuctionHouseX.getInstance().getEnchantingBottle().getThrownExpBottle().get(p.getUniqueId());

                AuctionHouseX.getInstance().getEnchantingBottle().getThrownExpBottle().remove(p.getUniqueId());
                AuctionHouseX.getInstance().getEnchantingBottle().addThrownExpBottle(e.getEntity().getUniqueId(), amount);
            }
        }
    }
}
