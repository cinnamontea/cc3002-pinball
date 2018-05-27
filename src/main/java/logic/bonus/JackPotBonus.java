package logic.bonus;

import controller.Game;

/**
 * This class seriously needs some kind of explanation.
 * @author sofia.castro
 */
public class JackPotBonus extends AbstractBonus{
    public JackPotBonus(){
        score = 100000;
    }

    public void trigger(Game game){
        timesTriggered++;
        notifyObservers(score);
    }
}