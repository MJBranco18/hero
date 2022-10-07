import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

public class Arena {
    private int width;
    private int height;

    Hero hero = new Hero(10,10);

    public Arena(int width, int height){
        this.height = height;
        this.width = width;
        hero = new Hero(10,10);
    }

    public void Draw(TextGraphics graphics){
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        hero.draw(graphics);
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width * 2, height * 2), ' ');
        graphics.putString(new TerminalPosition(hero.getPosition().getX() * 2, hero.getPosition().getY() * 2), "\\/");
        graphics.putString(new TerminalPosition(hero.getPosition().getX() * 2, hero.getPosition().getY() * 2 + 1), "/\\");
    }
    void processKey(KeyStroke key){
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
    }

    public void moveHero(Position position) {
        if (canHeroMove(position))
            hero.setPosition(position);
    }

    public boolean canHeroMove(Position position){
        if(position.getX() < 0 || position.getX() > width || position.getY() < 0 || position.getY() > height) return false;
        return true;
    }
}
