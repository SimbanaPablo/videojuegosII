package io.github.some_example_name.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Cactus {
    private Texture texture;
    private float x, y;
    private Rectangle bounds;

    public Cactus() {
        texture = new Texture("rock.png");
        x = 800;
        y = 50;
        bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    public void update(float delta) {
        x -= 300 * delta;
        bounds.setPosition(x, y);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public boolean isOffScreen() {
        return x + texture.getWidth() < 0;
    }

    public boolean collidesWith(Dino dino) {
        return bounds.overlaps(dino.getBounds());
    }

    public void dispose() {
        texture.dispose();
    }
}
