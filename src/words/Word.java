/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package words;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import prizes.Prizes;
import scrabble.ErrorCode;
import static scrabble.ErrorCode.*;
import scrabble.TableTop;

/**
 * Clase destinada a modelar as palabras que introduce o usuario.
 * 
 * @author Iago Oitavén Fraga e Samuel Novoa Comesaña
 */
public class Word {
    private Letter[] letters;
    private boolean prize;
    private byte[] pos;
    private boolean direction;
    private TableTop tp;
    
    /**
     * Constructor da clase Words
     * 
     * @param letters String cunha palabra
     * @param pos Byte[] coa posición X e Y do taboleiro
     * @param direction true - Colocación horizontal | false - Colocación vertical
     * @param tp Obxeto TableTop onde se realizan as operacións
     */
    public Word(String letters, byte[] pos, boolean direction, TableTop tp) {
        this.letters = getWord(letters);
        this.pos = pos;
        this.direction = direction;
        this.tp = tp;
    }
    
    /**
     * Lonxitude da palabra
     * 
     * @return Devolve a lonxitude da palabra
     */
    public int size() {
        return letters.length;
    }
    
    /**
     * Devolve a letra da posición indicada
     * 
     * @param index Posición da letra
     * @return Obxeto Letter coa letra da posición indicada
     */
    public Letter get(int index) {
        return letters[index];
    }
    
    /**
     * Xestión dos posibles erros que poden ocorrer ao colocar a palabra no TableTop
     * 
     * @return Obxeto ErrorCode co valor do error detectado
     */
    public ErrorCode check() {
        ErrorCode res = OK;
        
        if (pos[0] < 0 || pos[0] >= tp.size())
            res = ERR_INVALID_POS;
        else if (pos[1] < 0 || pos[1] >= tp.size())
            res = ERR_INVALID_POS;
        else if (pos[direction ? 0 : 1] + letters.length >= tp.size())
            res = ERR_NOT_SPACE;
        else if (!isValid()) 
            res = ERR_INVALID_POS;
        else if (!tp.getActPlayer().hasWord(clear()))
            res = ERR_INVALID_LETTER;
        else if (hasTooMuchJoker())
            res = ERR_TOO_MUCH_JOKER;
        
        return res;
    }
    
    /**
     * Establece a posción de colocación da palabra
     */
    public void store() {
        int xIndex;
        int yIndex;
        
        for (int i = 0; i < letters.length; i++) {
            xIndex = direction ? (pos[0] + i) : pos[0];
            yIndex = direction ? (pos[1]) : pos[1] + i;
            
            tp.set(xIndex, yIndex, letters[i].getLetter());
        }
        
        tp.getActPlayer().pickWord(clear());
    }
    
    /**
     * Calcular os puntos totais da palabra
     * 
     * @return int cos puntos que conseguiu esa palabra
     */
    public int getPoints() {
        int points = clear().length == 7 ? 50 : 0;
        
        for (Letter letter : letters)
            points += letter.getPoints();
        
        if (prize)
            points *= 2;
        
        return points;
    }

    /**
     * Limpiar a palabra introducida, deixando so as letras introducidas polo usuario, excluíndo as que xa están colocadas no taboleiro
     * 
     * @return String[] coas letras das que fai uso o xogador
     */
    private String[] clear() {
        List<String> clearLetters = new ArrayList<>();
        
        for (Letter letter : letters)
            if (letter.isNewLetter())
                clearLetters.add(letter.getLetter());
            
        String[] arr = clearLetters.toArray(new String[clearLetters.size()]);
        Arrays.sort(arr);
        
        return arr;
    }
    
    /**
     * Separa a palabra introducida en obxetos Letters
     * 
     * @param word Palabra que o xogador tenta colocar
     * @return Array de obxetos Letters coas distintas letras da palabra separadas
     */
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
    
    /**
     * Comprobar se algunha das letras introducidas é CH, LL ou RR
     * 
     * @param firstLetter Unha letra da palabra inicial, sen modificar
     * @param secondLetter Unha letra da palabra inicial, sen modificar
     * @return true - Se as letras pasadas forman un dos conxuntos de letras anteriores | false - se non
     */
    private boolean checkLetter(char firstLetter, char secondLetter) {
        boolean res = false;
        
        if ((firstLetter == 'C' && secondLetter == 'H')
        || (firstLetter == 'L' && secondLetter == 'L')
        || (firstLetter == 'R' && secondLetter == 'R'))
            res = true;
        
        return res;
    }
    
    /**
     * Comprobar se na palabra introducida, o xogador fai uso de máis dun comodín
     * 
     * @return true - Se usa máis dun comodín | false - se fai uso de 1 ou ningún
     */
    private boolean hasTooMuchJoker() {
        int jokers = 0;
        
        for (String ch : clear())
            if (ch.equals("*"))
                jokers++;
        
        return jokers > 1;
    }
    
    /**
     * Comprobar para cada letra que se coloque no TableTop, se é posible a súa colocación sen erros
     * 
     * @return true - Se a palabra se pode colocar | false - Se a palabra non se pode colocar
     */
    private boolean isValid() {
        boolean res = true;
        
        byte valid = 0;
        
        int xIndex;
        int yIndex;

        boolean hasNewLetters = false;
        for (int i = 0; i < letters.length && valid != -1; i++) {
            xIndex = direction ? (pos[0] + i) : pos[0];
            yIndex = direction ? pos[1] : (pos[1] + i);

            String ch = tp.get(xIndex, yIndex);
            boolean isWhiteOrPrize = false;

            if (ch.equals(""))
                isWhiteOrPrize = true;
            else {
                for (Prizes prize : Prizes.values()) {
                    if (ch.equals(prize.getIcon())) {
                        if (prize == Prizes.PRIZE_WORD_2)
                            this.prize = true;
                        else 
                            letters[i].setPrize(prize);
                    }
                    
                    isWhiteOrPrize = true;
                }
            }

            if (isWhiteOrPrize) {
                hasNewLetters = true;
                letters[i].setNewLetter(true);
                if (xIndex == 11 && yIndex == 11)
                    valid = 1;
            } else {
                if (ch.equals(letters[i].getLetter())) {
                    valid = 1;
                } else
                    valid = -1;
            }
        }

        if (valid == -1 || valid == 0 || !hasNewLetters)
            res = false;
        
        return res;
    }
}
