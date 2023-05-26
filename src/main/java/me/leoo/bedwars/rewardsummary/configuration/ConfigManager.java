package me.leoo.bedwars.rewardsummary.configuration;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class ConfigManager {
	private YamlConfiguration yml;
	private File config;
	private String name;

	public ConfigManager(Plugin plugin, String name, String dir) {
		File d = new File(dir);
		if (!d.exists() && !d.mkdirs()) {
			plugin.getLogger().log(Level.SEVERE, "Could not create " + d.getPath());
		} else {
			this.config = new File(dir, name + ".yml");
			if (!this.config.exists()) {
				plugin.getLogger().log(Level.INFO, "Creating " + this.config.getPath());

				try {
					if (!this.config.createNewFile()) {
						plugin.getLogger().log(Level.SEVERE, "Could not create " + this.config.getPath());
						return;
					}
				} catch (IOException var6) {
					var6.printStackTrace();
				}
			}

			this.yml = YamlConfiguration.loadConfiguration(this.config);
			this.yml.options().copyDefaults(true);
			this.name = name;
		}
	}

	public void reload() {
		this.yml = YamlConfiguration.loadConfiguration(this.config);
	}

	public void set(String path, Object value) {
		this.yml.set(path, value);
		this.save();
	}

	public YamlConfiguration getYml() {
		return this.yml;
	}

	public void save() {
		try {
			this.yml.save(this.config);
		} catch (IOException var2) {
			var2.printStackTrace();
		}

	}

	public List<String> getList(String path) {
		return (List)this.yml.getStringList(path).stream().map((s) -> {
			return s.replace("&", "§");
		}).collect(Collectors.toList());
	}

	public boolean getBoolean(String path) {
		return this.yml.getBoolean(path);
	}

	public int getInt(String path) {
		return this.yml.getInt(path);
	}

	public String getString(String path) {
		return ChatColor.translateAlternateColorCodes('&', this.yml.getString(path));
	}
}
