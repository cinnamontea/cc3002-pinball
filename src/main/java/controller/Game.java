package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import logic.bonus.*;
import logic.gameelements.Hittable;
import logic.table.*;
import logic.gameelements.bumper.*;
import logic.gameelements.target.*;


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

    public boolean isPlayableTable() {
        return currentTable.isPlayableTable();
    }

    public DropTargetBonus getDropTargetBonus() {
        return dropTargetBonus;
    }

    public ExtraBallBonus getExtraBallBonus() {
        return extraBallBonus;
    }

    public JackPotBonus getJackPotBonus() {
        return jackPotBonus;
    }

    public Table newPlayableTableWithNoTargets(String name, int numberOfBumpers, double prob){
        PinballTable table = new PinballTable(name, numberOfBumpers, prob, 0, 0);
        table.setPlayability();
        return table;
    }

    public Table newFullPlayableTable(String name, int numberOfBumpers, double prob, int numberOfSpotTargets, int numberOfDropTargets){
        PinballTable table = new PinballTable(name, numberOfBumpers, prob, numberOfSpotTargets, numberOfDropTargets);
        table.setPlayability();
        return table;
    }

    public Table getCurrentTable(){
        return currentTable;
    }

    public int getCurrentAvailableBalls() {
        return currentAvailableBalls;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    private void setCurrentScore(int plus){
        this.currentScore += plus;
    }

    public void setCurrentTable(Table currentTable) {
        this.currentTable = currentTable;

        List<Hittable> hittables = new ArrayList<>();
        hittables.addAll(currentTable.getBumpers());
        hittables.addAll(currentTable.getTargets());

        int index = 0;
        while (index < hittables.size()){
            hittables.get(index).addObserver(this);
            index++;
        }
    }

    public int lostABall() {
        this.currentAvailableBalls--;
        return currentAvailableBalls;
    }

    public void wonABall(){
        this.currentAvailableBalls++;

    }

    public void update(Observable observable, Object arg){
        this.update(observable, arg);
    }

    /*public void update(Table table, int plusScore){ // ???
        setCurrentScore(plusScore);
    }*/

    public void update(Bonus bonus, int plusScore){
        setCurrentScore(plusScore);
    }

    public void update(SpotTarget target, int plusScore){
        jackPotBonus.trigger(this);
    }

    public void update(DropTarget target, int plusScore){
        setCurrentScore(plusScore);
        if (currentTable.getCurrentlyDroppedDropTargets() == currentTable.getNumberOfDropTargets())
            dropTargetBonus.trigger(this);
    }

    public void update(DropTarget target, boolean bonus){
        extraBallBonus.trigger(this);
    }

    public void update(Bumper bumper, int plusScore){
        setCurrentScore(plusScore);
    }

    public void update(Bumper bumper, boolean bonus){
        extraBallBonus.trigger(this);
    }
}
