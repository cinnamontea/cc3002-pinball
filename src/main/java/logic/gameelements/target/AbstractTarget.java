package logic.gameelements.target;

import java.util.Observable;

/**
 * This class seriously needs some kind of explanation.
 * @author sofia.castro
 */
public abstract class AbstractTarget extends Observable implements Target{
    boolean active;
    int score;

    public int getScore(){
        return score;
    }
    public boolean isActive(){
        return active;
    }
    public void reset(){
        active = true;
    }
}
