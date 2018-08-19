package gui;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;

/**
 * The Component that stores the effects of interacting with Flippers.
 *
 * @author sofia.castro
 */

public class FlipperComponent extends Component {
    protected PhysicsComponent physics;

    private boolean isInMin = true;
    private boolean isInMax = false;
    private int side = 1;

    @Override
    public void onUpdate(double tpf) {
        if (side*entity.getRotation()<-20 && isInMin){
            physics.setAngularVelocity(0);
        }
        else if (side*entity.getRotation()>20 && isInMax){
            physics.setAngularVelocity(0);
        }
        if (side*entity.getRotation()<=-20)
            isInMin = true;
    }

    void forwardSpin(){
        isInMin = false;
        if (side*entity.getRotation()>=20)
            isInMax = true;
        physics.setAngularVelocity(side*15);
    }

    void backwardSpin(){
        isInMax =false;
        physics.setAngularVelocity(side*-15);
    }

    void changeSides(){
        side = -side;
    }
}

