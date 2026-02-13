package Actores;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorRoca extends Actor {
    private Texture tRoca;

    public Boolean isAlive() {
        return isAlive;
    }

    public void setAlive(Boolean alive) {
        isAlive = alive;
    }

    private Boolean isAlive;


    public Boolean getVida() {
        return vida;
    }

    public void setVida(Boolean vida) {
        this.vida = vida;
    }

    private Boolean vida;

    public ActorRoca(Texture tRoca) {
        this.tRoca = tRoca;
        setSize(tRoca.getWidth(), tRoca.getHeight());
        this.isAlive = true;
    }

    @Override
    public void act(float delta) {
        if (isAlive) {

            setX(getX() - 250 * delta);
        } else {
        }
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(tRoca, getX(), getY());
    }
}
