package ui;

import java.awt.*;
import javax.swing.*;

public class MenuOptions extends JPanel {
    private Image background;
    private Image title;
    private boolean isMuted = false; // Controla el estado del audio (silenciado o no)

    public MenuOptions(GameWindow frame, AudioPlayer audioPlayer) {
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

        // Acción del botón de música: silenciar / activar
        music.addActionListener(e -> {
            if (isMuted) {
                audioPlayer.setVolume(0.2f);  // Vuelve a activar el volumen (ajustable)
                isMuted = false;
            } else {
                audioPlayer.setVolume(0.0f);  // Silencia
                isMuted = true;
            }
        });

        // Botón Info
        JButton info = new JButton(new ImageIcon(getClass().getClassLoader().getResource("res/info.SF1.png")));
        info.setBounds(600, 200, 155, 228);
        info.setBorderPainted(false);
        info.setContentAreaFilled(false);
        add(info);

        info.addActionListener(e -> {
            String mensaje = "Desarrolladoras:\n" +
                             "Laura Camila Hurtado Mendez\n" +
                             "Evely Tatian Bernal Garcia\n" +
                             "Sara Stefanny Carvajal Pinzon\n" +
                             "Ingeniería de Sistemas\n" +
                             "Corporación Universitaria del Meta";
            UIManager.put("OptionPane.okButtonText", "OK");
            JOptionPane.showMessageDialog(frame, mensaje, "Información de Desarrolladores", JOptionPane.INFORMATION_MESSAGE);
        });

        // Botón regresar
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
            frame.setContentPane(new MainMenu(frame, audioPlayer));
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
