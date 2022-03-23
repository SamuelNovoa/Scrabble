/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import io.In;
import io.Out;
import tools.Matrix;

/**
 *
 * @author a21samuelnc
 */
public class Menu {
    private String name;
    private MenuFunction[] actions;
    private String[] options;
    private boolean hasLogo;
    private boolean refreshScreen;
    
    public Menu(String name, String[] options, boolean hasLogo, boolean refreshScreen) {
        this.name = name;
        this.hasLogo = hasLogo;
        this.options = options;
        this.refreshScreen = refreshScreen;
    }
    
    public Menu(String name, Object[][] optAct, boolean hasLogo, boolean refreshScreen) {
        this.name = name;
        this.hasLogo = hasLogo;
        
        optAct = Matrix.transpose(optAct);
        
        this.options = new String[optAct[0].length];
        this.actions = new MenuFunction[optAct[0].length];
        this.refreshScreen = refreshScreen;
        
        for (int i = 0; i < optAct[0].length; i++) {
            this.options[i] = (String)optAct[0][i];
            this.actions[i] = (MenuFunction)optAct[1][i];
        }
    }
    
    public int getOption() {
        showMenu();
        Out.msg(" > ", false);
        
        int opt = In.getOption(options.length) - 1;
        Out.msg("");
        
        return opt;
    }
    
    public void executeOption() {        
        actions[getOption()].run();
    }
    
    private void showMenu() {
        if (refreshScreen)
            Out.clear();
        
        if (hasLogo) {
            Out.msg(name);
        } else {
            Out.msg("  " + name);
            for (int i = 0; i < name.length() + 4; i++)
                Out.msg("=", false);
        }
        Out.msg("\n");
        for (int i = 0; i < options.length; i++) {
            Out.msg((i + 1) + ") " + options[i]);
        }
        Out.msg("\n");
    }
}
