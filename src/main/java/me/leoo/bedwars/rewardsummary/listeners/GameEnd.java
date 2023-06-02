package me.leoo.bedwars.rewardsummary.listeners;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import me.leoo.bedwars.rewardsummary.Main;
import me.leoo.bedwars.rewardsummary.utils.SummaryUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameEnd implements Listener {

	@EventHandler
	public void GameEndEvent(GameEndEvent event){
		IArena arena = event.getArena();
		Economy economy = Main.getPlugin().getEconomy();

		for(Player player : arena.getPlayers()){
			Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
				SummaryUtils.getEndMoney().put(player, (int) economy.getBalance(player));
				SummaryUtils.getEndExp().put(player, BedWars.getLevelSupport().getCurrentXp(player));
				Main.getPlugin().getUtils().sendMsg(player);
			}, 20L);
		}
	}
}
