package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LevelSelect extends JPanel {
    private Image background;
    private Image title;

    public LevelSelect(JFrame frame) {
        setLayout(null);
        setPreferredSize(new Dimension(800, 600));

        background = new ImageIcon(getClass().getClassLoader().getResource("res/fondo.png")).getImage();
        title = new ImageIcon(getClass().getClassLoader().getResource("res/SelectLevel.png")).getImage();

        // Botones de niveles
        JButton level1 = new JButton(new ImageIcon(getClass().getClassLoader().getResource("res/level1.SF.png")));
        level1.setBounds(450, 200, 148, 252);
        level1.setBorderPainted(false);
        level1.setContentAreaFilled(false);
        
        level1.addActionListener((ActionEvent e) -> {
            frame.setContentPane(new Level1((GameWindow) frame)); // Cambia al nivel 1
            frame.revalidate();
            frame.repaint();
        });
        
        add(level1);
/*
        JButton level2 = new JButton(new ImageIcon(getClass().getClassLoader().getResource("res/level2.SF.png")));
        level2.setBounds(450, 200, 148, 252);
        level2.setBorderPainted(false);
        level2.setContentAreaFilled(false);
        add(level2);

        JButton level3 = new JButton(new ImageIcon(getClass().getClassLoader().getResource("res/level3.SF.png")));
        level3.setBounds(700, 200, 148, 252);
        level3.setBorderPainted(false);
        level3.setContentAreaFilled(false);
        add(level3);
   */     
        JButton back = new JButton();
        back.setBounds(50, 500, 130, 114);

        // Cargar y escalar la imagen (ajustado al tamaño del botón)
        ImageIcon icon = new ImageIcon(getClass().getResource("/res/Back2.png"));
        Image scaled = icon.getImage().getScaledInstance(100, 40, Image.SCALE_SMOOTH);
        back.setIcon(new ImageIcon(scaled));

        // Quitar decoraciones para que solo se vea la imagen
        back.setBorderPainted(false);
        back.setContentAreaFilled(false);
        back.setFocusPainted(false);
        back.setOpaque(false);

        // Acción del botón
        back.addActionListener(e -> {
            frame.setContentPane(new MainMenu(frame));
            frame.revalidate();
        });

        // Agregar al panel
        add(back);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(title, getWidth()/2 - 450, 50, 400, 100, this);
    }
}
