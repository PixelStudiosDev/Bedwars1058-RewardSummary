package me.leoo.bedwars.rewardsummary.listeners;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import me.leoo.bedwars.rewardsummary.Main;
import me.leoo.bedwars.rewardsummary.utils.SummaryUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameStart implements Listener {

	@EventHandler
	public void GameStateChangeEvent(GameStateChangeEvent e){

		if(e.getNewState().equals(GameState.playing)){
			SummaryUtil.startMoney.clear();
			SummaryUtil.startExp.clear();

			SummaryUtil.endMoney.clear();
			SummaryUtil.endExp.clear();
			SummaryUtil.levelUpExp.clear();

			IArena arena = e.getArena();
			Economy economy = Main.getEconomy();
			for(Player player : arena.getPlayers()){
				SummaryUtil.startMoney.put(player, (int) economy.getBalance(player));
				SummaryUtil.startExp.put(player, BedWars.getLevelSupport().getCurrentXp(player));
				SummaryUtil.levelUpExp.put(player, 0);
			}
		}
	}
}
