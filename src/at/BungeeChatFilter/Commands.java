package at.BungeeChatFilter;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Commands extends Command{

	public Commands(String name) {
		super(name);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer) {
			if(!((ProxiedPlayer)sender).hasPermission("chatfilter.reload")) {
				return;
			}
		}
		if(args.length == 0) {
			sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',Main.TOO_FEW_ARGS)));
			return;
		}
		if(args[0].equals("reload")) {
			Main.main.load();
			sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',Main.RELOADED)));
		} else {
			sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',Main.TOO_FEW_ARGS)));
		}
	}

}
