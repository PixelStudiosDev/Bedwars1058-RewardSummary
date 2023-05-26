package me.leoo.bedwars.rewardsummary.utils;

import com.andrei1058.bedwars.BedWars;
import com.google.common.base.Strings;
import me.leoo.bedwars.rewardsummary.configuration.Config;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SummaryUtil {

    public static HashMap<Player, Integer> startMoney = new HashMap<>();
    public static HashMap<Player, Integer> endMoney = new HashMap<>();

    public static HashMap<Player, Integer> startExp = new HashMap<>();
    public static HashMap<Player, Integer> endExp = new HashMap<>();
    public static HashMap<Player, Integer> levelUpExp = new HashMap<>();

    public static Integer getEarnedMoney(Player player) {
        if (!endMoney.containsKey(player) || !startMoney.containsKey(player)) return 0;
        return endMoney.get(player) - startMoney.get(player);
    }

    public static Integer getEarnedExp(Player player) {
        if (!endExp.containsKey(player) || !startExp.containsKey(player) || !levelUpExp.containsKey(player)) return 0;
        return endExp.get(player) - startExp.get(player) + levelUpExp.get(player);
    }

    public static String getProgressBar(int current, int max, int totalBars, char symbol, String completedColor, String notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        return Strings.repeat("" + completedColor + symbol, progressBars)
                + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }

    public static String getPrestigeText(Player p) {
        String required = "Level " + (BedWars.getLevelSupport().getPlayerLevel(p) + 1);
        int lvl = BedWars.getLevelSupport().getPlayerLevel(p) + 1;
        for (String colorString : Config.config.getList("reward_summary.new_level.text")) {
            String[] str = colorString.split(":");
            int str1 = Integer.valueOf(str[0].split("-")[0]);
            int str2 = Integer.valueOf(str[0].split("-")[1]);
            if (lvl >= str1 && lvl <= str2) {
                String str3 = str[1].replace("{level}", ("Level " + (BedWars.getLevelSupport().getPlayerLevel(p) + 1)));
                required = str3;
            }
        }
        return required;
    }

    public static void sendMsg(Player p) {
        for (String s : Config.config.getList("reward_summary.message")) {
            String s2 = Config.config.getString("reward_summary.progress_bar.symbol");
            char c = s2.charAt(0);
            int percentage = BedWars.getLevelSupport().getCurrentXp(p) * 100 / 10000;
            double finalPercentage = Math.round(percentage * 10.0) / 10.0;
            s = s.replace("{coinsEarned}", String.valueOf(getEarnedMoney(p)))
                    .replace("{currentLevel}", String.valueOf(BedWars.getLevelSupport().getPlayerLevel(p)))
                    .replace("{nextLevelText}", getPrestigeText(p))
                    .replace("{progressBar}", getProgressBar(
                            BedWars.getLevelSupport().getCurrentXp(p),
                            BedWars.getLevelSupport().getRequiredXp(p),
                            34,
                            c,
                            ChatColor.translateAlternateColorCodes('&', Config.config.getString("reward_summary.progress_bar.unlocked-color")),
                            ChatColor.translateAlternateColorCodes('&', Config.config.getString("reward_summary.progress_bar.locked-color"))
                    ))
                    .replace("{currentXp}", String.valueOf(BedWars.getLevelSupport().getCurrentXp(p)))
                    .replace("{requiredXp}", String.valueOf(BedWars.getLevelSupport().getRequiredXp(p)))
                    .replace("{percentage}", String.valueOf(finalPercentage))
                    .replace("{expEarned}", String.valueOf(getEarnedExp(p)))
            ;
            p.sendMessage(s);
        }
    }
}
