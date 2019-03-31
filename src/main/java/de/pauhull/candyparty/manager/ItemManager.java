package de.pauhull.candyparty.manager;

import de.pauhull.candyparty.CandyParty;
import de.pauhull.candyparty.util.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Paul
 * on 22.03.2019
 *
 * @author pauhull
 */
public class ItemManager {

    @Getter
    private ItemStack leaveItem;

    public ItemManager(CandyParty candyParty) {
        this.leaveItem = new ItemBuilder(Material.SLIME_BALL).setDisplayName("§eZur Lobby §7§o<Rechtsklick>").build();
    }

    public void giveLobbyItems(Player player) {
        player.getInventory().clear();
        player.getInventory().setHeldItemSlot(4);
        player.getInventory().setItem(7, leaveItem);
    }

}
