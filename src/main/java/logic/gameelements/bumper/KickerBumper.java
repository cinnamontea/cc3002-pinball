package logic.gameelements.bumper;

import java.util.Random;

/**
 * This class seriously needs some kind of explanation.
 * @author sofia.castro
 */
public class KickerBumper extends AbstractBumper{

    public KickerBumper() {
        baseScore = 500;
        hitsToUp = 5;
        multiplier = 1;
        upgradedMultiplier = 2;
        hitCounter = 0;
        random = new Random();
    }
    public KickerBumper(long seed) {
        this();
        random = new Random(seed);
    }
}
