package logic.gameelements.target;

import controller.Game;
import logic.gameelements.Visitor;

import java.util.Observable;

/**
 * This class seriously needs some kind of explanation.
 * @author sofia.castro
 */
public abstract class AbstractTarget extends Observable implements Target, Visitor {
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
    // public void visit(Game game) is implemented in each type of Target classes (because they act differently).
}
