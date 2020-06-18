package at.BungeeChatFilter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Main extends Plugin{

	public static List<String> FilterRegexs = new ArrayList<>();
	
	public static HashMap<ProxiedPlayer, Long> cooldowns = new HashMap<>();
	
	public static Main main;

	public static String PREFIX = "§7[§3BungeeChatFilter§7] ",TOO_FAST,ALL_CAPS,WARNING,TOO_FEW_ARGS,RELOADED;
	public static int COOLDOWN,CAPS_PERCENT;

	private List<String> configStrings = new ArrayList<>();

	@Override
	public void onEnable() {
		main = this;

		configStrings = Arrays.asList("filter", "messages.prefix", "messages.typingtoofast", "messages.caps", "messages.warning", "settings.cooldown", "settings.capspercent", "messages.toofewargs", "messages.reloaded");

		load();
		new EventChat(this);
		getProxy().getPluginManager().registerCommand(this, new Commands("bcf"));
		getLogger().info(ChatColor.AQUA + "Enabled BungeeChatFilter");
	}
	
	protected static final File file = new File("plugins/UltimateWars", "config.yml");
	protected static Configuration cfg;
	
	public void load() {		
		if(!getDataFolder().exists())
			getDataFolder().mkdir();
		
		File f = new File(getDataFolder(), "config.yml");
		if(!f.exists()) {
			try(InputStream in = getResourceAsStream("config.yml")) {
				Files.copy(in, f.toPath());
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		for(String s : configStrings)
			if(!cfg.contains(s)) {
				getLogger().severe("[BungeeChatFilter] Config is not correct!!!");
				return;
			}

		FilterRegexs = cfg.getStringList("filter");
		PREFIX = cfg.getString("messages.prefix");
		TOO_FAST = cfg.getString("messages.typingtoofast");
		ALL_CAPS = cfg.getString("messages.caps");
		WARNING = cfg.getString("messages.warning");
		COOLDOWN = cfg.getInt("settings.cooldown");
		CAPS_PERCENT = cfg.getInt("settings.capspercent");
		TOO_FEW_ARGS = cfg.getString("messages.toofewargs");
		RELOADED = cfg.getString("messages.reloaded");
	}
	
}
