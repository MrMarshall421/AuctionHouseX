package dev.mrmarshall.auctionhousex.managers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

	public ItemStack getInstructionBook(File instruction, FileConfiguration instructionCfg) {
		ItemStack instructionBook = new ItemStack(Material.BOOK);
		ItemMeta instructionBookMeta = instructionBook.getItemMeta();
		instructionBookMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', instructionCfg.getString("item.name")));

		//> Colorize Lore
		List<String> lore = new ArrayList<>();
		for(String key : instructionCfg.getStringList("item.lore")) {
			lore.add(ChatColor.translateAlternateColorCodes('&', key));
		}

		instructionBookMeta.setLore(lore);
		instructionBook.setItemMeta(instructionBookMeta);
		return instructionBook;
	}

	public void loadAuctionhouseFiles(File instruction, FileConfiguration instructionCfg) {
		File auctionhouseDir = new File("plugins/AuctionHouseX/Auctionhouse");

		//> Auctionhouse directory
		if(!auctionhouseDir.exists()) {
			auctionhouseDir.mkdir();
		}

		//> Instruction File
		if(!instruction.exists()) {
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

		//> Categories directory
		File categories = new File("plugins/AuctionHouseX/Auctionhouse/Categories");

		if(!categories.exists()) {
			categories.mkdir();
		}

		//> Blocks Category
		File blocksCategory = new File("plugins/AuctionHouseX/Auctionhouse/Categories/Blocks");

		if(!blocksCategory.exists()) {
			blocksCategory.mkdir();
		}

		//> Decoration Category
		File decorationCategory = new File("plugins/AuctionHouseX/Auctionhouse/Categories/Decoration");

		if(!decorationCategory.exists()) {
			decorationCategory.mkdir();
		}

		//> Redstone & Transportation Category
		File rtCategory = new File("plugins/AuctionHouseX/Auctionhouse/Categories/Redstone+Transportation");

		if(!rtCategory.exists()) {
			rtCategory.mkdir();
		}

		//> Productive Items Category
		File productiveCategory = new File("plugins/AuctionHouseX/Auctionhouse/Categories/Productive");

		if(!productiveCategory.exists()) {
			productiveCategory.mkdir();
		}

		//> Food Category
		File foodCategory = new File("plugins/AuctionHouseX/Auctionhouse/Categories/Food");

		if(!foodCategory.exists()) {
			foodCategory.mkdir();
		}

		//> Tools Category
		File toolsCategory = new File("plugins/AuctionHouseX/Auctionhouse/Categories/Tools");

		if(!toolsCategory.exists()) {
			toolsCategory.mkdir();
		}

		//> Combat Category
		File combatCategory = new File("plugins/AuctionHouseX/AuctionHouse/Categories/Combat");

		if(!combatCategory.exists()) {
			combatCategory.mkdir();
		}

		//> Brewing Category
		File brewingCategory = new File("plugins/AuctionHouseX/Auctionhouse/Categories/Brewing");

		if(!brewingCategory.exists()) {
			brewingCategory.mkdir();
		}
	}
}
