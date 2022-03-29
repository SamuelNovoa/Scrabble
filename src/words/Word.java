/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package words;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import scrabble.ErrorCode;
import static scrabble.ErrorCode.*;
import scrabble.TableTop;

/**
 *
 * @author a21samuelnc
 */
public class Word {
    private Letter[] letters;
    private boolean prize;
    private byte[] pos;
    private boolean direction;
    
    public Word(String letters, byte[] pos, boolean direction) {
        this.letters = getWord(letters);
        this.pos = pos;
        this.direction = direction;
    }
    
    public int size() {
        return letters.length;
    }
    
    public Letter get(int index) {
        return letters[index];
    }
    
    public ErrorCode check(TableTop tp) {
        ErrorCode res = OK;
        
        if (pos[0] < 0 || pos[0] >= tp.size())
            res = ERR_INVALID_POS;
        else if (pos[1] < 0 || pos[1] >= tp.size())
            res = ERR_INVALID_POS;
        else if (pos[direction ? 0 : 1] + letters.length >= tp.size())
            res = ERR_NOT_SPACE;
        else {
            byte valid = 0;
        
            int xIndex;
            int yIndex;

            boolean hasNewLetters = false;
            for (int i = 0; i < letters.length && valid != -1; i++) {
                xIndex = direction ? (pos[0] + i) : pos[0];
                yIndex = direction ? pos[1] : (pos[1] + i);

                if (tp.get(xIndex, yIndex).equals("")) {
                    hasNewLetters = true;
                    letters[i].setNewLetter(true);
                    if (xIndex == 11 && yIndex == 11)
                        valid = 1;
                } else {
                    if (tp.get(xIndex, yIndex).equals(letters[i].getLetter())) {
                        valid = 1;
                    } else
                        valid = -1;
                }
            }
            
            if (valid == -1 || valid == 0 || !hasNewLetters)
                res = ERR_INVALID_POS;
        }
        
        if (!tp.getActPlayer().hasWord(clear()))
            res = ERR_INVALID_LETTER;
        
        return res;
    }
    
    public void store(TableTop tp) {
        int xIndex;
        int yIndex;
        
        for (int i = 0; i < letters.length; i++) {
            xIndex = direction ? (pos[0] + i) : pos[0];
            yIndex = direction ? (pos[1]) : pos[1] + i;
            
            if (tp.get(xIndex, yIndex).equals(""))
                tp.set(xIndex, yIndex, letters[i].getLetter());
        }
        
        tp.getActPlayer().pickWord(clear());
    }
    
    public int getPoints() {
        return 0;
    }
    
    private String[] clear() {
        List<String> clearLetters = new ArrayList<>();
        
        for (Letter letter : letters)
            if (letter.isNewLetter())
                clearLetters.add(letter.getLetter());
            
        String[] arr = clearLetters.toArray(new String[clearLetters.size()]);
        Arrays.sort(arr);
        
        return arr;
    }
    
    private Letter[] getWord(String word) {
        List<Letter> arr = new ArrayList<>();
        
        for (int i = 0; i < word.length(); i++) {
            if (i < word.length() - 1 && checkLetter(word.charAt(i), word.charAt(i + 1))) {
                String aux = Character.toString(word.charAt(i)) + Character.toString(word.charAt(i+1));
                arr.add(new Letter(aux));
                i++;
            } else {
                arr.add(new Letter(word.charAt(i)));
            }
        }

        return arr.toArray(new Letter[arr.size()]);
    }
    
    private boolean checkLetter(char firstLetter, char secondLetter) {
        boolean res = false;
        
        if ((firstLetter == 'C' && secondLetter == 'H')
        || (firstLetter == 'L' && secondLetter == 'L')
        || (firstLetter == 'R' && secondLetter == 'R'))
            res = true;
        
        return res;
    }
}
