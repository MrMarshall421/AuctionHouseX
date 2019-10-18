package dev.mrmarshall.auctionhousex.managers;

import dev.mrmarshall.auctionhousex.AuctionHouseX;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuctionhouseManager {

	private Map<UUID, ItemStack> confirmListingPrice;

	private File instruction;
	private FileConfiguration instructionCfg;

	public AuctionhouseManager() {
		instruction = new File("plugins/AuctionHouseX/Auctionhouse/instruction.yml");
		instructionCfg = YamlConfiguration.loadConfiguration(instruction);
		loadAuctionhouseFiles();

		confirmListingPrice = new HashMap<>();
	}

	private void loadAuctionhouseFiles() {
		AuctionHouseX.getInstance().getFileManager().loadAuctionhouseFiles(instruction, instructionCfg);
	}

	public ItemStack getInstructionBook() {
		return AuctionHouseX.getInstance().getFileManager().getInstructionBook(instruction, instructionCfg);
	}

	public void sellItem(Player p, ItemStack item, double price) {

	}

	public Map<UUID, ItemStack> getConfirmListingPrice() { return confirmListingPrice; }
}
