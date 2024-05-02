package csci.pushoff;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class AudioFacade {
    public void play(){
        Music menuMusic = Gdx.audio.newMusic(Gdx.files.internal("Pushoff_Theme-One.ogg"));
        menuMusic.setLooping(true);
        menuMusic.play();
    }
}
