package iscteiul.ista.battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {

    private Ship barge;
    private Position basePos;

    @BeforeEach
    void setUp() {
        basePos = new Position(3, 4);
        barge = new Barge(Compass.NORTH, basePos);
    }

    @AfterEach
    void tearDown() {
        barge = null;
        basePos = null;
    }

    @Test
    void buildShip() {
        Ship s1 = Ship.buildShip("barca", Compass.NORTH, new Position(0, 0));
        assertNotNull(s1);
        assertTrue(s1 instanceof Barge);

        Ship s2 = Ship.buildShip("caravela", Compass.EAST, new Position(1, 1));
        assertNotNull(s2);
        // other kinds should produce non-null ships
        Ship s3 = Ship.buildShip("nau", Compass.SOUTH, new Position(2, 2));
        assertNotNull(s3);
        Ship s4 = Ship.buildShip("fragata", Compass.WEST, new Position(3, 3));
        assertNotNull(s4);
        Ship s5 = Ship.buildShip("galeao", Compass.NORTH, new Position(4, 4));
        assertNotNull(s5);

        Ship invalid = Ship.buildShip("unknown", Compass.NORTH, new Position(5, 5));
        assertNull(invalid);
    }

    @Test
    void getCategory() {
        assertEquals("Barca", barge.getCategory());
    }

    @Test
    void getPositions() {
        assertEquals(1, barge.getPositions().size());
        IPosition p = barge.getPositions().get(0);
        assertEquals(basePos.getRow(), p.getRow());
        assertEquals(basePos.getColumn(), p.getColumn());
    }

    @Test
    void getPosition() {
        assertSame(basePos, barge.getPosition());
    }

    @Test
    void getBearing() {
        assertEquals(Compass.NORTH, barge.getBearing());
    }

    @Test
    void stillFloating() {
        // build a multi-cell ship to test the logic more thoroughly
        Ship multi = new Ship("Test", Compass.NORTH, new Position(1, 1)) {
            @Override
            public Integer getSize() {
                return 2;
            }
        };
        // define two segments
        multi.getPositions().add(new Position(1, 1));
        multi.getPositions().add(new Position(1, 2));

        // initially, no hits
        assertTrue(multi.stillFloating());

        // hit one position
        IPosition first = multi.getPositions().get(0);
        multi.shoot(first);
        assertTrue(multi.stillFloating(), "Ship should still float with one intact segment");

        // hit second position
        IPosition second = multi.getPositions().get(1);
        multi.shoot(second);
        assertFalse(multi.stillFloating(), "Ship should sink when all segments are hit");
    }

    @Test
    void getTopMostPos() {
        Ship multi = new Ship("Test", Compass.NORTH, new Position(5, 5)) {
            @Override
            public Integer getSize() {
                return 3;
            }
        };
        multi.getPositions().add(new Position(5, 5));
        multi.getPositions().add(new Position(3, 6));
        multi.getPositions().add(new Position(4, 4));

        assertEquals(3, multi.getTopMostPos());
    }

    @Test
    void getBottomMostPos() {
        Ship multi = new Ship("Test", Compass.NORTH, new Position(5, 5)) {
            @Override
            public Integer getSize() {
                return 3;
            }
        };
        multi.getPositions().add(new Position(5, 5));
        multi.getPositions().add(new Position(3, 6));
        multi.getPositions().add(new Position(7, 4));

        assertEquals(7, multi.getBottomMostPos());
    }

    @Test
    void getLeftMostPos() {
        Ship multi = new Ship("Test", Compass.NORTH, new Position(5, 5)) {
            @Override
            public Integer getSize() {
                return 3;
            }
        };
        multi.getPositions().add(new Position(5, 5));
        multi.getPositions().add(new Position(3, 6));
        multi.getPositions().add(new Position(7, 2));

        assertEquals(2, multi.getLeftMostPos());
    }

    @Test
    void getRightMostPos() {
        Ship multi = new Ship("Test", Compass.NORTH, new Position(5, 5)) {
            @Override
            public Integer getSize() {
                return 3;
            }
        };
        multi.getPositions().add(new Position(5, 5));
        multi.getPositions().add(new Position(3, 8));
        multi.getPositions().add(new Position(7, 2));

        assertEquals(8, multi.getRightMostPos());
    }

    @Test
    void occupies() {
        IPosition occupied = new Position(basePos.getRow(), basePos.getColumn());
        IPosition notOccupied = new Position(basePos.getRow() + 1, basePos.getColumn());

        assertTrue(barge.occupies(occupied));
        assertFalse(barge.occupies(notOccupied));
    }

    @Test
    void tooCloseToPosition() {
        Position adjacent = new Position(basePos.getRow() + 1, basePos.getColumn()); // directly below
        Position far = new Position(basePos.getRow() + 3, basePos.getColumn() + 3);

        assertTrue(barge.tooCloseTo(adjacent));
        assertFalse(barge.tooCloseTo(far));
    }

    @Test
    void tooCloseToShip() {
        // ship with an adjacent position
        Ship otherAdjacent = new Barge(Compass.SOUTH,
                new Position(basePos.getRow() + 1, basePos.getColumn())); // adjacent to barge

        // ship far away
        Ship otherFar = new Barge(Compass.SOUTH,
                new Position(basePos.getRow() + 5, basePos.getColumn() + 5));

        assertTrue(barge.tooCloseTo(otherAdjacent));
        assertFalse(barge.tooCloseTo(otherFar));
    }

    @Test
    void shoot() {
        Ship multi = new Ship("Test", Compass.NORTH, new Position(5, 5)) {
            @Override
            public Integer getSize() {
                return 2;
            }
        };
        Position p1 = new Position(5, 5);
        Position p2 = new Position(5, 6);
        multi.getPositions().add(p1);
        multi.getPositions().add(p2);

        assertFalse(p1.isHit());
        assertFalse(p2.isHit());

        // hit p1
        multi.shoot(new Position(5, 5));
        assertTrue(p1.isHit());
        assertFalse(p2.isHit(), "Only the targeted position should be hit");

        // shooting a non-occupied position must not change existing hits
        multi.shoot(new Position(0, 0));
        assertTrue(p1.isHit());
        assertFalse(p2.isHit());
    }

    @Test
    void testToString() {
        String s = barge.toString();
        assertNotNull(s);
        assertTrue(s.startsWith("["));
        assertTrue(s.contains("Barca"));
        assertTrue(s.contains(Compass.NORTH.toString()));
        // should at least include the row and column somewhere
        assertTrue(s.contains(Integer.toString(basePos.getRow())));
        assertTrue(s.contains(Integer.toString(basePos.getColumn())));
    }
}