/**
 * */
package iscteiul.ista.battleship;

/**
 * Representa uma Caravele no jogo Battleship.
 * Uma Caravela é um tipo de navio com um tamanho fixo de 2 unidades.
 
 * @version 1.0
 */
public class Caravel extends Ship {
    
    /**
     * O tamanho fixo da Caravela.
     */
    private static final Integer SIZE = 2;
    
    /**
     * O nome identificador deste tipo de navio.
     */
    private static final String NAME = "Caravela";

    /**
     * Constrói uma nova Caravela com uma orientação e posição inicial específicas.
     * O método calcula e preenche a lista de posições ocupadas pelo navio com base no seu tamanho.
     * * @param bearing a direção (norte, sul, este, oeste) para onde a Caravela está virada.
     * @param pos a posição inicial (ponto de origem) para posicionar o navio.
     * @throws NullPointerException se o bearing for nulo.
     * @throws IllegalArgumentException se o bearing for inválido para a lógica da caravela.
     */
    public Caravel(Compass bearing, IPosition pos) throws NullPointerException, IllegalArgumentException {
        super(Caravel.NAME, bearing, pos);

        if (bearing == null)
            throw new NullPointerException("ERROR! invalid bearing for the caravel");

        switch (bearing) {
            case NORTH:
            case SOUTH:
                for (int r = 0; r < SIZE; r++)
                    getPositions().add(new Position(pos.getRow() + r, pos.getColumn()));
                break;
            case EAST:
            case WEST:
                for (int c = 0; c < SIZE; c++)
                    getPositions().add(new Position(pos.getRow(), pos.getColumn() + c));
                break;
            default:
                throw new IllegalArgumentException("ERROR! invalid bearing for the caravel");
        }

    }

    /**
     * Devolve o tamanho da Caravela.
     * * @return o número de células que o navio ocupa (neste caso, 2).
     * @see iscteiul.ista.battleship.Ship#getSize()
     */
    @Override
    public Integer getSize() {
        return SIZE;
    }

}
