package dev.mrmarshall.auctionhousex.util;

import dev.mrmarshall.auctionhousex.AuctionHouseX;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeHandler {

    public String convertListingTime(long time) {
        long currentTime = System.currentTimeMillis();
        long diff = (time + convertFormattedToMillis(AuctionHouseX.getInstance().getConfig().getString("auction.listingDuration")) + 90000000) - currentTime;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        return diffDays + "d " + diffHours + "h " + diffMinutes + "m";
    }

    public String convertSoldTime(long time) {
        long currentTime = System.currentTimeMillis();
        long diff = currentTime - time;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        return diffDays + "d " + diffHours + "h " + diffMinutes + "m";
    }

    public long convertFormattedToMillis(String formatted) {
        try {
            Date date = new SimpleDateFormat("dd'd' hh'h' mm'm'").parse(formatted);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
