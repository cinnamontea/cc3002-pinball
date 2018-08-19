package gui;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.settings.GameSettings;
import facade.HomeworkTwoFacade;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.target.Target;

import java.util.Map;
import java.util.Random;

import static gui.GameFactory.*;

/**
 * Main class that shows and manages the GUI and user input.
 *
 * @author sofia.castro
 */

public class PinballApp extends GameApplication {
    private Entity leftFlipper;
    private Entity rightFlipper;
    private HomeworkTwoFacade game;
    private Random r;

    @Override
    protected void initSettings(GameSettings settings){
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("Pinball App");
        settings.setVersion("0.1");
        r = new Random();
        game = new HomeworkTwoFacade();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initGame() {
        Entity bg = newBackground();
        leftFlipper = newLFlipper();
        rightFlipper = newRFlipper();
        Entity walls = newWalls();
        Entity leftTriangle = newInnerWall(-50,540,220,100,30);
        Entity rightTriangle = newInnerWall(320,540,220,100,-30);
        Entity rightBlock = newInnerWall(500,0,300,600,0);
        Entity lLine = newInnerWall(20,480,100,10,30);
        Entity rLine = newInnerWall(370,480,100,10,-30);

        getGameWorld().addEntities(bg, leftFlipper, rightFlipper, walls);
        getGameWorld().addEntities(leftTriangle,rightTriangle, rightBlock,lLine,rLine);
    }

    @Override
    protected void initInput() {
        Input input = getInput();

        input.addAction(new UserAction("Left Spin") {
            @Override
            protected void onAction() {
                leftFlipper.getComponent(FlipperComponent.class).forwardSpin();
            }
            @Override
            protected void onActionEnd(){
                leftFlipper.getComponent(FlipperComponent.class).backwardSpin();
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Right Spin") {
            @Override
            protected void onAction() {
                rightFlipper.getComponent(FlipperComponent.class).forwardSpin();
            }
            @Override
            protected void onActionEnd(){
                rightFlipper.getComponent(FlipperComponent.class).backwardSpin();
            }
        }, KeyCode.D);

        input.addAction(new UserAction("New Ball") {
            @Override
            protected void onActionBegin() {
                boolean noBallInPlay = getGameWorld().getEntitiesByType(ExampleType.BALL).isEmpty();
                if (game.isPlayableTable() && noBallInPlay && !game.gameOver()) {
                    Entity ball = newBall(300, 500);
                    getGameWorld().addEntity(ball);
                }
            }
        }, KeyCode.SPACE);

        input.addAction(new UserAction("New Game") {
            @Override
            protected void onActionBegin() {
                game.setGameTable(game.newFullPlayableTable("Default Table", 5, 0.5, 4,2));
                getGameState().setValue("Balls",game.getAvailableBalls());
                getGameWorld().removeEntities(getGameWorld().getEntitiesByType(ExampleType.KICKER_BUMPER));
                getGameWorld().removeEntities(getGameWorld().getEntitiesByType(ExampleType.POP_BUMPER));
                getGameWorld().removeEntities(getGameWorld().getEntitiesByType(ExampleType.DROP_TARGET));
                getGameWorld().removeEntities(getGameWorld().getEntitiesByType(ExampleType.SPOT_TARGET));
                for (Bumper bumper: game.getBumpers()){
                    int y = r.nextInt(450);
                    int x = r.nextInt(450);
                    if (bumper.isKickerBumper()){
                        getGameWorld().addEntity(newKickerBumper(x,y));
                    }
                    else if (bumper.isPopBumper()){
                        getGameWorld().addEntity(newPopBumper(x,y));
                    }
                }
                for (Target target: game.getTargets()){
                    int y = r.nextInt(450);
                    int x = r.nextInt(450);
                    if (target.isDropTarget()){
                        getGameWorld().addEntity(newDropTarget(x,y));
                    }
                    else if (target.isSpotTarget()){
                        getGameWorld().addEntity(newSpotTarget(x,y));
                    }
                }
            }
        }, KeyCode.N);
    }

    @Override
    protected void initUI() {
        Text textBalls = new Text();
        textBalls.setTranslateX(600);
        textBalls.setTranslateY(100);
        textBalls.textProperty().bind(getGameState().intProperty("Balls").asString());
        getGameScene().addUINode(textBalls);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("Balls", game.getAvailableBalls());
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0,60);
        CollisionHandler bottomWallHandler = new CollisionHandler(ExampleType.BALL, ExampleType.WALL){
            @Override
            protected void onHitBoxTrigger(Entity ball, Entity wall, HitBox boxBall, HitBox boxWall){
                if (boxWall.getName().equals("BOT")){
                    ball.removeFromWorld();
                    game.dropBall();
                    getGameState().setValue("Balls",game.getAvailableBalls());
                }
            }
        };
        getPhysicsWorld().addCollisionHandler(bottomWallHandler);
    }
}