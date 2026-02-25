package iscteiul.ista.battleship;

/**
 * Represents a Barge ship in the Battleship game.
 * <p>
 * A Barge ("Barca" in the Discoveries version) is the smallest ship
 * in the game and occupies a single position on the board.
 * </p>
 * 
 * This class extends {@link Ship} and defines:
 * <ul>
 *   <li>Its fixed size (1 cell)</li>
 *   <li>Its display name</li>
 *   <li>Its occupied position on the board</li>
 * </ul>
 * 
 * @author YourName
 * @version 1.0
 */
public class Barge extends Ship {

    /**
     * The fixed size of the Barge (1 cell).
     */
    private static final Integer SIZE = 1;

    /**
     * The display name of the ship.
     */
    private static final String NAME = "Barca";

    /**
     * Constructs a Barge with a given bearing and initial position.
     * <p>
     * Since a Barge occupies only one cell, its position list
     * contains only the starting position.
     * </p>
     *
     * @param bearing the orientation of the ship (not relevant for size 1,
     *                but required by the superclass)
     * @param pos the upper-left position of the ship on the board
     */
    public Barge(Compass bearing, IPosition pos) {
        super(Barge.NAME, bearing, pos);
        getPositions().add(new Position(pos.getRow(), pos.getColumn()));
    }

    /**
     * Returns the size of the Barge.
     *
     * @return the size of the ship (always 1)
     */
    @Override
    public Integer getSize() {
        return SIZE;
    }
}
