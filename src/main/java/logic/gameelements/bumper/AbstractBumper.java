package logic.gameelements.bumper;

import controller.Game;
import logic.gameelements.Visitor;

import java.util.Observable;
import java.util.Random;

/**
 * This class seriously needs some kind of explanation.
 * @author sofia.castro
 */
public abstract class AbstractBumper extends Observable implements Bumper, Visitor {
    int baseScore;
    int hitsToUp;
    int multiplier;
    int upgradedMultiplier;
    int hitCounter;
    Random random;

    public int hit(){
        hitCounter++;
        if (remainingHitsToUpgrade() == 0){
            upgrade();
        }
        setChanged();
        notifyObservers(this.getScore());
        return getScore();
    }
    public int getScore(){
        return baseScore*multiplier;
    }

    public int remainingHitsToUpgrade(){
        return isUpgraded()? 0:hitsToUp-hitCounter;
    }

    public boolean isUpgraded(){
        return multiplier == upgradedMultiplier;
    }
    public void downgrade(){
        multiplier = 1;
    }

    public boolean manualUpgrade(){
        if (!isUpgraded()){
            multiplier = upgradedMultiplier;
        }
        return this.isUpgraded();
    }

    public void upgrade(){
        if (manualUpgrade() && random.nextInt(10)==7){
            setChanged();
            notifyObservers();
        }
    }

    public void visit(Game game){
        game.getExtraBallBonus().trigger(game);
    }
}
