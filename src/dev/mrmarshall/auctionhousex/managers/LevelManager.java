package dev.mrmarshall.auctionhousex.managers;

import org.bukkit.entity.Player;

public class LevelManager {

	public void setTotalExperience(Player p, int exp) {
		p.setExp(0.0F);
		p.setLevel(0);
		p.setTotalExperience(0);

		int amount = exp;
		while (amount > 0) {
			int expToLevel = getExpAtLevel(p);
			amount -= expToLevel;
			if (amount >= 0) {
				p.giveExp(expToLevel);
				continue;
			}
			amount += expToLevel;
			p.giveExp(amount);
			amount = 0;
		}
	}

	public int getTotalExperience(Player p) {
		int exp = Math.round(getExpAtLevel(p) * p.getExp());
		int currentLevel = p.getLevel();

		while (currentLevel > 0) {
			currentLevel--;
			exp += getExpAtLevel(currentLevel);
		}
		if (exp < 0) {
			exp = Integer.MAX_VALUE;
		}
		return exp;
	}

	private int getExpAtLevel(Player p) {
		return getExpAtLevel(p.getLevel());
	}

	public int getExpAtLevel(int level) {
		if (level <= 15) {
			return 2 * level + 7;
		}
		if (level >= 16 && level <= 30) {
			return 5 * level - 38;
		}
		return 9 * level - 158;
	}
}
