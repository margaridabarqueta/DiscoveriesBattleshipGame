/**
 *
 */
package iscteiul.ista.battleship;

import java.util.Set;
import java.util.stream.Collectors;

public class Galleon extends Ship {
    private static final Integer SIZE = 5;
    private static final String NAME = "Galeao";

    /**
     * @param bearing
     * @param pos
     */
    public Galleon(Compass bearing, IPosition pos) throws IllegalArgumentException {
        super(Galleon.NAME, bearing, pos);

        if (bearing == null)
            throw new NullPointerException("ERROR! invalid bearing for the galleon");

        switch (bearing) {
            case NORTH:
                fillNorth(pos);
                break;
            case EAST:
                fillEast(pos);
                break;
            case SOUTH:
                fillSouth(pos);
                break;
            case WEST:
                fillWest(pos);
                break;

            default:
                throw new IllegalArgumentException("ERROR! invalid bearing for the galleon");
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see battleship.Ship#getSize()
     */
    @Override
    public Integer getSize() {
        return Galleon.SIZE;
    }

    public String getName() {
        return Galleon.NAME;
    }

    private void fillNorth(IPosition pos) {
        for (int i = 0; i < 3; i++) {
            getPositions().add(new Position(pos.getRow(), pos.getColumn() + i));
        }
        getPositions().add(new Position(pos.getRow() + 1, pos.getColumn() + 1));
        getPositions().add(new Position(pos.getRow() + 2, pos.getColumn() + 1));
    }

    private void fillSouth(IPosition pos) {
        // Bloco 1: Adiciona 2 posições verticais (i=0, 1) na coluna C
        for (int i = 0; i < 2; i++) { // i = 0, 1
            getPositions().add(new Position(pos.getRow() + i, pos.getColumn())); // (R+0, C), (R+1, C)
        }
        // [5, 5], [6, 5]

        // Bloco 2: Adiciona 3 posições horizontais na Row R+2
        for (int j = 2; j < 5; j++) { // j = 2, 3, 4
            // Coluna: C + j - 3
            // j=2 => C + 2 - 3 = C - 1 = 4
            // j=3 => C + 3 - 3 = C + 0 = 5
            // j=4 => C + 4 - 3 = C + 1 = 6
            getPositions().add(new Position(pos.getRow() + 2, pos.getColumn() + j - 3)); // (R+2, C-1), (R+2, C), (R+2, C+1)
        }
        // [7, 4], [7, 5], [7, 6]
    }

    private void fillEast(IPosition pos) {
        getPositions().add(new Position(pos.getRow(), pos.getColumn()));
        for (int i = 1; i < 4; i++) {
            getPositions().add(new Position(pos.getRow() + 1, pos.getColumn() + i - 3));
        }
        getPositions().add(new Position(pos.getRow() + 2, pos.getColumn()));
    }

    private void fillWest(IPosition pos) {
        getPositions().add(new Position(pos.getRow(), pos.getColumn()));
        for (int i = 1; i < 4; i++) {
            getPositions().add(new Position(pos.getRow() + 1, pos.getColumn() + i - 1));
        }
        getPositions().add(new Position(pos.getRow() + 2, pos.getColumn()));
    }

}
