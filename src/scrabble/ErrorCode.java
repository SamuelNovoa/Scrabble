/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabble;

/**
 *  Tipo enumerado que define os posibles erros do programa.
 * 
 * @author Iago Oitavén Fraga e Samuel Novoa Comesaña
 */
public enum ErrorCode {
    ERR_INVALID_SYNTAX("¡Error! Tienes que introducir una línea con el formato \"PALABRA POS_X[INT] POS_Y[INT] DIRECCION[H|V]\""),
    ERR_INVALID_POS("Posición no válida."),
    ERR_NOT_SPACE("No queda suficiente espacio."),
    ERR_NOT_MATCH("No hay ninguna letra que coincida."),
    ERR_INVALID_LETTER("No tienes las letras necesarias."),
    ERR_TOO_MUCH_JOKER("Sólo puedes usar un comodín cada turno."),
    OK("");
    
    private String error;
    
    /**
     * Constructor do tipo enumerado
     * 
     * @param error Tipo de error definido
     */
    ErrorCode(String error) {
        this.error = error;
    }
    
    /**
     * Mensaxe asociada ao tipo de error
     * 
     * @return Develve un String coa mensaxe correspondente ao error
     */
    public String getError() {
        return error;
    }
}