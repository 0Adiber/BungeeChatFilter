package at.BungeeChatFilter;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class EventChat implements Listener{
	
	public EventChat(Plugin plugin) {
		ProxyServer.getInstance().getPluginManager().registerListener(plugin, this);
	}
	
	@EventHandler
	public void onChat(ChatEvent event) {
		if(!(event.getSender() instanceof ProxiedPlayer)) {
			return;
		}
		
		if(event.getMessage().trim().startsWith("/"))
			return;
				
		ProxiedPlayer player = (ProxiedPlayer)event.getSender();
		if(Main.cooldowns.containsKey(player)) {
			if(System.currentTimeMillis() - Main.cooldowns.get(player) < Main.COOLDOWN) {
				player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', Main.TOO_FAST)));
				event.setCancelled(true);
				return;
			} else {
				Main.cooldowns.remove(player);
			}
		}
		
		if(event.getMessage().length() > 10) {
			int counter = 0;
			for(char c : event.getMessage().toCharArray()) {
				if(c >= 'A' && c <= 'Z')
					counter++;
			}
			if((double)counter/event.getMessage().toCharArray().length > (Main.CAPS_PERCENT/100.)) {
				player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', Main.ALL_CAPS)));
				event.setCancelled(true);
				return;
			}
		}
		
		event.setMessage(" " + event.getMessage() + " ");
		
		for(String s : Main.FilterRegexs) {
						
			String after = event.getMessage().replaceAll(s, "f");
			
			if(!event.getMessage().equals(after)) {
				player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', Main.WARNING)));
				event.setCancelled(true);
				return;
			}
			
		}
		
		event.setMessage(event.getMessage().trim());
	}

}
