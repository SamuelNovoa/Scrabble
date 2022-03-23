/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author a21samuelnc
 */
public class In {
    private static Scanner sc = new Scanner(System.in, "utf8");
    
    public static long getLong(int min, int max) {
        while (!sc.hasNextLong()) {
            Out.err("Dato incorrecto, introduce un long: ", false);
            if (sc.hasNextLine())
                sc.nextLine();
        }
        
        return sc.nextLong();
    }
    
    public static int getInt() {
        while (!sc.hasNextInt()) {
            Out.err("Dato incorrecto, introduce un int: ", false);
            if (sc.hasNextLine())
                sc.nextLine();
        }
        
        return sc.nextInt();
    }
    
    public static short getShort() {
        while (!sc.hasNextShort()) {
            Out.err("Dato incorrecto, introduce un short: ", false);
            if (sc.hasNextLine())
                sc.nextLine();
        }
        
        return sc.nextShort();
    }
    
    public static byte getByte() {
        while (!sc.hasNextByte()) {
            Out.err("Dato incorrecto, introduce un byte: ", false);
            if (sc.hasNextLine())
                sc.nextLine();
        }
        
        return sc.nextByte();
    }
    
    public static float getFloat() {
        while (!sc.hasNextFloat()) {
            Out.err("Dato incorrecto, introduce un float: ", false);
            if (sc.hasNextLine())
                sc.nextLine();
        }
        
        return sc.nextFloat();
    }
    
    public static double getDouble() {
        while (!sc.hasNextDouble()) {
            Out.err("Dato incorrecto, introduce un double: ", false);
            if (sc.hasNextLine())
                sc.nextLine();
        }
        
        return sc.nextDouble();
    }
    
    public static boolean getBoolean() {
        while (!sc.hasNextBoolean()) {
            Out.err("Dato incorrecto, introduce un booleano: ", false);
            if (sc.hasNextLine())
                sc.nextLine();
        }
        
        return sc.nextBoolean();
    }
    
    public static String getString() {
        String line = "";
        do {
            line = sc.nextLine();
        } while(line.isEmpty());
        
        return line;
    }
    
    public static char getChar() {
        String line = "";
        do {
            while (!sc.hasNextLine());

            line = sc.nextLine();
            
            if (line.length() != 1)
                Out.err("Dato incorrecto, introduce un carácter: ", false);
        } while (line.length() != 1);

        return line.charAt(0);
    }
    
    public static boolean getConfirmation() {
        String line;
        String[] validOpts = new String[] {"s", "si", "y", "yes", "n", "no"};
        
        do {
            line = getString().toLowerCase();
            
            if (Arrays.binarySearch(validOpts, line) < 0)
                Out.err("Respuesta incorrecta, debes decir si o no: ", false);
        } while (Arrays.binarySearch(validOpts, line) < 0);
        
        String[] okOpts = new String[] {"s", "si", "y", "yes"};
        
        if (Arrays.binarySearch(okOpts, line) >= 0)
            return true;
        
        return false;
    }
    
    public static byte getOption(int maxValue) {
        return getOption(1, maxValue);
    }
    
    public static byte getOption(int minValue, int maxValue) {
        byte opt = 0;
        
        do {
            opt = getByte();
            
            if (opt < minValue || opt > maxValue)
                Out.err("Erro: Opción non válida, elixe unha no rango [" + minValue + " - " + maxValue + "]: ", false);
        } while (opt < minValue || opt > maxValue);
        
        return opt;
    }
}
