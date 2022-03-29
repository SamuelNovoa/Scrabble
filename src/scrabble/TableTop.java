package scrabble;

import prizes.PrizeCell;
import io.In;
import io.Out;
import io.Out.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import static prizes.Prizes.*;
import static scrabble.ErrorCode.*;
import tools.Matrix;
import words.Letter;
import words.Word;

/**
 *
 * @author a21iagoof
 */
public class TableTop {
    private enum Commands {
        CMD_PASS("PASAR"),
        CMD_EXIT("SALIR");
        
        private final String CMD;
        private Commands(String cmd) {
            this.CMD = cmd;
        }
    }
    
    private Out.Color[] validColors = {
        Out.Color.PURPLE,
        Out.Color.RED,
        Out.Color.GREEN,
        Out.Color.YELLOW,
        Out.Color.CYAN,
    };
    
    private static Stack<String> lettersPool;
    private static String[] validLetters = {"A","A","A","A","A","A","A","A","A","A","A","A","A","A","B","B","B","C","C","C","C","C","CH","D","D","D","D","D","D",
        "E","E","E","E","E","E","E","E","E","E","E","E","E","F","F","G","G","H","H","I","I","I","I","I","I","I","J","L","L","L","L","L","L","LL","M","M","M","M","N","N",
        "N","N","N","N","Ñ","O","O","O","O","O","O","O","O","O","O","P","P","P","Q","R","R","R","R","R","R","RR","S","S","S","S","S","S","S","T","T","T","T",
        "T","U","U","U","U","U","U","U","V","V","X","Y","Z","*", "*"};
    
    private String[][] tableTop;
    private Player[] players;
    
    private int playerActive;
    private int playersNum;
    private boolean gameActive;
    private String logo;
    
    public static String pickLetter() {
        return lettersPool.pop();
    }
    
    public static String[] pickLetter(int num) {
        String[] res = new String[num];
        for (int i = 0; i < num; i++)
            res[i] = lettersPool.pop();
        
        return res;
    }
    
    public TableTop() {
        this.playersNum = 2;
        this.gameActive = false;
        
        Random rnd = new Random();
        Out.Color color = validColors[rnd.nextInt(validColors.length)];
        
        logo = Out.toColor("▄▀▀ ▄▀▀ █▀▄ ▄▀▄ █▀▄ █▀▄ █░░ █▀▀\n", color)
             + Out.toColor("░▀▄ █░░ █▀▄ █▄█ █▀▄ █▀▄ █░░ █▀▀\n", color)
             + Out.toColor("▀▀░ ░▀▀ ▀░▀ ▀░▀ ▀▀░ ▀▀░ ▀▀▀ ▀▀▀\n\n", color);
    }
    
    public String getLogo() {
        return logo;
    }
    
    public void setPlayers(int playersNum) {
        this.playersNum = playersNum;
    }
    
    public int size() {
        return tableTop.length;
    }
    
    public String get(int row, int column) {
        return tableTop[row][column];
    }
    
    public void set(int row, int column, String data) {
        tableTop[row][column] = data;
    }
    
    public Player getActPlayer() {
        return players[playerActive];
    }
    
    public void print() {
        Out.clear();
        Out.msg(logo);
        
        Out.printArr(tableTop, Color.YELLOW, 3);
        Out.msg("\n");
    }
    
    public void newGame() {
        init();
        Out.clear();
        Out.msg(logo);
        
        for (int i = 0; i < playersNum; i++) {
            Out.msg("Introduce el nombre del jugador nº " + (i + 1) + ": ", false);
            players[i] = new Player(In.getString());
        }
        
        Out.msg("\n");
        gameActive = true;
        
        while (gameActive)
            nextRound();
    }
    
    private void init() {
        players = new Player[playersNum];
        lettersPool = new Stack<>();
        Matrix.shuffle(validLetters);
        
        for (String letter : validLetters)
            lettersPool.push(letter);
        
        tableTop = new String[23][23];
        for (int i = 0; i < 23; i++) {
            for (int j = 0; j < 23; j++) {
                
                if ((i == 0 && j == 0) || (i == 22 && j == 22) || (i == 0 && j == 22) || (i == 22 && j == 0))
                    tableTop[i][j] = Out.toColor("\u2593", Color.RED);
                else if (i == 0 || i == 22)
                    tableTop[i][j] = Out.toColor(Integer.toString(j), Color.RED);
                else if (j == 0 || j == 22)
                    tableTop[i][j] = Out.toColor(Integer.toString(i), Color.RED);
                else
                    tableTop[i][j] = "";
            }
        }
        
        for (PrizeCell cell : getPrizeCells())
            tableTop[cell.getX()][cell.getY()] = cell.getType().getIcon();

        Random rnd = new Random();
        Out.Color color = validColors[rnd.nextInt(validColors.length)];
        
        logo = Out.toColor("▄▀▀ ▄▀▀ █▀▄ ▄▀▄ █▀▄ █▀▄ █░░ █▀▀\n", color)
            + Out.toColor("░▀▄ █░░ █▀▄ █▄█ █▀▄ █▀▄ █░░ █▀▀\n", color)
            + Out.toColor("▀▀░ ░▀▀ ▀░▀ ▀░▀ ▀▀░ ▀▀░ ▀▀▀ ▀▀▀\n\n", color);
        
        playerActive = rnd.nextInt(playersNum);
    }
    
    private void nextRound() {
        print();
        
        Out.msg(validLetters.length + " : " + lettersPool.size());
        Out.msg("¡Turno de " + players[playerActive].getName() + "!\n");
        Out.printArr(new String[][] { players[playerActive].getLetters() }, validColors[playerActive]);
        Out.msg("\n\n > ", false);
        
        boolean res = false;
        while (!res) {
            String letters = In.getString().toUpperCase();
            
            String[] tokens = letters.split(" ");
            if (tokens.length != 4 || (!tokens[3].toUpperCase().equals("H") && !tokens[3].toUpperCase().equals("V"))) {
                Out.err("¡Error! Tienes que introducir una línea con el formato \"PALABRA POS_X[INT] POS_Y[INT] DIRECCION[H|V]\"");
            } else {
                byte[] pos = { Byte.parseByte(tokens[2]), Byte.parseByte(tokens[1]) };
                boolean dir = !tokens[3].toUpperCase().equals("H");
                
                Word word = new Word(tokens[0], pos, dir);

                if (setWord(word))
                    res = true;
            }
        }
        
        playerActive++;
                
        if (playerActive >= playersNum)
            playerActive = 0;
    }
    
        /**
     * Coloca a palabra no taboleiro
     * @param word Palabra separada en letras para colocar no taboleiro
     * @return true - Si se colocou a palabra, false se non
     */
    private boolean setWord(Word word) {
        boolean res = false;
        ErrorCode code = word.check(this);
        
        if (code == OK) {
            players[playerActive].plusPoints(word.getPoints());
            word.store(this);
            res = true;
        } else
            Out.err(code.getError());
            
        
        return res;
    }
    
    /**
     * Xenerar un array con todas as poscións premiadas
     * 
     * @return Array de PrizelCells coas casiilas de premio
     */
    private PrizeCell[] getPrizeCells() {
        Object[] arr = new Object[(tableTop.length-2)*(tableTop.length-2)];
        for (int i = 0; i < tableTop.length-2; i++) 
            for (int j = 0; j < tableTop[i].length-2; j++) 
                arr[i*(tableTop[i].length-2)+j] = new int[] {i+1, j+1};
        
        arr = Matrix.shuffle(arr, 14);
        
        PrizeCell[] prizeCell = new PrizeCell[14];
        
        int j = 0;
        for (int i = j; i < PRIZE_WORD_2.getCount(); i++)
            prizeCell[i] = new PrizeCell(PRIZE_WORD_2, ((int[]) arr[i])[0], ((int[]) arr[i])[1]);
        
        j += PRIZE_WORD_2.getCount();
        for (int i = j; i < (j + PRIZE_LETTER_4.getCount()); i++)
            prizeCell[i] = new PrizeCell(PRIZE_LETTER_4, ((int[]) arr[i])[0], ((int[]) arr[i])[1]);
        
        j += PRIZE_LETTER_4.getCount();
        for (int i = j; i < (j + PRIZE_LETTER_3.getCount()); i++)
            prizeCell[i] = new PrizeCell(PRIZE_LETTER_3, ((int[]) arr[i])[0], ((int[]) arr[i])[1]);
        
        j += PRIZE_LETTER_3.getCount();
        for (int i = j; i < (j + PRIZE_LETTER_2.getCount()); i++)
            prizeCell[i] = new PrizeCell(PRIZE_LETTER_2, ((int[]) arr[i])[0], ((int[]) arr[i])[1]);

        return prizeCell;
    }
}