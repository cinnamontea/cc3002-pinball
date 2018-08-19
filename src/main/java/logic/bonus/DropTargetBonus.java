package logic.bonus;

import controller.Game;

/**
 * A class that represents the behaviour of DropTarget bonuses.
 * This kind of bonus is characterized by giving 1.000.000 points and upgrading
 * every {@link logic.gameelements.bumper.Bumper} in the table when activated.
 *
 * @author sofia.castro
 */
public class DropTargetBonus extends AbstractBonus{
    /**
     * DropTargetBonus' constructor.
     */
    public DropTargetBonus(){
        score = 1000000;
    }

    public void trigger(Game game){
        timesTriggered++;
        game.getCurrentTable().upgradeAllBumpers();
        setChanged();
        notifyObservers(score);
        game.getCurrentTable().resetDropTargets(); // T3 addition
    }
}
