package facade;

import controller.Game;
import logic.bonus.Bonus;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.target.Target;
import logic.table.Table;
import java.util.List;

/**
 * Facade class to expose the logic of the game to a gui in the upcoming homework.
 *
 * @author Juan-Pablo Silva
 * @author sofia.castro
 */
public class HomeworkTwoFacade {
    /**
     * Instance of the game controller.
     *
     * @see Game
     */
    private Game game;

    /**
     * HomeworkTwoFacade's constructor.
     */
    public HomeworkTwoFacade(){
        game = new Game(); // Each game starts with 3 balls (currently determined in Game's constructor).
    }

    /**
     * Gets whether the current table is playable or not.
     *
     * @return true if the current table is playable, false otherwise
     */
    public boolean isPlayableTable() {
        return game.isPlayableTable();
    }

    /**
     * Gets the instance of {@link logic.bonus.DropTargetBonus} currently in the game.
     *
     * @return the DropTargetBonus instance
     */
    public Bonus getDropTargetBonus() {
        return game.getDropTargetBonus();
    }

    /**
     * Gets the instance of {@link logic.bonus.ExtraBallBonus} currently in the game.
     *
     * @return the ExtraBallBonus instance
     */
    public Bonus getExtraBallBonus() {
        return game.getExtraBallBonus();
    }

    /**
     * Gets the instance of {@link logic.bonus.JackPotBonus} currently in the game.
     *
     * @return the JackPotBonus instance
     */
    public Bonus getJackPotBonus() {
        return game.getJackPotBonus();
    }

    /**
     * Creates a new table with the given parameters with no targets.
     *
     * @param name            the name of the table
     * @param numberOfBumpers the number of bumpers in the table
     * @param prob            the probability of a {@link logic.gameelements.bumper.PopBumper}
     * @return a new table determined by the parameters
     */
    public Table newPlayableTableWithNoTargets(String name, int numberOfBumpers, double prob) {
        return game.newPlayableTableWithNoTargets(name, numberOfBumpers, prob);
    }

    /**
     * Creates a new table with the given parameters.
     *
     * @param name                the name of the table
     * @param numberOfBumpers     the number of bumpers in the table
     * @param prob                the probability of a {@link logic.gameelements.bumper.PopBumper}
     * @param numberOfTargets     the number of {@link logic.gameelements.target.SpotTarget}
     * @param numberOfDropTargets the number of {@link logic.gameelements.target.DropTarget}
     * @return a new table determined by the parameters
     */
    public Table newFullPlayableTable(String name, int numberOfBumpers, double prob, int numberOfTargets, int numberOfDropTargets) {
        return game.newFullPlayableTable(name, numberOfBumpers, prob, numberOfTargets, numberOfDropTargets);
    }

    /**
     * Gets the list of bumpers in the current table.
     *
     * @return the list of bumpers
     * @see Bumper
     */
    public List<Bumper> getBumpers() {
        return game.getCurrentTable().getBumpers();
    }

    /**
     * Gets the list of targets in the current table.
     *
     * @return the list of targets
     * @see Target
     */
    public List<Target> getTargets() {
        return game.getCurrentTable().getTargets();
    }

    /**
     * Gets the name of the current table.
     *
     * @return the name of the current table
     */
    public String getTableName() {
        return game.getCurrentTable().getTableName();
    }

    /**
     * Gets the current number of available balls to play.
     *
     * @return the number of available balls
     */
    public int getAvailableBalls() {
        return game.getCurrentAvailableBalls();
    }

    /**
     * Gets the points earned so far.
     *
     * @return the earned score
     */
    public int getCurrentScore() {
        return game.getCurrentScore();
    }

    /**
     * Gets the current table.
     *
     * @return the current table
     * @see Table
     */
    public Table getCurrentTable() {
        return game.getCurrentTable();
    }

    /**
     * Sets a new table to play.
     *
     * @param newTable the new table
     */
    public void setGameTable(Table newTable) {
        game.setCurrentTable(newTable);
    }

    /**
     * Reduces the number of available balls and returns the new number.
     *
     * @return the new number of available balls
     */
    public int dropBall() {
        return game.lostABall();
    }

    /**
     * Checks whether the game has finished or not.
     *
     * @return true if the game is over, false otherwise
     */
    public boolean gameOver() {
        return game.gameOver();
    }
}
