package logic.gameelements.bumper;
import java.util.Random;

/**
 * A class that represents the behaviour of PopBumpers.
 *
 * @author sofia.castro
 */
public class PopBumper extends AbstractBumper{
    /**
     * Default PopBumper constructor.
     */
    public PopBumper() {
        baseScore = 100;
        hitsToUp = 3;
        multiplier = 1;
        upgradedMultiplier = 3;
        hitCounter = 0;
        random = new Random();
    }

    /**
     * A PopBumper constructor.
     * Useful for testing random-based events.
     *
     * @param seed the seed used to generate random numbers
     */
    public PopBumper(long seed) {
        this();
        random = new Random(seed);
    }

    @Override
    public boolean isPopBumper(){
        return true;
    }
}
