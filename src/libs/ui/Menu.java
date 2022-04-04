/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libs.ui;

import libs.io.In;
import libs.io.Out;
import libs.tools.Matrix;

/**
 * Clase para modelar los menús y gestionar su uso.
 * 
 * @author Samuel Novoa Comesaña
 */
public class Menu {
    private String name;
    private MenuFunction[] actions;
    private String[] options;
    private boolean hasLogo;
    private boolean refreshScreen;
    
    /**
     * Constructor corto para uso sin lambdas.
     * 
     * @param name Nombre del menú (Texto mostrado en su encabezado)
     * @param options Array de strings con las opciones
     */
    public Menu(String name, String[] options) {
        this(name, options, false, false);
    }
    
    /**
     * Constructor para el uso sin lambdas.
     * 
     * @param name Nombre del menú (Texto mostrado en su encabezado)
     * @param options Array de strings con las opciones
     * @param hasLogo Indica si el nombre es un logo (No le aplica formateo) o un texto
     * @param refreshScreen Indica si la pantalla debe refrescarse cada vez que se imprima el menú
     */
    public Menu(String name, String[] options, boolean hasLogo, boolean refreshScreen) {
        this.name = name;
        this.hasLogo = hasLogo;
        this.options = options;
        this.refreshScreen = refreshScreen;
    }
    
    /**
     * Constructor corto para uso con lambdas.
     * 
     * @param name Nombre del menú (Texto mostrado en su encabezado)
     * @param optAct Array bidimensional con las opciones y la función lambda asociada
     */
    public Menu(String name, Object[][] optAct) {
        this(name, optAct, false, false);
    }
    
    /**
     * Constructor para el uso con lambdas.
     * 
     * @param name Nombre del menú (Texto mostrado en su encabezado)
     * @param optAct Array bidimensional con las opciones y la función lambda asociada
     * @param hasLogo Indica si el nombre es un logo (No le aplica formateo) o un texto
     * @param refreshScreen Indica si la pantalla debe refrescarse cada vez que se imprima el menú
     */
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
    
    /**
     * Método para mostrar el menú y obtener la opción de la consola.
     * 
     * @return Opción elegida por el usuario
     */
    public int getOption() {
        showMenu();
        Out.msg(" > ", false);
        
        int opt = In.getOption(options.length) - 1;
        Out.msg("");
        
        return opt;
    }
    
    /**
     * Método para obtener una opción y ejecutar directamente su lambda asociada (Sólo para uso con lambdas)
     */
    public void executeOption() {
        int opt = getOption();
        
        if (actions.length <= opt)
            return;
        
        actions[opt].run();
    }
    
    /**
     * Método para imprimir por consola el menú.
     */
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
