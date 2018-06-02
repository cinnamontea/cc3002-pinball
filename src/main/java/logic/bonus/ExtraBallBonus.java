package logic.bonus;

import controller.Game;

/**
 * This class seriously needs some kind of explanation.
 * @author sofia.castro
 */
public class ExtraBallBonus extends AbstractBonus{
    public ExtraBallBonus(){
        score = 0;
    }

    public void trigger(Game game){
        timesTriggered++;
        game.wonABall();
        setChanged();
        notifyObservers(score);
    }
}
