package de.pauhull.candyparty.minigame;

import de.pauhull.candyparty.CandyParty;
import de.pauhull.candyparty.phase.GamePhase;
import de.pauhull.candyparty.phase.type.IngamePhase;
import de.pauhull.candyparty.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul
 * on 21.03.2019
 *
 * @author pauhull
 */
public class MinigameLobby implements Minigame {

    private CandyParty candyParty;
    private int countdown, task;
    private List<Runnable> onEnd;

    public MinigameLobby(CandyParty candyParty) {
        this.candyParty = candyParty;
        this.onEnd = new ArrayList<>();
    }

    @Override
    public void start() {
        if (task != -1 && Bukkit.getScheduler().isCurrentlyRunning(task)) {
            return;
        }

        Location location = candyParty.getLocationManager().getLocation("MinigameLobby");
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.teleport(location);
            player.sendMessage(Messages.PREFIX + "Der nächste Spielmodus wird ausgesucht...");
        }
        location.getWorld().playSound(location, Sound.LEVEL_UP, 1, 1);

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(candyParty, this, 0, 20);
    }

    @Override
    public void end() {
        this.task = -1;
        this.countdown = 0;

        onEnd.forEach(Runnable::run);
        onEnd.clear();
    }

    @Override
    public void onEnd(Runnable runnable) {
        onEnd.add(runnable);
    }

    @Override
    public void run() {

        if (countdown >= 10 || candyParty.getPhaseHandler().getActivePhaseType() != GamePhase.Type.INGAME) {
            end();
            return;
        }

        IngamePhase ingamePhase = (IngamePhase) candyParty.getPhaseHandler().getActivePhase();

        if (countdown == 2) {
            Bukkit.broadcastMessage(Messages.PREFIX + "Der nächste Spielmodus wird ausgesucht...");
        } else if (countdown == 3) {
            Minigame nextMinigame = ingamePhase.getPlayedMinigames().get(ingamePhase.getPhase());

            List<ItemStack> pickFrom = new ArrayList<>();
            for (Minigame minigame : ingamePhase.getAllMinigames()) {
                pickFrom.add(minigame.getItem());
            }

            candyParty.getMinigamePickInventory().show(pickFrom, nextMinigame.getItem(), 20);
        }
        
        countdown++;
    }

    @Override
    public ItemStack getItem() {
        return null;
    }

}
