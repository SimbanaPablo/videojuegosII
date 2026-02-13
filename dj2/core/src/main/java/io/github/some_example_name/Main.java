package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    private static AssetManager manager;

    public static AssetManager getManager() {
        return manager;
    }

    @Override
    public void create() {
        manager =  new AssetManager();
        manager.load("vamo.png",Texture.class);
        manager.load("roca.png",Texture.class);
        manager.load("piso.png",Texture.class);
        manager.finishLoading();
        setScreen(new GameScreen(this));

    }

}
