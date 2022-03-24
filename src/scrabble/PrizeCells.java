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
    PRIZE_LETTER_2("✶"),
    PRIZE_LETTER_3("✷"),
    PRIZE_LETTER_4("✸"),
    PRIZE_WORD_2("✹");

    String icon;
    PrizeCells(String icon) {
        this.icon = Out.toColor(icon, Out.Color.PURPLE);
    }
}