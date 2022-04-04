package scrabble;

import libs.io.In;
import libs.io.Out;
import static libs.io.Out.Color.*;
import java.io.UnsupportedEncodingException;
import libs.ui.Menu;

/**
 * Clase principal del proyecto.
 * 
 * IMPORTANTE: Para que a saída se vexa adecuadamente é preciso seleccionar a fonte "DejaVu Sans Mono" na consola de netbeans.
 * Do contrario, a táboa veríase descadrada e os símbolos non se verían ben.
 * 
 * Melloras introducidas:
 *      - Escoller o número de xogadores [2 - 4].
 *      - Nome para cada xogador.
 *      - Primeira xogada aleatoria.
 *      - Menú coas regras do xogo e información de uso.
 *      - Cando un xogador pasa 3 veces, é eliminado, pero o resto pode seguir xogando.
 *      - A consola refréscase con cada acción do usuario.
 *      - Indicadores visuais dos comodíns, cantidade de veces que se pode pasar todavía e letras que quedan por saír.
 *      - Permite introducir comandos en lugar de indicarlle unha letra no taboleiro. Os comandos válidos son:
 *              * PASAR: Pasa turno.
 *              * SALIR: Remata a partida.
 *              * PUNTOS: Mostra a táboa coa puntuación dos xogadores.
 * 
 * @author Iago Oitavén Fraga e Samuel Novoa Comesaña
 */

public class Scrabble {
    private static TableTop tp = new TableTop();
    private static boolean isRunning = true;
    private static String[] opts = {
         "Juego nuevo",
         "Mostrar reglas",
         "Establecer número de jugadores",
         "Salir"
    };
    
    private static final Menu mainMenu = new Menu(tp.getLogo(), opts, true, true);
    
    /**
     * Método principal del proyecto.
     * 
     * @param args Argumentos de la línea de comandos
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        while (isRunning)
            executeOpt(mainMenu.getOption());
    }
    
    /**
     * Método dónde se definen las acciones para cada opción del menú.
     * 
     * @param opt Opción seleccionada por el usuario
     */
    private static void executeOpt(int opt) {
        switch (opt) {
            case 0:
                tp.newGame();
                break;
            case 1:
                Out.clear();
                Out.msg(Out.toColor("█▀▄ █▀▀ ▄▀▀▀ █░░ ▄▀▄ ▄▀▀\n", GREEN) + 
                        Out.toColor("█▀▄ █▀▀ █░▀█ █░░ █▄█ ░▀▄\n", GREEN) +
                        Out.toColor("▀░▀ ▀▀▀  ▀▀░ ▀▀▀ ▀░▀ ▀▀░\n\n", GREEN));
                
                Out.msg("\t1.- La primera palabra deberá tener al menos una de las letras en la coordenada 11 11\n\n" +
                        "\t2.- Cada jugador puede pasar hasta 3 veces antes de ser eliminado\n\n" +
                        "\t3.- Para poner una paralabra tienes que introducir una línea con el formato:\n\t\t\"PALABRA POS_X[INT] POS_Y[INT] DIRECCION[H|V]\"\n\n" +
                        "\t4.- Para usar el ' * ' (comodín) se escribe el caracter directamente en el lugar que ocuparía la letra y solo se podrá usar 1 por palabra a colocar\n\n" +
                        "\t5.- Casillas premiadas:\n\t\t\u2191 - Valor da letra x2\n\t\t\u21C8 - Valor da letra x3\n\t\t\u2191\u2191\u2191 - Valor da letra x4\n\t\t\u25B2 - Valor da palabra x2\n\n" +
                        "\t6.- Comandos disponibles:\n\t\tPASAR: Pasa turno.\n\t\tSALIR: Termina la partida actual\n\t\tPUNTOS: Muestra la puntuación de la partida.\n\n");
                
                Out.msg("Pulsa enter para continuar. ", false);
                In.pressToContinue();
                break;
            case 2:
                Out.msg("Introduce el número de jugadores deseado [2 - 4]: ", false);
                tp.setPlayers(In.getOption(2, 4));
                break;
            case 3:
                isRunning = false;
                break;
            default:
                break;
        }
    }
}
