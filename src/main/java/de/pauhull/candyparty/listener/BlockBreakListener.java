package de.pauhull.candyparty.listener;

import de.pauhull.candyparty.CandyParty;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by Paul
 * on 30.03.2019
 *
 * @author pauhull
 */
public class BlockBreakListener implements Listener {

    private CandyParty candyParty;

    public BlockBreakListener(CandyParty candyParty) {
        this.candyParty = candyParty;
        Bukkit.getPluginManager().registerEvents(this, candyParty);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(true);
    }

}
