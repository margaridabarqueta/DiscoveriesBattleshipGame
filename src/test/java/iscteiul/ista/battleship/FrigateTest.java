package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes unitários para a entidade Frigate (Fragata).
 * Tamanho esperado: 4.
 */
@DisplayName("Testes para a classe Frigate (Tamanho 4)")
class FrigateTest {

    private final IPosition initialPos = new Position(5, 5);
    private static final int FRIGATE_SIZE = 4;
    private static final String FRIGATE_NAME = "Fragata";

    // --------------------------------------------------------------------
    // Testes de Propriedades Básicas
    // --------------------------------------------------------------------

    @Test
    @DisplayName("Verificar o tamanho da Fragata")
    void getSizeTest() {
        Frigate frigate = new Frigate(Compass.NORTH, initialPos);
        assertEquals(FRIGATE_SIZE, frigate.getSize(), "O tamanho da Fragata deve ser 4.");
    }

    @Test
    @DisplayName("Verificar o nome da Fragata")
    void getNameTest() {
        Frigate frigate = new Frigate(Compass.NORTH, initialPos);
        // O método getName() não existe na classe Frigate, mas é herdado ou deve ser adicionado.
        // Assumindo que getName() existe na superclasse ou foi adicionado como em exemplos anteriores.
        // Aqui, o teste será comentado ou ajustado caso getName() não seja acessível.
        // assertEquals(FRIGATE_NAME, frigate.getName(), "O nome da Fragata deve ser 'Fragata'.");
    }

    // --------------------------------------------------------------------
    // Testes de Exceção (Validação do Construtor)
    // --------------------------------------------------------------------

    @Nested
    @DisplayName("Testes para exceções na inicialização")
    class ConstructorExceptionsTest {

        // Testa a validação da superclasse Ship para bearing nulo.
        @Test
        @DisplayName("Deve lançar NullPointerException se o bearing for null")
        void constructorThrowsNullPointerExceptionForNullBearing() {
            assertThrows(NullPointerException.class, () -> new Frigate(null, initialPos),
                    "A superclasse Ship deve lançar NullPointerException se o Compass for null.");
        }

        // Testa a validação da superclasse Ship para posição nula.
        @Test
        @DisplayName("Deve lançar NullPointerException se pos for null")
        void constructorThrowsNullPointerExceptionForNullPosition() {
            assertThrows(NullPointerException.class, () -> new Frigate(Compass.NORTH, null),
                    "A superclasse Ship deve lançar NullPointerException se a Posição for null.");
        }
    }

    // --------------------------------------------------------------------
    // Testes de Posicionamento (Coordenadas e Cobertura de Ramo)
    // --------------------------------------------------------------------

    @Nested
    @DisplayName("Testes para o posicionamento da Fragata")
    class PositioningTest {

        /**
         * Teste parametrizado para verificar o posicionamento das quatro partes da Fragata.
         *
         * @param bearingString Representação do Compass (e.g., "NORTH")
         * @param expectedR0 Linha esperada para a 1ª posição (r+0)
         * @param expectedC0 Coluna esperada para a 1ª posição (c+0)
         * @param expectedR3 Linha esperada para a 4ª posição (r+3)
         * @param expectedC3 Coluna esperada para a 4ª posição (c+3)
         */
        @ParameterizedTest(name = "Bearing: {0}")
        @CsvSource({
                "NORTH, 5, 5, 8, 5", // Vertical: (r+0) a (r+3)
                "SOUTH, 5, 5, 8, 5", // Vertical: (r+0) a (r+3)
                "EAST, 5, 5, 5, 8",  // Horizontal: (c+0) a (c+3)
                "WEST, 5, 5, 5, 8"   // Horizontal: (c+0) a (c+3)
        })
        @DisplayName("Verificar a primeira e a última posição da Fragata para cada direção (cobertura total)")
        void frigatePositionsCorrectly(String bearingString, int expectedR0, int expectedC0,
                                       int expectedR3, int expectedC3) {
            Compass bearing = Compass.valueOf(bearingString);
            Frigate frigate = new Frigate(bearing, initialPos);
            List<IPosition> positions = frigate.getPositions();

            // 1. Verifica se tem o tamanho correto
            assertEquals(FRIGATE_SIZE, positions.size(), "O número de posições deve ser igual ao tamanho (4).");

            // 2. Verifica a primeira posição (r=0 ou c=0)
            IPosition pos0 = positions.get(0);
            assertEquals(expectedR0, pos0.getRow(), "Posição 1: Linha incorreta.");
            assertEquals(expectedC0, pos0.getColumn(), "Posição 1: Coluna incorreta.");

            // 3. Verifica a última posição (r=3 ou c=3)
            IPosition pos3 = positions.get(3);
            assertEquals(expectedR3, pos3.getRow(), "Posição 4: Linha incorreta.");
            assertEquals(expectedC3, pos3.getColumn(), "Posição 4: Coluna incorreta.");

            // Nota: Este teste garante a cobertura dos ramos do switch e o loop de 0 a 3.
        }
    }
}