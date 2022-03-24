/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabble;

/**
 *
 * @author a21samuelnc
 */
public class PrizeCell {
    private PrizeCells type;
    private int x;
    private int y;
    
    public PrizeCell(PrizeCells type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public PrizeCells getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
