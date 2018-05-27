package logic.bonus;

import java.util.Observable;

public abstract class AbstractBonus extends Observable implements Bonus{
    int timesTriggered;
    int score;

    public int timesTriggered() {
        return timesTriggered;
    }
}
