package dev.mrmarshall.auctionhousex.sorting;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Comparator;

public class SortListingByPrice implements Comparator<Listing> {

    @Override
    public int compare(Listing o1, Listing o2) {
        FileConfiguration fileCfg1 = YamlConfiguration.loadConfiguration(o1.getListingFile());
        FileConfiguration fileCfg2 = YamlConfiguration.loadConfiguration(o2.getListingFile());

        int price1 = fileCfg1.getInt(o1.getListingId() + ".price");
        int price2 = fileCfg2.getInt(o2.getListingId() + ".price");

        return price1 - price2;
    }
}
