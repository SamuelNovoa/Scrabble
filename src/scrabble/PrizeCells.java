/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabble;

import io.Out;

/**
 *
 * @author a21samuelnc
 */
public enum PrizeCells {
    PRIZE_LETTER_2('\u2726', 5),
    PRIZE_LETTER_3('\u2736', 4),
    PRIZE_LETTER_4('\u2737', 3),
    PRIZE_WORD_2('\u2739', 2);

    private String icon;
    private int count;
    
    PrizeCells(char icon, int count) {
        this.icon = Out.toColor(Character.toString(icon), Out.Color.PURPLE);
        this.count = count;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public int getCount() {
        return count;
    }
}