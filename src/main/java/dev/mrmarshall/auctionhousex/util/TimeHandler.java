package dev.mrmarshall.auctionhousex.util;

import dev.mrmarshall.auctionhousex.AuctionHouseX;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeHandler {

    public String convertListingTime(long time) {
        long currentTime = System.currentTimeMillis();
        long expireTime = (time + convertFormattedToMillis(AuctionHouseX.getInstance().getConfig().getString("auction.listingDuration"))) - currentTime;
        Date currentDate = new Date(expireTime);
        DateFormat dateFormat = new SimpleDateFormat("dd'd' hh'h' mm'm'");
        return dateFormat.format(currentDate);
    }

    public String convertSoldTime(long time) {
        long currentTime = System.currentTimeMillis();
        long soldTime = time - currentTime;
        Date currentDate = new Date(soldTime);
        DateFormat dateFormat = new SimpleDateFormat("dd'd hh'h mm'm");
        return dateFormat.format(currentDate);
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
