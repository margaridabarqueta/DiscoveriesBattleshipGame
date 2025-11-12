package iscteiul.ista.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes para a classe Fleet")
class FleetTest {

    private Fleet fleet;
    private Ship caravel;
    private Ship frigate;

    @BeforeEach
    void setUp() {
        fleet = new Fleet();
        caravel = new Caravel(Compass.NORTH, new Position(0, 0)); // size 2
        frigate = new Frigate(Compass.EAST, new Position(5, 5)); // size 3
    }

    @Test
    @DisplayName("Adicionar um navio válido à frota")
    void testAddShipSuccessfully() {
        assertTrue(fleet.addShip(caravel), "Deveria ser possível adicionar um navio válido.");
        assertEquals(1, fleet.getShips().size(), "A frota deveria ter 1 navio após a adição.");
        assertTrue(fleet.getShips().contains(caravel), "A frota deve conter o navio adicionado.");
    }

    @Test
    @DisplayName("Não adicionar navio fora do tabuleiro")
    void testAddShipOutsideBoard() {
        Ship outsideShip = new Caravel(Compass.NORTH, new Position(IFleet.BOARD_SIZE, IFleet.BOARD_SIZE));
        assertFalse(fleet.addShip(outsideShip), "Não deveria ser possível adicionar um navio fora do tabuleiro.");
        assertTrue(fleet.getShips().isEmpty(), "A frota deveria continuar vazia.");
    }

    @Test
    @DisplayName("Não adicionar navios sobrepostos")
    void testAddOverlappingShip() {
        fleet.addShip(caravel);
        Ship overlappingShip = new Caravel(Compass.EAST, new Position(0, 0)); // Sobreposto
        assertFalse(fleet.addShip(overlappingShip), "Não deveria ser possível adicionar um navio sobreposto.");
        assertEquals(1, fleet.getShips().size(), "A frota deveria continuar com 1 navio.");
    }

    @Test
    @DisplayName("Não adicionar navios muito próximos")
    void testAddTooCloseShip() {
        fleet.addShip(caravel); // Ocupa (0,0) e (1,0)
        // Este navio está em (2,0), adjacente ao 'caravel'
        Ship tooCloseShip = new Barge(Compass.NORTH, new Position(2, 0));
        assertFalse(fleet.addShip(tooCloseShip), "Não deveria ser possível adicionar um navio muito próximo de outro.");
        assertEquals(1, fleet.getShips().size());
    }

    @Test
    @DisplayName("Encontrar navios por categoria")
    void testGetShipsLike() {
        fleet.addShip(caravel);
        fleet.addShip(frigate);

        List<IShip> caravels = fleet.getShipsLike("Caravela");
        assertEquals(1, caravels.size());
        assertEquals(caravel, caravels.get(0));

        List<IShip> frigates = fleet.getShipsLike("Fragata");
        assertEquals(1, frigates.size());
        assertEquals(frigate, frigates.get(0));

        List<IShip> galleons = fleet.getShipsLike("Galeao");
        assertTrue(galleons.isEmpty());
    }

    @Test
    @DisplayName("Encontrar navio numa posição específica")
    void testShipAt() {
        fleet.addShip(caravel); // Ocupa (0,0) e (1,0)

        assertEquals(caravel, fleet.shipAt(new Position(0, 0)), "Deveria encontrar a caravela na posição (0,0).");
        assertEquals(caravel, fleet.shipAt(new Position(1, 0)), "Deveria encontrar a caravela na posição (1,0).");
        assertNull(fleet.shipAt(new Position(0, 1)), "Não deveria encontrar navio na posição (0,1).");
        assertNull(fleet.shipAt(new Position(5, 5)), "Não deveria encontrar navio numa posição vazia.");
    }

    @Test
    @DisplayName("Obter navios a flutuar")
    void testGetFloatingShips() {
        fleet.addShip(caravel);
        fleet.addShip(frigate);

        assertEquals(2, fleet.getFloatingShips().size(), "Ambos os navios deveriam estar a flutuar inicialmente.");

        // Afundar a caravela
        caravel.shoot(new Position(0, 0));
        caravel.shoot(new Position(1, 0));
        assertFalse(caravel.stillFloating());

        List<IShip> floating = fleet.getFloatingShips();
        assertEquals(1, floating.size(), "Apenas a fragata deveria estar a flutuar.");
        assertEquals(frigate, floating.get(0));
    }

    @Test
    @DisplayName("Verificar se todos os navios foram afundados")
    void testIsFleetSunk() {
        fleet.addShip(caravel);

        assertFalse(fleet.getFloatingShips().isEmpty(), "A frota não deve ser considerada afundada com navios a flutuar.");

        // Afundar a caravela
        caravel.shoot(new Position(0, 0));
        caravel.shoot(new Position(1, 0));

        assertTrue(fleet.getFloatingShips().isEmpty(), "A frota deve ser considerada afundada quando todos os navios o estão.");
    }

    @Test
    @DisplayName("Não adicionar navios para além do tamanho da frota")
    void testAddShipBeyondFleetSize() {
        // Adicionar o número máximo de navios permitido
        for (int shipIndex = 0; shipIndex < IFleet.FLEET_SIZE; shipIndex++) {
            // Criar navios que não se sobrepõem e não são adjacentes
            int row = (shipIndex / 5) * 2;
            int col = (shipIndex % 5) * 2;
            Ship ship = new Barge(Compass.NORTH, new Position(row, col));
            assertTrue(fleet.addShip(ship), "Deveria ser possível adicionar o navio " + (shipIndex + 1));
        }
        assertEquals(IFleet.FLEET_SIZE, fleet.getShips().size(), "A frota deveria estar cheia.");

        // Tentar adicionar mais um navio
        Ship extraShip = new Barge(Compass.NORTH, new Position(IFleet.BOARD_SIZE - 1, IFleet.BOARD_SIZE - 1));
        assertFalse(fleet.addShip(extraShip), "Não deveria ser possível adicionar um navio para além do limite.");
        assertEquals(IFleet.FLEET_SIZE, fleet.getShips().size(), "O tamanho da frota não deveria mudar.");
    }

    @Test
    @DisplayName("Não adicionar navio parcialmente fora do tabuleiro (borda direita)")
    void testAddShipPartiallyOutsideRight() {
        // Navio começa em BOARD_SIZE - 1 mas estende-se para fora
        Ship outsideShip = new Caravel(Compass.EAST, new Position(0, IFleet.BOARD_SIZE - 1));
        assertFalse(fleet.addShip(outsideShip), "Não deveria adicionar navio parcialmente fora da borda direita.");
    }

    @Test
    @DisplayName("Não adicionar navio parcialmente fora do tabuleiro (borda inferior)")
    void testAddShipPartiallyOutsideBottom() {
        // Navio começa em BOARD_SIZE - 1 mas estende-se para fora
        Ship outsideShip = new Caravel(Compass.SOUTH, new Position(IFleet.BOARD_SIZE - 1, 0));
        assertFalse(fleet.addShip(outsideShip), "Não deveria adicionar navio parcialmente fora da borda inferior.");
    }

    @Test
    @DisplayName("Testar os métodos de impressão para cobertura")
    void testPrintMethods() {
        fleet.addShip(caravel);
        // Estes testes apenas chamam os métodos para garantir que não lançam exceções
        // e para aumentar a cobertura de métodos. A saída não é verificada.
        assertDoesNotThrow(() -> fleet.printStatus());
        assertDoesNotThrow(() -> fleet.printShipsByCategory("Caravela"));
        assertDoesNotThrow(() -> fleet.printFloatingShips());
        assertDoesNotThrow(() -> fleet.printAllShips());
    }
}
