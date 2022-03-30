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
        { new String[] { "A", "E", "I", "L", "N", "O", "R", "S", "T", "U" }, 1 },
        { new String[] { "D", "G" }, 2 },
        { new String[] { "B", "C", "M", "P" }, 3 },
        { new String[] { "F", "H", "V", "Y" }, 4 },
        { new String[] { "CH" }, 5 },
        { new String[] { "J", "LL", "RR", "X" }, 8 },
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
