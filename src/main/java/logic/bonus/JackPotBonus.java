package logic.bonus;

import controller.Game;

/**
 * A class that represents the behaviour of JackPot bonuses.
 * This kind of bonus is characterized by giving 100.000 points when activated.
 *
 * @author sofia.castro
 */
public class JackPotBonus extends AbstractBonus{
    /**
     * JackPotBonus' constructor.
     */
    public JackPotBonus(){
        score = 100000;
    }

    public void trigger(Game game){
        timesTriggered++;
        setChanged();
        notifyObservers(score);
    }
}