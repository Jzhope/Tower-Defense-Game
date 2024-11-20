package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to my project!");
        try {
             
            new GameApp();
             
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
