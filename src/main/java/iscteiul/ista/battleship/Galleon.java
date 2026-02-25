/**
 * */
package iscteiul.ista.battleship;

/**
 * Representa um Galeão no jogo Battleship.
 * O Galeão é um navio de grande porte com tamanho fixo de 5 unidades e uma 
 * geometria não linear, definida conforme a sua orientação.
 * * @author [O Teu Nome/ID]
 * @version 1.0
 */
public class Galleon extends Ship {
    
    /**
     * O tamanho fixo do Galeão (número de posições ocupadas).
     */
    private static final Integer SIZE = 5;
    
    /**
     * O nome identificador deste tipo de navio.
     */
    private static final String NAME = "Galeao";

    /**
     * Constrói um novo Galeão com uma orientação e posição inicial específicas.
     * Invoca métodos auxiliares de preenchimento dependendo da direção fornecida 
     * para definir a forma do navio.
     * * @param bearing a direção (norte, sul, este, oeste) para a qual o Galeão está virado.
     * @param pos a posição de referência para o posicionamento do navio.
     * @throws NullPointerException se o bearing for nulo.
     * @throws IllegalArgumentException se o bearing for inválido.
     */
    public Galleon(Compass bearing, IPosition pos) throws IllegalArgumentException {
        super(Galleon.NAME, bearing, pos);

        if (bearing == null)
            throw new NullPointerException("ERROR! invalid bearing for the galleon");

        switch (bearing) {
            case NORTH:
                fillNorth(pos);
                break;
            case EAST:
                fillEast(pos);
                break;
            case SOUTH:
                fillSouth(pos);
                break;
            case WEST:
                fillWest(pos);
                break;

            default:
                throw new IllegalArgumentException("ERROR! invalid bearing for the galleon");
        }
    }

    /**
     * Devolve o tamanho do Galeão.
     * * @return o número de células que o navio ocupa (neste caso, 5).
     * @see iscteiul.ista.battleship.Ship#getSize()
     */
    @Override
    public Integer getSize() {
        return Galleon.SIZE;
    }

    /**
     * Preenche as posições do navio quando este está orientado a Norte.
     * @param pos posição inicial de referência.
     */
    private void fillNorth(IPosition pos) {
        for (int i = 0; i < 3; i++) {
            getPositions().add(new Position(pos.getRow(), pos.getColumn() + i));
        }
        getPositions().add(new Position(pos.getRow() + 1, pos.getColumn() + 1));
        getPositions().add(new Position(pos.getRow() + 2, pos.getColumn() + 1));
    }

    /**
     * Preenche as posições do navio quando este está orientado a Sul.
     * @param pos posição inicial de referência.
     */
    private void fillSouth(IPosition pos) {
        for (int i = 0; i < 2; i++) {
            getPositions().add(new Position(pos.getRow() + i, pos.getColumn()));
        }
        for (int j = 2; j < 5; j++) {
            getPositions().add(new Position(pos.getRow() + 2, pos.getColumn() + j - 3));
        }
    }

    /**
     * Preenche as posições do navio quando este está orientado a Este.
     * @param pos posição inicial de referência.
     */
    private void fillEast(IPosition pos) {
        getPositions().add(new Position(pos.getRow(), pos.getColumn()));
        for (int i = 1; i < 4; i++) {
            getPositions().add(new Position(pos.getRow() + 1, pos.getColumn() + i - 3));
        }
        getPositions().add(new Position(pos.getRow() + 2, pos.getColumn()));
    }

    /**
     * Preenche as posições do navio quando este está orientado a Oeste.
     * @param pos posição inicial de referência.
     */
    private void fillWest(IPosition pos) {
        getPositions().add(new Position(pos.getRow(), pos.getColumn()));
        for (int i = 1; i < 4; i++) {
            getPositions().add(new Position(pos.getRow() + 1, pos.getColumn() + i - 1));
        }
        getPositions().add(new Position(pos.getRow() + 2, pos.getColumn()));
    }

}
