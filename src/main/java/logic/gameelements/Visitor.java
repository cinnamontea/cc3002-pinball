package logic.gameelements;
import controller.Game;

/**
 * Interface that adapts a very simple version of the Visitor Pattern
 * so that every {@link Game} reacts according to the type of element
 * that sends a notification through {@link java.util.Observable}{@code 's  notifyObservers}.
 *
 * @author sofia.castro
 * @see logic.gameelements.Hittable
 */
public interface Visitor {
    /**
     * Allows the current object to visit the {@link Game} where it belongs,
     * so that the desired response depends of each visitor.
     * Every {@link Hittable} is expected to implement the particular effects of this method.
     *
     * @param game the instance of {@link Game} where the element is inserted
     * @see logic.gameelements.target.DropTarget#visit(Game)
     * @see logic.gameelements.target.SpotTarget#visit(Game)
     * @see logic.gameelements.bumper.AbstractBumper#visit(Game)
     */
    void visit(Game game);
}
