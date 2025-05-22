package ui;

import inputs.Keyboard;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class Level1 extends JPanel {

    private Image fondoNivel1;
    private Keyboard teclado;
    private JButton volverButton;
    private GameWindow window;
    private Player player;
    private Timer timer;       // Timer para actualizar animación y entrada (~60 FPS)
    private Timer timerJuego;  // Timer para actualizar estado de juego (~60 FPS)
    private Object keys;
    private ArrayList<Object> souls;
    private int soulsRecolected = 0;
    private int tiempoRestante = 60;  // segundos

    private int cameraX = 0;           // Desplazamiento de la cámara en X
    private final int nivelAncho = 4000; // Ajusta esto al ancho real del nivel

    public Level1(GameWindow window) {
        this.window = window;
        cargarImagenes();
        configurarPanel();

        teclado = new Keyboard();
        addKeyListener(teclado);

        addHierarchyListener(e -> {
            if (isShowing()) {
                requestFocusInWindow();
            }
        });

        player = new Player(100, 400, 80, 80);

        keys = new Object(600, 300, 40, 40, "/res/key.png");

        souls = new ArrayList<>();
        souls.add(new Object(200, 350, 30, 30, "/res/soul.png"));
        souls.add(new Object(400, 350, 30, 30, "/res/soul.png"));
        souls.add(new Object(500, 320, 30, 30, "/res/soul.png"));

        agregarBotonVolver();

        timer = new Timer(16, e -> {
            player.actualizar(teclado.isLeft(), teclado.isRight(), teclado.isJump());
            teclado.resetJump();
            repaint();
        });
        timer.start();

        timerJuego = new Timer(16, e -> {
            actualizarEstado();
            repaint();
        });
        timerJuego.start();

        Timer timerSegundos = new Timer(1000, e -> {
            if (tiempoRestante > 0) {
                tiempoRestante--;
            }
        });
        timerSegundos.start();
    }

    private void cargarImagenes() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/res/Fondo3.jpeg"));
        fondoNivel1 = icon.getImage();
    }

    private void configurarPanel() {
        setLayout(null);
        setFocusable(true);
    }

    private void agregarBotonVolver() {
        Image img = new ImageIcon("src/res/Back2.png").getImage().getScaledInstance(100, 40, Image.SCALE_SMOOTH);
        ImageIcon volverIcon = new ImageIcon(img);

        volverButton = new JButton(volverIcon);
        volverButton.setBounds(20, 20, volverIcon.getIconWidth(), volverIcon.getIconHeight());
        volverButton.setBorderPainted(false);
        volverButton.setContentAreaFilled(false);
        volverButton.setFocusPainted(false);

        volverButton.addActionListener(e -> {
            window.setContentPane(new LevelSelect(window));
            window.revalidate();
            window.repaint();
        });

        add(volverButton);
    }

    private void actualizarEstado() {
        player.actualizar(teclado.isLeft(), teclado.isRight(), teclado.isJump());
        teclado.resetJump();

        // Colisiones con llave
        if (keys.estaActivo() && player.getBounds().intersects(keys.getBounds())) {
            keys.recolectar();
        }

        // Colisiones con almas
        for (Object alma : souls) {
            if (alma.estaActivo() && player.getBounds().intersects(alma.getBounds())) {
                alma.recolectar();
                soulsRecolected++;
            }
        }

        // Actualizar cámara centrada en el jugador
        cameraX = player.getX() - getWidth() / 2 + player.getWidth() / 2;
        if (cameraX < 0) cameraX = 0;
        if (cameraX > nivelAncho - getWidth()) cameraX = nivelAncho - getWidth();

        // Condición de victoria
        if (!keys.estaActivo() && soulsRecolected == souls.size() && tiempoRestante > 0) {
            timerJuego.stop();
            timer.stop();
            UIManager.put("OptionPane.okButtonText", "OK");
            JOptionPane.showMessageDialog(this, "¡You Win!");

            window.setContentPane(new LevelSelect(window));
            window.revalidate();
            window.repaint();
        }

        // Condición de derrota
        if (tiempoRestante == 0) {
            timerJuego.stop();
            timer.stop();
            UIManager.put("OptionPane.okButtonText", "OK");
            JOptionPane.showMessageDialog(this, "¡Time Over! You Lose.");

            window.setContentPane(new Level1(window));
            window.revalidate();
            window.repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibuja el fondo desplazado horizontalmente con la cámara
        g.drawImage(fondoNivel1, -cameraX, 0, nivelAncho, getHeight(), this);

        // Dibuja jugador y objetos restando cameraX para simular movimiento cámara
        player.dibujar(g, cameraX);
        keys.dibujar(g, cameraX);

        for (Object alma : souls) {
            alma.dibujar(g, cameraX);
        }

        // HUD
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Time: " + tiempoRestante, 950, 40);
        g.drawString("Souls: " + soulsRecolected + "/" + souls.size(), 800, 40);
    }
}
