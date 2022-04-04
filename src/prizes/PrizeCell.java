/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prizes;

/**
 * Clase destinada a modelar ás casiñas de premio.
 * 
 * @author Iago Oitavén Fraga e Samuel Novoa Comesaña
 */
public class PrizeCell {
    private Prizes type;
    private int x;
    private int y;
    
    /**
     * Constructor da clase
     * 
     * @param type Tipo de premio que terá esa casilla. Recibe un Obxeto Prizes
     * @param x Coordenada X da casillo dentro do taboleiro
     * @param y Coordenada Y da casillo dentro do taboleiro
     */
    public PrizeCell(Prizes type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    /**
     * Tipo de premio que ten asignado esa casilla
     * 
     * @return Tipo do obxecto Prizes
     */
    public Prizes getType() {
        return type;
    }

    /**
     * Coordenada X da casilla premiada
     * 
     * @return int co valor da coor. X
     */
    public int getX() {
        return x;
    }

    /**
     * Coordenada Y da casilla premiada
     * 
     * @return int co valor da coordenada Y
     */
    public int getY() {
        return y;
    }
}
