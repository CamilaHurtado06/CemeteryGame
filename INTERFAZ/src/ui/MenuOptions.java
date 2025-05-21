package ui;

import javax.swing.*;
import java.awt.*;

public class MenuOptions extends JPanel {
    private Image background;
    private Image title;

    public MenuOptions(JFrame frame) {
        setLayout(null);
        setPreferredSize(new Dimension(800, 600));
        background = new ImageIcon(getClass().getClassLoader().getResource("res/fondo.png")).getImage();
        title = new ImageIcon(getClass().getClassLoader().getResource("res/MenuTitulo.png")).getImage();
        
        // Botón música
        JButton music = new JButton(new ImageIcon(getClass().getClassLoader().getResource("res/music.SF1.png")));
        music.setBounds(300, 190, 150, 225);
        music.setBorderPainted(false);
        music.setContentAreaFilled(false);
        add(music);
        //Boton Info
        JButton info = new JButton(new ImageIcon(getClass().getClassLoader().getResource("res/info.SF1.png")));
        info.setBounds(600, 200, 155, 228);
        info.setBorderPainted(false);
        info.setContentAreaFilled(false);
        add(info);
        
        // Botón regresar
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
        g.drawImage(title, getWidth()/2 - 450, 50, 200, 50, this);
    }
}
