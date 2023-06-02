package me.leoo.bedwars.rewardsummary.listeners;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import me.leoo.bedwars.rewardsummary.Main;
import me.leoo.bedwars.rewardsummary.utils.SummaryUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameStart implements Listener {

	@EventHandler
	public void GameStateChangeEvent(GameStateChangeEvent event){
		if(event.getNewState().equals(GameState.playing)){

			SummaryUtils.getStartMoney().clear();
			SummaryUtils.getStartExp().clear();

			SummaryUtils.getEndMoney().clear();
			SummaryUtils.getEndExp().clear();
			SummaryUtils.getLevelUpExp().clear();

			IArena arena = event.getArena();
			Economy economy = Main.getPlugin().getEconomy();
			for(Player player : arena.getPlayers()){
				SummaryUtils.getStartMoney().put(player, (int) economy.getBalance(player));
				SummaryUtils.getStartExp().put(player, BedWars.getLevelSupport().getCurrentXp(player));
				SummaryUtils.getLevelUpExp().put(player, 0);
			}
		}
	}
}
