package iscteiul.ista.battleship;

/**
 * Representa as direções cardeais utilizadas para a orientação dos navios no tabuleiro.
 * Permite mapear caracteres (n, s, e, o) para direções lógicas.
 * * @author fba
 */
public enum Compass {

    /** Direção Norte. */
    NORTH('n'), 
    
    /** Direção Sul. */
    SOUTH('s'), 
    
    /** Direção Este. */
    EAST('e'), 
    
    /** Direção Oeste. */
    WEST('o'), 
    
    /** Direção desconhecida ou inválida. */
    UNKNOWN('u');

    /** Carácter representativo da direção. */
    private final char c;

    /**
     * Construtor interno para associar um carácter a uma direção.
     * * @param c O carácter representativo (ex: 'n', 's').
     */
    Compass(char c) {
        this.c = c;
    }

    /**
     * Obtém o carácter que representa a direção.
     * * @return O carácter da direção.
     */
    public char getDirection() {
        return c;
    }

    /**
     * Retorna a representação textual da direção (o carácter).
     * * @return String contendo o carácter da direção.
     */
    @Override
    public String toString() {
        return "" + c;
    }

    /**
     * Converte um carácter na constante Compass correspondente.
     * * @param ch O carácter a converter ('n', 's', 'e', 'o').
     * @return A constante Compass correspondente ou {@link #UNKNOWN} se o carácter for inválido.
     */
    static Compass charToCompass(char ch) {
        Compass bearing;
        switch (ch) {
            case 'n':
                bearing = NORTH;
                break;
            case 's':
                bearing = SOUTH;
                break;
            case 'e':
                bearing = EAST;
                break;
            case 'o':
                bearing = WEST;
                break;
            default:
                bearing = UNKNOWN;
        }

        return bearing;
    }
}
