package me.leoo.bedwars.rewardsummary.listeners;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import me.leoo.bedwars.rewardsummary.RewardSummary;
import me.leoo.bedwars.rewardsummary.reward.RewardPlayer;
import me.leoo.bedwars.rewardsummary.utils.SummaryUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameStartListener implements Listener {

    private static final Map<IArena, List<RewardPlayer>> players = SummaryUtils.getPlayers();

    @EventHandler
    public void GameStateChangeEvent(GameStateChangeEvent event) {
        if (event.getNewState().equals(GameState.playing)) {

            IArena arena = event.getArena();

            if (players.containsKey(arena)) {
                players.get(arena).clear();
            } else {
                players.put(arena, new ArrayList<>());
            }

            for (Player player : event.getArena().getPlayers()) {
                players.get(arena).add(new RewardPlayer(
                        player.getUniqueId(),
                        (int) RewardSummary.get().getEconomy().getBalance(player),
                        BedWars.getLevelSupport().getCurrentXp(player)
                ));
            }
        }
    }
}
