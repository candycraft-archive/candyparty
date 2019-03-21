package de.pauhull.candyparty.inventory;

import de.pauhull.candyparty.CandyParty;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Paul
 * on 21.03.2019
 *
 * @author pauhull
 */
public class MinigamePickInventory implements Listener {

    private static final String TITLE = "§cMinigame wird ausgesucht...";

    private CandyParty candyParty;

    public MinigamePickInventory(CandyParty candyParty) {
        this.candyParty = candyParty;
        Bukkit.getPluginManager().registerEvents(this, candyParty);
    }

    public void show(Player... players) {
        Inventory inventory = Bukkit.createInventory(null, 27, TITLE);

        for (Player player : players) {
            player.openInventory(inventory);
        }

        AtomicInteger task = new AtomicInteger();
        AtomicInteger tick = new AtomicInteger();
        task.set(Bukkit.getScheduler().scheduleSyncRepeatingTask(candyParty, () -> {

            if (inventory.getViewers().isEmpty()) {
                Bukkit.getScheduler().cancelTask(task.get());
                return;
            }

            update(inventory, tick.getAndIncrement());

        }, 0, 1));
    }

    public void update(Inventory inventory, int tick) {

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();

        if (inventory != null && inventory.getTitle() != null && !inventory.getTitle().equals(TITLE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();

        if (event.getInventory() != null && event.getInventory().getTitle() != null && event.getInventory().getTitle().equals(TITLE)) {
            player.openInventory(inventory);
        }
    }

}
