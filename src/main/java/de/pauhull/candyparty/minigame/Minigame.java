package de.pauhull.candyparty.minigame;

import org.bukkit.inventory.ItemStack;

/**
 * Created by Paul
 * on 30.03.2019
 *
 * @author pauhull
 */
public interface Minigame extends Runnable {

    void start();

    void end();

    void onEnd(Runnable runnable);

    ItemStack getItem();

}
