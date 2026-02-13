package io.github.some_example_name.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Dino {
    private Texture texture;
    private float x, y, velocityY;
    private final float GRAVITY = -20f;
    private final float GROUND = 50;
    private Rectangle bounds;
    private Sprite sprite;

    public Dino() {
        texture = new Texture("dino.png");
        x = 100;
        y = GROUND;
        bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && y == GROUND)
            velocityY = 500;

        velocityY += GRAVITY;
        y += velocityY * delta;

        if (y < GROUND) {
            y = GROUND;
            velocityY = 0;
        }

        bounds.setPosition(x, y);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void reset() {
        y = GROUND;
        velocityY = 0;
    }

    public void dispose() {
        texture.dispose();
    }
}
