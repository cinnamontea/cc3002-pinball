package logic.table;

import logic.gameelements.bumper.*;
import logic.gameelements.target.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class seriously needs some kind of explanation.
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
    private Random random = new Random();

    public PinballTable(String name){
        this(name,0,0,0,0);
    }

    public PinballTable(String name, int numberOfBumpers, double prob, int numberOfSpotTargets, int numberOfDropTargets){
        this.name = name;
        this.numberOfBumpers = numberOfBumpers;
        this.numberOfSpotTargets = numberOfSpotTargets;
        this.numberOfDropTargets = numberOfDropTargets;
        isPlayable = false;
        setTargets();
        setBumpers(prob);
    }

    public String getTableName() {
        return name;
    }

    public int getNumberOfDropTargets() {
        return numberOfDropTargets;
    }

    /*public int getCurrentlyDroppedDropTargets() {
        int inactiveDropTargets = 0;
        int index = 0;
        while (index < targets.size()) {
            Target target = targets.get(index);
            if (target.getScore() == 300 && !target.isActive()) {
                inactiveDropTargets++; // DropTargets can be distinguished by their score (300 points).
            }
            index++;
        }
        return inactiveDropTargets;
    }*/
    public int getCurrentlyDroppedDropTargets() { // DropTargets are the first numOfDT elements of targets list.
        int inactiveDropTargets = 0;
        int index = 0;
        while (index < numberOfDropTargets){
            if (!targets.get(index).isActive())
                inactiveDropTargets++;
            index++;
        }
        return inactiveDropTargets;
    }

    public List<Bumper> getBumpers() {
        return bumpers;
    }

    public List<Target> getTargets() {
        return targets;
    }

    /*public void resetDropTargets() {
        int index = 0;
        while (index < targets.size()) {
            Target target = targets.get(index);
            if (target.getScore() == 300 && !target.isActive()) {
                target.reset();
            }
            index++;
        }
    }*/
    public void resetDropTargets() { // DropTargets are the first numOfDT elements of targets list.
        int index = 0;
        while (index < numberOfDropTargets) {
            if (!targets.get(index).isActive())
                targets.get(index).reset();
            index++;
        }
    }

    public void upgradeAllBumpers() {
        int index = 0;
        while (index < bumpers.size()){
            bumpers.get(index).upgradeWithoutBonus();
            index++;
        }
    }

    public boolean isPlayableTable() { // Needs Observer/Observable relation *********************
        return isPlayable;
    }

    public int getNumberOfPopBumpers() {
        int numberOfPopBumpers = 0;
        int index = 0;
        while (index < numberOfBumpers){
            if (bumpers.get(index).getScore()==100)
                numberOfPopBumpers++;
            index++;
        }
        return numberOfPopBumpers;
    }

    protected void setTargets() { // Javadoc missing
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

    protected void setBumpers(double prob) { // Javadoc missing
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

    public void setSeed(long seed) { // Javadoc missing
        random.setSeed(seed);
    }

    public void setPlayability(){
        this.isPlayable = true;
    }

}

