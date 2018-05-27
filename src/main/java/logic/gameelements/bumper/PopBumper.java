package logic.gameelements.bumper;

import java.util.Random;

/**
 * This class seriously needs some kind of explanation.
 * @author sofia.castro
 */
public class PopBumper extends AbstractBumper{

    public PopBumper() {
        baseScore = 100;
        hitsToUp = 3;
        multiplier = 1;
        upgradedMultiplier = 3;
        hitCounter = 0;
        random = new Random();
    }
    public PopBumper(long seed) {
        this();
        random = new Random(seed);
    }
}
