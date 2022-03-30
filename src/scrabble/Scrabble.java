package scrabble;

import io.In;
import io.Out;
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
                Out.msg("  REGLAS\n" +
                    "==========\n\n" +
                    "1.- La primera palabra deberá ponerse en las coordenadas 11 11\n" +
                    "2.- Cada jugador puede pasar hasta 3 veces antes de ser eliminado\n" +
                    "3.- \n");
                
                Out.msg("Pulsa enter para continuar");
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
