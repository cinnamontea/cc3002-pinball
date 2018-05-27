package logic.gameelements.bumper;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class KickerBumperTest {
    private KickerBumper bumper;

    @Before
    public void setUp() {
        bumper = new KickerBumper();
    }

    @Test
    public void hit() {
        assertEquals(500,bumper.getScore());
        int i = 5;
        while (i > 0){
            assertEquals(i,bumper.remainingHitsToUpgrade());
            assertFalse(bumper.isUpgraded());
            bumper.hit();
            i--;
        }
        assertTrue(bumper.isUpgraded());
        assertEquals(1000,bumper.getScore());
    }

    @Test
    public void downgrade(){
        bumper.upgrade();
        assertTrue(bumper.isUpgraded());
        assertEquals(0,bumper.remainingHitsToUpgrade());
        bumper.hit(); // (Just to check if the counter works properly)
        bumper.downgrade();
        assertFalse(bumper.isUpgraded());
        assertEquals(4,bumper.remainingHitsToUpgrade());
    }

    @Test
    public void upgrade() {
        // Can't check bonuses here, so this will only test that both upgrades work.
        assertFalse(bumper.isUpgraded());
        bumper.upgrade();
        assertTrue(bumper.isUpgraded());
    }
}