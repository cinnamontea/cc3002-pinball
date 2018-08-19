package logic.gameelements.bumper;

import java.util.Random;

/**
 * A class that represents the behaviour of KickerBumpers.
 *
 * @author sofia.castro
 */
public class KickerBumper extends AbstractBumper{
    /**
     * Default KickBumper constructor.
     */
    public KickerBumper() {
        baseScore = 500;
        hitsToUp = 5;
        multiplier = 1;
        upgradedMultiplier = 2;
        hitCounter = 0;
        random = new Random();
    }

    /**
     * A KickBumper constructor.
     * Useful for testing random-based events.
     *
     * @param seed the seed used to generate random numbers
     */
    public KickerBumper(long seed) {
        this();
        random = new Random(seed);
    }

    @Override
    public boolean isKickerBumper(){
        return true;
    }
}
