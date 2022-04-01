package scrabble;

import io.In;
import io.Out;
import static io.Out.Color.*;
import java.io.UnsupportedEncodingException;
import ui.Menu;

/**
 * Clase principal del proyecto.
 * 
 * Importante: Para que la salida no se descuadre es necesario
 * seleccionar la fuente "DejaVu Sans Mono 12" en la consola de
 * netbeans.
 * 
 * @author Iago Oitavén Fraga y Samuel Novoa Comesaña
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
                
                Out.msg("1.- La primera palabra deberá tener al menos una de las letras en la coordenada 11 11\n\n" +
                        "2.- Cada jugador puede pasar hasta 3 veces antes de ser eliminado\n\n" +
                        "3.- Para poner una paralabra tienes que introducir una línea con el formato:\n\t\"PALABRA POS_X[INT] POS_Y[INT] DIRECCION[H|V]\"\n\n" +
                        "4.- Para usar el ' * ' (comodín) se escribe el caracter directamente en el lugar que ocuparía la letra\n\n" +
                        "5.- Casillas premiadas:\n\t\u2191 - Valor da letra x2\n\t\u21C8 - Valor da letra x3\n\t\u2191\u2191\u2191 - Valor da letra x4\n\t\u25B2 - Valor da palabra x2\n\n");
                
                Out.msg("Pulsa enter para continuar", false);
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
