package iscteiul.ista.battleship;

import java.util.Objects;

/**
 * Representa uma posição (célula) no tabuleiro do jogo Battleship.
 * <p>
 * Cada posição é identificada por uma linha e uma coluna e pode
 * encontrar-se ocupada por um navio e/ou atingida por um disparo.
 * </p>
 */
public class Position implements IPosition {

    /**
     * Índice da linha da posição.
     */
    private int row;

    /**
     * Índice da coluna da posição.
     */
    private int column;

    /**
     * Indica se a posição está ocupada por um navio.
     */
    private boolean isOccupied;

    /**
     * Indica se a posição já foi atingida por um disparo.
     */
    private boolean isHit;

    /**
     * Constrói uma nova posição com a linha e coluna especificadas.
     * <p>
     * Por omissão, a posição não está ocupada nem foi atingida.
     * </p>
     *
     * @param row índice da linha
     * @param column índice da coluna
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
        this.isOccupied = false;
        this.isHit = false;
    }

    /**
     * Devolve o índice da linha da posição.
     *
     * @return valor da linha
     */
    @Override
    public int getRow() {
        return row;
    }

    /**
     * Devolve o índice da coluna da posição.
     *
     * @return valor da coluna
     */
    @Override
    public int getColumn() {
        return column;
    }

    /**
     * Calcula o código hash da posição.
     *
     * @return valor hash baseado na linha, coluna e estado interno
     */
    @Override
    public int hashCode() {
        return Objects.hash(column, isHit, isOccupied, row);
    }

    /**
     * Compara esta posição com outro objeto.
     * <p>
     * Duas posições são consideradas iguais se tiverem a mesma
     * linha e coluna, independentemente do estado (ocupada ou atingida).
     * </p>
     *
     * @param otherPosition objeto a comparar
     * @return {@code true} se as posições tiverem a mesma linha e coluna;
     *         {@code false} caso contrário
     */
    @Override
    public boolean equals(Object otherPosition) {
        if (this == otherPosition)
            return true;

        if (otherPosition instanceof IPosition) {
            IPosition other = (IPosition) otherPosition;
            return (this.getRow() == other.getRow() &&
                    this.getColumn() == other.getColumn());
        }

        return false;
    }

    /**
     * Verifica se esta posição é adjacente a outra.
     * <p>
     * Considera-se adjacente qualquer posição cuja diferença
     * absoluta de linha e coluna seja no máximo 1
     * (inclui diagonais).
     * </p>
     *
     * @param other posição a comparar
     * @return {@code true} se for adjacente;
     *         {@code false} caso contrário
     */
    @Override
    public boolean isAdjacentTo(IPosition other) {
        return (Math.abs(this.getRow() - other.getRow()) <= 1 &&
                Math.abs(this.getColumn() - other.getColumn()) <= 1);
    }

    /**
     * Marca a posição como ocupada por um navio.
     */
    @Override
    public void occupy() {
        isOccupied = true;
    }

    /**
     * Marca a posição como atingida por um disparo.
     */
    @Override
    public void shoot() {
        isHit = true;
    }

    /**
     * Indica se a posição está ocupada.
     *
     * @return {@code true} se estiver ocupada;
     *         {@code false} caso contrário
     */
    @Override
    public boolean isOccupied() {
        return isOccupied;
    }

    /**
     * Indica se a posição já foi atingida.
     *
     * @return {@code true} se já tiver sido alvo de disparo;
     *         {@code false} caso contrário
     */
    @Override
    public boolean isHit() {
        return isHit;
    }

    /**
     * Devolve uma representação textual da posição.
     *
     * @return string com linha e coluna
     */
    @Override
    public String toString() {
        return "Linha = " + row + " Coluna = " + column;
    }
}
