package gui;

import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/**
 * A class that contains every method needed to create any type of Entity used in the GUI.
 *
 * @author sofia.castro
 */

public final class GameFactory {

    public enum ExampleType {
        L_FLIPPER,
        R_FLIPPER,
        BALL,
        WALL,
        INNER_WALL,
        KICKER_BUMPER,
        POP_BUMPER,
        DROP_TARGET,
        SPOT_TARGET;
    }

    public static Entity newFlipper() {
        Entity flipper = Entities.builder()
                .viewFromNodeWithBBox(new Rectangle(100, 30, Color.BLUE))
                .with(new CollidableComponent(true))
                .build();

        PhysicsComponent flipperPhysics = new PhysicsComponent();
        flipperPhysics.setBodyType(BodyType.KINEMATIC);
        flipper.addComponent(flipperPhysics);
        flipper.addComponent(new FlipperComponent());
        return flipper;
    }

    public static Entity newRFlipper(double x, double y) {
        Entity flipper = newFlipper();
        flipper.setPosition(x,y);
        flipper.setType(ExampleType.R_FLIPPER);
        flipper.setRotation(-15);
        return flipper;
    }

    public static Entity newLFlipper(double x, double y) {
        Entity flipper = newFlipper();
        flipper.setPosition(x,y);
        flipper.setType(ExampleType.L_FLIPPER);
        flipper.setRotation(15);
        flipper.getComponent(FlipperComponent.class).changeSides();
        return flipper;
    }

    public static Entity newBackground() {
        return Entities.builder()
                .viewFromNode(new Rectangle(600, 600, Color.GAINSBORO))
                .renderLayer(RenderLayer.BACKGROUND)
                .build();
    }

    public static Entity newBall(double x, double y) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setFixtureDef(
                new FixtureDef().restitution(0.6f).density(0.1f));
        physics.setOnPhysicsInitialized(()->physics.setLinearVelocity(5*60,-5*60));
        return Entities.builder()
                .at(x, y)
                .type(ExampleType.BALL)
                .bbox(new HitBox("Ball", BoundingShape.circle(10)))
                .viewFromNode(new Circle(10, Color.LIGHTCORAL))
                .with(physics, new CollidableComponent(true))
                .build();
    }

    public static Entity newWalls() {
        Entity walls = Entities.makeScreenBounds(100);
        walls.setType(ExampleType.WALL);
        walls.addComponent(new CollidableComponent(true));
        return walls;
    }

    public static Entity newKickerBumper(double x, double y){
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(0.0, 0.0, 30.0, 80.0, 0.0, 70.0);
        triangle.setFill(Color.DARKGREEN);
        Entity bumper = Entities.builder()
                .at(x,y)
                .type(ExampleType.KICKER_BUMPER)
                .viewFromNode(triangle)
                .bbox(new HitBox("Triangle",BoundingShape.polygon(0.0, 0.0, 30.0, 80.0, 0.0, 70.0)))
                .with(new CollidableComponent(true))
                .build();
        PhysicsComponent bumperPhysics = new PhysicsComponent();
        bumperPhysics.setBodyType(BodyType.KINEMATIC);
        bumper.addComponent(bumperPhysics);
        return bumper;
    }

    public static Entity newPopBumper(double x, double y){
        Entity bumper = Entities.builder()
                .at(x,y)
                .type(ExampleType.POP_BUMPER)
                .viewFromNodeWithBBox(new Circle(20,Color.ORANGERED))
                .with(new CollidableComponent(true))
                .build();
        PhysicsComponent bumperPhysics = new PhysicsComponent();
        bumperPhysics.setBodyType(BodyType.KINEMATIC);
        bumper.addComponent(bumperPhysics);
        return bumper;
    }

    public static Entity newDropTarget(double x, double y){
        Entity target = Entities.builder()
                .at(x,y)
                .type(ExampleType.DROP_TARGET)
                .viewFromNodeWithBBox(new Rectangle(50,15,Color.PLUM))
                .with(new CollidableComponent(true))
                .build();
        PhysicsComponent targetPhysics = new PhysicsComponent();
        targetPhysics.setBodyType(BodyType.KINEMATIC);
        target.addComponent(targetPhysics);
        return target;
    }

    public static Entity newSpotTarget(double x, double y){
        Entity target = Entities.builder()
                .at(x,y)
                .type(ExampleType.SPOT_TARGET)
                .viewFromNodeWithBBox(new Rectangle(20,20,Color.LIGHTYELLOW))
                .with(new CollidableComponent(true))
                .build();
        PhysicsComponent targetPhysics = new PhysicsComponent();
        targetPhysics.setBodyType(BodyType.KINEMATIC);
        target.addComponent(targetPhysics);
        return target;
    }

    public static Entity newInnerWall(double x, double y, int w, int h, double r){
        Entity wall = Entities.builder()
                .at(x,y)
                .viewFromNodeWithBBox(new Rectangle(w,h,Color.SLATEBLUE))
                .type(ExampleType.INNER_WALL)
                .with(new CollidableComponent(true))
                .rotate(r)
                .build();
        PhysicsComponent wallPhysics = new PhysicsComponent();
        wallPhysics.setBodyType(BodyType.KINEMATIC);
        wall.addComponent(wallPhysics);
        return wall;
    }
}