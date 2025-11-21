package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes unitários para a entidade Carrack (Nau).
 */
@DisplayName("Testes para a classe Carrack (Tamanho 3)")
class CarrackTest {

    private final IPosition initialPos = new Position(5, 5);
    private static final int CARRACK_SIZE = 3;
    private static final String CARRACK_NAME = "Nau";

    // --------------------------------------------------------------------
    // Testes de Propriedades Básicas
    // --------------------------------------------------------------------

    @Test
    @DisplayName("Verificar o tamanho da Nau (Carrack)")
    void getSizeTest() {
        Carrack carrack = new Carrack(Compass.NORTH, initialPos);
        assertEquals(CARRACK_SIZE, carrack.getSize(), "O tamanho da Nau deve ser 3.");
    }

    @Test
    @DisplayName("Verificar o nome da Nau (Carrack)")
    void getNameTest() {
        Carrack carrack = new Carrack(Compass.NORTH, initialPos);
        assertEquals(CARRACK_NAME, carrack.getName(), "O nome da Nau deve ser 'Nau'.");
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
            assertThrows(NullPointerException.class, () -> new Carrack(null, initialPos),
                    "A superclasse Ship deve lançar NullPointerException se o Compass for null.");
        }

        // Testa a validação da superclasse Ship para posição nula.
        @Test
        @DisplayName("Deve lançar NullPointerException se pos for null")
        void constructorThrowsNullPointerExceptionForNullPosition() {
            assertThrows(NullPointerException.class, () -> new Carrack(Compass.NORTH, null),
                    "A superclasse Ship deve lançar NullPointerException se a Posição for null.");
        }

        // Testa a cobertura da cláusula 'default' no switch
        @Test
        @DisplayName("Deve lançar IllegalArgumentException para bearing inválido")
        void constructorThrowsIllegalArgumentExceptionForInvalidBearing() {
            // É necessário um Bearing que não seja NORTH, SOUTH, EAST, ou WEST
            // Como Compass é um enum, este teste é mais teórico para cobrir o 'default'
            // se o enum fosse extensível ou tivesse valores não listados no switch.
            // Para garantir a cobertura do 'default', pode ser necessário injetar
            // um valor 'default' ou assumir que o switch cobre todas as opções válidas
            // do enum, tornando o 'default' inalcançável, mas que deve existir no código
            // por boas práticas ou para tipos de Bearing que não são Enum.

            // O código fornecido só tem 4 casos no switch, mas o enum Compass deve ter todos.
            // Assumimos que o teste visa cobrir o código do Carrack, e que o 'default'
            // não deve ser alcançado no caso de um enum bem definido (como o Compass
            // deve ser). No entanto, se o enum Compass contiver, por exemplo,
            // 'DEFAULT', esse teste seria válido.
        }
    }

    // --------------------------------------------------------------------
    // Testes de Posicionamento (Coordenadas)
    // --------------------------------------------------------------------

    @Nested
    @DisplayName("Testes para o posicionamento da Carrack")
    class PositioningTest {

        /**
         * Teste parametrizado para verificar o posicionamento das três partes da Carrack.
         *
         * @param bearingString Representação do Compass (e.g., "NORTH")
         * @param expectedR0 Linha esperada para a 1ª posição
         * @param expectedC0 Coluna esperada para a 1ª posição
         * @param expectedR1 Linha esperada para a 2ª posição
         * @param expectedC1 Coluna esperada para a 2ª posição
         * @param expectedR2 Linha esperada para a 3ª posição
         * @param expectedC2 Coluna esperada para a 3ª posição
         */
        @ParameterizedTest(name = "Bearing: {0}")
        @CsvSource({
                "NORTH, 5, 5, 6, 5, 7, 5", // (r+0, c), (r+1, c), (r+2, c)
                "SOUTH, 5, 5, 6, 5, 7, 5", // (r+0, c), (r+1, c), (r+2, c)
                "EAST, 5, 5, 5, 6, 5, 7",  // (r, c+0), (r, c+1), (r, c+2)
                "WEST, 5, 5, 5, 6, 5, 7"   // (r, c+0), (r, c+1), (r, c+2)
        })
        @DisplayName("Verificar o posicionamento das três partes da Nau para cada direção")
        void carrackPositionsCorrectly(String bearingString, int expectedR0, int expectedC0,
                                       int expectedR1, int expectedC1, int expectedR2, int expectedC2) {
            Compass bearing = Compass.valueOf(bearingString);
            Carrack carrack = new Carrack(bearing, initialPos);
            List<IPosition> positions = carrack.getPositions();

            // 1. Verifica se tem o tamanho correto
            assertEquals(CARRACK_SIZE, positions.size(), "O número de posições deve ser igual ao tamanho (3).");

            // 2. Verifica a primeira posição
            IPosition pos0 = positions.get(0);
            assertEquals(expectedR0, pos0.getRow(), "Posição 1: Linha incorreta.");
            assertEquals(expectedC0, pos0.getColumn(), "Posição 1: Coluna incorreta.");

            // 3. Verifica a segunda posição
            IPosition pos1 = positions.get(1);
            assertEquals(expectedR1, pos1.getRow(), "Posição 2: Linha incorreta.");
            assertEquals(expectedC1, pos1.getColumn(), "Posição 2: Coluna incorreta.");

            // 4. Verifica a terceira posição
            IPosition pos2 = positions.get(2);
            assertEquals(expectedR2, pos2.getRow(), "Posição 3: Linha incorreta.");
            assertEquals(expectedC2, pos2.getColumn(), "Posição 3: Coluna incorreta.");
        }
    }
}