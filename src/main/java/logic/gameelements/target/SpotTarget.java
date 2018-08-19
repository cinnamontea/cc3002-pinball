package logic.gameelements.target;

import controller.Game;

/**
 * A class that represents the behaviour of SpotTargets.
 *
 * @author sofia.castro
 */
public class SpotTarget extends AbstractTarget {
    /**
     * SpotTarget's constructor.
     */
    public SpotTarget(){
        active = true;
        score = 0;
    }

    public int hit(){
        if (isActive()){
            setChanged();
            notifyObservers();
            active = false;
        }
        return score;
    }

    public void visit(Game game) {
        // SpotTarget doesn't give any points by itself so I can omit this notification.
        game.getJackPotBonus().trigger(game);
    }

    @Override
    public boolean isSpotTarget(){
        return true;
    }
}
