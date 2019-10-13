package dev.mrmarshall.auctionhousex.items;

import dev.mrmarshall.auctionhousex.AuctionHouseX;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class EnchantingBottle {

	private Map<UUID, Integer> thrownExpBottle = new HashMap<>();

	public ItemStack getItem(int exp) {
		List<String> lore = new ArrayList<>();
		lore.add("§7Contains " + exp + " EXP");
		ItemStack item = AuctionHouseX.getInstance().getItemCreator().create(Material.EXPERIENCE_BOTTLE, "§eBottle o' Enchanting", lore, true);
		return item;
	}

	public void addThrownExpBottle(UUID pUUID, int amount) {
		thrownExpBottle.put(pUUID, amount);
	}

	public Map<UUID, Integer> getThrownExpBottle() {
		return thrownExpBottle;
	}
}
