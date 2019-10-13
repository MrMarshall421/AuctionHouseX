package dev.mrmarshall.auctionhousex.util;

import net.minecraft.server.v1_14_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.regex.Pattern;

public class Message {

	public String prefix = "§bAuctionHouse§6§lX §7» ";
	public String noPermission = "§cYou have no permission for this command.";

	public void replaceItem(Player p, String originalMessage) {
		ItemStack is = p.getInventory().getItemInMainHand();
		if (!is.getType().equals(Material.AIR)) {
			net.minecraft.server.v1_14_R1.ItemStack b = CraftItemStack.asNMSCopy(is);

			NBTTagCompound c = new NBTTagCompound();

			b.save(c);

			ChatComponentText d = null;

			String item = "§f[%item%§f x%amount%§f] §r";
			item = item.replace("%item%", b.getName().getText());
			item = item.replace("%amount%", String.valueOf(b.getCount()));

			originalMessage = originalMessage.replaceFirst(Pattern.quote("*item"), item);
			d = new ChatComponentText(originalMessage);

			ChatModifier g = d.getChatModifier();

			g.setChatHoverable(new ChatHoverable(ChatHoverable.EnumHoverAction.SHOW_ITEM, new ChatComponentText(c.toString())));

			d.setChatModifier(g);

			PlayerList h = MinecraftServer.getServer().getPlayerList();
			for (Player i : Bukkit.getOnlinePlayers()) {
				h.getPlayer(i.getName()).sendMessage(d);
			}
		}
	}
}
