/*
 * Proxecto 1
 *
 * Palabras encadenadas + Scrabble
 * Autores: Iago Oitavén Fraga, Samuel Novoa Comesaña
 */

package scrabble;

import libs.io.Out.Color;
import java.util.Arrays;

/**
 * Clase que modela a información dun xogador.
 * 
 * @author Iago Oitavén Fraga e Samuel Novoa Comesaña
 */
public class Player implements Comparable<Player> {
    private final String NAME;
    
    private int points;
    private int fails;
    private String[] letters;
    private TableTop tp;
    private Color color;
    
    /**
     * Constructor do xogador.
     * 
     * @param name Nome do xogador
     */
    public Player(String name, TableTop tp, Color color) {
        this.NAME = name;
        this.points = 0;
        this.fails = 3;
        this.letters = tp.pickLetter(7);
        this.tp = tp;
        this.color = color;
        Arrays.sort(letters);
    }

    /**
     * Método para obter o nome do xogador.
     * 
     * @return O nome do xogador
     */
    public String getName() {
        return NAME;
    }

    /**
     * Método para obtener os puntos do xogador.
     * 
     * @return Os puntos do xogador
     */
    public int getPoints() {
        return points;
    }
    
    /**
     * Método para establecer os puntos do xogador.
     * 
     * @param points Os novos puntos do xogador
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Método para sumar puntos.
     * 
     * @param points Os puntos a sumar ó xogador.
     */
    public void plusPoints(int points) {
        this.points += points;
    }
    
    /**
     * Método para obtener o número de fallos do xogador.
     * 
     * @return O número de fallos do xogador
     */
    public int getFails() {
        return fails;
    }
    
    /**
     * Método para sumar un fallo ó xogador.
     */
    public void lessFails() {
       this.fails--;
    }
     
    
     /**
      * Devolve o array coas letras do xogador
      * @return Array de String
      */
    public String[] getLetters() {
        return letters;
    }

    /**
     * Método para obter o color asignado ó xogador
     * @return Color do xogador
     */
    public Color getColor() {
        return color;
    }
    
    public boolean hasLetters() {
        return !letters[6].isEmpty();
    }
    
    /**
     * Comprpbar si o xogador ten as letras necesarias para por a palabra
     * @param word Array coas letras que o xogador coloca sobre a mesa
     * @return true - Ten as letras necesarias | false - Non ten as letras necesarias
     */
    public boolean hasWord(String[] word) {
        boolean correct = true;
        
        Arrays.sort(word);
        
        for (int i = 0, j = 0; (i < word.length) && (j < letters.length); j++) {
            if (!letters[j].isEmpty())
                if (word[i].equals(letters[j])) {
                     i++;
                     correct = true;
                } else
                    correct = false;
        }
        
        return correct;
    }
    
    /**
     * Quitar as letras que usa o xogador da súa man
     * @param word Letras de palabra que se colocan no taboleiro
     */
    public void pickWord(String[] word) {
        for (int i = 0, j = 0; (i < word.length) && (j < letters.length); j++) {
            if (word[i].equals(letters[j])) {
                letters[j] = tp.pickLetter();
                i++;
            }
        }
        
        Arrays.sort(letters);
    }

    /**
     * Sobreescritura de toString.
     * Método para obter unha representación en string do obxecto.
     * 
     * @return Representación en string do obxecto
     */
    @Override
    public String toString() {
        return "O xogador " + NAME + " obtivo " + points + " puntos e tivo " + fails + " erros.";
    }

    /**
     * Sobreescritura de compareTo.
     * Método para comparar este obxecto con outro do mesmo tipo.
     * 
     * @param p Xogador co que é comparado
     * @return Menor que 0 se o outro xogador ten menos puntos ca éste, 0 se son iguais e maior que 0 noutro caso
     */
    @Override
    public int compareTo(Player p) {
        return p.getPoints() - points;
    }
}
