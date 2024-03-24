package me.leoo.bedwars.rewardsummary.listeners;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.events.player.PlayerLevelUpEvent;
import com.andrei1058.bedwars.arena.Arena;
import me.leoo.bedwars.rewardsummary.reward.RewardPlayer;
import me.leoo.bedwars.rewardsummary.utils.SummaryUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.Map;

public class LevelUpListener implements Listener {

    private static final Map<IArena, List<RewardPlayer>> players = SummaryUtils.getPlayers();

    @EventHandler
    public void LevelUpEvent(PlayerLevelUpEvent event) {
        Player player = event.getPlayer();
        IArena arena = Arena.getArenaByPlayer(player);

        if (arena == null) return;

        players.get(arena).forEach(rewardPlayer -> {
            if (rewardPlayer.getUuid().equals(player.getUniqueId())) {
                rewardPlayer.setLevelUpExp(rewardPlayer.getLevelUpExp() + BedWars.getLevelSupport().getRequiredXp(player));

                SummaryUtils.sendMsg(player);
            }
        });
    }
}
