package de.pauhull.candyparty.listener;

import de.pauhull.candyparty.CandyParty;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Paul
 * on 22.03.2019
 *
 * @author pauhull
 */
public class PlayerInteractListener implements Listener {

    private CandyParty candyParty;

    public PlayerInteractListener(CandyParty candyParty) {
        this.candyParty = candyParty;
        Bukkit.getPluginManager().registerEvents(this, candyParty);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack stack = event.getItem();

        event.setCancelled(true);

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (stack != null) {
                if (stack.equals(candyParty.getItemManager().getLeaveItem())) {
                    player.kickPlayer("");
                }
            }
        }
    }

}
