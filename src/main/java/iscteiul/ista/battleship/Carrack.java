package iscteiul.ista.battleship;

/**
 * Representa uma embarcação do tipo Carrack (Nau) no jogo Battleship.
 * <p>
 * A Carrack tem tamanho fixo de 3 posições consecutivas no tabuleiro.
 * A sua orientação pode ser NORTH, SOUTH, EAST ou WEST.
 * Dependendo da orientação, as posições ocupadas são calculadas
 * a partir de uma posição inicial.
 * </p>
 * 
 * @author 
 */
public class Carrack extends Ship {

    /**
     * Tamanho fixo da Carrack.
     */
    private static final Integer SIZE = 3;

    /**
     * Nome da embarcação.
     */
    private static final String NAME = "Nau";

    /**
     * Constrói uma Carrack com uma determinada orientação e posição inicial.
     * <p>
     * As posições ocupadas pelo navio são calculadas automaticamente
     * com base na orientação:
     * <ul>
     *     <li>NORTH ou SOUTH – ocupa 3 posições consecutivas na vertical</li>
     *     <li>EAST ou WEST – ocupa 3 posições consecutivas na horizontal</li>
     * </ul>
     * </p>
     *
     * @param bearing orientação da embarcação (NORTH, SOUTH, EAST ou WEST)
     * @param pos posição inicial (célula de origem) da embarcação
     * @throws IllegalArgumentException se a orientação for inválida
     */
    public Carrack(Compass bearing, IPosition pos) throws IllegalArgumentException {
        super(Carrack.NAME, bearing, pos);

        switch (bearing) {
            case NORTH:
            case SOUTH:
                for (int r = 0; r < SIZE; r++) {
                    getPositions().add(new Position(pos.getRow() + r, pos.getColumn()));
                }
                break;

            case EAST:
            case WEST:
                for (int c = 0; c < SIZE; c++) {
                    getPositions().add(new Position(pos.getRow(), pos.getColumn() + c));
                }
                break;

            default:
                throw new IllegalArgumentException("ERROR! Invalid bearing for Carrack.");
        }
    }

    /**
     * Devolve o tamanho da Carrack.
     *
     * @return o número de posições ocupadas pela embarcação (3)
     */
    @Override
    public Integer getSize() {
        return Carrack.SIZE;
    }
}
