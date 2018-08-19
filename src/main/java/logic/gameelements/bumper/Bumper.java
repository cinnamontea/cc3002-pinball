package logic.gameelements.bumper;

import logic.gameelements.Hittable;

/**
 * Interface that represents operations related to a bumper behavior.
 *
 * @author Juan-Pablo Silva
 * @author sofia.castro
 * @see KickerBumper
 * @see PopBumper
 */
public interface Bumper extends Hittable {
    /**
     * Gets the remaining hits the bumper has to receive to upgrade.
     *
     * @return the remaining hits to upgrade the bumper
     */
    int remainingHitsToUpgrade();

    /**
     * Gets whether the bumper is currently upgraded or not.
     *
     * @return true if the bumper is upgraded, false otherwise
     */
    boolean isUpgraded();

    /**
     * Upgrades a bumper making {@link #isUpgraded()} return true.
     */
    void upgrade();

    /**
     * Downgrades a bumper making {@link #isUpgraded()} return false.
     */
    void downgrade();

    /**
     * Upgrades a bumper without the consequences that this event may trigger in another item, such as activating an
     * instance of {@link logic.bonus.ExtraBallBonus} or notifying its score to certain {@link controller.Game}.
     * @return true if the upgrade was successful, false otherwise
     */
    boolean manualUpgrade();

    /**
     * Tells whether the bumper is a KickerBumper.
     * @return true if it's a KickerBumper, false otherwise
     */
    boolean isKickerBumper();

    /**
     * Tells whether the bumper is a PopBumper.
     * @return true if it's a PopBumper, false otherwise
     */
    boolean isPopBumper();
}
