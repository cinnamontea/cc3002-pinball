package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import logic.bonus.*;
import logic.gameelements.Hittable;
import logic.table.*;

/**
 * Game logic controller class.
 *
 * @author Juan-Pablo Silva
 * @author sofia.castro
 */
public class Game implements Observer{
    private DropTargetBonus dropTargetBonus;
    private ExtraBallBonus extraBallBonus;
    private JackPotBonus jackPotBonus;

    private Table currentTable;
    private int currentScore;
    private int currentAvailableBalls;

    /**
     * Game's constructor. It instantiates one of each {@link Bonus}, starts with an empty
     * {@link Table} and establishes the initial values of the score and available balls.
     */
    public Game(){
        this.dropTargetBonus = new DropTargetBonus();
        this.extraBallBonus = new ExtraBallBonus();
        this.jackPotBonus = new JackPotBonus();
        this.currentTable = new PinballTable("",0,0,0,0);
        this.currentScore = 0;
        this.currentAvailableBalls = 3;

        dropTargetBonus.addObserver(this);
        extraBallBonus.addObserver(this);
        jackPotBonus.addObserver(this);
    }

    /**
     * Gets whether the current table is playable or not.
     *
     * @return true if the current table is playable, false otherwise
     */
    public boolean isPlayableTable() {
        return currentTable.isPlayableTable();
    }

    /**
     * Gets the instance of {@link DropTargetBonus} currently in the game.
     *
     * @return the DropTargetBonus instance
     */
    public DropTargetBonus getDropTargetBonus() {
        return dropTargetBonus;
    }

    /**
     * Gets the instance of {@link ExtraBallBonus} currently in the game.
     *
     * @return the ExtraBallBonus instance
     */
    public ExtraBallBonus getExtraBallBonus() {
        return extraBallBonus;
    }

    /**
     * Gets the instance of {@link JackPotBonus} currently in the game.
     *
     * @return the JackPotBonus instance
     */
    public JackPotBonus getJackPotBonus() {
        return jackPotBonus;
    }

    /**
     * Creates a new table based on the given parameters. It doesn't have any targets.
     *
     * @param name            the name of the table
     * @param numberOfBumpers the number of bumpers in the table
     * @param prob            the probability of generating a {@link logic.gameelements.bumper.PopBumper}
     * @return a new playable table determined by the parameters
     */
    public Table newPlayableTableWithNoTargets(String name, int numberOfBumpers, double prob){
        PinballTable table = new PinballTable(name, numberOfBumpers, prob, 0, 0);
        table.setPlayability();
        return table;
    }

    /**
     * Creates a new table with the given parameters.
     *
     * @param name                the name of the table
     * @param numberOfBumpers     the number of bumpers in the table
     * @param prob                the probability of generating a {@link logic.gameelements.bumper.PopBumper}
     * @param numberOfSpotTargets the number of {@link logic.gameelements.target.SpotTarget}
     * @param numberOfDropTargets the number of {@link logic.gameelements.target.DropTarget}
     * @return a new playable table determined by the parameters
     */
    public Table newFullPlayableTable(String name, int numberOfBumpers, double prob, int numberOfSpotTargets, int numberOfDropTargets){
        PinballTable table = new PinballTable(name, numberOfBumpers, prob, numberOfSpotTargets, numberOfDropTargets);
        table.setPlayability();
        return table;
    }

    /**
     * Gets the current table used in this game.
     *
     * @return the current table
     * @see Table
     */
    public Table getCurrentTable(){
        return currentTable;
    }

    /**
     * Gets the current number of available balls to play.
     *
     * @return the number of available balls
     */
    public int getCurrentAvailableBalls() {
        return currentAvailableBalls;
    }

    /**
     * Gets the points earned so far.
     *
     * @return the earned score
     */
    public int getCurrentScore() {
        return currentScore;
    }

    /**
     * Changes the score, adding the amount received to the current earned points.
     *
     * @param plus the points that will be added to the overall score
     */
    private void setCurrentScore(int plus){
        this.currentScore += plus;
    }

    /**
     * Sets a new table to play. It also registers this instance of {@code Game} as an
     * observer of every {@link Hittable} in that table.
     *
     * @param table the new table
     */
    public void setCurrentTable(Table table) {
        this.currentTable = table;

        List<Hittable> hittables = new ArrayList<>();
        hittables.addAll(table.getBumpers());
        hittables.addAll(table.getTargets());

        int index = 0;
        while (index < hittables.size()){
            hittables.get(index).addObserver(this);
            index++;
        }
    }

    /**
     * Removes one unit from the counter of available balls, returning the new number.
     *
     * @return the new number of available balls
     */
    public int lostABall() {
        this.currentAvailableBalls--;
        return currentAvailableBalls;
    }

    /**
     * Adds a single ball to the counter of available balls.
     */
    public void wonABall(){
        this.currentAvailableBalls++;

    }

    /**
     * Implementation of the {@code Observer} interface.
     * It receives the notification from {@link Observable#notifyObservers(Object)}
     * and checks whether this message is related to updating the game's score by
     * adding certain amount (which is obtained as an int in {@code arg}),
     * or some kind of special event (understood as a null {@code arg} parameter).
     *
     * @param observable an observable game element or bonus
     * @param arg the argument passed to the {@code notifyObservers(Object)} method
     */
    public void update(Observable observable, Object arg){
        if (arg == null)
            updateEvent((Hittable)observable);
        else
            setCurrentScore((int)arg);
    }

    /**
     * A particular case of the {@link #update} method. It delegates the activation of a
     * {@link logic.bonus.Bonus} instance to the {@link logic.gameelements.Visitor} elements of this game.
     * @param hittable the game element that notified the event
     */
    private void updateEvent(Hittable hittable){
        // This method works as the "accept" from a Visitor Pattern.
        hittable.visit(this);
    }
}
