package ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Plataforma {
    private int x, y, width, height;
    private static BufferedImage[] imagenes;
    private int tipoImagen;

    static {
        imagenes = new BufferedImage[6];
        try {
            imagenes[0] = ImageIO.read(Plataforma.class.getResource("/res/Plataforma1.png"));
            imagenes[1] = ImageIO.read(Plataforma.class.getResource("/res/Plataforma2.png"));
            imagenes[2] = ImageIO.read(Plataforma.class.getResource("/res/Plataforma3.png"));
            imagenes[3] = ImageIO.read(Plataforma.class.getResource("/res/Plataforma4.png"));
            imagenes[4] = ImageIO.read(Plataforma.class.getResource("/res/Plataforma5.png"));
            imagenes[5] = ImageIO.read(Plataforma.class.getResource("/res/Plataforma6.png"));
        } catch (IOException e) {
            System.err.println("Error al cargar imágenes de plataforma:");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Ruta de imagen no encontrada:");
            e.printStackTrace();
        }
    }

    public Plataforma(int x, int y, int width, int height, int tipoImagen) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.tipoImagen = tipoImagen;
    }

    public Plataforma(int x, int y, int tipoImagen) {
        this(x, y, 100, 20, tipoImagen);
    }

    public void dibujar(Graphics g, int cameraX) {
        BufferedImage imagen = null;
        if (tipoImagen >= 0 && tipoImagen < imagenes.length) {
            imagen = imagenes[tipoImagen];
        }
        if (imagen != null) {
            g.drawImage(imagen, x - cameraX, y, width, height, null);
        } else {
            g.setColor(Color.RED);
            g.fillRect(x - cameraX, y, width, height);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // Getters directos
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    // Método para saber si un rectángulo (ejemplo jugador) colisiona con esta plataforma
    public boolean colisionaCon(Rectangle r) {
        return this.getBounds().intersects(r);
    }

    // Método para verificar si el jugador está justo encima (para "pararse" en la plataforma)
    // El rectángulo jugador debe estar dentro del ancho de la plataforma, y caer sobre ella desde arriba
    public boolean jugadorEncima(Rectangle jugador, int velY) {
        // Verifica que el jugador intersecta horizontalmente con la plataforma
        boolean dentroX = jugador.x + jugador.width > x && jugador.x < x + width;
        // Verifica que la base del jugador esté justo encima y que en el próximo movimiento vertical toque la plataforma
        boolean baseCerca = jugador.y + jugador.height <= y && jugador.y + jugador.height + velY >= y;
        return dentroX && baseCerca;
    }
}
