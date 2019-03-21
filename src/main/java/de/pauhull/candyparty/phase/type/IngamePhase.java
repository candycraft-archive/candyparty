package de.pauhull.candyparty.phase.type;

import de.pauhull.candyparty.phase.GamePhase;
import de.pauhull.candyparty.phase.GamePhaseHandler;
import lombok.Getter;

/**
 * Created by Paul
 * on 21.03.2019
 *
 * @author pauhull
 */
public class IngamePhase extends GamePhase {

    @Getter
    private Type type = Type.INGAME;

    public IngamePhase(GamePhaseHandler handler) {
        super(handler);
    }

    @Override
    public void run() {

    }

}
