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
    PRIZE_LETTER_2("✶", 5),
    PRIZE_LETTER_3("✷", 4),
    PRIZE_LETTER_4("✸", 3),
    PRIZE_WORD_2("✹", 2);

    String icon;
    int count;
    
    PrizeCells(String icon, int count) {
        this.icon = Out.toColor(icon, Out.Color.PURPLE);
        this.count = count;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public int getCount() {
        return count;
    }
}