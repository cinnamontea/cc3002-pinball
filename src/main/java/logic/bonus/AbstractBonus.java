package logic.bonus;

import controller.Game;

import java.util.Observable;

/**
 * Abstract class that represents bonuses and their common features in a game.
 *
 * @author sofia.castro
 * @see DropTargetBonus
 * @see ExtraBallBonus
 * @see JackPotBonus
 */
public abstract class AbstractBonus extends Observable implements Bonus{
    int timesTriggered;
    int score;

    /**
     * Gets the number of times a bonus has been triggered.
     * @return number of times that this bonus has been triggered so far
     */
    public int timesTriggered() {
        return timesTriggered;
    }

    /**
     * Activates the specific action the bonus does and applies it to the
     * {@link Game} object.
     * In general, it updates the {@code timesTriggered} variable, calls
     * the methods that produce additional effects, and notifies the
     * {@link Game} of any change to be made in the score.
     *
     * @param game the game controller object
     * @see DropTargetBonus#trigger(Game)
     * @see ExtraBallBonus#trigger(Game)
     * @see JackPotBonus#trigger(Game)
     */
    public abstract void trigger(Game game);
}
