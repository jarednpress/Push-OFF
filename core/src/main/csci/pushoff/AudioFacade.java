package csci.pushoff;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class AudioFacade {
    private final Music menuMusic;
    public  AudioFacade(){
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("Pushoff_Theme-One.ogg"));
    }

    public void play(){

        menuMusic.setLooping(true);
        menuMusic.play();
    }
    public void setVolume(float volume){
        menuMusic.setVolume(volume);
    }
    public float getVolume(){
        return menuMusic.getVolume();
    }
    public void stop(){
        menuMusic.stop();
    }
}
