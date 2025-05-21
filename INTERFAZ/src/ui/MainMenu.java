package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainMenu extends JPanel {
    private JButton playButton, optionsButton;
    private Image background;
    private Image title;

    public MainMenu(JFrame frame) {
        setLayout(null);
        setPreferredSize(new Dimension(1000, 800));

        // Cargar fondo y título
        background = new ImageIcon(getClass().getClassLoader().getResource("res/fondo.png")).getImage();
        title = new ImageIcon(getClass().getClassLoader().getResource("res/TituloGame.png")).getImage();

        // Botón PLAY
        playButton = new JButton(new ImageIcon(getClass().getClassLoader().getResource("res/play2.png")));
        playButton.setBounds(680, 50, 262, 508);
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.addActionListener((ActionEvent e) -> {
            frame.setContentPane(new LevelSelect(frame));
            frame.revalidate();
        });
        add(playButton);

        // Botón MENÚ (Opciones)
        optionsButton = new JButton(new ImageIcon(getClass().getClassLoader().getResource("res/Menu1.png")));
        optionsButton.setBounds(960, 460, 90, 138);
        optionsButton.setBorderPainted(false);
        optionsButton.setContentAreaFilled(false);
        optionsButton.addActionListener((ActionEvent e) -> {
            frame.setContentPane(new MenuOptions(frame));
            frame.revalidate();
        });
        add(optionsButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        // No hace falta usar imágenes tan grandes que cubren los botones
        g.drawImage(title, getWidth()/2 - 450, 40, 500, 500, this); 
    }
}
