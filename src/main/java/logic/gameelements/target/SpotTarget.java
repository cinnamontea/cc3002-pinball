package logic.gameelements.target;

import controller.Game;

/**
 * This class seriously needs some kind of explanation.
 * @author sofia.castro
 */
public class SpotTarget extends AbstractTarget {

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
        return 0;
    }

    public void visit(Game game) {
        // SpotTarget doesn't give any points so I can omit this notification.
        game.getJackPotBonus().trigger(game);
    }
}
