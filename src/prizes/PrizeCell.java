/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prizes;

/**
 *
 * @author a21samuelnc
 */
public class PrizeCell {
    private Prizes type;
    private int x;
    private int y;
    
    public PrizeCell(Prizes type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public Prizes getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
