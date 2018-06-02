package logic.bonus;

import controller.Game;

/**
 * This class seriously needs some kind of explanation.
 * @author sofia.castro
 */
public class DropTargetBonus extends AbstractBonus{
    public DropTargetBonus(){
        score = 1000000;
    }

    public void trigger(Game game){
        timesTriggered++;
        game.getCurrentTable().upgradeAllBumpers();
        setChanged();
        notifyObservers(score);
    }
}
