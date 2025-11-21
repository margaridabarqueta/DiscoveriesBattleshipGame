package iscteiul.ista.battleship;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Objects; // Assuming Objects is used in Position.java for hashCode()


class PositionTest {

    @Test
    void testConstructorAndGetters() {
        Position pos = new Position(5, 10);
        assertEquals(5, pos.getRow(), "Row should be 5");
        assertEquals(10, pos.getColumn(), "Column should be 10");
        assertFalse(pos.isOccupied(), "Initially, position should not be occupied");
        assertFalse(pos.isHit(), "Initially, position should not be hit");
    }

    @Test
    void testEquals() {
        Position pos1 = new Position(1, 1);
        Position pos2 = new Position(1, 1);
        Position pos3 = new Position(2, 2);
        Object nonPosition = new Object();

        // Same object
        assertTrue(pos1.equals(pos1), "A position should be equal to itself");

        // Equal objects
        assertTrue(pos1.equals(pos2), "Positions with same row and column should be equal");
        assertTrue(pos2.equals(pos1), "Equals should be symmetric");

        // Non-equal objects
        assertFalse(pos1.equals(pos3), "Positions with different row/column should not be equal");

        // Null object
        assertFalse(pos1.equals(null), "A position should not be equal to null");

        // Different type
        assertFalse(pos1.equals(nonPosition), "A position should not be equal to an object of a different type");
    }

    @Test
    void testHashCode() {
        Position pos1 = new Position(3, 7);
        Position pos2 = new Position(3, 7);
        Position pos3 = new Position(4, 7);

        assertEquals(pos1.hashCode(), pos2.hashCode(), "Equal positions should have equal hash codes");
        assertNotEquals(pos1.hashCode(), pos3.hashCode(), "Different positions should ideally have different hash codes");
    }

    @Test
    void testIsAdjacentTo() {
        Position center = new Position(5, 5);

        // Directly adjacent
        assertTrue(center.isAdjacentTo(new Position(5, 4)), "Adjacent left");
        assertTrue(center.isAdjacentTo(new Position(5, 6)), "Adjacent right");
        assertTrue(center.isAdjacentTo(new Position(4, 5)), "Adjacent up");
        assertTrue(center.isAdjacentTo(new Position(6, 5)), "Adjacent down");

        // Diagonally adjacent
        assertTrue(center.isAdjacentTo(new Position(4, 4)), "Diagonal up-left");
        assertTrue(center.isAdjacentTo(new Position(4, 6)), "Diagonal up-right");
        assertTrue(center.isAdjacentTo(new Position(6, 4)), "Diagonal down-left");
        assertTrue(center.isAdjacentTo(new Position(6, 6)), "Diagonal down-right");

        // Self-adjacent (same position)
        assertTrue(center.isAdjacentTo(new Position(5, 5)), "A position should be adjacent to itself");

        // Not adjacent
        assertFalse(center.isAdjacentTo(new Position(5, 7)), "Not adjacent (too far right)");
        assertFalse(center.isAdjacentTo(new Position(7, 5)), "Not adjacent (too far down)");
        assertFalse(center.isAdjacentTo(new Position(7, 7)), "Not adjacent (too far diagonal)");
    }

    @Test
    void testOccupyAndIsOccupied() {
        Position pos = new Position(1, 1);
        assertFalse(pos.isOccupied(), "Initially, not occupied");
        pos.occupy();
        assertTrue(pos.isOccupied(), "After occupy(), should be occupied");
    }

    @Test
    void testShootAndIsHit() {
        Position pos = new Position(2, 2);
        assertFalse(pos.isHit(), "Initially, not hit");
        pos.shoot();
        assertTrue(pos.isHit(), "After shoot(), should be hit");
    }

    @Test
    void testToString() {
        Position pos = new Position(0, 0);
        assertEquals("Linha = 0 Coluna = 0", pos.toString(), "ToString should match expected format");

        Position pos2 = new Position(10, 20);
        assertEquals("Linha = 10 Coluna = 20", pos2.toString(), "ToString should match expected format for different values");
    }
}