package iscteiul.ista.battleship;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe abstrata que representa um navio no jogo Battleship.
 * <p>
 * Fornece implementação base comum a todos os tipos concretos
 * de embarcações (Barge, Caravel, Carrack, Frigate, Galleon).
 * </p>
 * <p>
 * A classe é responsável por:
 * <ul>
 *     <li>Gerir categoria, orientação e posição inicial</li>
 *     <li>Manter a lista de posições ocupadas</li>
 *     <li>Controlar o estado de dano</li>
 *     <li>Verificar proximidade e colisões</li>
 * </ul>
 * </p>
 */
public abstract class Ship implements IShip {

    /* Constantes usadas pela factory method */
    private static final String GALEAO = "galeao";
    private static final String FRAGATA = "fragata";
    private static final String NAU = "nau";
    private static final String CARAVELA = "caravela";
    private static final String BARCA = "barca";

    /**
     * Método fábrica responsável por instanciar o tipo correto de navio.
     *
     * @param shipKind tipo de navio (galeao, fragata, nau, caravela, barca)
     * @param bearing orientação do navio
     * @param pos posição inicial
     * @return instância concreta de {@code Ship} correspondente;
     *         {@code null} se o tipo for inválido
     */
    static Ship buildShip(String shipKind, Compass bearing, Position pos) {
        Ship s;
        switch (shipKind) {
            case BARCA:
                s = new Barge(bearing, pos);
                break;
            case CARAVELA:
                s = new Caravel(bearing, pos);
                break;
            case NAU:
                s = new Carrack(bearing, pos);
                break;
            case FRAGATA:
                s = new Frigate(bearing, pos);
                break;
            case GALEAO:
                s = new Galleon(bearing, pos);
                break;
            default:
                s = null;
        }
        return s;
    }

    /**
     * Categoria (nome) do navio.
     */
    private String category;

    /**
     * Orientação do navio.
     */
    private Compass bearing;

    /**
     * Posição inicial (origem) do navio.
     */
    private IPosition pos;

    /**
     * Lista de posições ocupadas pelo navio.
     */
    protected List<IPosition> positions;

    /**
     * Construtor base para todos os navios.
     *
     * @param category categoria do navio
     * @param bearing orientação
     * @param pos posição inicial
     */
    public Ship(String category, Compass bearing, IPosition pos) {
        assert bearing != null;
        assert pos != null;

        this.category = category;
        this.bearing = bearing;
        this.pos = pos;
        this.positions = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCategory() {
        return category;
    }

    /**
     * Devolve a lista de posições ocupadas pelo navio.
     *
     * @return lista de posições
     */
    public List<IPosition> getPositions() {
        return positions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPosition getPosition() {
        return pos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Compass getBearing() {
        return bearing;
    }

    /**
     * Indica se o navio ainda possui pelo menos uma posição não atingida.
     *
     * @return {@code true} se ainda estiver a flutuar;
     *         {@code false} se todas as posições estiverem atingidas
     */
    @Override
    public boolean stillFloating() {
        for (int i = 0; i < getSize(); i++)
            if (!getPositions().get(i).isHit())
                return true;
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTopMostPos() {
        int top = getPositions().get(0).getRow();
        for (int i = 1; i < getSize(); i++)
            if (getPositions().get(i).getRow() < top)
                top = getPositions().get(i).getRow();
        return top;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBottomMostPos() {
        int bottom = getPositions().get(0).getRow();
        for (int i = 1; i < getSize(); i++)
            if (getPositions().get(i).getRow() > bottom)
                bottom = getPositions().get(i).getRow();
        return bottom;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLeftMostPos() {
        int left = getPositions().get(0).getColumn();
        for (int i = 1; i < getSize(); i++)
            if (getPositions().get(i).getColumn() < left)
                left = getPositions().get(i).getColumn();
        return left;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRightMostPos() {
        int right = getPositions().get(0).getColumn();
        for (int i = 1; i < getSize(); i++)
            if (getPositions().get(i).getColumn() > right)
                right = getPositions().get(i).getColumn();
        return right;
    }

    /**
     * Verifica se o navio ocupa uma determinada posição.
     *
     * @param pos posição a verificar
     * @return {@code true} se ocupar essa posição
     */
    @Override
    public boolean occupies(IPosition pos) {
        assert pos != null;

        for (int i = 0; i < getSize(); i++)
            if (getPositions().get(i).equals(pos))
                return true;

        return false;
    }

    /**
     * Verifica se o navio está demasiado próximo de outro navio.
     *
     * @param other outro navio
     * @return {@code true} se existir proximidade proibida
     */
    @Override
    public boolean tooCloseTo(IShip other) {
        assert other != null;

        Iterator<IPosition> otherPos = other.getPositions().iterator();
        while (otherPos.hasNext())
            if (tooCloseTo(otherPos.next()))
                return true;

        return false;
    }

    /**
     * Verifica se o navio está demasiado próximo de uma posição.
     *
     * @param pos posição a verificar
     * @return {@code true} se alguma posição do navio for adjacente
     */
    @Override
    public boolean tooCloseTo(IPosition pos) {
        for (int i = 0; i < this.getSize(); i++)
            if (getPositions().get(i).isAdjacentTo(pos))
                return true;

        return false;
    }

    /**
     * Regista um disparo numa determinada posição.
     *
     * @param pos posição alvo
     */
    @Override
    public void shoot(IPosition pos) {
        assert pos != null;

        for (IPosition position : getPositions()) {
            if (position.equals(pos))
                position.shoot();
        }
    }

    /**
     * Devolve representação textual do navio.
     *
     * @return string com categoria, orientação e posição inicial
     */
    @Override
    public String toString() {
        return "[" + category + " " + bearing + " " + pos + "]";
    }
}
