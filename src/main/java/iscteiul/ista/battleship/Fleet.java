/**
 * Pacote que contém a lógica do jogo Battleship da ISCTE-IUL.
 */
package iscteiul.ista.battleship;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma frota de navios no jogo Battleship.
 * Implementa a interface {@link IFleet} e gere o posicionamento, 
 * colisão e estado dos navios que pertencem a um jogador.
 * * @author [O Teu Nome/ID]
 * @version 1.0
 */
public class Fleet implements IFleet {
    
    /**
     * Operação auxiliar estática para imprimir no terminal os detalhes de uma lista de navios.
     *
     * @param ships A lista de navios ({@link IShip}) a serem impressos.
     */
    static void printShips(List<IShip> ships) {
        for (IShip ship : ships)
            System.out.println(ship);
    }

    // -----------------------------------------------------

    /**
     * Lista interna que armazena os navios que compõem a frota.
     */
    private List<IShip> ships;

    /**
     * Cria uma nova frota vazia inicializando a lista de navios.
     */
    public Fleet() {
        ships = new ArrayList<>();
    }

    /**
     * Obtém a lista completa de navios da frota.
     * * @return Uma {@link List} contendo todos os objetos {@link IShip} na frota.
     */
    @Override
    public List<IShip> getShips() {
        return ships;
    }

    /**
     * Tenta adicionar um navio à frota, verificando as regras de integridade.
     * O navio só é adicionado se houver espaço na frota, se estiver dentro dos 
     * limites do tabuleiro e se não colidir/estiver demasiado perto de outros navios.
     * * @param s O navio a ser adicionado.
     * @return {@code true} se o navio foi adicionado com sucesso; {@code false} caso contrário.
     */
    @Override
    public boolean addShip(IShip s) {
        boolean result = false;
        if ((ships.size() <= FLEET_SIZE) && (isInsideBoard(s)) && (!colisionRisk(s))) {
            ships.add(s);
            result = true;
        }
        return result;
    }

    /**
     * Filtra os navios da frota com base na sua categoria.
     * * @param category O nome da categoria a procurar (ex: "Caravela", "Nau").
     * @return Uma lista de navios que pertencem à categoria especificada.
     */
    @Override
    public List<IShip> getShipsLike(String category) {
        List<IShip> shipsLike = new ArrayList<>();
        for (IShip s : ships)
            if (s.getCategory().equals(category))
                shipsLike.add(s);

        return shipsLike;
    }

    /**
     * Filtra os navios da frota que ainda não foram totalmente afundados.
     * * @return Uma lista de navios que ainda têm partes intactas.
     */
    @Override
    public List<IShip> getFloatingShips() {
        List<IShip> floatingShips = new ArrayList<>();
        for (IShip s : ships)
            if (s.stillFloating())
                floatingShips.add(s);

        return floatingShips;
    }

    /**
     * Verifica se existe algum navio da frota a ocupar uma posição específica.
     * * @param pos A posição a verificar.
     * @return O objeto {@link IShip} presente na posição, ou {@code null} se a posição estiver vazia.
     */
    @Override
    public IShip shipAt(IPosition pos) {
        for (int i = 0; i < ships.size(); i++)
            if (ships.get(i).occupies(pos))
                return ships.get(i);
        return null;
    }

    /**
     * Verifica se o navio está totalmente contido dentro das dimensões do tabuleiro.
     * * @param s O navio a verificar.
     * @return {@code true} se estiver dentro dos limites; {@code false} caso contrário.
     */
    private boolean isInsideBoard(IShip s) {
        return (s.getLeftMostPos() >= 0 && s.getRightMostPos() <= BOARD_SIZE - 1 && s.getTopMostPos() >= 0
                && s.getBottomMostPos() <= BOARD_SIZE - 1);
    }

    /**
     * Verifica se o navio fornecido entra em colisão ou está em contacto direto 
     * com algum navio já existente na frota.
     * * @param s O navio a testar.
     * @return {@code true} se houver risco de colisão; {@code false} se a posição for segura.
     */
    private boolean colisionRisk(IShip s) {
        for (int i = 0; i < ships.size(); i++) {
            if (ships.get(i).tooCloseTo(s))
                return true;
        }
        return false;
    }

    /**
     * Imprime o estado geral da frota, incluindo todos os navios, 
     * os que flutuam e a listagem por categorias pré-definidas.
     */
    public void printStatus() {
        printAllShips();
        printFloatingShips();
        printShipsByCategory("Galeao");
        printShipsByCategory("Fragata");
        printShipsByCategory("Nau");
        printShipsByCategory("Caravela");
        printShipsByCategory("Barca");
    }

    /**
     * Imprime todos os navios da frota pertencentes a uma categoria específica.
     * * @param category A categoria de interesse. Não deve ser nula.
     */
    public void printShipsByCategory(String category) {
        assert category != null;

        printShips(getShipsLike(category));
    }

    /**
     * Imprime todos os navios da frota que ainda estão a flutuar.
     */
    public void printFloatingShips() {
        printShips(getFloatingShips());
    }

    /**
     * Imprime todos os navios presentes na frota.
     */
    void printAllShips() {
        printShips(ships);
    }

}
