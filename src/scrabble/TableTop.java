package scrabble;

import prizes.PrizeCell;
import io.In;
import io.Out;
import io.Out.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import static prizes.Prizes.*;
import static scrabble.ErrorCode.*;
import tools.Matrix;
import words.Word;

/**
 *
 * @author a21iagoof
 */
public class TableTop {
    private enum CmdResult {
        CMD_NOT_CMD,
        CMD_CONTINUE,
        CMD_NEXT_ROUND
    }
    
    private Out.Color[] validColors = {
        Out.Color.PURPLE,
        Out.Color.RED,
        Out.Color.GREEN,
        Out.Color.YELLOW,
        Out.Color.CYAN,
    };
    
    private Stack<String> lettersPool;
    private String[] validLetters = {"A","A","A","A","A","A","A","A","A","A","A","A","A","A","B","B","B","C","C","C","C","C","CH","D","D","D","D","D","D",
        "E","E","E","E","E","E","E","E","E","E","E","E","E","F","F","G","G","H","H","I","I","I","I","I","I","I","J","L","L","L","L","L","L","LL","M","M","M","M","N","N",
        "N","N","N","N","Ñ","O","O","O","O","O","O","O","O","O","O","P","P","P","Q","R","R","R","R","R","R","RR","S","S","S","S","S","S","S","T","T","T","T",
        "T","U","U","U","U","U","U","U","V","V","X","Y","Z","*", "*"};
    
    private String[][] tableTop;
    private ArrayList<Player> players;
    private ArrayList<Player> playing;
    
    private int playerActive;
    private int playersNum;
    private boolean gameActive;
    private String logo;
    
    public String pickLetter() {
        return lettersPool.isEmpty() ? "" : lettersPool.pop();
    }
    
    public String[] pickLetter(int num) {
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
        return playing.get(playerActive);
    }
    
    public void print() {
        Out.clear();
        Out.msg(logo);
        
        Out.printArr(tableTop, Color.YELLOW, 3);
        Out.msg("\n");
        
        Out.msg(Out.toColor("\t¡Turno de " + getActPlayer().getName() + "!\n", getActPlayer().getColor()));
        
        String lastLetters = "Fichas restantes:\t";
        
        int pct = (int)Math.ceil(((double)lettersPool.size() / (double)validLetters.length) * 100.0);
        int i = 0;
        
        for (; i < Math.ceil(pct / 10); i++)
            lastLetters += "▰";
        for (; i < 10; i++)
            lastLetters += "▱";
        
        lastLetters += "\t" + pct + "% " + lettersPool.size() + " / " + validLetters.length + "\n";
        
        String fails = "Fallos restantes:\t";
        
        i = 0;
        for (; i < getActPlayer().getFails(); i++)
            fails += "▰";
        for (; i < 3; i++)
            fails += "▱";
        
        fails += "\t\t" + getActPlayer().getFails() + " / 3\n\n";
        
        Out.printArr(new String[] {lastLetters, fails}, getActPlayer().getColor());
        Out.printArr(new String[][] { getActPlayer().getLetters() }, getActPlayer().getColor());
    }
    
    public void newGame() {
        init();
        Out.clear();
        Out.msg(logo);
        
        for (int i = 0; i < playersNum; i++) {
            Out.msg("Introduce el nombre del jugador nº " + (i + 1) + ": ", false);
            players.add(new Player(In.getString(), this, validColors[i]));
        }
        playing = (ArrayList<Player>) players.clone();
        
        Out.msg("\n");
        gameActive = true;
        
        while (gameActive && !playing.isEmpty())
            nextRound();
        
        printResults();
        Out.msg("Pulsa enter para continuar.", false);
        In.pressToContinue();
    }
    
    private void removePlayer(Player player) {
        playing.remove(player);
        
        if (playing.size() == 1)
            gameActive = false;
        
        if (playerActive >= playing.size())
            playerActive = 0;
    }
    
    private void init() {
        players = new ArrayList<>();
        lettersPool = new Stack<>();
        Matrix.shuffle(validLetters);
        Matrix.shuffle(validColors);
        
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
        
        boolean stop = false;
        while (!stop) {
            Out.msg("\n\n > ", false);
            String letters = In.getString().toUpperCase();
            CmdResult cmdResult = executeCmd(letters);
            
            if (cmdResult == CmdResult.CMD_NEXT_ROUND)
                stop = true;
            else if (cmdResult == CmdResult.CMD_CONTINUE)
                print();
            else if (cmdResult == CmdResult.CMD_NOT_CMD) {
                String[] tokens = letters.split(" ");
                if (tokens.length != 4 || (!tokens[3].toUpperCase().equals("H") && !tokens[3].toUpperCase().equals("V"))) {
                    Out.err("¡Error! Tienes que introducir una línea con el formato \"PALABRA POS_X[INT] POS_Y[INT] DIRECCION[H|V]\"");
                } else {
                    byte[] pos = { Byte.parseByte(tokens[2]), Byte.parseByte(tokens[1]) };
                    boolean dir = !tokens[3].toUpperCase().equals("H");

                    Word word = new Word(tokens[0], pos, dir, this);

                    if (setWord(word))
                        stop = true;
                }
            }
        }
        
        if (!getActPlayer().hasLetters())
            gameActive = false;
        
        playerActive++;
        
        if (playerActive >= playing.size())
            playerActive = 0;
    }
    
    private CmdResult executeCmd(String str) {
        CmdResult res = CmdResult.CMD_NEXT_ROUND;
        
        switch (str) {
            case "PASAR":
                getActPlayer().lessFails();
                if (getActPlayer().getFails() < 0)
                    removePlayer(getActPlayer());
                break;
            case "SALIR":
                gameActive = false;
                break;
            case "PUNTOS":
                printResults();
                Out.msg("Pulsa enter para continuar. ", false);
                In.pressToContinue();
                res = CmdResult.CMD_CONTINUE;
                break;
            default:
                res = CmdResult.CMD_NOT_CMD;
                break;
        }
        
        return res;
    }
    
        /**
     * Coloca a palabra no taboleiro
     * @param word Palabra separada en letras para colocar no taboleiro
     * @return true - Si se colocou a palabra, false se non
     */
    private boolean setWord(Word word) {
        boolean res = false;
        ErrorCode code = word.check();
        
        if (code == OK) {
            getActPlayer().plusPoints(word.getPoints());
            word.store();
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
    
    private void printResults() {
        Random rnd = new Random();
        Color color = validColors[rnd.nextInt(validColors.length)];
        
        Out.clear();
        Out.msg(Out.toColor("█▀▄ █▀▀ ▄▀▀ █ █ █░░ ▀█▀ ▄▀▄ █▀▄ █▀█ ▄▀▀\n", color)
                   + Out.toColor("█▀▄ █▀▀ ░▀▄ █ █ █░░  █  █▄█ █ █ █ █ ░▀▄\n", color)
                   + Out.toColor("▀░▀ ▀▀▀ ▀▀░ ▀▀▀ ▀▀▀  ▀  ▀░▀ ▀▀░ ▀▀▀ ▀▀░\n\n", color));
        
        String[][] results = new String[players.size()+1][3];
        results[0][0] = "";
        results[0][1] = "Jugador";
        results[0][2] = "Puntos";
        
        // Ordenar results por los puntos de los jugadores
        players.sort(null);
        for (int i = 1; i < results.length; i++) {
            Player player = players.get(i-1);
            if ((i != 1) && (player.getPoints() == Integer.parseInt(results[i-1][2])))
                results[i][0] = results[i-1][0];
            else
                results[i][0] = Integer.toString(i) + "º";
            
            results[i][1] = player.getName();
            results[i][2] = Integer.toString(player.getPoints());
        }
        
        Out.printArr(results, color);
        Out.msg("");
    }
    
}