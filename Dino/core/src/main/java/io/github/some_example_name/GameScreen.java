package io.github.some_example_name;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.Iterator;

import io.github.some_example_name.entities.Cactus;
import io.github.some_example_name.entities.Dino;

public class GameScreen implements Screen {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture background;
    private Dino dino;
    private ArrayList<Cactus> cactusList;
    private float cactusTimer;

    @Override
    public void show() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        background = new Texture("background-star.png");
        dino = new Dino();
        cactusList = new ArrayList<>();
        cactusTimer = 0;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Lógica
        dino.update(delta);

        cactusTimer += delta;
        if (cactusTimer > 1.5f) {
            cactusList.add(new Cactus());
            cactusTimer = 0;
        }

        Iterator<Cactus> iter = cactusList.iterator();
        while (iter.hasNext()) {
            Cactus c = iter.next();
            c.update(delta);
            if (c.isOffScreen()) iter.remove();

            // Colisión simple
            if (c.collidesWith(dino)) {
                System.out.println("💀 GAME OVER 💀");
                dino.reset();
                cactusList.clear();
                break;
            }
        }

        // Dibujo
        batch.begin();
        batch.draw(background, 0, 0);
        dino.draw(batch);
        for (Cactus c : cactusList) c.draw(batch);
        batch.end();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        batch.dispose();
        background.dispose();
    }
}
