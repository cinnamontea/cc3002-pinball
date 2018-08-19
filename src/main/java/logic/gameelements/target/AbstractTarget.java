package logic.gameelements.target;

import java.util.Observable;

/**
 * Abstract class that represents targets and their common features in a game.
 *
 * @author sofia.castro
 * @see DropTarget
 * @see SpotTarget
 */
public abstract class AbstractTarget extends Observable implements Target {
    boolean active;
    int score;

    /**
     * Gets the points the target would grant when hit.
     *
     * @return score given by this particular target
     */
    public int getScore(){
        return score;
    }

    /**
     * Gets whether the target is currently active or not.
     *
     * @return true if the target is active, false otherwise
     */
    public boolean isActive(){
        return active;
    }

    /**
     * Resets a target by switching its status to "active".
     */
    public void reset(){
        active = true;
    }

    /**
     * Defines that a target have been hit.
     * When active, it changes this state, and informs the {@link java.util.Observer}
     * ({@link controller.Game}) of the points to be awarded and any additional effects.
     *
     * @return the score the player obtained by hitting this object
     * @see DropTarget#hit()
     * @see SpotTarget#hit()
     */
    public abstract int hit();

    public boolean isDropTarget(){
        return false;
    }

    public boolean isSpotTarget(){
        return false;
    }
}
