package logic.gameelements;
import controller.Game;

public interface Visitor {
    void visit(Game game);
}
