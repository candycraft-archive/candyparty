package de.pauhull.candyparty.inventory;

import de.pauhull.candyparty.CandyParty;
import de.pauhull.candyparty.util.ItemBuilder;
import de.pauhull.candyparty.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Paul
 * on 21.03.2019
 *
 * @author pauhull
 */
public class MinigamePickInventory implements Listener {

    private static final Random RANDOM = new Random();
    private static final String TITLE = "§cMinigame wird ausgesucht...";
    private static final ItemStack BLACK_GLASS_PANE = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 15).setDisplayName(" ").build();
    private static final ItemStack PICK_MINIGAME = new ItemBuilder(Material.HOPPER).setDisplayName("§cNächster Spielmodus!").build();

    private CandyParty candyParty;
    private Map<Inventory, Integer> steps;
    private List<Inventory> keepOpen;

    public MinigamePickInventory(CandyParty candyParty) {
        this.candyParty = candyParty;
        this.steps = new HashMap<>();
        this.keepOpen = new ArrayList<>();
        Bukkit.getPluginManager().registerEvents(this, candyParty);
    }

    public void show(List<ItemStack> pickFrom, ItemStack picked, int steps) {
        this.show(pickFrom, picked, steps, Bukkit.getOnlinePlayers().toArray(new Player[0]));
    }

    public void show(List<ItemStack> pickFrom, ItemStack picked, int steps, Player... players) {
        Inventory inventory = Bukkit.createInventory(null, 27, TITLE);

        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, BLACK_GLASS_PANE);
        }

        inventory.setItem(4, PICK_MINIGAME);

        for (int i = 0; i < 7; i++) {
            ItemStack nextItem = pickFrom.get(RANDOM.nextInt(pickFrom.size()));
            inventory.setItem(i + 10, nextItem);
        }

        for (Player player : players) {
            player.openInventory(inventory);
        }

        this.keepOpen.add(inventory);
        this.steps.put(inventory, 0);
        AtomicInteger task = new AtomicInteger();
        AtomicInteger tick = new AtomicInteger();
        task.set(Bukkit.getScheduler().scheduleSyncRepeatingTask(candyParty, () -> {

            if (!keepOpen.contains(inventory)) {
                Bukkit.getScheduler().cancelTask(task.get());
                this.steps.remove(inventory);
                return;
            }

            update(inventory, tick.getAndIncrement(), pickFrom, picked, steps);

        }, 0, 1));
    }

    public void update(Inventory inventory, int tick, List<ItemStack> pickFrom, ItemStack picked, int steps) {
        int step = this.steps.get(inventory);

        if (step != 0 && tick % Math.max(3, Math.ceil((step * step) / 4.0)) != 0) {
            return;
        }

        if (step >= steps) {
            this.keepOpen.remove(inventory);
            Bukkit.broadcastMessage(Messages.PREFIX + "Der Nächste Spielmodus ist: ");
            return;
        }

        for (int i = 15; i > 9; i--) {
            inventory.setItem(i + 1, inventory.getItem(i));
        }
        ItemStack nextItem;
        if (step + 4 == steps) {
            nextItem = picked;
        } else {
            nextItem = pickFrom.get(RANDOM.nextInt(pickFrom.size()));
        }
        inventory.setItem(10, nextItem);

        for (HumanEntity human : inventory.getViewers()) {
            ((Player) human).playSound(human.getLocation(), Sound.CLICK, 1, 1);
        }

        this.steps.put(inventory, step + 1);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();

        if (inventory != null && inventory.getTitle() != null && inventory.getTitle().equals(TITLE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();

        if (keepOpen.contains(inventory)) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(candyParty, () -> {
                player.openInventory(inventory);
            }, 1);
        }
    }

}
