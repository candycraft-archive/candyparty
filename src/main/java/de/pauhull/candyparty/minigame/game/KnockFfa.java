package de.pauhull.candyparty.minigame.game;

import de.pauhull.candyparty.CandyParty;
import de.pauhull.candyparty.minigame.Minigame;
import de.pauhull.candyparty.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul
 * on 31.03.2019
 *
 * @author pauhull
 */
public class KnockFfa implements Minigame {

    private static final ItemStack ITEM = new ItemBuilder(Material.STICK).setDisplayName("§9KnockFFA").setLore("§7Schlage alle Gegner von", "§7der Platform herunter").build();

    private CandyParty candyParty;
    private List<Runnable> onEnd;
    private int task, countdown;

    public KnockFfa(CandyParty candyParty) {
        this.candyParty = candyParty;
        this.onEnd = new ArrayList<>();
    }

    @Override
    public void start() {
        if (task != -1 && Bukkit.getScheduler().isCurrentlyRunning(task)) {
            return;
        }

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
        countdown++;
    }

    @Override
    public ItemStack getItem() {
        return ITEM;
    }

}
