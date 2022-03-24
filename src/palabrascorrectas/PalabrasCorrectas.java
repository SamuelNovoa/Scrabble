package palabrascorrectas;

import io.In;
import io.Out;
import ui.Menu;

/**
 * Clase principal del proyecto.
 * 
 * @author Iago Oitavén Fraga y Samuel Novoa Comesaña
 */

public class PalabrasCorrectas {
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
    public static void main(String[] args) {
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
                    "1.-\n" +
                    "2.-\n");
                
                Out.msg("¿Continuar? [S/N]: ");
                In.getConfirmation();
                break;
            case 2:
                Out.msg("Introduce el número de jugadores deseado: ", false);
                tp.setPlayers(In.getOption(2, 6));
                break;
            case 3:
                isRunning = false;
                break;
            default:
                break;
        }
    }
}
