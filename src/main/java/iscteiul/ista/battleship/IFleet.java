/**
 * */
package iscteiul.ista.battleship;

import java.util.List;

/**
 * Interface que define o contrato para uma frota de navios no jogo Battleship.
 * Estabelece as dimensões do tabuleiro, o limite de navios e as operações
 * essenciais para gerir o posicionamento e o estado dos navios.
 * * @author fba
 * @version 1.0
 */
public interface IFleet {
    
    /**
     * Tamanho padrão do tabuleiro (largura e altura).
     */
    Integer BOARD_SIZE = 10;
    
    /**
     * Número máximo de navios permitidos numa frota.
     */
    Integer FLEET_SIZE = 10;

    /**
     * Retorna a lista de todos os navios que compõem a frota.
     * * @return Uma {@link List} de objetos {@link IShip}.
     */
    List<IShip> getShips();

    /**
     * Tenta adicionar um navio à frota seguindo as regras de negócio 
     * (limites do tabuleiro, colisões e tamanho máximo da frota).
     * * @param s O navio ({@link IShip}) a ser adicionado.
     * @return {@code true} se o navio foi adicionado com sucesso, {@code false} caso contrário.
     */
    boolean addShip(IShip s);

    /**
     * Procura e retorna todos os navios da frota que pertencem a uma determinada categoria.
     * * @param category O nome da categoria (ex: "Fragata", "Galeao").
     * @return Uma {@link List} com os navios da categoria especificada.
     */
    List<IShip> getShipsLike(String category);

    /**
     * Filtra a frota e retorna apenas os navios que ainda não foram afundados.
     * * @return Uma {@link List} de navios que ainda flutuam.
     */
    List<IShip> getFloatingShips();

    /**
     * Verifica qual o navio que ocupa uma determinada posição no tabuleiro.
     * * @param pos A posição ({@link IPosition}) a ser consultada.
     * @return O objeto {@link IShip} presente nessa posição, ou {@code null} se estiver vazia.
     */
    IShip shipAt(IPosition pos);

    /**
     * Exibe no terminal o estado atual da frota, detalhando os navios 
     * existentes e o seu estado de integridade.
     */
    void printStatus();
}
