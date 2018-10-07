package main;


import gui.MainScreen;
import data_structure.FileHolder;

public class App {

    public static FileHolder fileHolder = new FileHolder();

    public void start() {
        MainScreen mainScreen = new MainScreen();
        mainScreen.initGui();
    }

}
