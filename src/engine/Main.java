package engine;


import game.Game;

public class Main {

    public static void main(String[] args) {
        GameLoop game = new GameLoop("Game of Life", new Game(30));
        game.start();
    }
}