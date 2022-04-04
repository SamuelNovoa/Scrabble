/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libs.tools;

import java.util.Arrays;
import java.util.Random;

/**
 * Clase con métodos destinados a tratar matrices
 * 
 * @author Samuel Novoa Comesaña
 */
public class Matrix {
    
    /**
     * Método para trasponer matrices.
     * 
     * @param matrix La matriz a trasponer
     * @return La matriz traspuesta
     */
    public static Object[][] transpose(Object[][] matrix) {
        Object[][] transMatrix = new Object[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                transMatrix[j][i] = matrix[i][j];
        
        return transMatrix;
    }
    
    /**
     * Método para barajar un vector.
     * 
     * @param vector Vector a barajar
     * @param max Valor máximo ata o que intercambia as posicións
     * @return 
     */
    public static Object[] shuffle(Object[] vector, int max) {
        for (int i = 0; i < max ; i++) {
            int rnd = new Random().nextInt(vector.length - i) + i;
            
            Object aux = vector[i];
            vector[i] = vector[rnd];
            vector[rnd] = aux;
        }
        
        return Arrays.copyOf(vector, max);
    }
    
    /**
     * Método para barajar un vector.
     * 
     * @param vector Vector a barajar
     */
    public static void shuffle(Object[] vector) {
        shuffle(vector, vector.length);
    }
}
