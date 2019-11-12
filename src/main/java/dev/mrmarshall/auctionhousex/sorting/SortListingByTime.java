package dev.mrmarshall.auctionhousex.sorting;

import java.util.Comparator;

public class SortListingByTime implements Comparator<Listing> {

    @Override
    public int compare(Listing o1, Listing o2) {
        long result = o1.getTime() - o2.getTime();
        return (int) result;
    }
}
