import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

public class Arena {
    private int width;
    private int height;

    private Position position;

    private Hero hero;

    public Arena(int width, int height){
        this.height = height;
        this.width = width;
        hero = new Hero(10,10);
    }

    public void Draw(Screen screen){
        screen.setCharacter(hero.getPosition().getX(),hero.getPosition().getY(), TextCharacter.fromCharacter('X')[0]);
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
