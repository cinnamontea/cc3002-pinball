package logic.gameelements.target;

/**
 * This class seriously needs some kind of explanation.
 * @author sofia.castro
 */
public class SpotTarget extends AbstractTarget {

    public SpotTarget(){
        active = true;
        score = 0;
    }

    public int hit(){
        if (isActive()){
            notifyObservers(score);
            active = false;
        }
        return 0;
    }
}
