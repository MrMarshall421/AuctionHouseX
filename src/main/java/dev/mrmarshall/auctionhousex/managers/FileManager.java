package dev.mrmarshall.auctionhousex.managers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class FileManager {

    private File blocksCategory;
    private FileConfiguration blocksCategoryCfg;
    private File decorationCategory;
    private FileConfiguration decorationCategoryCfg;
    private File rtCategory;
    private FileConfiguration rtCategoryCfg;
    private File productiveCategory;
    private FileConfiguration productiveCategoryCfg;
    private File foodCategory;
    private FileConfiguration foodCategoryCfg;
    private File toolsCategory;
    private FileConfiguration toolsCategoryCfg;
    private File combatCategory;
    private FileConfiguration combatCategoryCfg;
    private File brewingCategory;
    private FileConfiguration brewingCategoryCfg;

    public FileManager() {
        blocksCategory = new File("plugins/AuctionHouseX/Auctionhouse/Blocks.yml");
        blocksCategoryCfg = YamlConfiguration.loadConfiguration(blocksCategory);
        decorationCategory = new File("plugins/AuctionHouseX/Auctionhouse/Decoration.yml");
        decorationCategoryCfg = YamlConfiguration.loadConfiguration(decorationCategory);
        rtCategory = new File("plugins/AuctionHouseX/Auctionhouse/Redstone+Transportation.yml");
        rtCategoryCfg = YamlConfiguration.loadConfiguration(rtCategory);
        productiveCategory = new File("plugins/AuctionHouseX/Auctionhouse/Productive.yml");
        productiveCategoryCfg = YamlConfiguration.loadConfiguration(productiveCategory);
        foodCategory = new File("plugins/AuctionHouseX/Auctionhouse/Food.yml");
        foodCategoryCfg = YamlConfiguration.loadConfiguration(foodCategory);
        toolsCategory = new File("plugins/AuctionHouseX/Auctionhouse/Tools.yml");
        toolsCategoryCfg = YamlConfiguration.loadConfiguration(toolsCategory);
        combatCategory = new File("plugins/AuctionHouseX/AuctionHouse/Combat.yml");
        combatCategoryCfg = YamlConfiguration.loadConfiguration(combatCategory);
        brewingCategory = new File("plugins/AuctionHouseX/Auctionhouse/Brewing.yml");
        brewingCategoryCfg = YamlConfiguration.loadConfiguration(brewingCategory);
    }

    public List<Integer> getListingsFromFile(FileConfiguration categoryFile) {
        List<Integer> list = new ArrayList<>();
        Map<String, Object> listings = categoryFile.getValues(false);
        Iterator iterator = listings.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            list.add(Integer.parseInt(entry.getKey().toString()));
        }

        return list;
    }

    public Map<String, List<Integer>> getPlayerListings(Player p) {
        Map<String, List<Integer>> finalList = new HashMap<>();
        File dir = new File("plugins/AuctionHouseX/Auctionhouse");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File file : directoryListing) {
                if (!(file.getName().equals("instruction.yml") || file.getName().equals("Expired.yml") || file.getName().equals("Sold.yml"))) {
                    FileConfiguration fileCfg = YamlConfiguration.loadConfiguration(file);
                    Map<String, Object> listings = fileCfg.getValues(false);
                    Iterator iterator = listings.entrySet().iterator();
                    List<Integer> list = new ArrayList<>();

                    while (iterator.hasNext()) {
                        Map.Entry entry = (Map.Entry) iterator.next();
                        int listingId = Integer.parseInt(entry.getKey().toString());

                        if (fileCfg.getString(listingId + ".seller").contains(p.getUniqueId().toString())) {
                            list.add(listingId);
                        }
                    }

                    finalList.put(file.getName(), list);
                }
            }
        }

        return finalList;
    }

    public List<Integer> getPlayerSoldListings(Player p) {
        File sold = new File("plugins/AuctionHouseX/Auctionhouse/Sold.yml");
        FileConfiguration soldCfg = YamlConfiguration.loadConfiguration(sold);
        Map<String, Object> listings = soldCfg.getValues(false);
        Iterator iterator = listings.entrySet().iterator();
        List<Integer> list = new ArrayList<>();

        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            int listingId = Integer.parseInt(entry.getKey().toString());

            if (soldCfg.getString(listingId + ".seller").contains(p.getUniqueId().toString())) {
                list.add(listingId);
            }
        }

        return list;
    }

    public Map<String, List<Integer>> getAllListings() {
        Map<String, List<Integer>> finalList = new HashMap<>();
        File dir = new File("plugins/AuctionHouseX/Auctionhouse");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File file : directoryListing) {
                if (!(file.getName().equals("instruction.yml") || file.getName().equals("Expired.yml") || file.getName().equals("Sold.yml"))) {
                    FileConfiguration fileCfg = YamlConfiguration.loadConfiguration(file);
                    Map<String, Object> listings = fileCfg.getValues(false);
                    Iterator iterator = listings.entrySet().iterator();
                    List<Integer> list = new ArrayList<>();

                    while (iterator.hasNext()) {
                        Map.Entry entry = (Map.Entry) iterator.next();
                        int listingId = Integer.parseInt(entry.getKey().toString());

                        if (fileCfg.getString(listingId + ".seller") != null) {
                            list.add(listingId);
                        }
                    }

                    finalList.put(file.getName(), list);
                }
            }
        }

        return finalList;
    }

    public ItemStack getInstructionBook(FileConfiguration instructionCfg) {
        ItemStack instructionBook = new ItemStack(Material.BOOK);
        ItemMeta instructionBookMeta = instructionBook.getItemMeta();
        instructionBookMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', instructionCfg.getString("item.name")));
        instructionBookMeta.addEnchant(Enchantment.DURABILITY, 1, true);
        instructionBookMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        //> Colorize Lore
        List<String> lore = new ArrayList<>();
        for (String key : instructionCfg.getStringList("item.lore")) {
            lore.add(ChatColor.translateAlternateColorCodes('&', key));
        }

        instructionBookMeta.setLore(lore);
        instructionBook.setItemMeta(instructionBookMeta);
        return instructionBook;
    }

    public int getCurrentPlayerListings(Player p) {
        int currentListings = 0;

        for (int i = 0; i < 999; i++) {
            if (blocksCategoryCfg.getString(i + ".seller") == p.getUniqueId().toString()) {
                currentListings++;
            }
            if (decorationCategoryCfg.getString(i + ".seller") == p.getUniqueId().toString()) {
                currentListings++;
            }
            if (rtCategoryCfg.getString(i + ".seller") == p.getUniqueId().toString()) {
                currentListings++;
            }
            if (productiveCategoryCfg.getString(i + ".seller") == p.getUniqueId().toString()) {
                currentListings++;
            }
            if (foodCategoryCfg.getString(i + ".seller") == p.getUniqueId().toString()) {
                currentListings++;
            }
            if (toolsCategoryCfg.getString(i + ".seller") == p.getUniqueId().toString()) {
                currentListings++;
            }
            if (combatCategoryCfg.getString(i + ".seller") == p.getUniqueId().toString()) {
                currentListings++;
            }
            if (brewingCategoryCfg.getString(i + ".seller") == p.getUniqueId().toString()) {
                currentListings++;
            }
        }

        return currentListings;
    }

    public int getNextListingId(String category) {
        File categoryFile = new File("plugins/AuctionHouseX/Auctionhouse/" + category + ".yml");
        FileConfiguration categoryFileCfg = YamlConfiguration.loadConfiguration(categoryFile);
        List<Integer> listings = new ArrayList<>();

        for (int i = 0; i < 999; i++) {
            if (categoryFileCfg.getString(i + ".seller") != null) {
                listings.add(i);
            }
        }

        if (!listings.isEmpty()) {
            return listings.get(listings.size() - 1) + 1;
        }

        return 0;
    }

    public void loadAuctionhouseFiles(File instruction, FileConfiguration instructionCfg) {
        File auctionhouseDir = new File("plugins/AuctionHouseX/Auctionhouse");

        //> Auctionhouse directory
        if (!auctionhouseDir.exists()) {
            auctionhouseDir.mkdir();
        }

        //> Instruction File
        if (!instruction.exists()) {
            try {
                instruction.createNewFile();
                instructionCfg.set("item.name", "&6What Is This Page?");
                ArrayList<String> lore = new ArrayList<>();
                lore.add("§aThis is the auction house. Here you can");
                lore.add("§alist items for sale and purchase items");
                lore.add("§athat others have listed for sale.");
                lore.add("§aThe auction is also a great way to make");
                lore.add("§amoney by selling farmable items other");
                lore.add("§aplayers may be interested in buying.");
                instructionCfg.set("item.lore", lore);
                instructionCfg.save(instruction);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File expired = new File("plugins/AuctionHouseX/Auctionhouse/Expired.yml");
        //> Expired File
        if (!expired.exists()) {
            try {
                expired.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File sold = new File("plugins/AuctionHouseX/Auctionhouse/Sold.yml");
        //> Sold File
        if (!sold.exists()) {
            try {
                sold.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //> Blocks Category
        if (!blocksCategory.exists()) {
            try {
                blocksCategory.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //> Decoration Category
        if (!decorationCategory.exists()) {
            try {
                decorationCategory.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //> Redstone & Transportation Category
        if (!rtCategory.exists()) {
            try {
                rtCategory.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //> Productive Items Category
        if (!productiveCategory.exists()) {
            try {
                productiveCategory.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //> Food Category
        if (!foodCategory.exists()) {
            try {
                foodCategory.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //> Tools Category
        if (!toolsCategory.exists()) {
            try {
                toolsCategory.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //> Combat Category
        if (!combatCategory.exists()) {
            try {
                combatCategory.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //> Brewing Category
        if (!brewingCategory.exists()) {
            try {
                brewingCategory.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
