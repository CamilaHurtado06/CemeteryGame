package ui;

import inputs.Keyboard;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Level1 extends JPanel {

    private Image fondoNivel1;
    private Keyboard teclado;
    private JButton volverButton;
    private GameWindow window;
    private Player player;
    private Timer timer;       // Timer para animación y entrada (~60 FPS)
    private Timer timerJuego;  // Timer para estado de juego (~60 FPS)

    private Object keys;
    private ArrayList<Object> souls;
    private ArrayList<Plataforma> plataformas;

    private int soulsRecolected = 0;
    private int tiempoRestante = 60;  // segundos
    private int cameraX = 0;           // desplazamiento cámara horizontal
    private final int nivelAncho = 4100; // ancho del nivel

    public Level1(GameWindow window) {
        this.window = window;

        plataformas = new ArrayList<>();
        inicializarPlataformas();

        teclado = new Keyboard();
        addKeyListener(teclado);
        setFocusable(true);

        cargarImagenes();

        addHierarchyListener(e -> {
            if (isShowing()) {
                requestFocusInWindow();
            }
        });

        player = new Player(100, 100, 40, 40, plataformas);

        keys = new Object(3150, 400, 40, 40, "/res/key.png");

        souls = new ArrayList<>();
        inicializarAlmas();

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

    private void inicializarPlataformas() {
        // --- SECCIÓN 1: Zona de inicio fácil ---
        plataformas.add(new Plataforma(-30, 480, 300, 150, 0));
        plataformas.add(new Plataforma(350, 480, 100, 150, 0));
        plataformas.add(new Plataforma(500, 430, 80, 30, 3));
        plataformas.add(new Plataforma(600, 380, 80, 30, 3));
        plataformas.add(new Plataforma(700, 330, 80, 30, 3));
        plataformas.add(new Plataforma(850, 480, 150, 150, 0));

        // --- SECCIÓN 2: Plataformas flotantes ---
        plataformas.add(new Plataforma(1100, 420, 80, 25, 1));
        plataformas.add(new Plataforma(1200, 370, 80, 25, 1));
        plataformas.add(new Plataforma(1300, 320, 80, 25, 1));
        plataformas.add(new Plataforma(1400, 270, 80, 25, 1));
        plataformas.add(new Plataforma(1500, 220, 120, 30, 4));

        // --- SECCIÓN 3: Camino superior e inferior ---
        plataformas.add(new Plataforma(1700, 480, 300, 150, 0));
        plataformas.add(new Plataforma(1750, 300, 80, 25, 2));
        plataformas.add(new Plataforma(1850, 260, 80, 25, 2));
        plataformas.add(new Plataforma(1950, 300, 80, 25, 2));

        // --- SECCIÓN 4: Zigzag tipo puente ---
        plataformas.add(new Plataforma(2150, 400, 60, 25, 5));
        plataformas.add(new Plataforma(2250, 350, 60, 25, 5));
        plataformas.add(new Plataforma(2350, 300, 60, 25, 5));
        plataformas.add(new Plataforma(2450, 350, 60, 25, 5));
        plataformas.add(new Plataforma(2550, 400, 60, 25, 5));

        // --- SECCIÓN 5: Sky Platforms ---
        plataformas.add(new Plataforma(2700, 400, 100, 30, 2));
        plataformas.add(new Plataforma(2850, 350, 100, 30, 1));
        plataformas.add(new Plataforma(3000, 300, 100, 30, 2));
        plataformas.add(new Plataforma(3150, 250, 100, 30, 1));
        plataformas.add(new Plataforma(3300, 480, 150, 150, 0));

        // --- SECCIÓN FINAL: Entrada al castillo ---
        plataformas.add(new Plataforma(3500, 480, 200, 150, 0));
        plataformas.add(new Plataforma(3100, 480, 120, 40, 1));
        plataformas.add(new Plataforma(3750, 330, 80, 40, 3));
        plataformas.add(new Plataforma(3900, 270, 200, 40, 3));
    }

    private void inicializarAlmas() {
        souls.add(new Object(710, 290, 30, 30, "/res/soul.png"));
        souls.add(new Object(1300, 280, 30, 30, "/res/soul.png"));
        souls.add(new Object(1850, 220, 30, 30, "/res/soul.png"));
        souls.add(new Object(2350, 260, 30, 30, "/res/soul.png"));
        souls.add(new Object(3000, 260, 30, 30, "/res/soul.png"));
        souls.add(new Object(2850, 310, 30, 30, "/res/soul.png"));
        souls.add(new Object(3350, 440, 30, 30, "/res/soul.png"));
        souls.add(new Object(3750, 290, 30, 30, "/res/soul.png"));
        souls.add(new Object(1700, 380, 30, 30, "/res/soul.png"));
        souls.add(new Object(3900, 230, 30, 30, "/res/soul.png"));
    }

    private void agregarBotonVolver() {
        Image img = new ImageIcon(getClass().getResource("/res/Back2.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
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
        // Actualizar jugador con entradas
        player.actualizar(teclado.isLeft(), teclado.isRight(), teclado.isJump());
        teclado.resetJump();

        // Colisión y recolección de llave
        if (keys.estaActivo() && player.getBounds().intersects(keys.getBounds())) {
            keys.recolectar();
        }

        // Colisión y recolección de almas
        for (Object alma : souls) {
            if (alma.estaActivo() && player.getBounds().intersects(alma.getBounds())) {
                alma.recolectar();
                soulsRecolected++;
            }
        }

        // Actualiza posición de cámara centrada en jugador
        cameraX = player.getX() - getWidth() / 2 + player.getWidth() / 2;
        if (cameraX < 0) cameraX = 0;
        if (cameraX > nivelAncho - getWidth()) cameraX = nivelAncho - getWidth();

        // Condición de victoria: llave recolectada, todas las almas recogidas y tiempo restante
        if (!keys.estaActivo() && soulsRecolected == souls.size() && tiempoRestante > 0) {
            timerJuego.stop();
            timer.stop();
            UIManager.put("OptionPane.okButtonText", "OK");
            JOptionPane.showMessageDialog(this, "¡You Win!");

            window.setContentPane(new LevelSelect(window));
            window.revalidate();
            window.repaint();
        }
        if (player.haPerdido()) {
            timerJuego.stop();
            timer.stop();
            UIManager.put("OptionPane.okButtonText", "OK");
            JOptionPane.showMessageDialog(this, "¡You Lose!");

            window.setContentPane(new Level1(window));
            window.revalidate();
            window.repaint();
        }

        // Condición de derrota: tiempo agotado
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

        // Dibuja fondo desplazado por cámara
        g.drawImage(fondoNivel1, -cameraX, 0, nivelAncho, getHeight(), this);
           try {
        g.drawImage(ImageIO.read(getClass().getResource("/res/Elemento3.png")), 350 - cameraX, 390, 80, 100, null);
        g.drawImage(ImageIO.read(getClass().getResource("/res/Elemento2.png")), 4000 - cameraX, 180, 80, 100, null);
        g.drawImage(ImageIO.read(getClass().getResource("/res/Elemento4.png")), 0 - cameraX, 425, 60, 60, null);
        g.drawImage(ImageIO.read(getClass().getResource("/res/Elemento6.png")), 200 - cameraX, 395, 60, 100, null);
        g.drawImage(ImageIO.read(getClass().getResource("/res/Elemento6.png")), 1800 - cameraX, 395, 60, 100, null);
        g.drawImage(ImageIO.read(getClass().getResource("/res/Elemento6.png")), 900 - cameraX, 395, 60, 100, null);
        g.drawImage(ImageIO.read(getClass().getResource("/res/Elemento7.png")), 180 - cameraX, 450, 40, 40, null);
         g.drawImage(ImageIO.read(getClass().getResource("/res/Elemento7.png")), 900 - cameraX, 450, 40, 40, null);
    } catch (IOException e) {
        e.printStackTrace();
    }
        // Dibuja plataformas
        for (Plataforma plataforma : plataformas) {
            plataforma.dibujar(g, cameraX);
        }

        // Dibuja jugador y objetos restando desplazamiento de cámara
        player.dibujar(g, cameraX);
        keys.dibujar(g, cameraX);

        for (Object alma : souls) {
            alma.dibujar(g, cameraX);
        }

        // HUD: tiempo y almas recolectadas
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Time: " + tiempoRestante, 950, 40);
        g.drawString("Souls: " + soulsRecolected + "/" + souls.size(), 800, 40);
        try {
            Image elemento5 = ImageIO.read(getClass().getResource("/res/Elemento5.png"));
            
            int anchoEscalado = 50;  // Cambia este valor según el tamaño que quieras
            int altoEscalado = 100;
            int ySuelo = 570; // Altura del suelo
            int anchoPantalla = getWidth() + cameraX;

            for (int x = 0; x < anchoPantalla; x += anchoEscalado) {
                g.drawImage(elemento5, x - cameraX, ySuelo, anchoEscalado, altoEscalado, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
