package de.pauhull.candyparty;

import de.pauhull.candyparty.command.SetLocationCommand;
import de.pauhull.candyparty.display.LobbyScoreboard;
import de.pauhull.candyparty.inventory.MinigamePickInventory;
import de.pauhull.candyparty.listener.*;
import de.pauhull.candyparty.manager.ItemManager;
import de.pauhull.candyparty.manager.LocationManager;
import de.pauhull.candyparty.phase.GamePhaseHandler;
import de.pauhull.candyparty.phase.type.LobbyPhase;
import de.pauhull.scoreboard.ScoreboardManager;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by Paul
 * on 20.03.2019
 *
 * @author pauhull
 */
public class CandyParty extends JavaPlugin {

    @Getter
    private static CandyParty instance;

    @Getter
    private ScoreboardManager scoreboardManager;

    @Getter
    private GamePhaseHandler phaseHandler;

    @Getter
    private FileConfiguration config;

    @Getter
    private boolean pluginEnabled;

    @Getter
    private int minPlayers, maxPlayers;

    @Getter
    private LocationManager locationManager;

    @Getter
    private ItemManager itemManager;

    @Getter
    private MinigamePickInventory minigamePickInventory;

    @Override
    public void onEnable() {
        instance = this;

        this.scoreboardManager = new ScoreboardManager(this, LobbyScoreboard.class);
        this.locationManager = new LocationManager(this);
        this.itemManager = new ItemManager(this);
        this.phaseHandler = new GamePhaseHandler();
        this.config = copyAndLoad("config.yml", new File(getDataFolder(), "config.yml"));
        this.pluginEnabled = config.getBoolean("Enabled");
        this.minPlayers = config.getInt("MinPlayers");
        this.maxPlayers = config.getInt("MaxPlayers");
        this.minigamePickInventory = new MinigamePickInventory(this);

        if (pluginEnabled) {
            this.phaseHandler.startPhase(LobbyPhase.class);
            new PlayerInteractListener(this);
            new PlayerJoinListener(this);
            new PlayerQuitListener(this);
            new BlockBreakListener(this);
            new BlockPlaceListener(this);
        }

        new SetLocationCommand(this);

    }

    @Override
    public void onDisable() {
        instance = null;
    }

    private YamlConfiguration copyAndLoad(String resource, File file) {
        if (!file.exists()) {
            file.getParentFile().mkdirs();

            if (file.exists()) {
                file.delete();
            }

            try {
                Files.copy(getResource(resource), file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return YamlConfiguration.loadConfiguration(file);
    }

}
