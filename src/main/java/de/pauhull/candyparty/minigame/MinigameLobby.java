package de.pauhull.candyparty.minigame;

import de.pauhull.candyparty.CandyParty;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by Paul
 * on 21.03.2019
 *
 * @author pauhull
 */
public class MinigameLobby implements Runnable {

    private CandyParty candyParty;
    private int countdown, task;

    public MinigameLobby(CandyParty candyParty) {
        this.candyParty = candyParty;
    }

    public void start() {
        if (task != -1 && Bukkit.getScheduler().isCurrentlyRunning(task)) {
            return;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {

        }

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(candyParty, this, 0, 20);
    }

    public void end() {
        this.task = -1;
        this.countdown = 0;
    }

    @Override
    public void run() {

        if (countdown > 10) {
            end();
            return;
        }

        countdown++;
    }

}
