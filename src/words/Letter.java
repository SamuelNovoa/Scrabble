/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package words;

import java.util.Arrays;
import prizes.Prizes;

/**
 * Clase destinada a modelar as letras que compóñen as palabras.
 * 
 * @author Iago Oitavén Fraga e Samuel Novoa Comesaña
 */
public class Letter {
    private String letter;
    private boolean newLetter;
    private Prizes prize;
    
    private static final Object[][] LETTER_PUNT = new Object[][] {
        { new String[] { "A", "E", "I", "L", "N", "O", "R", "S", "T", "U" }, 1 },
        { new String[] { "D", "G" }, 2 },
        { new String[] { "B", "C", "M", "P" }, 3 },
        { new String[] { "F", "H", "V", "Y" }, 4 },
        { new String[] { "CH" }, 5 },
        { new String[] { "J", "LL", "RR", "X" }, 8 },
        { new String[] { "Q", "Z", "Ñ" }, 10 }
    };
    
    /**
     * Constructor da clase Letter. 
     * 
     * @param letter Recibe un char e o pasa a String para crear o obxecto.
     */
    public Letter(char letter) {
        this(Character.toString(letter));
    }
    
    /**
     * Contructor da clase Letter.
     * 
     * @param letter String co caracterer para formar a letra
     */
    public Letter(String letter) {
        this.letter = letter;
        this.newLetter = false;
        this.prize = null;
    }

    /**
     * Obter a letra
     * 
     * @return String 
     */
    public String getLetter() {
        return letter;
    }
    
    /**
     * Verificar se a letra introducida está nas letras dispoñibles polo xogador
     * 
     * @return true - Si a letra introducida polo usuario non está na súa pool de letras | false - Se o xogador xa ten a letra
     */
    public boolean isNewLetter() {
        return newLetter;
    }

    /**
     * Modificar o atributo
     * 
     * @param newLetter true - false
     */
    public void setNewLetter(boolean newLetter) {
        this.newLetter = newLetter;
    }
    
    /**
     * Devolve o premio dunha casilla premiada
     * 
     * @return Obxeto Prizes co caracter asignado ao premio e o seu valor
     */
    public Prizes getPrize() {
        return prize;
    }

    /**
     * Introducir o valor dos premios
     * 
     * @param prize Obxeto Prizes co caracter asignado ao premio e o seu valor
     */
    public void setPrize(Prizes prize) {
        this.prize = prize;
    }
    
    /**
     * Obter os puntos correspondentes a cada casilla, as premiadas e as que non o están
     * 
     * @return int cos puntos de cada letra
     */
    public int getPoints() {
        int points = 0;
        
        for (Object[] row : LETTER_PUNT)
            if (Arrays.binarySearch((String[])row[0], letter) >= 0)
                points = (int)row[1];
        
        if (prize != null)
            switch(prize) {
                case PRIZE_LETTER_2:
                    points *= 2;
                    break;
                case PRIZE_LETTER_3:
                    points *= 3;
                    break;
                case PRIZE_LETTER_4:
                    points *= 4;
                    break;
                    default:
                        break;
            }
        
        return points;
    }
}
