package ui;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;

public class AudioPlayer {
    private Clip clip;

    public void reproducirMusicaFondo(String ruta) {
        try {
            URL sonidoURL = getClass().getResource(ruta);
            if (sonidoURL == null) {
                System.err.println("Archivo de audio no encontrado: " + ruta);
                return;
            }

            AudioInputStream audioInput = AudioSystem.getAudioInputStream(sonidoURL);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void detenerMusica() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void setVolume(float volumen) {
        if (clip != null) {
            FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float db = (float) (Math.log10(volumen) * 20);
            control.setValue(db);
        }
    }
}
