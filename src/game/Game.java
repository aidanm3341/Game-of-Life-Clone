package game;

import engine.BasicGame;
import engine.GameLoop;
import engine.Color;
import engine.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Game implements BasicGame, MouseListener, ActionListener {

    private enum Direction {
        N, NE, E, SE, S, SW, W, NW
    }

    private boolean paused;
    private boolean[] cells;
    private int width, height;
    private int ticks;

    public Game(int size) {
        this.width = size;
        this.height = size;
    }

    public void init(GameLoop gl){
        ticks = 0;
        paused = true;

        cells = new boolean[width * height];
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                cells[i + width * j] = false;

        cells[10 + width * 10] = true;
        cells[10 + width * 11] = true;
        cells[10 + width * 12] = true;

        JButton start = new JButton("Pause/Un-pause");
        start.setActionCommand("start");
        start.addActionListener(this);

        gl.add(start, BorderLayout.SOUTH);
        gl.addMouseListener(this);
    }

    public void update()
    {
        if(!paused) {
            if (ticks > 10) {
                boolean[] cells2 = new boolean[width * height];

                for (int i = 0; i < width; i++)
                    for (int j = 0; j < height; j++)
                        applyRules(i, j, cells2);

                cells = cells2;

                ticks = 0;
            }
            ticks++;
        }
    }

    private void applyRules(int i, int j, boolean[] cells2)
    {
        int count = countLiveNeibours(i, j);

        if(cells[i + width * j] && count < 2)
            cells2[i + width * j] = false;
        else if(cells[i + width * j] && count == 2 || count == 3)
            cells2[i + width * j] = true;
        else if(cells[i + width * j] && count > 3)
            cells2[i + width * j] = false;
        else if(!cells[i + width * j] && count == 3)
            cells2[i + width * j] = true;
        else
            cells2[i + width * j] = false;
    }

    private int countLiveNeibours(int i, int j)
    {
        int count = 0;

        for (Direction d: Direction.values()) {
            if (getAdjacentCellDirectional(i, j, d))
                count++;
        }

        return count;
    }

    private Boolean getAdjacentCellDirectional(int i, int j, Direction dir)
    {
        switch(dir){
            case N:
                j = (j == 0) ? height-1 : j - 1; break;
            case NE:
                j = (j == 0) ? height-1 : j - 1;
                i = (i == width-1) ? 0 : i + 1;  break;
            case E:
                i = (i == width-1) ? 0 : i + 1;  break;
            case SE:
                j = (j == height-1) ? 0 : j + 1;
                i = (i == width-1) ? 0 : i + 1;  break;
            case S:
                j = (j == height-1) ? 0 : j + 1; break;
            case SW:
                j = (j == height-1) ? 0 : j + 1;
                i = (i == 0) ? width-1 : i - 1;  break;
            case W:
                i = (i == 0) ? width-1 : i - 1;  break;
            case NW:
                j = (j == 0) ? height-1 : j - 1;
                i = (i == 0) ? width-1 : i - 1;  break;
        }
        return cells[i + width * j];
    }

    public void render(Screen screen) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                screen.renderRect(i*5, j*5, 6, 6, Color.getRGB(100, 100, 100));
                if(cells[i * width + j])
                    screen.renderFillRect(i*5 + 1, j*5 + 1, 4, 4, Color.getRGB(200, 200, 200));
                else
                    screen.renderFillRect(i*5 + 1, j*5 + 1, 4, 4, Color.getRGB(0, 0, 0));
            }
        }
    }

    public void setPaused(boolean paused){
        this.paused = paused;
    }

    public boolean isPaused(){
        return paused;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / GameLoop.SCALE;
        int y = e.getY() / GameLoop.SCALE;

        x /= 5;
        y /= 5;
        cells[y + width * x] = !cells[y + width * x];
    }

    public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("start")) {
            setPaused(!isPaused());
        }
    }
}
