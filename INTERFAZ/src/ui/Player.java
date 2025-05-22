package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Player {
    private int x, y;
    private int width, height;

    private double velXActual = 0;
    private final double velXMax = 5;
    private final double aceleracion = 0.5;
    private final double desaceleracion = 0.3;

    private double velY = 0;
    private final double gravedad = 1.5;
    private final double impulsoSalto = -25;
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

    private ArrayList<Plataforma> plataformas;

    private boolean perdio = false; // <-- NUEVO FLAG

    // Constructor que recibe lista de plataformas (debe ser no nula)
    public Player(int x, int y, int width, int height, ArrayList<Plataforma> plataformas) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.plataformas = (plataformas != null) ? plataformas : new ArrayList<>();

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
        if (perdio) return; // Si ya perdió, no se actualiza más

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

        if (saltar && !enElAire) {
            velY = impulsoSalto;
            enElAire = true;
            saltando = true;
        }

        velY += gravedad;
        y += (int) Math.round(velY);

        checarColisiones();

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

    // Método modificado para manejar colisiones con plataformas y perder al caer
    private void checarColisiones() {
        Rectangle boundsJugador = getBounds();
        enElAire = true;

        if (plataformas == null) {
            plataformas = new ArrayList<>();
        }

        for (Plataforma plataforma : plataformas) {
            Rectangle boundsPlat = plataforma.getBounds();

            if (boundsJugador.intersects(boundsPlat)) {
                Rectangle interseccion = boundsJugador.intersection(boundsPlat);

                if (interseccion.height < interseccion.width) {
                    if (y < plataforma.getY()) {
                        y = plataforma.getY() - height;
                        velY = 0;
                        enElAire = false;
                        saltando = false;
                    } else {
                        y = plataforma.getY() + plataforma.getHeight();
                        velY = 0;
                    }
                } else {
                    if (x < plataforma.getX()) {
                        x = plataforma.getX() - width;
                    } else {
                        x = plataforma.getX() + plataforma.getWidth();
                    }
                    velXActual = 0;
                }

                boundsJugador = getBounds(); // actualizar
            }
        }

        // Piso fijo en y=550 (suelo del mundo)
        if (enElAire && y >= 550) {
            y = 550;
            velY = 0;
            enElAire = false;
            saltando = false;
            perdio = true; // <-- AQUÍ MARCA QUE PERDIÓ
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
            g.drawImage(imagenDibujar, x - cameraX, y, width, height, null);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // NUEVO MÉTODO PARA CONSULTAR SI PERDIÓ
    public boolean haPerdido() {
        return perdio;
    }

    // Getters y setters
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
