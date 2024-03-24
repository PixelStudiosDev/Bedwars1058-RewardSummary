package me.leoo.bedwars.rewardsummary.configuration;

import me.leoo.bedwars.rewardsummary.RewardSummary;
import me.leoo.utils.bukkit.config.ConfigManager;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Arrays;

public class Config extends ConfigManager {

    public Config(String name, String directory) {
        super(name, directory);

        YamlConfiguration yml = getYml();

        yml.options().header(RewardSummary.get().getDescription().getName() + " v" + RewardSummary.get().getDescription().getVersion() + " made by " + RewardSummary.get().getDescription().getAuthors() + ".\n" +
                "Dependencies: " + RewardSummary.get().getDescription().getDepend() + ".\n" +
                "SoftDependencies: " + RewardSummary.get().getDescription().getSoftDepend() + ".\n" +
                "Join my discord for support: https://pixelstudios.dev/discord\n");

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
