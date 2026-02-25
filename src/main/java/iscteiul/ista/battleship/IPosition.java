/**
 * */
package iscteiul.ista.battleship;

/**
 * Interface que define o contrato para uma posição (célula) no tabuleiro de Battleship.
 * Uma posição gere as suas coordenadas, a presença de componentes de um navio
 * e o registo de impactos de tiros.
 * * @author fba
 * @version 1.0
 */
public interface IPosition {
    
    /**
     * Obtém o índice da linha desta posição.
     * * @return O número da linha (geralmente começando em 0).
     */
    int getRow();

    /**
     * Obtém o índice da coluna desta posição.
     * * @return O número da coluna (geralmente começando em 0).
     */
    int getColumn();

    /**
     * Compara esta posição com outro objeto para verificar a igualdade de coordenadas.
     * * @param other O objeto a comparar.
     * @return {@code true} se as coordenadas (linha e coluna) forem iguais; {@code false} caso contrário.
     */
    boolean equals(Object other);

    /**
     * Verifica se esta posição é adjacente a outra posição (incluindo diagonais).
     * Útil para validar regras de proximidade entre navios.
     * * @param other A outra posição a verificar.
     * @return {@code true} se as posições forem vizinhas; {@code false} caso contrário.
     */
    boolean isAdjacentTo(IPosition other);

    /**
     * Marca esta posição como estando ocupada por uma parte de um navio.
     */
    void occupy();

    /**
     * Regista um disparo sobre esta posição, alterando o seu estado para "atingida".
     */
    void shoot();

    /**
     * Indica se a posição contém atualmente uma parte de um navio.
     * * @return {@code true} se estiver ocupada; {@code false} se for apenas água.
     */
    boolean isOccupied();

    /**
     * Indica se esta posição já foi alvo de um tiro bem-sucedido.
     * * @return {@code true} se a posição tiver sido atingida; {@code false} caso contrário.
     */
    boolean isHit();
}
