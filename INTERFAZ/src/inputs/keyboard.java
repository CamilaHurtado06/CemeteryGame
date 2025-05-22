package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

    private boolean left, right, jump;

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isJump() {
        return jump;
    }

    // Para evitar salto repetido mientras mantienes la tecla presionada,
    // esta función reinicia el salto después de procesarlo.
    public void resetJump() {
        jump = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                left = true;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                right = true;
                break;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                if (!jump) {
                    jump = true;
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                left = false;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                right = false;
                break;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                jump = false;
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No necesario
    }
}
