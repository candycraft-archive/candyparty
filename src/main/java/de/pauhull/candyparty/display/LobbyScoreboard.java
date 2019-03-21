package de.pauhull.candyparty.display;

import de.pauhull.candyparty.CandyParty;
import de.pauhull.friends.common.party.Party;
import de.pauhull.friends.spigot.SpigotFriends;
import de.pauhull.scoreboard.CustomScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionGroup;


/**
 * Created by Paul
 * on 07.12.2018
 *
 * @author pauhull
 */
public class LobbyScoreboard extends CustomScoreboard {

    private DisplayScore online;

    public LobbyScoreboard(Player player) {
        super(player, player.getName() + "_lobby", "§5§lCandyParty Lobby");
        this.descending = false;
    }

    @Override
    public void show() {
        new DisplayScore(" §d§lCandyCraft§7.§dde");
        new DisplayScore("§fServer:");
        new DisplayScore();
        this.online = new DisplayScore(" §aLädt...");
        new DisplayScore("§fOnline:");
        new DisplayScore();

        super.show();
    }

    @Override
    public void update() {
        String online = " §a" + Bukkit.getOnlinePlayers().size();
        if (!this.online.getScore().getEntry().equals(online)) {
            this.online.setName(online);
        }
    }

    @Override
    public void updateTeam(Player player) {
        SpigotFriends.getInstance().getPartyManager().getAllParties(parties -> {
            Bukkit.getScheduler().runTask(CandyParty.getInstance(), () -> {

                String prefix;
                String rank;
                if (player.getDisplayName().equals(player.getName())) {
                    PermissionGroup group = this.getHighestPermissionGroup(player);
                    rank = group.getRank() + "";
                    prefix = ChatColor.translateAlternateColorCodes('&', group.getPrefix());
                } else {
                    rank = "65";
                    prefix = "§a";
                }

                String name = rank + player.getName();
                if (name.length() > 16) {
                    name = name.substring(0, 16);
                }

                if (scoreboard.getTeam(name) != null) {
                    scoreboard.getTeam(name).unregister();
                }

                org.bukkit.scoreboard.Team scoreboardTeam = scoreboard.registerNewTeam(name);

                String suffix = "";
                for (Party party : parties) {
                    if (party.getMembers().contains(player.getDisplayName()) && party.getMembers().contains(this.player.getDisplayName())) {
                        suffix = " §7[§5Party§7]";
                    }
                }

                scoreboardTeam.setSuffix(suffix);
                scoreboardTeam.setPrefix(prefix);
                scoreboardTeam.addEntry(player.getName());
            });
        });
    }

}
