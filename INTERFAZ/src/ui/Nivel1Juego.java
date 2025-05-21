package ui;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Nivel1Juego extends JPanel {

    private BufferedImage fondo;
    private BufferedImage plataformaImg;
    private ArrayList<Plataforma> plataformas;
    
 
    public Nivel1Juego() {
        cargarImagenes();
        inicializarPlataformas();
        setFocusable(true);
        requestFocus();
    }

    private void cargarImagenes() {
        try {
            fondo = ImageIO.read(getClass().getResource("/res/level1.SF.png")); // fondo del nivel
            plataformaImg = ImageIO.read(getClass().getResource("/res/e72cd228-b6d5-4a3b-b8ba-66cd34be4e8b.png")); // imagen plataforma
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void inicializarPlataformas() {
        plataformas = new ArrayList<>();

        // Coordenadas ejemplo de plataformas
        plataformas.add(new Plataforma(100, 400, plataformaImg));
        plataformas.add(new Plataforma(300, 350, plataformaImg));
        plataformas.add(new Plataforma(500, 300, plataformaImg));

        // Puedes seguir agregando más plataformas aquí
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), null);

        // Dibujar todas las plataformas
        for (Plataforma p : plataformas) {
            p.dibujar(g);
        }
    }
}
