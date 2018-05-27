package logic.gameelements.target;

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
            if (lucky())
                notifyObservers(true);
            active = false;
            notifyObservers(score);
            return score;
        }
        return 0;
    }
}