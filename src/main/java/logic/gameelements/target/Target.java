package logic.gameelements.target;

import logic.gameelements.Hittable;

/**
 * Interface that represents operations related to a target behavior.
 *
 * @author Juan-Pablo Silva
 * @see SpotTarget
 * @see DropTarget
 */
public interface Target extends Hittable {
    /**
     * Gets whether the target is currently active or not.
     *
     * @return true if the target is active, false otherwise
     */
    boolean isActive();

    /**
     * Resets the state of a target making it active again.
     */
    void reset();

    /**
     * Tells whether the target is a DropTarget.
     * @return true if it's a DropTarget, false otherwise
     */
    boolean isDropTarget();

    /**
     * Tells whether the target is a SpotTarget.
     * @return true if it's a SpotTarget, false otherwise
     */
    boolean isSpotTarget();
}
