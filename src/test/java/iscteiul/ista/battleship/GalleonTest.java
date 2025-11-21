package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes unitários para a entidade Galleon (Galeão).
 * Tamanho esperado: 5. O posicionamento é não-linear (forma de cruz ou T).
 */
@DisplayName("Testes para a classe Galleon (Tamanho 5)")
class GalleonTest {

    // Posição base (Row=5, Column=5)
    private final IPosition initialPos = new Position(5, 5);
    private static final int GALLEON_SIZE = 5;
    private static final String GALLEON_NAME = "Galeao";

    // --------------------------------------------------------------------
    // Testes de Propriedades Básicas
    // --------------------------------------------------------------------

    @Test
    @DisplayName("Verificar o tamanho do Galeão")
    void getSizeTest() {
        Galleon galleon = new Galleon(Compass.NORTH, initialPos);
        assertEquals(GALLEON_SIZE, galleon.getSize(), "O tamanho do Galeão deve ser 5.");
    }

    @Test
    @DisplayName("Verificar o nome do Galeão")
    void getNameTest() {
        Galleon galleon = new Galleon(Compass.NORTH, initialPos);
        assertEquals(GALLEON_NAME, galleon.getName(), "O nome do Galeão deve ser 'Galeao'.");
    }

    // --------------------------------------------------------------------
    // Testes de Exceção (Validação do Construtor)
    // --------------------------------------------------------------------

    @Nested
    @DisplayName("Testes para exceções na inicialização")
    class ConstructorExceptionsTest {

        // Testa a validação do Galleon para bearing nulo (lançada explicitamente no construtor).
        @Test
        @DisplayName("Deve lançar NullPointerException se o bearing for null")
        void constructorThrowsNullPointerExceptionForNullBearing() {
            assertThrows(NullPointerException.class, () -> new Galleon(null, initialPos),
                    "O construtor do Galleon deve lançar NullPointerException se o Compass for null.");
        }

        // Testa a validação da superclasse Ship para posição nula.
        @Test
        @DisplayName("Deve lançar NullPointerException se pos for null")
        void constructorThrowsNullPointerExceptionForNullPosition() {
            assertThrows(NullPointerException.class, () -> new Galleon(Compass.NORTH, null),
                    "A superclasse Ship deve lançar NullPointerException se a Posição for null.");
        }
    }

    // --------------------------------------------------------------------
    // Testes de Posicionamento (Cobertura e Lógica Complexa)
    // --------------------------------------------------------------------

    @Nested
    @DisplayName("Testes detalhados para o posicionamento e forma do Galeão")
    class PositioningTest {

        /**
         * Teste parametrizado para verificar as 5 posições exatas
         * para cada Bearing, garantindo que os métodos fillXxx funcionam.
         * A comparação é feita por Set de coordenadas String para evitar problemas de equals/hashCode na classe Position.
         *
         * Argumentos:
         * 1: Bearing
         * 2-11: R0, C0, R1, C1, R2, C2, R3, C3, R4, C4 (As 5 posições esperadas)
         */
        @ParameterizedTest(name = "Bearing: {0}")
        @CsvSource({
                // NORTH: Base 5,5. Shape: [5,5][5,6][5,7], [6,6], [7,6]
                "NORTH, 5, 5, 5, 6, 5, 7, 6, 6, 7, 6",

                // SOUTH: Base 5,5. Shape: [5,5], [6,5], [7,4], [7,5], [7,6] (Corrigido para corresponder à lógica de fillSouth)
                "SOUTH, 5, 5, 6, 5, 7, 4, 7, 5, 7, 6",

                // EAST: Base 5,5. Shape: [5,5], [6,3], [6,4], [6,5], [7,5]
                "EAST, 5, 5, 6, 3, 6, 4, 6, 5, 7, 5",

                // WEST: Base 5,5. Shape: [5,5], [6,5], [6,6], [6,7], [7,5]
                "WEST, 5, 5, 6, 5, 6, 6, 6, 7, 7, 5"
        })
        @DisplayName("Verificar as 5 coordenadas exatas para todas as direções")
        void galleonPositionsAreCorrect(String bearingString,
                                        int r0, int c0, int r1, int c1,
                                        int r2, int c2, int r3, int c3,
                                        int r4, int c4) {
            Compass bearing = Compass.valueOf(bearingString);
            Galleon galleon = new Galleon(bearing, initialPos);
            List<IPosition> positions = galleon.getPositions();

            assertEquals(GALLEON_SIZE, positions.size(), "Deve ter 5 posições.");

            // 1. Constrói o conjunto de coordenadas geradas (como strings "R#C#")
            Set<String> generatedCoords = positions.stream()
                    .map(p -> "R" + p.getRow() + "C" + p.getColumn())
                    .collect(Collectors.toSet());

            // 2. Constrói o conjunto de coordenadas esperadas (como strings "R#C#")
            Set<String> expectedCoords = Set.of(
                    "R" + r0 + "C" + c0, "R" + r1 + "C" + c1, "R" + r2 + "C" + c2,
                    "R" + r3 + "C" + c3, "R" + r4 + "C" + c4
            );

            // 3. Verifica se os conjuntos de coordenadas são idênticos, garantindo a cobertura total do navio.
            assertEquals(expectedCoords, generatedCoords,
                    "O conjunto de coordenadas geradas não corresponde ao conjunto esperado para o Bearing: " + bearingString);
        }
    }
}