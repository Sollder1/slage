package gameoflife;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GameOfLifeMain {

    //State:
    private static boolean[][] gameField;
    private static int generations;
    private static int alive;

    //Config:
    private static GameOfLifeGUI canvas;
    private static int size = 100;


    public static void main(String[] args) {
        gameField = initRandomGameField(size);
        initUI();
        startGameLoop();
    }

    private static void initUI() {

        JFrame meinFrame = new JFrame("Conway's Game of Life");
        meinFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        meinFrame.setSize(1000, 500);
        meinFrame.setVisible(true);

        canvas = new GameOfLifeGUI(gameField);
        canvas.setBackground(Color.GRAY);
        canvas.setSize(1000, 500);
        meinFrame.getContentPane().add(canvas);

    }


    private static void startGameLoop() {

        var lastRedraw = System.currentTimeMillis();
        drawToScreen();

        while (true) {
            var now = System.currentTimeMillis();


            if (now - lastRedraw >= 100) {
                System.out.println("Redrawing");
                generateNextGeneration();
                drawToScreen();
                lastRedraw = now;
                System.out.println("Redrawn");
            }
        }
    }

    private static boolean[][] initGameField1(int size) {

        boolean[][] gameField = new boolean[size][size];

        int middle = size / 2;

        gameField[middle][middle] = true;
        gameField[middle - 1][middle] = true;
        gameField[middle][middle - 1] = true;
        gameField[middle + 1][middle - 1] = true;
        gameField[middle][middle + 1] = true;

        return gameField;

    }


    private static boolean[][] initRandomGameField(int size) {
        boolean[][] gameField = new boolean[size][size];

        Random random = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gameField[i][j] = random.nextBoolean();
            }
        }
        return gameField;
    }

    private static void generateNextGeneration() {

        boolean[][] newGeneration = new boolean[size][size];
        alive = 0;
        generations++;

        for (int i = 1; i < size - 1; i++) {
            for (int j = 1; j < size - 1; j++) {
                int livingNeighbors = countAliveNeighbours(i, j);
                var currentState = gameField[i][j];

                if (!currentState) {
                    if (livingNeighbors == 3) {
                        newGeneration[i][j] = true;
                    }
                } else {
                    if (livingNeighbors < 2) {
                        newGeneration[i][j] = false;
                    } else if (livingNeighbors > 3) {
                        newGeneration[i][j] = false;
                    } else {
                        newGeneration[i][j] = true;
                    }
                }

                if (currentState) {
                    alive++;
                }

            }
        }

        gameField = newGeneration;

    }

    //TODO: refactor...
    private static int countAliveNeighbours(int i, int j) {
        int livingNeighbors = 0;

        if (gameField[i - 1][j]) {
            livingNeighbors++;
        }

        if (gameField[i + 1][j]) {
            livingNeighbors++;
        }

        if (gameField[i][j - 1]) {
            livingNeighbors++;
        }

        if (gameField[i][j + 1]) {
            livingNeighbors++;
        }

        if (gameField[i + 1][j + 1]) {
            livingNeighbors++;
        }

        if (gameField[i + 1][j - 1]) {
            livingNeighbors++;
        }

        if (gameField[i - 1][j + 1]) {
            livingNeighbors++;
        }

        if (gameField[i - 1][j - 1]) {
            livingNeighbors++;
        }
        return livingNeighbors;
    }

    private static void drawToScreen() {
        canvas.updateGameField(gameField, alive, generations);
    }


}
