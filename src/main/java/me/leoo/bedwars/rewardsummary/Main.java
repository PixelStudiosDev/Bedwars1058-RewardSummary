package me.leoo.bedwars.rewardsummary;

import lombok.Getter;
import me.leoo.bedwars.rewardsummary.configuration.Config;
import me.leoo.bedwars.rewardsummary.configuration.ConfigManager;
import me.leoo.bedwars.rewardsummary.listeners.GameEnd;
import me.leoo.bedwars.rewardsummary.listeners.GameStart;
import me.leoo.bedwars.rewardsummary.listeners.LevelUp;
import me.leoo.bedwars.rewardsummary.utils.SummaryUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

@Getter
public class Main extends JavaPlugin {

    @Getter
    private static Main plugin;
    private ConfigManager mainConfig;
    private Economy economy;
    private final SummaryUtils utils = new SummaryUtils();

    @Override
    public void onEnable() {
        plugin = this;

        if (!Bukkit.getPluginManager().isPluginEnabled("BedWars1058")) {
            getLogger().severe("BedWars1058 was not found. Disabling...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        if (!Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            getLogger().severe("Vault was not found. Disabling...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        vaultHook();

        mainConfig = new Config(this, "config", getDataFolder().getPath());

        registerEvents();

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&aBedWars1058 Reward Summary addon has been successfully enabled."));
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cBedWars1058 Reward Summary addon has been successfully disabled."));
    }

    private void registerEvents() {
        List.of(new GameStart(), new GameEnd(), new LevelUp())
                .forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
    }

    private void vaultHook() {
        RegisteredServiceProvider<Economy> provider = getServer().getServicesManager().getRegistration(Economy.class);
        if (provider == null) {
            return;
        }
        economy = provider.getProvider();
    }
}
