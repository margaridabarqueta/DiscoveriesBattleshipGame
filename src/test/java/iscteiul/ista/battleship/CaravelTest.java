package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes unitários para a entidade Caravel.
 */
@DisplayName("Testes para a classe Caravel (Tamanho 2)")
class CaravelTest {

    private final IPosition initialPos = new Position(5, 5);
    private static final int CARAVEL_SIZE = 2;
    private static final String CARAVEL_NAME = "Caravela";

    // Teste para o método getSize()
    @Test
    @DisplayName("Verificar o tamanho da Caravel")
    void getSizeTest() {
        Caravel caravel = new Caravel(Compass.NORTH, initialPos);
        assertEquals(CARAVEL_SIZE, caravel.getSize(), "O tamanho da Caravela deve ser 2.");
    }

    // Teste para o nome
    @Test
    @DisplayName("Verificar o nome da Caravel")
    void getNameTest() {
        Caravel caravel = new Caravel(Compass.NORTH, initialPos);
        assertEquals(CARAVEL_NAME, caravel.getName(), "O nome da Caravela deve ser 'Caravela'.");
    }

    // Testes de exceção
    @Nested
    @DisplayName("Testes para exceções na inicialização")
    class ConstructorExceptionsTest {
        @Test
        @DisplayName("Deve lançar NullPointerException se o bearing for null")
        void constructorThrowsNullPointerExceptionForNullBearing() {
            assertThrows(NullPointerException.class, () -> new Caravel(null, initialPos),
                    "Deve lançar NullPointerException se o Compass for null.");
        }

        @Test
        @DisplayName("Não deve lançar exceção se pos for null (deve ser tratado na classe Ship)")
        void constructorThrowsNullPointerExceptionForNullPosition() {
            assertThrows(NullPointerException.class, () -> new Caravel(Compass.NORTH, null),
                    "Deve lançar NullPointerException se a Posição for null, conforme a validação na superclasse Ship.");
        }
    }

    // Testes de posicionamento (Coordenadas)
    @Nested
    @DisplayName("Testes para o posicionamento da Caravel")
    class PositioningTest {

        /**
         * Teste parametrizado para verificar o posicionamento da Caravel
         * (row, column) da primeira e segunda posição para diferentes bearings.
         *
         * @param bearingString  Representação do Compass (e.g., "NORTH")
         * @param expectedR0     Linha esperada para a 1ª posição (pos.getRow())
         * @param expectedC0     Coluna esperada para a 1ª posição (pos.getColumn())
         * @param expectedR1     Linha esperada para a 2ª posição
         * @param expectedC1     Coluna esperada para a 2ª posição
         */
        @ParameterizedTest(name = "Bearing: {0}")
        @CsvSource({
                "NORTH, 5, 5, 6, 5",
                "SOUTH, 5, 5, 6, 5",
                "EAST, 5, 5, 5, 6",
                "WEST, 5, 5, 5, 6"
        })
        @DisplayName("Verificar o posicionamento das duas partes da Caravel para cada direção")
        void caravelPositionsCorrectly(String bearingString, int expectedR0, int expectedC0, int expectedR1, int expectedC1) {
            Compass bearing = Compass.valueOf(bearingString);
            Caravel caravel = new Caravel(bearing, initialPos);
            List<IPosition> positions = caravel.getPositions();

            // Verifica se tem o tamanho correto
            assertEquals(CARAVEL_SIZE, positions.size(), "O número de posições deve ser igual ao tamanho.");

            // Verifica a primeira posição
            IPosition pos0 = positions.get(0);
            assertEquals(expectedR0, pos0.getRow(), "Posição 1: Linha incorreta.");
            assertEquals(expectedC0, pos0.getColumn(), "Posição 1: Coluna incorreta.");

            // Verifica a segunda posição
            IPosition pos1 = positions.get(1);
            assertEquals(expectedR1, pos1.getRow(), "Posição 2: Linha incorreta.");
            assertEquals(expectedC1, pos1.getColumn(), "Posição 2: Coluna incorreta.");
        }
    }
}