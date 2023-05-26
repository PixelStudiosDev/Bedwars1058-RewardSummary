package me.leoo.bedwars.rewardsummary.listeners;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.andrei1058.bedwars.api.events.player.PlayerLevelUpEvent;
import com.andrei1058.bedwars.arena.Arena;
import me.leoo.bedwars.rewardsummary.Main;
import me.leoo.bedwars.rewardsummary.utils.SummaryUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LevelUp implements Listener {

	@EventHandler
	public void LevelUpEvent(PlayerLevelUpEvent e){
		Player player = e.getPlayer();

		if(Arena.getArenaByPlayer(player) != null){
			SummaryUtil.levelUpExp.put(player, SummaryUtil.levelUpExp.get(player) + BedWars.getLevelSupport().getRequiredXp(player));
		}
	}
}
