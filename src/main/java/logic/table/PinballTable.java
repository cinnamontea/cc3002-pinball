package logic.table;

import logic.gameelements.bumper.*;
import logic.gameelements.target.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A class that contains the group of game elements that form a
 * table of a Pinball game.
 *
 * @author sofia.castro
 */
public class PinballTable implements Table{
    private String name;
    private int numberOfBumpers;
    private int numberOfSpotTargets;
    private int numberOfDropTargets;
    private boolean isPlayable;

    private List<Bumper> bumpers;
    private List<Target> targets;
    private Random random;

    /**
     * Default PinballTable constructor.
     *
     * @param name                 the table's name
     * @param numberOfBumpers      amount of {@link Bumper} on this particular table
     * @param prob                 probability of generating a {@link PopBumper} kind of {@code Bumper}
     * @param numberOfSpotTargets  amount of {@link SpotTarget} on this particular table
     * @param numberOfDropTargets  amount of {@link DropTarget} on this particular table
     */
    public PinballTable(String name, int numberOfBumpers, double prob, int numberOfSpotTargets, int numberOfDropTargets){
        this.random = new Random();
        this.name = name;
        this.numberOfBumpers = numberOfBumpers;
        this.numberOfSpotTargets = numberOfSpotTargets;
        this.numberOfDropTargets = numberOfDropTargets;
        isPlayable = false;
        setTargets();
        setBumpers(prob);
    }

    /**
     * A PinballTable constructor.
     * Useful for testing tables with a fixed amount of bumpers.
     *
     * @param name                 the table's name
     * @param numberOfBumpers      amount of {@link Bumper} on this particular table
     * @param prob                 probability of generating a {@link PopBumper} kind of {@code Bumper}
     * @param numberOfSpotTargets  amount of {@link SpotTarget} on this particular table
     * @param numberOfDropTargets  amount of {@link DropTarget} on this particular table
     * @param seed                 the seed used to generate random numbers
     */
    public PinballTable(String name, int numberOfBumpers, double prob, int numberOfSpotTargets,
                        int numberOfDropTargets, long seed){
        this.random = new Random(seed);
        this.name = name;
        this.numberOfBumpers = numberOfBumpers;
        this.numberOfSpotTargets = numberOfSpotTargets;
        this.numberOfDropTargets = numberOfDropTargets;
        isPlayable = false;
        setTargets();
        setBumpers(prob);
    }

    /**
     * A PinballTable constructor that receives the game elements as lists.
     * Useful for testing particular sets of game elements so their random
     * characteristics can be determined beforehand.
     *
     * @param bumperList a list of {@link Bumper} elements to be added to the table
     * @param targetList a list of {@link Target} elements to be added to the table
     * @param numberOfDT the number of {@link DropTarget} elements in the list
     */
    public PinballTable(List<Bumper> bumperList,List<Target> targetList,int numberOfDT){
        this.random = new Random();
        this.name = "Lucky Hittables";
        this.numberOfBumpers = bumperList.size();
        this.numberOfDropTargets = numberOfDT;
        this.numberOfSpotTargets = targetList.size()-numberOfDT;

        this.isPlayable = false;
        this.bumpers = bumperList;
        this.targets = targetList;
    }

    /**
     * Gets the table's name.
     *
     * @return the name of this table
     */
    public String getTableName() {
        return name;
    }

    /**
     * Gets the amount of {@link DropTarget} targets in the table.
     *
     * @return the number of DropTargets in this table
     */
    public int getNumberOfDropTargets() {
        return numberOfDropTargets;
    }

    /**
     * Gets the number of {@link DropTarget} that are currently dropped.
     * It looks over the first part of the targets list (where DropTargets are stored)
     * and counts how many of them are inactive.
     *
     * @return the amount of inactive DropTargets in the table
     */
    public int getCurrentlyDroppedDropTargets() {
        int inactiveDropTargets = 0;
        int index = 0;
        // DropTargets are the first [numberOfDropTargets] elements of the [targets] list.
        while (index < numberOfDropTargets){
            if (!targets.get(index).isActive())
                inactiveDropTargets++;
            index++;
        }
        return inactiveDropTargets;
    }

    /**
     * Gets the list where the table's bumpers are stored.
     *
     * @return every bumper in the table
     */
    public List<Bumper> getBumpers() {
        return bumpers;
    }

    /**
     * Gets the list where the table's targets are stored.
     *
     * @return every target in the table
     */
    public List<Target> getTargets() {
        return targets;
    }

    /**
     * Changes the state of every {@link DropTarget} in the table back to "active".
     * It goes through the targets' list and reactivates any DropTargets that were dropped.
     */
    public void resetDropTargets() {
        int index = 0;
        // DropTargets are the first [numberOfDropTargets] elements of the [targets] list.
        while (index < numberOfDropTargets) {
            if (!targets.get(index).isActive())
                targets.get(index).reset();
            index++;
        }
    }

    /**
     * Levels up any bumpers that are not upgraded.
     * It checks the bumpers' list and avoids any possibility of activating upgrading bonuses.
     */
    public void upgradeAllBumpers() {
        int index = 0;
        while (index < bumpers.size()){
            // manualUpgrade() ensures that it won't activate bonuses.
            bumpers.get(index).manualUpgrade();
            index++;
        }
    }

    /**
     * Gets whether the table is considered playable or not.
     *
     * @return true if this table is playable, false otherwise
     */
    public boolean isPlayableTable() {
        return isPlayable;
    }

    /**
     * Counts every {@link PopBumper} in the table's bumpers list.
     *
     * @return the number of PopBumpers in this table
     */
    public int getNumberOfPopBumpers() {
        int numberOfPopBumpers = 0;
        int index = 0;
        while (index < numberOfBumpers){
            // PopBumpers are the only Bumpers that give less than 500 points even when upgraded.
            if (bumpers.get(index).getScore()<500)
                numberOfPopBumpers++;
            index++;
        }
        return numberOfPopBumpers;
    }

    /**
     * Sets the list of {@link Target} elements in this table.
     * The first {@code numberOfDropTargets} items are {@link DropTarget},
     * and the rest {@link SpotTarget}.
     */
    private void setTargets() {
        targets = new ArrayList<>();
        int index = 0;
        while (index < numberOfDropTargets){
            targets.add(new DropTarget());
            index++;
        }

        while (index < numberOfDropTargets + numberOfSpotTargets){
            targets.add(new SpotTarget());
            index++;
        }
    }

    /**
     * Sets the list of {@link Bumper} elements in this table.
     * The amount of each type is decided randomly, just as their positions in the list.
     *
     * @param prob probability of generating a {@link PopBumper}
     */
    void setBumpers(double prob) {
        bumpers = new ArrayList<>();
        int index = 0;
        while (index < numberOfBumpers){
            double luck = random.nextDouble();
            if (luck <= prob){
                bumpers.add(new PopBumper());
            }
            else{
                bumpers.add(new KickerBumper());
            }
            index++;
        }
    }

    /**
     * Sets the status of this table to "playable".
     */
    public void setPlayability(){
        this.isPlayable = true;
    }
}

