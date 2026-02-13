package Actores;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorJugador extends Actor {
private Texture tJugador;

    public Boolean isAlive() {
        return alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }

    private Boolean alive;

    public ActorJugador(Texture tJugador) {
        this.tJugador = tJugador;
        setSize(tJugador.getWidth(),tJugador.getHeight());
        this.alive = true;
    }

    @Override
    public void act(float delta) {
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
       batch.draw(tJugador, getX(),getY());
    }
}
