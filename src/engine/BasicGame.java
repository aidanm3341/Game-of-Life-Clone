package engine;

public interface BasicGame {
    void init(GameLoop gameLoop);
    void update();
    void render(Screen screen);
}
