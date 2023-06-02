package me.leoo.bedwars.rewardsummary.listeners;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.events.player.PlayerLevelUpEvent;
import com.andrei1058.bedwars.arena.Arena;
import me.leoo.bedwars.rewardsummary.utils.SummaryUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LevelUp implements Listener {

	@EventHandler
	public void LevelUpEvent(PlayerLevelUpEvent event){
		Player player = event.getPlayer();

		if(Arena.getArenaByPlayer(player) != null){
			if(SummaryUtils.getLevelUpExp().containsKey(player)){
				SummaryUtils.getLevelUpExp().put(player, SummaryUtils.getLevelUpExp().get(player) + BedWars.getLevelSupport().getRequiredXp(player));
			}
		}
	}
}
