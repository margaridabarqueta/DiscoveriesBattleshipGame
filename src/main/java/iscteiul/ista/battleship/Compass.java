package iscteiul.ista.battleship;

/**
 * Represents the possible compass directions used to define
 * the orientation (bearing) of a ship on the game board.
 * <p>
 * Each direction is associated with a single character code:
 * <ul>
 *   <li>'n' - North</li>
 *   <li>'s' - South</li>
 *   <li>'e' - East</li>
 *   <li>'o' - West ("Oeste" in Portuguese)</li>
 *   <li>'u' - Unknown</li>
 * </ul>
 * </p>
 * 
 * This enumeration is used when placing ships on the board
 * to determine their orientation.
 * 
 * @author fba
 * @version 1.0
 */
public enum Compass {

    /** North direction ('n'). */
    NORTH('n'),

    /** South direction ('s'). */
    SOUTH('s'),

    /** East direction ('e'). */
    EAST('e'),

    /** West direction ('o'). */
    WEST('o'),

    /** Unknown direction ('u'). */
    UNKNOWN('u');

    /**
     * Character representation of the direction.
     */
    private final char c;

    /**
     * Constructs a Compass direction associated with a character.
     *
     * @param c the character representing the direction
     */
    Compass(char c) {
        this.c = c;
    }

    /**
     * Returns the character associated with this direction.
     *
     * @return the direction character
     */
    public char getDirection() {
        return c;
    }

    /**
     * Returns the string representation of the direction.
     *
     * @return a string containing the direction character
     */
    @Override
    public String toString() {
        return String.valueOf(c);
    }

    /**
     * Converts a character into the corresponding Compass value.
     * <p>
     * If the character does not match any known direction,
     * {@link #UNKNOWN} is returned.
     * </p>
     *
     * @param ch the character representing a direction
     * @return the corresponding Compass value, or UNKNOWN if invalid
     */
    static Compass charToCompass(char ch) {
        Compass bearing;
        switch (ch) {
            case 'n':
                bearing = NORTH;
                break;
            case 's':
                bearing = SOUTH;
                break;
            case 'e':
                bearing = EAST;
                break;
            case 'o':
                bearing = WEST;
                break;
            default:
                bearing = UNKNOWN;
        }

        return bearing;
    }
}
