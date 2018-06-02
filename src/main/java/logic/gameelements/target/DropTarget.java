package logic.gameelements.target;

import controller.Game;

import java.util.Random;

/**
 * This class seriously needs some kind of explanation.
 * @author sofia.castro
 */
public class DropTarget extends AbstractTarget {
    private Random random;

    public DropTarget(){
        active = true;
        score = 100;
        random = new Random();
    }

    public DropTarget(long seed){
        this();
        random = new Random(seed);
    }

    public boolean lucky(){
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