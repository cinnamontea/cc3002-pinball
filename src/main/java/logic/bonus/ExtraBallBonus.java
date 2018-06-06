package logic.bonus;

import controller.Game;

/**
 * A class that represents the behaviour of ExtraBall bonuses.
 * This kind of bonus is characterized by awarding one additional ball
 * to the game where it's been activated.
 *
 * @author sofia.castro
 */
public class ExtraBallBonus extends AbstractBonus{
    /**
     * ExtraBallBonus' constructor.
     */
    public ExtraBallBonus(){
        score = 0;
    }

    public void trigger(Game game){
        timesTriggered++;
        game.wonABall();
        setChanged();
        notifyObservers(score); // Not necessary, but I like to keep the same structure from the other bonuses.
    }
}
