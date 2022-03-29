/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package words;

import java.util.Arrays;
import prizes.Prizes;

/**
 *
 * @author a21samuelnc
 */
public class Letter {
    private String letter;
    private boolean newLetter;
    private Prizes prize;
    
    private static final Object[][] LETTER_PUNT = new Object[][] {
        { new String[] { "A", "E", "I", "O", "U", "L", "N", "R", "S", "T" }, 1 },
        { new String[] { "D", "G" }, 2 },
        { new String[] { "B", "C", "M", "P" }, 3 },
        { new String[] { "F", "H", "V", "W", "Y" }, 4 },
        { new String[] { "K" }, 5 },
        { new String[] { "J", "X" }, 8 },
        { new String[] { "Q", "Z", "Ñ" }, 10 }
    };
    
    public Letter(char letter) {
        this(Character.toString(letter));
    }
    
    public Letter(String letter) {
        this.letter = letter;
        this.newLetter = false;
        this.prize = null;
    }

    public String getLetter() {
        return letter;
    }
    
    public boolean isNewLetter() {
        return newLetter;
    }

    public void setNewLetter(boolean newLetter) {
        this.newLetter = newLetter;
    }
    
    public Prizes getPrize() {
        return prize;
    }

    public void setPrize(Prizes prize) {
        this.prize = prize;
    }
    
    public int getPoints() {
        int points = 0;
        
        for (Object[] row : LETTER_PUNT)
            if (Arrays.binarySearch((String[])row[0], letter) >= 0)
                points = (int)row[1];
        
        return points;
    }
}
