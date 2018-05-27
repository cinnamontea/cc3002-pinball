package logic.gameelements.target;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DropTargetTest {
    private DropTarget target;

    @Before
    public void setUp() {
        target = new DropTarget();
    }

    @Test
    public void hit() {
        assertEquals(100,target.getScore());
        assertTrue(target.isActive());
        assertEquals(100, target.hit());
        assertFalse(target.isActive());
        assertEquals(0, target.hit());
    }

    @Test
    public void reset() {
        target.hit();
        assertEquals(0, target.hit());
        target.reset();
        assertEquals(100, target.hit());
    }
}