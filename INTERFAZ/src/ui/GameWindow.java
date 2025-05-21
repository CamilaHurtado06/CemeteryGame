package ui;

import javax.swing.*;

public class GameWindow extends JFrame {

    public GameWindow() {
        setTitle("Juego Gótico");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080, 640);
        setResizable(false);
        setLocationRelativeTo(null);

        setContentPane(new MainMenu(this));  

        setVisible(true);
    }
}
