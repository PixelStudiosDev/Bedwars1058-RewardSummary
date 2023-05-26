package me.leoo.bedwars.rewardsummary.listeners;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import me.leoo.bedwars.rewardsummary.Main;
import me.leoo.bedwars.rewardsummary.utils.SummaryUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameEnd implements Listener {

	@EventHandler
	public void GameEndEvent(GameEndEvent e){

		IArena arena = e.getArena();
		Economy economy = Main.getEconomy();
		for(Player player : arena.getPlayers()){
			Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
				SummaryUtil.endMoney.put(player, (int) economy.getBalance(player));
				SummaryUtil.endExp.put(player, BedWars.getLevelSupport().getCurrentXp(player));
				SummaryUtil.sendMsg(player);
			}, 20L);
		}
	}
}
