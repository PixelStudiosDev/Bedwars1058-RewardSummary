package me.leoo.bedwars.rewardsummary.listeners;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import me.leoo.bedwars.rewardsummary.RewardSummary;
import me.leoo.bedwars.rewardsummary.reward.RewardPlayer;
import me.leoo.bedwars.rewardsummary.utils.SummaryUtils;
import me.leoo.utils.bukkit.task.Tasks;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.Map;

public class GameEndListener implements Listener {

	private static final Map<IArena, List<RewardPlayer>> players = SummaryUtils.getPlayers();

	@EventHandler
	public void GameEndEvent(GameEndEvent event){
		IArena arena = event.getArena();
		Economy economy = RewardSummary.get().getEconomy();

		for(Player player : arena.getPlayers()){
			Tasks.later(() -> {
				players.get(arena).forEach(rewardPlayer -> {
					if(rewardPlayer.getUuid().equals(player.getUniqueId())){
						rewardPlayer.setEndMoney((int) economy.getBalance(player));
						rewardPlayer.setEndExp(BedWars.getLevelSupport().getCurrentXp(player));

						SummaryUtils.sendMsg(player);
					}
				});
			}, 20L);
		}
	}
}
