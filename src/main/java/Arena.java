import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    private int width;
    private int height;
    private Hero hero;
    private List<Coin> coins;
    private List<Wall> walls;

    private List<Monster> monsters;

    public Arena(int width, int height){
        this.height = height;
        this.width = width;
        this.hero = new Hero(10, 10);
        this.walls = createWalls();
        this.coins = createCoins();
        this.monsters = createMonsters();
    }

    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            coins.add(new Coin(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1));
        return coins;
    }

    public void Draw(TextGraphics graphics){
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        hero.draw(graphics);
        for (Wall wall : walls)
            wall.draw(graphics);
        for (Coin coin : coins)
            coin.draw(graphics);
        for (Monster monster : monsters)
            monster.draw(graphics);
    }
    public void processKey(KeyStroke key, Screen screen) throws IOException {
        System.out.println(key);
        switch (key.getKeyType()){
            case ArrowUp:
                moveHero(hero.moveUp());
                break;
            case ArrowDown:
                moveHero(hero.moveDown());
                break;
            case ArrowLeft:
                moveHero(hero.moveLeft());
                break;
            case ArrowRight:
                moveHero(hero.moveRight());
                break;
        }
        moveMonsters();
        verifyMonsterCollisions(screen);
    }

    public void moveHero(Position position) {
        if (canHeroMove(position))
            hero.setPosition(position);
    }

    public boolean canHeroMove(Position position){
        if(position.getX() < 0 || position.getX() > width || position.getY() < 0 || position.getY() > height) return false;
        for(Wall wall : walls) {
            if (wall.getPosition().equals(position)) return false;
        }
        retrieveCoins();
        return true;
    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();
        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }
        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }
        return walls;
    }
    private void retrieveCoins() {
        for (Coin coin : coins) {
            if (coin.getPosition().equals(hero.getPosition())) {
                coins.remove(coin);
                break;
            }
        }
    }
    private List<Monster> createMonsters() {
        Random random = new Random();
        ArrayList<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            monsters.add(new Monster(random.nextInt(width - 2) + 1,
                    random.nextInt(height - 2) + 1));
        return monsters;
    }

    private boolean canMonsterMove(Monster monster, Position movePosition) {
        for (Wall wall : walls)
            if (wall.getPosition().equals(movePosition)) return false;
        return true;
    }

    private void moveMonsters() {
        for (Monster monster : monsters) {
            Position movePosition = monster.move();
            if (canMonsterMove(monster, movePosition))
                monster.setPosition(movePosition);
        }
    }

    private void verifyMonsterCollisions(Screen screen) throws IOException {
        for (Monster monster : monsters) {
            if (monster.getPosition().equals(hero.getPosition())) {
                screen.close();
                System.out.println("Game Over!");
            }
        }
    }
}
