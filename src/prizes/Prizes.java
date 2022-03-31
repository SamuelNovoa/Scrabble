/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prizes;

import io.Out;

/**
 *
 * @author a21samuelnc
 */
public enum Prizes {
    PRIZE_LETTER_2("\u2191", 5),
    PRIZE_LETTER_3("\u21C8", 4),
    PRIZE_LETTER_4("\u2191\u2191\u2191", 3),
    PRIZE_WORD_2("\u25B2", 2);

    private String icon;
    private int count;
    
    Prizes(String icon, int count) {
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