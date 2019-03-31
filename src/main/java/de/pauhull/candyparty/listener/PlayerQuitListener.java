package de.pauhull.candyparty.listener;

import de.pauhull.candyparty.CandyParty;
import de.pauhull.candyparty.phase.GamePhase;
import de.pauhull.candyparty.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Paul
 * on 22.03.2019
 *
 * @author pauhull
 */
public class PlayerQuitListener implements Listener {

    private CandyParty candyParty;

    public PlayerQuitListener(CandyParty candyParty) {
        this.candyParty = candyParty;
        Bukkit.getPluginManager().registerEvents(this, candyParty);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        //candyParty.getSpectators().remove(player);

        if (candyParty.getPhaseHandler().getActivePhaseType() == GamePhase.Type.LOBBY) {

            event.setQuitMessage(Messages.PREFIX + "§e" + player.getName() + "§7 hat das Spiel §cverlassen§7! §8[§e"
                    + (Bukkit.getOnlinePlayers().size() - 1) + "§8/§e" + candyParty.getMaxPlayers() + "§8]");

        } else {
            //if (candyParty.getSpectators().contains(player)) {
            event.setQuitMessage(Messages.PREFIX + "§e" + player.getName() + "§7 hat das Spiel §cverlassen§7!");
            //} else {
            //    event.setQuitMessage(null);
            //}
        }
    }


}
