/**
 *
 */
package iscteiul.ista.battleship;

/**
 * @author fba
 */
public enum Compass {
    NORTH('n'), SOUTH('s'), EAST('e'), WEST('o'), UNKNOWN('u');

    private final char c;

    Compass(char c) {
        this.c = c;
    }

    public char getDirection() {
        return c;
    }

    @Override
    public String toString() {
        return "" + c;
    }

    public static Compass charToCompass(char ch) {
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
