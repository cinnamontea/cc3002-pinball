package controller;

import logic.bonus.DropTargetBonus;
import logic.bonus.ExtraBallBonus;
import logic.bonus.JackPotBonus;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.bumper.KickerBumper;
import logic.gameelements.bumper.PopBumper;
import logic.gameelements.target.DropTarget;
import logic.gameelements.target.Target;
import logic.table.PinballTable;
import logic.table.Table;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameTest {
    private Game game; // Made with seeded constructor (not playable)
    private Game gameTNT; // "Table with No Targets" (made with newPlayableTableWithNoTargets)
    private Game gameFT; // "Full Table" (made with newFullPlayableTable)

    @Before
    public void setUp() {
        game = new Game();
        Table table = new PinballTable("Two of each",4,0.5,2,2,85493);
        game.setCurrentTable(table);
    }

    @Test
    public void isPlayableTable() {
        this.newPlayableTableWithNoTargets();
        this.newFullPlayableTable();
        assertFalse(game.isPlayableTable());
        assertTrue(gameTNT.isPlayableTable());
        assertTrue(gameFT.isPlayableTable());
    }

    @Test
    public void getDropTargetBonus() {
        DropTargetBonus dropTargetBonus = game.getDropTargetBonus();
        assertEquals(0,dropTargetBonus.timesTriggered());
    }

    @Test
    public void getExtraBallBonus() {
        ExtraBallBonus extraBallBonus = game.getExtraBallBonus();
        assertEquals(0,extraBallBonus.timesTriggered());
    }

    @Test
    public void getJackPotBonus() {
        JackPotBonus jackPotBonus = game.getJackPotBonus();
        assertEquals(0,jackPotBonus.timesTriggered());
    }

    @Test
    public void newPlayableTableWithNoTargets() {
        gameTNT = new Game();
        gameTNT.setCurrentTable(game.newPlayableTableWithNoTargets("3 Bumpers and 0 Targets", 3,0.5));
    }

    @Test
    public void newFullPlayableTable() {
        gameFT = new Game();
        gameFT.setCurrentTable(game.newFullPlayableTable("5 Bumpers and 4 Targets",5,0.5,2,2));
    }

    @Test
    public void getCurrentTable() {
        assertEquals(2,game.getCurrentTable().getNumberOfDropTargets());
        int numberOfSpotTargets = game.getCurrentTable().getTargets().size() - game.getCurrentTable().getNumberOfDropTargets();
        assertEquals(2,numberOfSpotTargets);
        assertEquals(2,game.getCurrentTable().getNumberOfPopBumpers());
        int numberOfKickerBumpers = game.getCurrentTable().getBumpers().size() - game.getCurrentTable().getNumberOfPopBumpers();
        assertEquals(2,numberOfKickerBumpers);
    }

    @Test
    public void getCurrentAvailableBalls() {
        assertEquals(3,game.getCurrentAvailableBalls());
    }

    @Test
    public void getCurrentScore() {
        assertEquals(0,game.getCurrentScore());
        int score = game.getCurrentTable().getBumpers().get(0).hit();
        assertEquals(score,game.getCurrentScore());
        game.getCurrentTable().getTargets().get(0).hit();
        assertNotEquals(score,game.getCurrentScore());
    }

    @Test
    public void setCurrentTable() {
        assertEquals(2,game.getCurrentTable().getNumberOfDropTargets());
        Table table2 = new PinballTable("Empty",0,0,0,0);
        game.setCurrentTable(table2);
        assertEquals(0,game.getCurrentTable().getNumberOfDropTargets());
    }

    @Test
    public void lostABall() {
        assertEquals(3,game.getCurrentAvailableBalls());
        game.lostABall();
        assertEquals(2,game.getCurrentAvailableBalls());
    }

    @Test
    public void wonABall() {
        assertEquals(3,game.getCurrentAvailableBalls());
        game.wonABall();
        assertEquals(4,game.getCurrentAvailableBalls());
    }

    @Test
    public void kickerBumperInteraction() {
        Bumper kickerBumper = game.getCurrentTable().getBumpers().get(2);
        assertEquals(500,kickerBumper.hit());
        assertEquals(500,game.getCurrentScore());
        assertEquals(500,kickerBumper.hit());
        assertEquals(1000,game.getCurrentScore());
    }

    @Test
    public void popBumperInteraction() {
        Bumper popBumper = game.getCurrentTable().getBumpers().get(0);
        assertEquals(100,popBumper.hit());
        assertEquals(100,game.getCurrentScore());
        assertEquals(100,popBumper.hit());
        assertEquals(200,game.getCurrentScore());
    }

    @Test
    public void dropTargetInteraction() {
        Target dropTarget = game.getCurrentTable().getTargets().get(0);
        assertEquals(100,dropTarget.hit());
        assertEquals(100,game.getCurrentScore());
        assertEquals(0,dropTarget.hit());
        assertEquals(100,game.getCurrentScore());
    }

    @Test
    public void spotTargetInteraction() {
        Target spotTarget = game.getCurrentTable().getTargets().get(2);
        assertEquals(0,spotTarget.hit());
        assertEquals(100000,game.getCurrentScore());
        assertEquals(0,spotTarget.hit());
        assertEquals(100000,game.getCurrentScore());
    }

    @Test
    public void jackPotBonusActivation() {
        Target spot1 = game.getCurrentTable().getTargets().get(2);
        Target spot2 = game.getCurrentTable().getTargets().get(3);
        spot1.hit();
        assertEquals(100000,game.getCurrentScore());
        spot1.hit();
        assertEquals(100000,game.getCurrentScore());
        spot2.hit();
        assertEquals(200000,game.getCurrentScore());
        spot2.hit();
        assertEquals(200000,game.getCurrentScore());
    }

    @Test
    public void dropTargetBonusActivation() {
        Target drop1 = game.getCurrentTable().getTargets().get(0);
        Target drop2 = game.getCurrentTable().getTargets().get(1);
        drop1.hit();
        assertEquals(100,game.getCurrentScore());
        drop2.hit();
        assertEquals(1000200,game.getCurrentScore());
    }

    @Test
    public void extraBallBonusActivation() {
        // Setting hittables that can activate this bonus on the first try:
        List<Bumper> bumperList = new ArrayList<>();
        List<Target> targetList = new ArrayList<>();
        bumperList.add(new KickerBumper(5));
        bumperList.add(new PopBumper(5));
        targetList.add(new DropTarget(1));
        targetList.add(new DropTarget()); // (This is to avoid DropTargetBonus)
        Table table = new PinballTable(bumperList,targetList,2);
        game.setCurrentTable(table);

        // Checking KickerBumper:
        int i = 5;
        while (i > 0) {
            bumperList.get(0).hit();
            i--;
        }
        assertEquals(4,game.getCurrentAvailableBalls());
        assertEquals(3000,game.getCurrentScore());

        // Checking PopBumper:
        i = 3;
        while (i > 0) {
            bumperList.get(1).hit();
            i--;
        }
        assertEquals(5,game.getCurrentAvailableBalls());
        assertEquals(3500,game.getCurrentScore());

        // Checking DropTarget:
        targetList.get(0).hit();
        assertEquals(6,game.getCurrentAvailableBalls());
        assertEquals(3600,game.getCurrentScore());
    }
}