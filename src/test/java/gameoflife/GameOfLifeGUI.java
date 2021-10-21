package gameoflife;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class GameOfLifeGUI extends Canvas {

    private static final int MOVE_SIZE = 10;
    private static final int HUD_OFFSET = 50;


    private boolean[][] gameField;
    private int generations;
    private int alive;


    private int scale = 5;
    private int xOffset = 2;
    private int yOffset = 2;


    public GameOfLifeGUI(boolean[][] gameField) {
        this.gameField = gameField;


        this.addMouseWheelListener(e -> {
            if ((scale + (e.getWheelRotation() * -1)) > 0) {
                scale += (e.getWheelRotation() * -1);
                repaint();
            }
        });

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //NOOP
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //NOOP
            }

            @Override
            public void keyReleased(KeyEvent e) {

                System.out.println(e.getKeyCode());

                switch (e.getKeyCode()) {
                    case 38 -> {
                        yOffset += (scale * MOVE_SIZE);
                        repaint();
                    }
                    case 40 -> {
                        yOffset -= (scale * MOVE_SIZE);
                        repaint();
                    }
                    case 39 -> {
                        xOffset -= (scale * MOVE_SIZE);
                        repaint();
                    }
                    case 37 -> {
                        xOffset += (scale * MOVE_SIZE);
                        repaint();
                    }
                    case 82 -> {
                        scale = 2;
                        xOffset = 2;
                        yOffset = 2;
                        repaint();
                    }
                }
            }
        });

    }

    public void updateGameField(boolean[][] gameField, int alive, int generations) {
        this.gameField = gameField;
        this.alive = alive;
        this.generations = generations;
        repaint();
    }


    @Override
    public void paint(Graphics g) {

        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            bs = getBufferStrategy();
        }

        Graphics bufferedGraphics = bs.getDrawGraphics();

        bufferedGraphics.setColor(Color.LIGHT_GRAY);
        bufferedGraphics.fillRect(0, 0, getWidth(), getHeight());
        bufferedGraphics.setColor(Color.BLACK);

        bufferedGraphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
        bufferedGraphics.drawString(String.format("Cells alive: %d    Current generation: %s", alive, generations), 10, 30);


        for (int i = 0; i < gameField.length; i++) {
            var line = gameField[i];
            for (int j = 0; j < gameField[i].length; j++) {
                var currentVal = line[j];
                if (currentVal) {
                    bufferedGraphics.fillRect((j * scale) + xOffset, (i * scale) + yOffset + HUD_OFFSET, scale, scale);
                }
            }
        }
        bufferedGraphics.dispose();
        bs.show();
    }

    @Override
    public void update(Graphics g) {
        this.paint(g);
    }
}
