package logic.gameelements.bumper;

import controller.Game;
import java.util.Observable;
import java.util.Random;

/**
 * Abstract class that represents bumpers and their common features in a game.
 *
 * @author sofia.castro
 * @see KickerBumper
 * @see PopBumper
 */
public abstract class AbstractBumper extends Observable implements Bumper {
    /**
     * The amount of points awarded before upgrading.
     */
    int baseScore;
    /**
     * Fixed number of hits needed to upgrade.
     */
    int hitsToUp;
    /**
     * The factor in which the points awarded increase after upgrading.
     */
    int upgradedMultiplier;
    /**
     * Current multiplier that applies to the score given.
     */
    int multiplier;
    /**
     * Current number of times that a particular bumper has been hit.
     */
    int hitCounter;
    /**
     * Object that generates numbers to simulate random event activation.
     */
    Random random;

    /**
     * Defines that a bumper has been hit.
     * It updates the {@code hitCounter} variable, checking if it's necessary to upgrade,
     * and informs the {@link java.util.Observer} ({@link Game}) of the points to be awarded.
     *
     * @return the score the player obtained by hitting this object
     */
    public int hit(){
        hitCounter++;
        if (remainingHitsToUpgrade() == 0){
            upgrade();
        }
        setChanged();
        notifyObservers(this.getScore());
        return getScore();
    }

    /**
     * Gets the points the bumper would grant when hit
     *
     * @return score given considering upgrades
     */
    public int getScore(){
        return baseScore*multiplier;
    }

    /**
     * Gets the remaining hits the bumper has to receive to upgrade.
     * Returns zero if it's already upgraded.
     *
     * @return number of hits left to upgrade
     */
    public int remainingHitsToUpgrade(){
        return isUpgraded()? 0:hitsToUp-hitCounter;
    }

    /**
     * Gets whether the bumper is currently upgraded or not.
     *
     * @return true if the bumper is upgraded, false otherwise
     */
    public boolean isUpgraded(){
        return multiplier == upgradedMultiplier;
    }

    /**
     * Upgrades a bumper by changing the {@code multiplier} variable.
     * It also may trigger an instance of {@link logic.bonus.ExtraBallBonus}.
     */
    public void upgrade(){
        if (manualUpgrade() && random.nextInt(10)==7){
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Downgrades a bumper by changing the {@code multiplier}
     * variable back to 1.
     */
    public void downgrade(){
        multiplier = 1;
    }

    public boolean manualUpgrade(){
        if (!isUpgraded()){
            multiplier = upgradedMultiplier;
        }
        return this.isUpgraded();
    }

    public void visit(Game game){
        game.getExtraBallBonus().trigger(game);
    }

    public boolean isKickerBumper(){
        return false;
    }

    public boolean isPopBumper(){
        return false;
    }
}
