package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class LevelSelect extends JPanel {
    private Image background;
    private Image title;

    public LevelSelect(GameWindow frame) {
        setLayout(null);
        setPreferredSize(new Dimension(800, 600));

        background = new ImageIcon(getClass().getClassLoader().getResource("res/fondo.png")).getImage();
        title = new ImageIcon(getClass().getClassLoader().getResource("res/SelectLevel.png")).getImage();

        // Botón de Nivel 1
        JButton level1 = new JButton(new ImageIcon(getClass().getClassLoader().getResource("res/level1.SF.png")));
        level1.setBounds(450, 200, 148, 252);
        level1.setBorderPainted(false);
        level1.setContentAreaFilled(false);
        level1.addActionListener((ActionEvent e) -> {
            frame.setContentPane(new Level1(frame));
            frame.revalidate();
            frame.repaint();
        });
        add(level1);

        // Botón Regresar
        JButton back = new JButton();
        back.setBounds(50, 500, 130, 114);
        ImageIcon icon = new ImageIcon(getClass().getResource("/res/Back2.png"));
        Image scaled = icon.getImage().getScaledInstance(100, 40, Image.SCALE_SMOOTH);
        back.setIcon(new ImageIcon(scaled));
        back.setBorderPainted(false);
        back.setContentAreaFilled(false);
        back.setFocusPainted(false);
        back.setOpaque(false);
        back.addActionListener(e -> {
            frame.setContentPane(new MainMenu(frame, frame.getAudioPlayer()));
            frame.revalidate();
        });
        add(back);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(title, getWidth() / 2 - 450, 50, 200, 50, this);
    }
}
