package de.pauhull.candyparty.listener;

import de.pauhull.candyparty.CandyParty;
import de.pauhull.candyparty.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Paul
 * on 22.03.2019
 *
 * @author pauhull
 */
public class PlayerJoinListener implements Listener {

    private CandyParty candyParty;

    public PlayerJoinListener(CandyParty candyParty) {
        this.candyParty = candyParty;
        Bukkit.getPluginManager().registerEvents(this, candyParty);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        switch (candyParty.getPhaseHandler().getActivePhaseType()) {
            case LOBBY:
                event.setJoinMessage(Messages.PREFIX + "§e" + player.getName() + "§7 ist dem Spiel §abeigetreten§7! §8[§e"
                        + Bukkit.getOnlinePlayers().size() + "§8/§e" + candyParty.getMaxPlayers() + "§8]");

                candyParty.getItemManager().giveLobbyItems(player);
                candyParty.getLocationManager().teleport(player, "Lobby");
                player.setLevel(0);
                player.setGameMode(GameMode.ADVENTURE);
                player.setFoodLevel(20);
                player.setHealth(20.0);
                player.setExp(0);

                for (Player all : Bukkit.getOnlinePlayers()) {
                    all.showPlayer(player);
                }

                break;
            case ENDING:
            case INGAME:
                event.setJoinMessage(null);
                Location location = candyParty.getLocationManager().getLocation("Spectator");
                if (location != null) {
                    player.teleport(location);
                    player.setGameMode(GameMode.ADVENTURE);
                    player.setAllowFlight(true);
                    player.setFlying(true);
                    //candyParty.getItemManager().giveSpectatorItems(player);
                    candyParty.getScoreboardManager().updateTeam(player);
                    //candyParty.getSpectators().add(player);

                    for (Player online : Bukkit.getOnlinePlayers()) {
                        online.hidePlayer(player);
                    }
                }
                break;
        }

        /*
        for (Player spectator : candyParty.getSpectators()) {
            player.hidePlayer(spectator);
        }
        */
    }

}
