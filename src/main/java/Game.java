import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.security.Key;

public class Game {
    private Screen screen;
    private int x = 10;
    private int y = 10;

    public Game() throws IOException {
        TerminalSize terminalSize = new TerminalSize(40, 20);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
        Terminal terminal = terminalFactory.createTerminal();
        screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null); // we don't need a cursor
        screen.startScreen(); // screens must be started
        screen.doResizeIfNecessary();
    }
    private void Draw() throws IOException {
        screen.clear();
        screen.setCharacter(x, y, TextCharacter.fromCharacter('X')[0]);
        screen.refresh();
    }
    public void Run() throws IOException {
        Draw();
        while(true){
            KeyStroke key = screen.readInput();
            processKey(key);
            Draw();
            if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q'){
                screen.close();
            }
            if (key.getKeyType() == KeyType.EOF) break;
        }
    }
    private void processKey(KeyStroke key){
        System.out.println(key);
        switch (key.getKeyType()){
            case ArrowUp:
                y -= 1;
                break;
            case ArrowDown:
                y += 1;
                break;
            case ArrowLeft:
                x -= 1;
                break;
            case ArrowRight:
                x += 1;
                break;
        }

    }
}