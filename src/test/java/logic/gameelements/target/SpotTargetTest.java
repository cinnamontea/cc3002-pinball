package logic.gameelements.target;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpotTargetTest {
    private SpotTarget target;

    @Before
    public void setUp() {
        target = new SpotTarget();
    }

    @Test
    public void hit() {
        assertEquals(0,target.getScore());
        assertTrue(target.isActive());
        assertEquals(0, target.hit());
        assertFalse(target.isActive());
        assertEquals(0, target.hit());
    }

    @Test
    public void reset() {
        target.hit();
        assertFalse(target.isActive());
        target.reset();
        assertTrue(target.isActive());
    }
}