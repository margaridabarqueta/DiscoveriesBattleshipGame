package iscteiul.ista.battleship;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Classe utilitária que contém diferentes tarefas (tasks) para testar
 * incrementalmente as várias funcionalidades do jogo Battleship.
 * <p>
 * Cada método taskX representa um nível de complexidade crescente:
 * <ul>
 *     <li>taskA – Teste de criação de navios e ocupação de posições</li>
 *     <li>taskB – Construção e gestão básica de frota</li>
 *     <li>taskC – Frota com possibilidade de visualização completa (batota)</li>
 *     <li>taskD – Execução completa do jogo com rondas de disparos</li>
 * </ul>
 * </p>
 */
public class Tasks {

    /**
     * Logger da aplicação.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Número de disparos por rajada.
     */
    private static final int NUMBER_SHOTS = 3;

    /**
     * Mensagem de despedida.
     */
    private static final String GOODBYE_MESSAGE = "Bons ventos!";

    /* Comandos suportados */
    private static final String NOVAFROTA = "nova";
    private static final String DESISTIR = "desisto";
    private static final String RAJADA = "rajada";
    private static final String VERTIROS = "ver";
    private static final String BATOTA = "mapa";
    private static final String STATUS = "estado";

    /**
     * Testa a criação de navios.
     * <p>
     * Para cada navio lido, são testadas três posições,
     * indicando se o navio ocupa cada uma delas.
     * </p>
     */
    public static void taskA() {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            Ship s = readShip(in);
            if (s != null)
                for (int i = 0; i < NUMBER_SHOTS; i++) {
                    Position p = readPosition(in);
                    LOGGER.info("{} {}", p, s.occupies(p));
                }
        }
    }

    /**
     * Testa a construção de uma frota e consulta do seu estado.
     */
    public static void taskB() {
        Scanner in = new Scanner(System.in);
        IFleet fleet = null;
        String command = in.next();

        while (!command.equals(DESISTIR)) {
            switch (command) {
                case NOVAFROTA:
                    fleet = buildFleet(in);
                    break;
                case STATUS:
                    if (fleet != null)
                        fleet.printStatus();
                    break;
                default:
                    LOGGER.info("Que comando é esse??? Repete lá ...");
            }
            command = in.next();
        }
        LOGGER.info(GOODBYE_MESSAGE);
    }

    /**
     * Testa a construção de frota com possibilidade de visualizar
     * internamente o mapa (modo batota).
     */
    public static void taskC() {
        Scanner in = new Scanner(System.in);
        IFleet fleet = null;
        String command = in.next();

        while (!command.equals(DESISTIR)) {
            switch (command) {
                case NOVAFROTA:
                    fleet = buildFleet(in);
                    break;
                case STATUS:
                    if (fleet != null)
                        fleet.printStatus();
                    break;
                case BATOTA:
                    LOGGER.info(fleet);
                    break;
                default:
                    LOGGER.info("Que comando é esse??? Repete lá ...");
            }
            command = in.next();
        }
        LOGGER.info(GOODBYE_MESSAGE);
    }

    /**
     * Testa o jogo completo incluindo disparos em rajada.
     * <p>
     * Cada rajada corresponde a três disparos consecutivos.
     * Apresenta estatísticas da ronda:
     * <ul>
     *     <li>Número de acertos</li>
     *     <li>Disparos inválidos</li>
     *     <li>Disparos repetidos</li>
     *     <li>Navios restantes</li>
     * </ul>
     * </p>
     */
    public static void taskD() {

        Scanner in = new Scanner(System.in);
        IFleet fleet = null;
        IGame game = null;
        String command = in.next();

        while (!command.equals(DESISTIR)) {
            switch (command) {
                case NOVAFROTA:
                    fleet = buildFleet(in);
                    game = new Game(fleet);
                    break;
                case STATUS:
                    if (fleet != null)
                        fleet.printStatus();
                    break;
                case BATOTA:
                    if (fleet != null)
                        game.printFleet();
                    break;
                case RAJADA:
                    if (game != null) {
                        firingRound(in, game);

                        LOGGER.info(
                                "Hits: {} Inv: {} Rep: {} Restam {} navios.",
                                game.getHits(),
                                game.getInvalidShots(),
                                game.getRepeatedShots(),
                                game.getRemainingShips()
                        );

                        if (game.getRemainingShips() == 0)
                            LOGGER.info("Maldito sejas, Java Sparrow, eu voltarei, glub glub glub...");
                    }
                    break;
                case VERTIROS:
                    if (game != null)
                        game.printValidShots();
                    break;
                default:
                    LOGGER.info("Que comando é esse??? Repete ...");
            }
            command = in.next();
        }
        LOGGER.info(GOODBYE_MESSAGE);
    }

    /**
     * Constrói uma frota com base nos dados fornecidos pelo utilizador.
     *
     * @param in scanner de entrada
     * @return frota criada
     */
    static Fleet buildFleet(Scanner in) {
        assert in != null;

        Fleet fleet = new Fleet();
        int i = 0;

        while (i <= Fleet.FLEET_SIZE) {
            IShip s = readShip(in);
            if (s != null) {
                boolean success = fleet.addShip(s);
                if (success)
                    i++;
                else
                    LOGGER.info("Falha na criacao de {} {} {}", 
                            s.getCategory(), s.getBearing(), s.getPosition());
            } else {
                LOGGER.info("Navio desconhecido!");
            }
        }

        LOGGER.info("{} navios adicionados com sucesso!", i);
        return fleet;
    }

    /**
     * Lê os dados de um navio e constrói a respetiva instância.
     *
     * @param in scanner de entrada
     * @return navio criado ou {@code null} se inválido
     */
    static Ship readShip(Scanner in) {
        String shipKind = in.next();
        Position pos = readPosition(in);
        char c = in.next().charAt(0);
        Compass bearing = Compass.charToCompass(c);
        return Ship.buildShip(shipKind, bearing, pos);
    }

    /**
     * Lê uma posição do input.
     *
     * @param in scanner de entrada
     * @return posição criada
     */
    static Position readPosition(Scanner in) {
        int row = in.nextInt();
        int column = in.nextInt();
        return new Position(row, column);
    }

    /**
     * Executa uma ronda de disparos (três tiros).
     *
     * @param in scanner de entrada
     * @param game contexto do jogo
     */
    static void firingRound(Scanner in, IGame game) {
        for (int i = 0; i < NUMBER_SHOTS; i++) {
            IPosition pos = readPosition(in);
            IShip sh = game.fire(pos);
            if (sh != null)
                LOGGER.info("Mas... mas... {}s nao sao a prova de bala? :-(", 
                        sh.getCategory());
        }
    }
}
