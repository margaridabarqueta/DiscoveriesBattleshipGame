/**
 * */
package iscteiul.ista.battleship;

import java.util.ArrayList;
import java.util.List;

/**
 * Controla a lógica principal de uma partida de Battleship.
 * Gere a frota, o registo de tiros efetuados e as estatísticas de jogo,
 * como acertos, navios afundados e tiros inválidos.
 * * @author fba
 * @version 1.0
 */
public class Game implements IGame {
    
    /** A frota de navios associada a este jogo. */
    private IFleet fleet;
    
    /** Lista de posições onde já foram efetuados tiros válidos. */
    private List<IPosition> shots;

    /** Contador de tiros disparados fora dos limites do tabuleiro. */
    private Integer countInvalidShots;
    
    /** Contador de tiros disparados em coordenadas já atacadas anteriormente. */
    private Integer countRepeatedShots;
    
    /** Contador de tiros que atingiram uma parte de um navio. */
    private Integer countHits;
    
    /** Contador de navios que foram totalmente afundados. */
    private Integer countSinks;

    /**
     * Constrói uma nova instância de um jogo com uma frota específica.
     * * @param fleet A frota de navios ({@link IFleet}) que será utilizada no jogo.
     */
    public Game(IFleet fleet) {
        shots = new ArrayList<>();
        countInvalidShots = 0;
        countRepeatedShots = 0;
        countHits = 0; // Inicializado para evitar NullPointerException
        countSinks = 0; // Inicializado para evitar NullPointerException
        this.fleet = fleet;
    }

    /**
     * Executa a ação de disparar sobre uma posição no tabuleiro.
     * O método valida o tiro, verifica se é repetido e, caso atinja um navio,
     * atualiza o estado do navio e as estatísticas do jogo.
     * * @param pos A posição ({@link IPosition}) de destino do tiro.
     * @return O navio ({@link IShip}) que foi afundado como resultado deste tiro; 
     * null se nenhum navio foi atingido ou se o navio atingido ainda flutua.
     */
    @Override
    public IShip fire(IPosition pos) {
        if (!validShot(pos)) {
            countInvalidShots++;
        } else { // valid shot!
            if (repeatedShot(pos)) {
                countRepeatedShots++;
            } else {
                shots.add(pos);
                IShip s = fleet.shipAt(pos);
                if (s != null) {
                    s.shoot(pos);
                    countHits++;
                    if (!s.stillFloating()) {
                        countSinks++;
                        return s;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Obtém a lista de todas as posições atacadas (tiros válidos e não repetidos).
     * * @return Uma lista de objetos {@link IPosition}.
     */
    @Override
    public List<IPosition> getShots() {
        return shots;
    }

    /**
     * Devolve o número total de tiros efetuados em coordenadas repetidas.
     * * @return O contador de tiros repetidos.
     */
    @Override
    public int getRepeatedShots() {
        return this.countRepeatedShots;
    }

    /**
     * Devolve o número total de tiros disparados fora dos limites do tabuleiro.
     * * @return O contador de tiros inválidos.
     */
    @Override
    public int getInvalidShots() {
        return this.countInvalidShots;
    }

    /**
     * Devolve o número total de vezes que um navio foi atingido.
     * * @return O contador de "hits".
     */
    @Override
    public int getHits() {
        return this.countHits;
    }

    /**
     * Devolve o número total de navios da frota que já foram afundados.
     * * @return O contador de navios afundados.
     */
    @Override
    public int getSunkShips() {
        return this.countSinks;
    }

    /**
     * Calcula quantos navios da frota ainda estão a flutuar.
     * * @return O número de navios restantes.
     */
    @Override
    public int getRemainingShips() {
        List<IShip> floatingShips = fleet.getFloatingShips();
        return floatingShips.size();
    }

    /**
     * Verifica se uma posição está dentro dos limites definidos para o tabuleiro.
     * * @param pos A posição a validar.
     * @return true se a posição for válida, false caso contrário.
     */
    private boolean validShot(IPosition pos) {
        return (pos.getRow() >= 0 && pos.getRow() < Fleet.BOARD_SIZE && pos.getColumn() >= 0
                && pos.getColumn() < Fleet.BOARD_SIZE);
    }

    /**
     * Verifica se uma posição já foi alvo de um tiro anteriormente.
     * * @param pos A posição a verificar.
     * @return true se o tiro for repetido, false caso contrário.
     */
    private boolean repeatedShot(IPosition pos) {
        for (int i = 0; i < shots.size(); i++)
            if (shots.get(i).equals(pos))
                return true;
        return false;
    }

    /**
     * Desenha uma representação textual do tabuleiro no terminal.
     * * @param positions A lista de posições a marcar no tabuleiro.
     * @param marker O caractere a usar para representar as posições fornecidas.
     */
    public void printBoard(List<IPosition> positions, Character marker) {
        char[][] map = new char[Fleet.BOARD_SIZE][Fleet.BOARD_SIZE];

        for (int r = 0; r < Fleet.BOARD_SIZE; r++)
            for (int c = 0; c < Fleet.BOARD_SIZE; c++)
                map[r][c] = '.';

        for (IPosition pos : positions)
            map[pos.getRow()][pos.getColumn()] = marker;

        for (int row = 0; row < Fleet.BOARD_SIZE; row++) {
            for (int col = 0; col < Fleet.BOARD_SIZE; col++)
                System.out.print(map[row][col]);
            System.out.println();
        }
    }

    /**
     * Imprime o tabuleiro marcando com 'X' todos os tiros válidos efetuados.
     */
    public void printValidShots() {
        printBoard(getShots(), 'X');
    }

    /**
     * Imprime o tabuleiro marcando com '#' a localização de todos os navios da frota.
     */
    public void printFleet() {
        List<IPosition> shipPositions = new ArrayList<IPosition>();

        for (IShip s : fleet.getShips())
            shipPositions.addAll(s.getPositions());

        printBoard(shipPositions, '#');
    }
}
