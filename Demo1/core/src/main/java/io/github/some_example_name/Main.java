package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import sun.security.mscapi.CKeyPairGenerator;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private Audio audio = Gdx.audio;


    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("fondo.jpg");
    }

    @Override
    public void render() {
        //ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        ScreenUtils.clear(0, 0.15f, 0, 1f);
        batch.begin();
        batch.draw(image, 300 ,25 );
        batch.end();
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            System.out.println("Se presiono la tecla A");
        }

    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
