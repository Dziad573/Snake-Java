import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class PlaySound{
    String path = "src/sounds/music.wav";

    public void playSound(String soundFileName){
        try {
            File soundFile = new File(soundFileName);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            if (soundFileName.equals(path))
            {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }else{
                clip.loop(0);
            }

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}