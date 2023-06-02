package me.leoo.bedwars.rewardsummary.configuration;

import me.leoo.bedwars.rewardsummary.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class Config extends ConfigManager {

    public Config(Plugin plugin, String name, String directory) {
        super(plugin, name, directory);

        YamlConfiguration yml = getYml();

        yml.options().header(Main.getPlugin().getDescription().getName() + " v" + Main.getPlugin().getDescription().getVersion() + " made by " + Main.getPlugin().getDescription().getAuthors() + ".\n" +
                "Dependencies: " + Main.getPlugin().getDescription().getDepend() + ".\n" +
                "SoftDependencies: " + Main.getPlugin().getDescription().getSoftDepend() + ".\n" +
                "Join my discord for support: https://discord.gg/dtwanz4GQg\n");

        yml.addDefault("reward_summary.message", Arrays.asList(
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "                                   &f&lReward Summary",
                "",
                "   &7You earned",
                "    &f• &6{coinsEarned} Bed Wars Coins",
                "",
                "                         &bBed Wars Experience",
                "             &bLevel {currentLevel}                           {nextLevelText}",
                "             &8[&b{progressBar}&8]",
                "                          &b{currentXp} &7/ &a{requiredXp} &7({percentage}%)",
                "",
                "&7You earned &b{expEarned} Bed Wars Experience",
                "",
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        yml.addDefault("reward_summary.progress_bar.symbol", "■");
        yml.addDefault("reward_summary.progress_bar.unlocked-color", "&b");
        yml.addDefault("reward_summary.progress_bar.locked-color", "&7");

        yml.addDefault("reward_summary.new_level.level_name", "Level");
        yml.addDefault("reward_summary.new_level.text", Arrays.asList("0-99:{level}",
                "100-100:&fIron Prestige",
                "101-199:{level}",
                "200-200:&6Gold Prestige",
                "201-299:{level}",
                "300-300:&bAqua Prestige",
                "301-1000000:{level}"
        ));

        yml.options().copyDefaults(true);
        save();
    }
}
