package logic.gameelements;

import java.util.Observer;

/**
 * Interface that represents a hittable object.
 *
 * <p>Objects that are game elements should implement this interface.</p>
 *
 * @author Juan-Pablo Silva
 * @author sofia.castro
 * @see logic.gameelements.bumper.Bumper
 * @see logic.gameelements.target.Target
 */
public interface Hittable extends Visitor {
    /**
     * Defines that an object have been hit.
     * Implementations should consider the events that a hit to an object can trigger.
     *
     * @return the score the player obtained hitting the object
     */
    int hit();

    /**
     * Defines that a hittable object has to have a score when it is hit.
     *
     * @return the current score of the object when hit
     */
    int getScore();

    /**
     * Registers an observer of this object.
     * Every Hittable is expected to extend from the {@link java.util.Observable} class
     * so that this method is implemented automatically.
     *
     * @param o an observer to be added
     */
    void addObserver(Observer o);
}
