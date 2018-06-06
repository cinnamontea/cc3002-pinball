package logic.gameelements.target;

import controller.Game;
import java.util.Random;

/**
 * A class that represents the behaviour of DropTargets.
 *
 * @author sofia.castro
 */
public class DropTarget extends AbstractTarget {
    private Random random;

    /**
     * Default DropTarget constructor.
     */
    public DropTarget(){
        active = true;
        score = 100;
        random = new Random();
    }

    /**
     * A DropTarget constructor.
     * Useful for testing random-based events.
     *
     * @param seed the seed used to generate random numbers
     */
    public DropTarget(long seed){
        this();
        random = new Random(seed);
    }

    /**
     * Represents a condition that occurs with a 3/10 probability.
     * Needed to determine when {@link logic.bonus.ExtraBallBonus}
     * should be activated in {@link #visit}.
     *
     * @return true if the condition is met, false otherwise
     */
    private boolean lucky(){
        int number = random.nextInt(10) + 1;
        return number%3==0;
    }

    public int hit(){
        if (isActive()){
            active = false;
            setChanged();
            notifyObservers();
            return score;
        }
        return 0;
    }

    public void visit(Game game){
        setChanged();
        notifyObservers(score);
        if (lucky())
            game.getExtraBallBonus().trigger(game);
        if (game.getCurrentTable().getCurrentlyDroppedDropTargets() == game.getCurrentTable().getNumberOfDropTargets())
            game.getDropTargetBonus().trigger(game);
    }
}