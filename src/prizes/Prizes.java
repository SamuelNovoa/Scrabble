/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prizes;

import libs.io.Out;

/**
 * Tipo enumerado que define os posibles premios.   
 * 
 * @author Iago Oitavén Fraga e Samuel Novoa Comesaña
 */
public enum Prizes {
    PRIZE_LETTER_2("\u2191", 5),
    PRIZE_LETTER_3("\u21C8", 4),
    PRIZE_LETTER_4("\u2191\u2191\u2191", 3),
    PRIZE_WORD_2("\u25B2", 2);

    private String icon;
    private int count;
    
    /**
     * Constructor do tipo enumerado
     * 
     * @param icon Icono que se vai mostrar na casilla de premio
     * @param count Cantidade de casillas que se vai usar dese tipo
     */
    Prizes(String icon, int count) {
        this.icon = Out.toColor(icon, Out.Color.PURPLE);
        this.count = count;
    }
    
    /**
     * Devolve o icono usado para a casilla de premio
     * 
     * @return Icono asignado
     */
    public String getIcon() {
        return icon;
    }
    
    /**
     * Devolve a cantidade de ese tipo de obxetos a crear
     * 
     * @return 
     */
    public int getCount() {
        return count;
    }
}