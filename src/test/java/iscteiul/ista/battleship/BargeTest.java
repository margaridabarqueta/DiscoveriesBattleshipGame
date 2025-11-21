package iscteiul.ista.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BargeTest {

    private Barge barge;
    private Position initial;

    @BeforeEach
    void setUp() {
        initial = new Position(2, 3);
        barge = new Barge(Compass.NORTH, initial);
    }

    @Test
    void testGetSizeReturnsOne() {
        assertEquals(1, barge.getSize());
    }

    @Test
    void testInitialPositionIsStored() {
        List<IPosition> positions = barge.getPositions();
        assertNotNull(positions);
        assertEquals(1, positions.size());
        IPosition stored = positions.get(0);
        assertEquals(initial.getRow(), stored.getRow());
        assertEquals(initial.getColumn(), stored.getColumn());
    }

    @Test
    void testCategoryIsBarca() {
        assertEquals("Barca", barge.getCategory());
    }
}
