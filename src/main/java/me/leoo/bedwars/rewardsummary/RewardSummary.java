package me.leoo.bedwars.rewardsummary;

import lombok.Getter;
import me.leoo.bedwars.rewardsummary.configuration.Config;
import me.leoo.bedwars.rewardsummary.listeners.GameEndListener;
import me.leoo.bedwars.rewardsummary.listeners.GameStartListener;
import me.leoo.bedwars.rewardsummary.listeners.LevelUpListener;
import me.leoo.utils.bukkit.config.ConfigManager;
import me.leoo.utils.bukkit.events.Events;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class RewardSummary extends JavaPlugin {

    private static RewardSummary plugin;

    private ConfigManager mainConfig;
    private Economy economy;

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

        if (!vaultHook()) {
            getLogger().severe("Could not hook into Vault. Disabling...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        mainConfig = new Config("config", "plugins/BedWars1058/Addons/RewardSummary");

        registerEvents();

        getLogger().info(getDescription().getName() + " plugin by itz_leoo has been successfully enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info(getDescription().getName() + " plugin by itz_leoo has been successfully disabled.");
    }

    private void registerEvents() {
        Events.register(new GameStartListener(), new GameEndListener(), new LevelUpListener());
    }

    private boolean vaultHook() {
        RegisteredServiceProvider<Economy> provider = getServer().getServicesManager().getRegistration(Economy.class);
        if (provider == null) {
            return false;
        }

        economy = provider.getProvider();

        return economy != null;
    }

    public static RewardSummary get() {
        return plugin;
    }
}
