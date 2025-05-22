package ui;

import javax.swing.*;

public class GameWindow extends JFrame {

    private AudioPlayer audioPlayer;

    public GameWindow() {
        setTitle("Juego Gótico");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080, 640);
        setResizable(false);
        setLocationRelativeTo(null);

        audioPlayer = new AudioPlayer();
        audioPlayer.reproducirMusicaFondo("/res/Audio.wav");

        setContentPane(new MainMenu(this, audioPlayer)); // <-- constructor corregido

        setVisible(true);
    }

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }
}
