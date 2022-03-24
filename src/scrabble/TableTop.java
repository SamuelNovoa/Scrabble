package scrabble;

import io.In;
import io.Out;
import io.Out.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import static scrabble.PrizeCells.*;
import tools.Matrix;

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
    
    private enum ErrorCode {
        ERR_INVALID_POS,
        ERR_NOT_SPACE,
        ERR_NOT_MATCH,
        ERR_INVALID_LETTER,
        OK
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
    
    public void print() {
        Out.clear();
        Out.msg(logo);
        
        Out.printArr(tableTop, Color.YELLOW, 3);
        Out.msg("\n");
    }
    
    public void newGame() {
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
        
        Object[] asd = new Object[3];
        asd[0] = new Object[]{PrizeCells.PRIZE_LETTER_2, 1, 2};
        
        Random rnd = new Random();
        Out.Color color = validColors[rnd.nextInt(validColors.length)];
        
        logo = Out.toColor("▄▀▀ ▄▀▀ █▀▄ ▄▀▄ █▀▄ █▀▄ █░░ █▀▀\n", color)
            + Out.toColor("░▀▄ █░░ █▀▄ █▄█ █▀▄ █▀▄ █░░ █▀▀\n", color)
            + Out.toColor("▀▀░ ░▀▀ ▀░▀ ▀░▀ ▀▀░ ▀▀░ ▀▀▀ ▀▀▀\n\n", color);
        
        Out.clear();
        Out.msg(logo);
        
        for (int i = 0; i < playersNum; i++) {
            Out.msg("Introduce el nombre del jugador nº " + (i + 1) + ": ", false);
            players[i] = new Player(In.getString());
        }
        
        Out.msg("\n");
        playerActive = rnd.nextInt(playersNum);
        gameActive = true;
        
        while (gameActive)
            nextRound();
    }
    
    public static String pickLetter() {
        return lettersPool.pop();
    }
    
    public static String[] pickLetter(int num) {
        String[] res = new String[num];
        for (int i = 0; i < num; i++)
            res[i] = lettersPool.pop();
        
        return res;
    }
    
    /**
     * Coloca a palabra no taboleiro
     * @param word Palabra separada en letras para colocar no taboleiro
     * @param pos Coordenada da primeira letra da palabra
     * @param direction true - Dirección da palabra en horizontal, false - Dirección da palabra en vertical
     * @return true - Si se colocou a palabra, false se non
     */
    public boolean setWord(String word, byte[] pos, boolean direction) {
        boolean res = false;
        
        String[] wordLetters = getWord(word);
        
        switch (checkWord(wordLetters, pos, direction)) {
            case ERR_INVALID_POS:
                Out.err("Posición no válida.");
                break;
            case ERR_NOT_SPACE:
                Out.err("No queda suficiente espacio.");
                break;
            case ERR_NOT_MATCH:
                Out.err("No hay ninguna letra que coincida.");
                break;
            case ERR_INVALID_LETTER:
                Out.err("No tienes las letras necesarias.");
                break;
            case OK:
                players[playerActive].plusPoints(storeWord(wordLetters, pos, direction));
                res = true;
                break;
            default:
                break;
        }
        
        return res;
    }
    
    private void nextRound() {
        print();
        
        Out.msg(validLetters.length + " : " + lettersPool.size());
        Out.msg("¡Turno de " + players[playerActive].getName() + "!\n");
        Out.printArr(new String[][] { players[playerActive].getLetters() }, validColors[playerActive]);
        Out.msg("\n\n > ", false);
        
        boolean res = false;
        while (!res) {
            String word = In.getString().toUpperCase();
            
            String[] tokens = word.split(" ");
            if (tokens.length != 4 || (!tokens[3].toUpperCase().equals("H") && !tokens[3].toUpperCase().equals("V"))) {
                Out.err("¡Error! Tienes que introducir una línea con el formato \"PALABRA POS_X[INT] POS_Y[INT] DIRECCION[H|V]\"");
            } else {
                word = tokens[0];
                byte[] pos = { Byte.parseByte(tokens[2]), Byte.parseByte(tokens[1]) };
                boolean dir = !tokens[3].toUpperCase().equals("H");

                if (setWord(word, pos, dir))
                    res = true;
            }
        }
        
        playerActive++;
                
        if (playerActive >= playersNum)
            playerActive = 0;
    }
    
    private static String[] getWord(String word) {
        ArrayList<String> arr = new ArrayList<>();
        
        for (int i = 0 ; i < word.length(); i++) {
            if (i < word.length() - 1 && checkLetter(word.charAt(i), word.charAt(i + 1))) {
                String aux = Character.toString(word.charAt(i)) + Character.toString(word.charAt(i+1));
                arr.add(aux);
                i++;
            } else {
                arr.add(Character.toString(word.charAt(i)));
            }
        }
        
        String[] letters = new String[arr.size()];
        for (int j = 0 ; j < arr.size() ; j++) {
            letters[j] = arr.get(j);
        }

        return letters;
    }
    
    private static boolean checkLetter(char firstLetter, char secondLetter) {
        boolean res = false;
        
        if ((firstLetter == 'C' && secondLetter == 'H')
        || (firstLetter == 'L' && secondLetter == 'L')
        || (firstLetter == 'R' && secondLetter == 'R'))
            res = true;
        
        return res;
    }
    
    private ErrorCode checkWord(String[] word, byte[] pos, boolean direction) {
        ErrorCode res = ErrorCode.OK;
        List<String> clearWord = new ArrayList<>();
        
        if (pos[0] < 0 || pos[0] >= tableTop.length)
            res = ErrorCode.ERR_INVALID_POS;
        else if (pos[1] < 0 || pos[1] >= tableTop.length)
            res = ErrorCode.ERR_INVALID_POS;
        else if (pos[direction ? 0 : 1] + word.length >= tableTop.length)
            res = ErrorCode.ERR_NOT_SPACE;
        else {
            byte valid = 0;
        
            int xIndex;
            int yIndex;

            boolean hasNewLetters = false;
            for (int i = 0; i < word.length && valid != -1; i++) {
                xIndex = direction ? (pos[0] + i) : pos[0];
                yIndex = direction ? pos[1] : (pos[1] + i);

                if (tableTop[xIndex][yIndex].equals("")) {
                    hasNewLetters = true;
                    clearWord.add(word[i]);
                    if (xIndex == 11 && yIndex == 11)
                        valid = 1;
                } else {
                    if (tableTop[xIndex][yIndex].equals(word[i])) {
                        valid = 1;
                    } else
                        valid = -1;
                }
            }
            
            if (valid == -1 || valid == 0 || !hasNewLetters)
                res = ErrorCode.ERR_INVALID_POS;
        }
        
        if (!players[playerActive].hasWord(clearWord.toArray(new String[clearWord.size()])))
            res = ErrorCode.ERR_INVALID_LETTER;
        
        return res;
    }
    
    private int storeWord(String[] word, byte[] pos, boolean direction) {
        int points = 0;
        
        int xIndex;
        int yIndex;
        
        List<String> clearWord = new ArrayList<>();
        for (int i = 0; i < word.length; i++) {
            xIndex = direction ? (pos[0] + i) : pos[0];
            yIndex = direction ? (pos[1]) : pos[1] + i;
            
            if (tableTop[xIndex][yIndex].equals("")) {
                tableTop[xIndex][yIndex] = word[i];
                clearWord.add(word[i]);
            }
        }
        
        players[playerActive].pickWord(clearWord.toArray(new String[clearWord.size()]));
        
        return points;
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
        
        int i = 0;
        for (; i < PRIZE_WORD_2.getCount(); i++)
            prizeCell[i] = new PrizeCell(PRIZE_WORD_2, ((int[]) arr[i])[0], ((int[]) arr[i])[1]);
        
        for (; i < PRIZE_LETTER_4.getCount(); i++)
            prizeCell[i] = new PrizeCell(PRIZE_LETTER_4, ((int[]) arr[i])[0], ((int[]) arr[i])[1]);
        
        for (; i < PRIZE_LETTER_3.getCount(); i++)
            prizeCell[i] = new PrizeCell(PRIZE_LETTER_3, ((int[]) arr[i])[0], ((int[]) arr[i])[1]);
        
        for (; i < PRIZE_LETTER_2.getCount(); i++)
            prizeCell[i] = new PrizeCell(PRIZE_LETTER_2, ((int[]) arr[i])[0], ((int[]) arr[i])[1]);
        
        
        return prizeCell;
    }
}