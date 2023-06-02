package me.leoo.bedwars.rewardsummary.utils;

import com.andrei1058.bedwars.BedWars;
import com.google.common.base.Strings;
import lombok.Getter;
import me.leoo.bedwars.rewardsummary.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.HashMap;

public class SummaryUtils {

    @Getter
    private static final HashMap<Player, Integer> startMoney = new HashMap<>();
    @Getter
    private static final HashMap<Player, Integer> endMoney = new HashMap<>();

    @Getter
    private static final HashMap<Player, Integer> startExp = new HashMap<>();
    @Getter
    private static final HashMap<Player, Integer> endExp = new HashMap<>();

    @Getter
    private static final HashMap<Player, Integer> levelUpExp = new HashMap<>();

    public void sendMsg(Player player) {
        for (String s : Main.getPlugin().getMainConfig().getList("reward_summary.message")) {

            DecimalFormat decimalFormat = new DecimalFormat("###.##");
            String percentage = decimalFormat.format(((double) BedWars.getLevelSupport().getCurrentXp(player) / BedWars.getLevelSupport().getRequiredXp(player)) * 100);

            s = s.replace("{coinsEarned}", String.valueOf(getEarnedMoney(player)))
                    .replace("{currentLevel}", String.valueOf(BedWars.getLevelSupport().getPlayerLevel(player)))
                    .replace("{nextLevelText}", getPrestigeText(player))
                    .replace("{progressBar}", getProgressBar(
                            BedWars.getLevelSupport().getCurrentXp(player),
                            BedWars.getLevelSupport().getRequiredXp(player),
                            Main.getPlugin().getMainConfig().getString("reward_summary.progress_bar.symbol").charAt(0),
                            ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getMainConfig().getString("reward_summary.progress_bar.unlocked-color")),
                            ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getMainConfig().getString("reward_summary.progress_bar.locked-color"))
                    ))
                    .replace("{currentXp}", String.valueOf(BedWars.getLevelSupport().getCurrentXp(player)))
                    .replace("{requiredXp}", String.valueOf(BedWars.getLevelSupport().getRequiredXp(player)))
                    .replace("{percentage}", percentage)
                    .replace("{expEarned}", String.valueOf(getEarnedExp(player)))
            ;
            player.sendMessage(s);
        }
    }

    private Integer getEarnedMoney(Player player) {
        if (!endMoney.containsKey(player) || !startMoney.containsKey(player)) return 0;
        return endMoney.get(player) - startMoney.get(player);
    }

    private Integer getEarnedExp(Player player) {
        if (!endExp.containsKey(player) || !startExp.containsKey(player) || !levelUpExp.containsKey(player)) return 0;
        return endExp.get(player) - startExp.get(player) + levelUpExp.get(player);
    }

    private String getProgressBar(int current, int max, char symbol, String completedColor, String notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (34 * percent);

        return Strings.repeat("" + completedColor + symbol, progressBars)
                + Strings.repeat("" + notCompletedColor + symbol, 34 - progressBars);
    }

    private String getPrestigeText(Player player) {
        String required = Main.getPlugin().getMainConfig().getString("reward_summary.new_level.level_name") + (BedWars.getLevelSupport().getPlayerLevel(player) + 1);

        int level = BedWars.getLevelSupport().getPlayerLevel(player) + 1;

        for (String colorString : Main.getPlugin().getMainConfig().getList("reward_summary.new_level.text")) {
            String[] str = colorString.split(":");
            int str1 = Integer.parseInt(str[0].split("-")[0]);
            int str2 = Integer.parseInt(str[0].split("-")[1]);
            if (level >= str1 && level <= str2) {
                required = str[1].replace("{level}", (Main.getPlugin().getMainConfig().getString("reward_summary.new_level.level_name") + " " + (BedWars.getLevelSupport().getPlayerLevel(player) + 1)));
            }
        }
        return required;
    }
}
