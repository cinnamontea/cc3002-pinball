package logic.table;

import logic.gameelements.bumper.Bumper;
import logic.gameelements.bumper.KickerBumper;
import logic.gameelements.bumper.PopBumper;
import logic.gameelements.target.DropTarget;
import logic.gameelements.target.SpotTarget;
import logic.gameelements.target.Target;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PinballTableTest {
    private PinballTable table;
    private PopBumper popBumper;
    private KickerBumper kickerBumper;
    private DropTarget dropTarget;
    private SpotTarget spotTarget;

    @Before
    public void setUp() {
        table = new PinballTable("One of each", 2, 0.5, 1, 1,38439);
        /*table.setSeed(38439);
        table.setTargets();
        table.setBumpers(0.5);*/
        popBumper = (PopBumper) table.getBumpers().get(0);
        kickerBumper = (KickerBumper) table.getBumpers().get(1);
        dropTarget = (DropTarget) table.getTargets().get(0);
        spotTarget = (SpotTarget) table.getTargets().get(1);
    }

    @Test
    public void getBumpers() {
        List<Bumper> bumpers = table.getBumpers();
        assertEquals(2, bumpers.size());
        // I used the next tests to identify the bumpers in the table to use them in the setup:
        assertEquals(100,bumpers.get(0).getScore()); // First one is a PopBumper
        assertEquals(500,bumpers.get(1).getScore()); // Next one is a KickerBumper
    }

    @Test
    public void getTargets() {
        List<Target> targets = table.getTargets();
        assertEquals(2, targets.size());
        // I used the next tests to identify the targets in the table to use them in the setup:
        assertEquals(100,targets.get(0).getScore()); // First one is a DropTarget
        assertEquals(0,targets.get(1).getScore()); // Next one is a SpotTarget
    }

    @Test
    public void getTableName() {
        assertEquals("One of each", table.getTableName());
    }

    @Test
    public void getNumberOfDropTargets() {
        assertEquals(1,table.getNumberOfDropTargets());
    }

    @Test
    public void getCurrentlyDroppedDropTargets() {
        assertEquals(0, table.getCurrentlyDroppedDropTargets());
        dropTarget.hit();
        assertEquals(1, table.getCurrentlyDroppedDropTargets());
    }

    @Test
    public void resetDropTargets() {
        dropTarget.hit();
        assertEquals(1, table.getCurrentlyDroppedDropTargets());
        table.resetDropTargets();
        assertEquals(0, table.getCurrentlyDroppedDropTargets());
    }

    @Test
    public void upgradeAllBumpers() {
        assertFalse(kickerBumper.isUpgraded());
        assertFalse(popBumper.isUpgraded());
        table.upgradeAllBumpers();
        assertTrue(kickerBumper.isUpgraded());
        assertTrue(popBumper.isUpgraded());
    }

    @Test
    public void isPlayableTable() {
        // Also checks setPlayability()
        assertFalse(table.isPlayableTable());
        table.setPlayability();
        assertTrue(table.isPlayableTable());
    }

    @Test
    public void setTargets() {
        assertEquals(1,table.getNumberOfDropTargets());
        assertEquals(1,table.getTargets().size()-table.getNumberOfDropTargets());
    }

    @Test
    public void setBumpers() {
        assertEquals(1,table.getNumberOfPopBumpers());
        table.setBumpers(1);
        assertEquals(2,table.getNumberOfPopBumpers());
        table.setBumpers(0);
        assertEquals(0,table.getNumberOfPopBumpers());
    }
}