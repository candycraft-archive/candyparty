package de.pauhull.candyparty.phase.type;

import de.pauhull.candyparty.CandyParty;
import de.pauhull.candyparty.minigame.Minigame;
import de.pauhull.candyparty.minigame.game.KnockFfa;
import de.pauhull.candyparty.minigame.game.TntRun;
import de.pauhull.candyparty.phase.GamePhase;
import de.pauhull.candyparty.phase.GamePhaseHandler;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Paul
 * on 21.03.2019
 *
 * @author pauhull
 */
public class IngamePhase extends GamePhase {

    @Getter
    private Type type = Type.INGAME;

    @Getter
    private CandyParty candyParty;

    @Getter
    private Minigame currentMinigame;

    @Getter
    private List<Minigame> allMinigames, playedMinigames;

    @Getter
    private int phase;

    public IngamePhase(GamePhaseHandler handler) {
        super(handler);

        this.candyParty = CandyParty.getInstance();

        this.allMinigames = new ArrayList<>();
        allMinigames.add(new TntRun(candyParty));
        allMinigames.add(new KnockFfa(candyParty));

        this.playedMinigames = new ArrayList<>(allMinigames);
        Collections.shuffle(playedMinigames);
        if (playedMinigames.size() > 5) {
            playedMinigames = getPlayedMinigames().subList(0, 5);
        }
    }

    @Override
    public void start() {
        super.start();


    }

    @Override
    public void run() {

    }

}
