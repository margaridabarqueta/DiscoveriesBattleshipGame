package iscteiul.ista.battleship;

import java.util.List;

/**
 * Representa o contrato de uma embarcação no jogo Battleship.
 * <p>
 * Define o comportamento comum a todos os tipos de navios,
 * incluindo informação sobre posição, orientação, estado
 * (atingido ou não) e regras de proximidade.
 * </p>
 */
public interface IShip {

    /**
     * Devolve a categoria (nome) do navio.
     *
     * @return a designação da embarcação
     */
    String getCategory();

    /**
     * Devolve o tamanho do navio.
     *
     * @return o número de posições ocupadas no tabuleiro
     */
    Integer getSize();

    /**
     * Devolve a lista de todas as posições ocupadas pelo navio.
     *
     * @return lista de posições ocupadas
     */
    List<IPosition> getPositions();

    /**
     * Devolve a posição inicial (posição de origem) do navio.
     *
     * @return posição inicial
     */
    IPosition getPosition();

    /**
     * Devolve a orientação do navio.
     *
     * @return orientação (NORTH, SOUTH, EAST ou WEST)
     */
    Compass getBearing();

    /**
     * Indica se o navio ainda se encontra a flutuar.
     * <p>
     * Um navio deixa de flutuar quando todas as suas posições
     * tiverem sido atingidas.
     * </p>
     *
     * @return {@code true} se ainda existir pelo menos uma posição não atingida;
     *         {@code false} caso contrário
     */
    boolean stillFloating();

    /**
     * Devolve o índice da linha mais a norte ocupada pelo navio.
     *
     * @return valor mínimo da linha ocupada
     */
    int getTopMostPos();

    /**
     * Devolve o índice da linha mais a sul ocupada pelo navio.
     *
     * @return valor máximo da linha ocupada
     */
    int getBottomMostPos();

    /**
     * Devolve o índice da coluna mais a oeste ocupada pelo navio.
     *
     * @return valor mínimo da coluna ocupada
     */
    int getLeftMostPos();

    /**
     * Devolve o índice da coluna mais a este ocupada pelo navio.
     *
     * @return valor máximo da coluna ocupada
     */
    int getRightMostPos();

    /**
     * Verifica se o navio ocupa uma determinada posição.
     *
     * @param pos posição a verificar
     * @return {@code true} se o navio ocupar essa posição;
     *         {@code false} caso contrário
     */
    boolean occupies(IPosition pos);

    /**
     * Verifica se o navio está demasiado próximo de outro navio.
     * <p>
     * A definição de "demasiado próximo" depende das regras do jogo
     * (por exemplo, navios não se podem tocar nem nas diagonais).
     * </p>
     *
     * @param other outro navio
     * @return {@code true} se estiverem demasiado próximos;
     *         {@code false} caso contrário
     */
    boolean tooCloseTo(IShip other);

    /**
     * Verifica se o navio está demasiado próximo de uma posição.
     *
     * @param pos posição a verificar
     * @return {@code true} se estiver demasiado próximo;
     *         {@code false} caso contrário
     */
    boolean tooCloseTo(IPosition pos);

    /**
     * Regista um disparo numa determinada posição.
     * <p>
     * Caso a posição pertença ao navio, essa posição deve
     * ser marcada como atingida.
     * </p>
     *
     * @param pos posição onde foi efetuado o disparo
     */
    void shoot(IPosition pos);
}
