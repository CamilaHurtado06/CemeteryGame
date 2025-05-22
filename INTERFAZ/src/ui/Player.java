package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player {
    private int x, y;
    private int width, height;

    private double velXActual = 0;
    private final double velXMax = 5;
    private final double aceleracion = 0.5;
    private final double desaceleracion = 0.3;

    private double velY = 0;
    private final double gravedad = 1;
    private final double impulsoSalto = -15;
    private boolean enElAire = false;

    private Image spriteQuieto;
    private Image spriteSaltando;
    private Image spriteQuietoIZQ;
    private Image spriteSaltandoIZQ;
    private Image[] correrDerecha;
    private Image[] correrIzquierda;

    private int frameActual = 0;
    private int contadorFrames = 0;
    private final int maxFramesPorImagen = 10;

    private boolean mirandoDerecha = true;
    private boolean corriendo = false;
    private boolean saltando = false;

    public Player(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        try {
            spriteQuieto = ImageIO.read(getClass().getResource("/res/quieto.png"));
            spriteSaltando = ImageIO.read(getClass().getResource("/res/saltando.png"));
            spriteQuietoIZQ = ImageIO.read(getClass().getResource("/res/quietoIZQ.png"));
            spriteSaltandoIZQ = ImageIO.read(getClass().getResource("/res/saltandoIZQ.png"));

            correrDerecha = new Image[] {
                ImageIO.read(getClass().getResource("/res/correr_derecha1.png")),
                ImageIO.read(getClass().getResource("/res/correr_derecha2.png")),
                ImageIO.read(getClass().getResource("/res/correr_derecha3.png")),
            };

            correrIzquierda = new Image[] {
                ImageIO.read(getClass().getResource("/res/correr_izquierda1.png")),
                ImageIO.read(getClass().getResource("/res/correr_izquierda2.png")),
                ImageIO.read(getClass().getResource("/res/correr_izquierda3.png")),
            };
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void actualizar(boolean izquierda, boolean derecha, boolean saltar) {
        // Movimiento horizontal
        if (izquierda && !derecha) {
            velXActual -= aceleracion;
            if (velXActual < -velXMax) velXActual = -velXMax;
            mirandoDerecha = false;
            corriendo = true;
        } else if (derecha && !izquierda) {
            velXActual += aceleracion;
            if (velXActual > velXMax) velXActual = velXMax;
            mirandoDerecha = true;
            corriendo = true;
        } else {
            // Desaceleración
            if (velXActual > 0) {
                velXActual -= desaceleracion;
                if (velXActual < 0) velXActual = 0;
            } else if (velXActual < 0) {
                velXActual += desaceleracion;
                if (velXActual > 0) velXActual = 0;
            }
            corriendo = false;
        }

        x += (int) Math.round(velXActual);

        // Salto
        if (saltar && !enElAire) {
            velY = impulsoSalto;
            enElAire = true;
            saltando = true;
        }

        // Física vertical
        velY += gravedad;
        y += (int) Math.round(velY);

        // Piso (suelo fijo en y=400)
        if (y >= 400) {
            y = 400;
            velY = 0;
            enElAire = false;
            saltando = false;
        }

        // Actualización animación
        if (corriendo || saltando) {
            contadorFrames++;
            if (contadorFrames >= maxFramesPorImagen) {
                contadorFrames = 0;
                frameActual++;
                if (mirandoDerecha && frameActual >= correrDerecha.length) {
                    frameActual = 0;
                }
                if (!mirandoDerecha && frameActual >= correrIzquierda.length) {
                    frameActual = 0;
                }
            }
        } else {
            frameActual = 0;
            contadorFrames = 0;
        }
    }

    public void dibujar(Graphics g, int cameraX) {
        Image imagenDibujar;

        if (saltando) {
            imagenDibujar = mirandoDerecha ? spriteSaltando : spriteSaltandoIZQ;
        } else if (corriendo) {
            imagenDibujar = mirandoDerecha ? correrDerecha[frameActual] : correrIzquierda[frameActual];
        } else {
            imagenDibujar = mirandoDerecha ? spriteQuieto : spriteQuietoIZQ;
        }

        if (imagenDibujar != null) {
            // Aplica desplazamiento de cámara en X
            g.drawImage(imagenDibujar, x - cameraX, y, width, height, null);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }
}
