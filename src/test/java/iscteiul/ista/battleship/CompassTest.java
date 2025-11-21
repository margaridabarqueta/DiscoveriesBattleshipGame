package iscteiul.ista.battleship;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CompassTest {

    @Test
    void testCharToCompassValidCharacters() {
        assertEquals(Compass.NORTH, Compass.charToCompass('n'));
        assertEquals(Compass.SOUTH, Compass.charToCompass('s'));
        assertEquals(Compass.EAST, Compass.charToCompass('e'));
        assertEquals(Compass.WEST, Compass.charToCompass('o'));
    }

    @Test
    void testCharToCompassInvalidCharacter() {
        assertEquals(Compass.UNKNOWN, Compass.charToCompass('x'));
        assertEquals(Compass.UNKNOWN, Compass.charToCompass('N')); // Case-sensitive
        assertEquals(Compass.UNKNOWN, Compass.charToCompass(' '));
        assertEquals(Compass.UNKNOWN, Compass.charToCompass('1'));
    }

    @Test
    void testGetDirection() {
        assertEquals('n', Compass.NORTH.getDirection());
        assertEquals('s', Compass.SOUTH.getDirection());
        assertEquals('e', Compass.EAST.getDirection());
        assertEquals('o', Compass.WEST.getDirection());
        assertEquals('u', Compass.UNKNOWN.getDirection());
    }

    @Test
    void testToString() {
        assertEquals("n", Compass.NORTH.toString());
        assertEquals("s", Compass.SOUTH.toString());
        assertEquals("e", Compass.EAST.toString());
        assertEquals("o", Compass.WEST.toString());
        assertEquals("u", Compass.UNKNOWN.toString());
    }
}