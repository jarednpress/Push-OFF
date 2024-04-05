package csci.pushoff.characters;
//import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
//import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.utils.ScreenUtils;
//import csci.pushoff.GdxGameMain;
//import csci.pushoff.screens.stages.*;
public class player {
    private String playerName = "";
    private  String characterName = "";
    private int friction = 100;
    private int knockback = 10;

    private int sheildStrength = 20;
    private  Texture texture;

//    public player(String name, int friction, int knockback, ){
//        this.name
//    }
    public void  setPlayerName(String name){this.playerName = name;}
    public String getPlayerName(){return this.playerName;}
    public void  setCharacterName(String name){this.characterName = name;}
    public String getCharacterName(){return this.characterName;}
    public void setFriction(int friction){this.friction = friction;}
    public int getFriction(){return this.friction;}
    public void setKnockback(int knockback){this.knockback = knockback;}
    public int getKnockback(){return this.knockback;}

    public void setTexture(Texture texture){this.texture = texture;}
    public Texture getTexture(){return this.texture;}


}
