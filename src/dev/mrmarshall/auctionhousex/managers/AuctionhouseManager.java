package dev.mrmarshall.auctionhousex.managers;

import dev.mrmarshall.auctionhousex.AuctionHouseX;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.List;

public class AuctionhouseManager {

	private File instruction;
	private FileConfiguration instructionCfg;

	public AuctionhouseManager() {
		instruction = new File("plugins/AuctionHouseX/Auctionhouse/instruction.yml");
		instructionCfg = YamlConfiguration.loadConfiguration(instruction);
		loadAuctionhouseFiles();
	}

	private void loadAuctionhouseFiles() {
		AuctionHouseX.getInstance().getFileManager().loadAuctionhouseFiles(instruction, instructionCfg);
	}

	public ItemStack getInstructionBook() {
		return AuctionHouseX.getInstance().getFileManager().getInstructionBook(instructionCfg);
	}

	private boolean isBlacklisted(ItemStack item) {
		List<String> blacklisted = AuctionHouseX.getInstance().getConfig().getStringList("blacklist");
		return blacklisted.contains(item.getType().name());
	}

	private String getItemCategory(ItemStack item) {
		if (AuctionHouseX.getInstance().getCategoryManager().getBuildingCategory().contains(item.getType())) {
			return "Blocks";
		} else if (AuctionHouseX.getInstance().getCategoryManager().getBrewingCategory().contains(item.getType())) {
			return "Brewing";
		} else if (AuctionHouseX.getInstance().getCategoryManager().getCombatCategory().contains(item.getType())) {
			return "Combat";
		} else if (AuctionHouseX.getInstance().getCategoryManager().getDecorationCategory().contains(item.getType())) {
			return "Decoration";
		} else if (AuctionHouseX.getInstance().getCategoryManager().getFoodCategory().contains(item.getType())) {
			return "Food";
		} else if (AuctionHouseX.getInstance().getCategoryManager().getRtCategory().contains(item.getType())) {
			return "Redstone+Transportation";
		} else if (AuctionHouseX.getInstance().getCategoryManager().getProductiveCategory().contains(item.getType())) {
			return "Productive";
		} else if (AuctionHouseX.getInstance().getCategoryManager().getToolsCategory().contains(item.getType())) {
			return "Tools";
		}

		return null;
	}

	public void sellItem(Player p, ItemStack item, double price) {
		//> Check if item is blacklisted
		if (!isBlacklisted(item)) {
			if (item.getType() == Material.ENCHANTED_BOOK) {
				//> Categorize enchanted book under Enchantment

			} else {
				//> Categorize item from Lists
				String category = getItemCategory(item);

				if (category != null) {
					File categoryFile = new File("plugins/AuctionHouseX/Auctionhouse/" + category + ".yml");
					FileConfiguration categoryFileCfg = YamlConfiguration.loadConfiguration(categoryFile);


				} else {
					p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§cThe item you try to sell is not categorized!");
				}
			}
		} else {
			p.sendMessage(AuctionHouseX.getInstance().getMessage().prefix + "§cThe item you try to sell is blacklisted!");
		}
	}
}
