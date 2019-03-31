package de.pauhull.candyparty.listener;

import de.pauhull.candyparty.CandyParty;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Created by Paul
 * on 30.03.2019
 *
 * @author pauhull
 */
public class BlockPlaceListener implements Listener {

    private CandyParty candyParty;

    public BlockPlaceListener(CandyParty candyParty) {
        this.candyParty = candyParty;
        Bukkit.getPluginManager().registerEvents(this, candyParty);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        event.setCancelled(true);
    }

}
