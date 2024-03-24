package me.leoo.bedwars.rewardsummary.utils;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.levels.Level;
import com.google.common.base.Strings;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import me.leoo.bedwars.rewardsummary.RewardSummary;
import me.leoo.bedwars.rewardsummary.reward.RewardPlayer;
import me.leoo.utils.bukkit.config.ConfigManager;
import me.leoo.utils.common.number.NumberUtil;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
public class SummaryUtils {

    @Getter
    private static final Map<IArena, List<RewardPlayer>> players = new HashMap<>();

    private static final ConfigManager config = RewardSummary.get().getMainConfig();
    private static final Level levelSupport = BedWars.getLevelSupport();

    public void sendMsg(Player player) {
        for (String s : RewardSummary.get().getMainConfig().getList("reward_summary.message")) {

            int percentage = NumberUtil.getPercentage(levelSupport.getCurrentXp(player), levelSupport.getRequiredXp(player));

            s = s.replace("{coinsEarned}", String.valueOf(getEarnedMoney(player)))
                    .replace("{currentLevel}", String.valueOf(levelSupport.getPlayerLevel(player)))
                    .replace("{nextLevelText}", getPrestigeText(player))
                    .replace("{progressBar}", getProgressBar(
                            levelSupport.getCurrentXp(player),
                            levelSupport.getRequiredXp(player),
                            config.getString("reward_summary.progress_bar.symbol").charAt(0),
                            config.getString("reward_summary.progress_bar.unlocked-color"),
                            config.getString("reward_summary.progress_bar.locked-color")
                    ))
                    .replace("{currentXp}", String.valueOf(levelSupport.getCurrentXp(player)))
                    .replace("{requiredXp}", String.valueOf(levelSupport.getRequiredXp(player)))
                    .replace("{percentage}", String.valueOf(percentage))
                    .replace("{expEarned}", String.valueOf(getEarnedExp(player)))
            ;
            player.sendMessage(s);
        }
    }

    public RewardPlayer getRewardPlayer(Player player) {
        for (List<RewardPlayer> playerList : players.values()) {
            for (RewardPlayer rewardPlayer : playerList) {
                if (rewardPlayer.getUuid().equals(player.getUniqueId())) return rewardPlayer;
            }
        }

        return null;
    }

    private int getEarnedMoney(Player player) {
        RewardPlayer rewardPlayer = getRewardPlayer(player);
        if(rewardPlayer == null) return 0;

        return rewardPlayer.getEndMoney() - rewardPlayer.getStartMoney();
    }

    private int getEarnedExp(Player player) {
        RewardPlayer rewardPlayer = getRewardPlayer(player);
        if(rewardPlayer == null) return 0;

        return rewardPlayer.getEndExp() - rewardPlayer.getStartExp() + rewardPlayer.getLevelUpExp();
    }

    private String getProgressBar(int current, int max, char symbol, String completedColor, String notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (34 * percent);

        return Strings.repeat(completedColor + symbol, progressBars)
                + Strings.repeat(notCompletedColor + symbol, 34 - progressBars);
    }

    private String getPrestigeText(Player player) {
        String required = RewardSummary.get().getMainConfig().getString("reward_summary.new_level.level_name") + (levelSupport.getPlayerLevel(player) + 1);

        int level = levelSupport.getPlayerLevel(player) + 1;

        for (String colorString : RewardSummary.get().getMainConfig().getList("reward_summary.new_level.text")) {
            String[] str = colorString.split(":");
            int str1 = Integer.parseInt(str[0].split("-")[0]);
            int str2 = Integer.parseInt(str[0].split("-")[1]);
            if (level >= str1 && level <= str2) {
                required = str[1].replace("{level}", (RewardSummary.get().getMainConfig().getString("reward_summary.new_level.level_name") + " " + (levelSupport.getPlayerLevel(player) + 1)));
            }
        }

        return required;
    }
}
