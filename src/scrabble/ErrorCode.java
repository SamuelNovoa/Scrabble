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
public enum ErrorCode {
    ERR_INVALID_POS("Posición no válida."),
    ERR_NOT_SPACE("No queda suficiente espacio."),
    ERR_NOT_MATCH("No hay ninguna letra que coincida."),
    ERR_INVALID_LETTER("No tienes las letras necesarias."),
    OK("");
    
    private String error;
    
    ErrorCode(String error) {
        this.error = error;
    }
    
    public String getError() {
        return error;
    }
}