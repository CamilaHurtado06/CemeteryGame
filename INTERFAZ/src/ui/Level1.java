package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Level1 extends JPanel {

    private Image fondoNivel1;
    private JButton playButton;
    private JButton volverButton;
    private GameWindow window;

    public Level1(GameWindow window) {
        this.window = window;
        cargarImagenes();
        configurarPanel();
        //agregarBotonPlay();
        agregarBotonVolver();
    }

    private void cargarImagenes() {
        // Asegúrate de que esta imagen exista dentro de tu carpeta res
        ImageIcon icon = new ImageIcon(getClass().getResource("/res/Fondo3.jpeg"));
        fondoNivel1 = icon.getImage();
    }

    private void configurarPanel() {
        setLayout(null); // Usamos coordenadas absolutas para posicionar botones
        setFocusable(true);
    }
/*
    private void agregarBotonPlay() {
        // Imagen del botón Play
        playButton = new JButton(new ImageIcon(getClass().getResource("/res/playGame.png")));
        playButton.setBounds(450, 150, 148, 287); // Posición y tamaño en la ventana
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.setFocusPainted(false);
        playButton.setOpaque(false);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarJuego(); // Aquí irá el contenido del nivel
            }
        });

        add(playButton);
    }*/
    
    private void agregarBotonVolver() {
        // Cargar el ícono desde los recursos
    	Image img = new ImageIcon("src/res/Back2.png").getImage().getScaledInstance(100, 40, Image.SCALE_SMOOTH);
    	ImageIcon volverIcon = new ImageIcon(img);

        // Crear el botón con la imagen
        volverButton = new JButton(volverIcon);
        volverButton.setBounds(20, 20, volverIcon.getIconWidth(), volverIcon.getIconHeight());
        volverButton.setBorderPainted(false);
        volverButton.setContentAreaFilled(false);
        volverButton.setFocusPainted(false);

        volverButton.addActionListener(e -> {
            // Volver al menú principal automáticamente
            window.setContentPane(new LevelSelect(window));
            window.revalidate();
            window.repaint();
        });

        add(volverButton);
    }


    private void iniciarJuego() {
    	JFrame juegoFrame = new JFrame("Nivel 1 - Juego");
        juegoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        juegoFrame.setContentPane(new Nivel1Juego()); // Carga el panel del juego real
        juegoFrame.setSize(800, 600);
        juegoFrame.setLocationRelativeTo(null);
        juegoFrame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondoNivel1, 0, 0, getWidth(), getHeight(), this);
        // Aquí luego puedes dibujar plataformas, personaje, enemigos, etc.
    }
}
