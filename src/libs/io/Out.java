/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libs.io;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.PrintStream;

/**
 *
 * @author a21samuelnc
 */
public class Out {
    private static PrintStream ps = System.out;
    
    public enum Color {
        RESET("\u001B[0m"),
        RED("\u001B[31m"),
        GREEN("\u001B[32m"),
        YELLOW("\u001B[33m"),
        BLUE("\u001B[34m"),
        PURPLE("\u001B[35m"),
        CYAN("\u001B[36m"),
        WHITE("\u001B[37m");
        
        private String ansiCode;
        
        Color(String ansiCode) {
            this.ansiCode = ansiCode;
        }
        
        public String getAnsiCode() {
            return ansiCode;
        }
    }
    
    public static PrintStream getPrintStream() {
        return ps;
    }

    public static void setPrintStream(PrintStream out) {
        Out.ps = out;
    }
    
    public static void clear() {
        try{
            
            if (System.console() != null) {
                final String os = System.getProperty("os.name");
                Runtime.getRuntime().exec(os.contains("Windows") ? "cls" : "clear");
            } else {
                Robot robot = new Robot();
                robot.setAutoDelay(100);
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_L);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                robot.keyRelease(KeyEvent.VK_L);
            }
        }
        catch (Exception e){
        }
        
        ps.flush();
    }
    
    public static void msg(String str) {
        msg(str, true);
    }
    
    public static void msg(String str, boolean lineBreak) {
        ps.print(str);
        if (lineBreak)
            ps.print("\n");
    }
    
    public static void err(String str) {
        err(str, true);
    }
    
    public static void err(String str, boolean lineBreak) {
        ps.print(toErr(str));
        if (lineBreak)
            ps.print("\n");
    }
    
    public static void inf(String str) {
        inf(str, true);
    }
    
    public static void inf(String str, boolean lineBreak) {
        ps.print(toInf(str));
        if (lineBreak)
            ps.print("\n");
    }
    
    public static void ok(String str) {
        ok(str, true);
    }
    
    public static void ok(String str, boolean lineBreak) {
        ps.print(toOk(str));
        if (lineBreak)
            ps.print("\n");
    }
    
    public static String toErr(String str) {
        return toColor(str, Color.RED);
    }
    
    public static String toInf(String str) {
        return toColor(str, Color.YELLOW);
    }
    
    public static String toOk(String str) {
        return toColor(str, Color.GREEN);
    }
    
    public static String toColor(String str, Color color) {
        return color == null ? str : color.getAnsiCode() + str + Color.RESET.getAnsiCode();
    }
    
    public static void printArr(Object[] arr) {
        printArr(arr, null);
    }
    
    public static void printArr(Object[] arr, Color color) {
        int maxWidth = 0;
        for (Object obj : arr)
            if (obj.toString().length() > maxWidth)
                maxWidth = obj.toString().length() + 2;
        
        ps.print("╔");
        for (int i = 0; i < maxWidth; i++)
            ps.print("═");
        ps.println("╗");
        
        for (int i = 0; i < arr.length; i++) {
            if (i != 0) {
                ps.print("╠");
                for (int j = 0; j < maxWidth; j++)
                    ps.print("═");
                ps.println("╣");
            }
            ps.print("║");
            int diffWidth = maxWidth - arr[i].toString().length();
            for (int j = 0; j < Math.floor(diffWidth / 2.0); j++)
                ps.print(" ");
            
            ps.print(color == null ? arr[i].toString() : toColor(arr[i].toString(), color));
            for (int j = 0; j < Math.ceil(diffWidth / 2.0); j++)
                ps.print(" ");
            
            ps.println("║");
        }
        
        ps.print("╚");
        for (int i = 0; i < maxWidth; i++)
            ps.print("═");
        ps.println("╝");
    }
    
    public static void printArr(Object[][] arr) {
        printArr(arr, null);
    }
    
    public static void printArr(Object[][] arr, Color color) {
        int maxWidth = 0;
        for (Object[] row : arr)
            for (Object obj : row)
                if (obj.toString().length() > maxWidth)
                    maxWidth = obj.toString().length() + 2;
        
        printArr(arr, color, maxWidth);
    }
    
    public static void printArr(Object[][] arr, int maxWidth) {
        printArr(arr, null, maxWidth);
    }
    
    public static void printArr(Object[][] arr, Color color, int maxWidth) {
        String topLine = "╔";
        String midLine = "╠";
        String botLine = "╚";
        
        String table = "";
        
        for (int i = 0; i < arr[0].length; i++) {
            for (int j = 0; j < maxWidth; j++) {
                topLine += "═";
                midLine += "═";
                botLine += "═";
            }
            
            if (i == arr[0].length - 1) {
                topLine += "╗\n";
                midLine += "╣\n";
                botLine += "╝\n";
            } else {
                topLine += "╦";
                midLine += "╬";
                botLine += "╩";
            }
        }
        
        topLine = Out.toColor(topLine, color);
        midLine = Out.toColor(midLine, color);
        botLine = Out.toColor(botLine, color);
        
        for (int i = 0; i < arr.length; i++) {
            if (i == 0)
                table += topLine;
            
            String dataLine = Out.toColor("║", color);
            for (int j = 0; j < arr[0].length; j++) {
                int diffWidth = maxWidth - arr[i][j].toString().replaceAll("\u001B\\[[\\d;]*[^\\d;]", "").length();
                for (int k = 0; k < Math.floor(diffWidth / 2.0); k++)
                    dataLine += " ";

                dataLine += arr[i][j].toString();

                for (int k = 0; k < Math.ceil(diffWidth / 2.0); k++)
                    dataLine += " ";
                
                dataLine += Out.toColor("║", color);
            }
            
            table += dataLine + "\n";
            
            if (i == arr.length - 1)
                table += botLine;
            else
                table += midLine;
        }
        
        try {
            System.setOut(new PrintStream(System.out, true, "UTF8"));
        } catch (Exception e) {
        }
        
        Out.msg(table);
    }
}
