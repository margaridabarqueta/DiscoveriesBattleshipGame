/**
 * */
package iscteiul.ista.battleship;

/**
 * Representa uma Fragata no jogo Battleship.
 * Uma Fragata é um navio de grande porte com um tamanho fixo de 4 unidades.
 * * @author [O Teu Nome/ID]
 * @version 1.0
 */
public class Frigate extends Ship {
    
    /**
     * O tamanho fixo da Fragata.
     */
    private static final Integer SIZE = 4;
    
    /**
     * O nome identificador deste tipo de navio.
     */
    private static final String NAME = "Fragata";

    /**
     * Constrói uma nova Fragata com uma orientação e posição inicial específicas.
     * Este construtor calcula as posições ocupadas pelo navio no tabuleiro com base 
     * no seu tamanho (4) e na direção fornecida.
     * * @param bearing a direção (norte, sul, este, oeste) para a qual a Fragata está orientada.
     * @param pos a posição inicial (âncora) a partir da qual o navio é gerado.
     * @throws IllegalArgumentException se o bearing for inválido ou nulo.
     */
    public Frigate(Compass bearing, IPosition pos) throws IllegalArgumentException {
        super(Frigate.NAME, bearing, pos);
        
        if (bearing == null) {
             throw new IllegalArgumentException("ERROR! invalid bearing for the frigate");
        }

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
                throw new IllegalArgumentException("ERROR! invalid bearing for the frigate");
        }
    }

    /**
     * Devolve o tamanho da Fragata.
     * * @return o número de células que o navio ocupa (neste caso, 4).
     * @see iscteiul.ista.battleship.Ship#getSize()
     */
    @Override
    public Integer getSize() {
        return Frigate.SIZE;
    }

}
