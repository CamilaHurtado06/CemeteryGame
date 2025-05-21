package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Plataforma {
    private int x, y;
    private BufferedImage imagen;

    public Plataforma(int x, int y, BufferedImage imagen) {
        this.x = x;
        this.y = y;
        this.imagen = imagen;
    }

    private BufferedImage cargarImagen(int tipo) {
        String ruta = "/res/";
        switch (tipo) {
            case 1:
                ruta += "Plataforma1.png";
                break;
            case 2:
                ruta += "Plataforma2.png";
                break;
            default:
                ruta += "Plataforma1.png"; // por defecto
        }

        try {
            return ImageIO.read(getClass().getResource(ruta));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void dibujar(Graphics g) {
        if (imagen != null) {
            g.drawImage(imagen, x, y, null);
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getAncho() { return imagen != null ? imagen.getWidth() : 0; }
    public int getAlto() { return imagen != null ? imagen.getHeight() : 0; }
}
