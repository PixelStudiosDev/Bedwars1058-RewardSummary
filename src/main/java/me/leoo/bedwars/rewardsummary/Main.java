package me.leoo.bedwars.rewardsummary;

import lombok.Getter;
import me.leoo.bedwars.rewardsummary.configuration.Config;
import me.leoo.bedwars.rewardsummary.configuration.ConfigManager;
import me.leoo.bedwars.rewardsummary.listeners.GameEnd;
import me.leoo.bedwars.rewardsummary.listeners.GameStart;
import me.leoo.bedwars.rewardsummary.listeners.LevelUp;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Main extends JavaPlugin {

    @Getter
    private static Main plugin;
    private ConfigManager config;
    private Economy economy;

    @Override
    public void onEnable() {
        plugin = this;

        if (Bukkit.getPluginManager().getPlugin("BedWars1058") == null) {
            getLogger().severe("BedWars1058 was not found. Disabling...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            getLogger().severe("Vault was not found. Disabling...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        Config.setupConfig();
        registerCommands();
        registerEvents();

        vaultHook();

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&aBedWars1058 Reward Summary addon has been successfully enabled."));
    }

    @Override
    public void onDisable() {
        Bukkit.getPluginManager().disablePlugin((Plugin) this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cBedWars1058 Reward Summary addon has been successfully disabled."));
    }

    private void registerCommands() {
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new GameEnd(), this);
        Bukkit.getPluginManager().registerEvents(new GameStart(), this);
        Bukkit.getPluginManager().registerEvents(new LevelUp(), this);
    }

    private boolean vaultHook() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }
}
