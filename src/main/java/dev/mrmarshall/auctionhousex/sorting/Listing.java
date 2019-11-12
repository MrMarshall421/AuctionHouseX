package dev.mrmarshall.auctionhousex.sorting;

import java.io.File;

public class Listing {

    private File listingFile;
    private int listingId;
    private long time;

    public Listing(File listingFile, int listingId, long time) {
        this.listingFile = listingFile;
        this.listingId = listingId;
        this.time = time;
    }

    public File getListingFile() {
        return this.listingFile;
    }

    public Integer getListingId() {
        return this.listingId;
    }

    public Long getTime() {
        return this.time;
    }

}
