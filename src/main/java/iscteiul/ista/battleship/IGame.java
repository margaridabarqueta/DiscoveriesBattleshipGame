/**
 * */
package iscteiul.ista.battleship;

import java.util.List;

/**
 * Interface que define as operações essenciais para o controlo de um jogo de Battleship.
 * Estabelece os métodos para a execução de jogadas (tiros) e para a obtenção
 * de estatísticas e representações visuais do estado do jogo.
 * * @author fba
 * @version 1.0
 */
public interface IGame {
    
    /**
     * Executa um disparo numa posição específica do tabuleiro.
     * * @param pos A posição ({@link IPosition}) onde o tiro é desferido.
     * @return O navio ({@link IShip}) que foi afundado com este tiro; 
     * {@code null} se o tiro falhar ou se o navio atingido ainda estiver a flutuar.
     */
    IShip fire(IPosition pos);

    /**
     * Obtém a lista de todas as posições onde foram efetuados tiros válidos e não repetidos.
     * * @return Uma {@link List} de objetos {@link IPosition}.
     */
    List<IPosition> getShots();

    /**
     * Retorna o número de vezes que o jogador disparou sobre uma posição já atacada.
     * * @return O total de tiros repetidos.
     */
    int getRepeatedShots();

    /**
     * Retorna o número de tiros efetuados fora dos limites do tabuleiro.
     * * @return O total de tiros inválidos.
     */
    int getInvalidShots();

    /**
     * Retorna o número total de segmentos de navios atingidos com sucesso.
     * * @return O total de "hits".
     */
    int getHits();

    /**
     * Retorna o número de navios da frota que já foram totalmente destruídos.
     * * @return O total de navios afundados.
     */
    int getSunkShips();

    /**
     * Calcula o número de navios que ainda permanecem a flutuar na frota.
     * * @return O número de navios restantes.
     */
    int getRemainingShips();

    /**
     * Apresenta uma representação visual do tabuleiro contendo apenas os tiros válidos efetuados.
     */
    void printValidShots();

    /**
     * Apresenta uma representação visual do tabuleiro contendo a localização de todos os navios.
     */
    void printFleet();
}
