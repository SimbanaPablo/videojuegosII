package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

import Actores.ActorJugador;
import Actores.ActorRoca;

public class MainGameScreen extends BaseScreen {
    private Stage stage; // escenario//
    private ActorJugador jugador;
    private Texture texturaJugador;
    private ActorRoca obstaculo;
    private Texture texturaObstaculo;

    public MainGameScreen(Main game) {
        super(game);
        this.texturaJugador = new Texture("vamo.png");
        this.texturaObstaculo = new Texture("roca.png");
    }

    @Override
    public void show() {
        //libreria que se encarga de trabajar esenarios y actores
        stage = new Stage();
        jugador = new ActorJugador(texturaJugador);
        stage.addActor(jugador);
        obstaculo = new ActorRoca(texturaObstaculo);
        stage.addActor(obstaculo);
        jugador.setPosition(10, 100);
        obstaculo.setPosition(400, 85);
    }

    @Override
    public void hide() {
        stage.dispose();
        // lo pones aqui porque cuando cambia de pantalla
    }

    @Override
    public void dispose() {
        texturaJugador.dispose();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.30f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(); //actualiza los elementos
        stage.draw();
        comprobarColisiones();
    }

    private void comprobarColisiones() {
        if (jugador.isAlive() && jugador.getX() + jugador.getWidth() > obstaculo.getX()) {
            System.out.println("Colision Detectada");
            jugador.setAlive(false);
            obstaculo.setAlive(false);
        }

    }
}

