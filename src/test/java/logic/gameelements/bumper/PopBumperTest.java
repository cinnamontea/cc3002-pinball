package logic.gameelements.bumper;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PopBumperTest {
    private PopBumper bumper;

    @Before
    public void setUp() {
        bumper = new PopBumper();
    }

    @Test
    public void hit() {
        assertEquals(100,bumper.getScore());
        int i = 3;
        while (i > 0){
            assertEquals(i,bumper.remainingHitsToUpgrade());
            assertFalse(bumper.isUpgraded());
            bumper.hit();
            i--;
        }
        assertTrue(bumper.isUpgraded());
        assertEquals(300,bumper.getScore());
    }

    @Test
    public void downgrade() {
        bumper.manualUpgrade();
        assertTrue(bumper.isUpgraded());
        assertEquals(0,bumper.remainingHitsToUpgrade());
        bumper.hit(); // (Just to check if the counter works properly)
        bumper.downgrade();
        assertFalse(bumper.isUpgraded());
        assertEquals(2,bumper.remainingHitsToUpgrade());
    }

    @Test
    public void upgrade() {
        // Can't check bonuses here, so this will only test that the upgrading part works.
        assertFalse(bumper.isUpgraded());
        bumper.manualUpgrade();
        assertTrue(bumper.isUpgraded());
    }
}