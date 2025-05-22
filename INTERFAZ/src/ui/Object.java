package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Object {
    private int x, y, width, height;
    private Image sprite;
    private boolean activo = true;  // true si no ha sido recolectado

    public Object(int x, int y, int width, int height, String rutaImagen) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        try {
            sprite = ImageIO.read(getClass().getResource(rutaImagen));
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public boolean estaActivo() {
        return activo;
    }

    public void recolectar() {
        activo = false;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void dibujar(Graphics g, int cameraX) {
        if (activo && sprite != null) {
            g.drawImage(sprite, x - cameraX, y, width, height, null);
        }
    }
}
