package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;

import Actores.ObstacleEntity;
import Actores.PlayerEntity;
import Actores.SueloEntity;

public class GameScreen  extends  BaseScreen{

    private Stage stage;
    private World world;

    private PlayerEntity player;
    private SueloEntity suelo;
    private ObstacleEntity obstacle;

    private WorldContactListener contactListener;
    private boolean jumpRequested = false;
    // Para el mensaje de GAME OVER
    private BitmapFont font;
    private SpriteBatch spriteBatch;
    public static boolean isGameOver = false;
    public GameScreen(Main Game) {
        super(Game);
        stage = new Stage(new FillViewport(640,360));
        world = new World(new Vector2(0,-10),true); // Gravedad hacia abajo
        contactListener = new WorldContactListener(); // Inicializa el listener
        world.setContactListener(contactListener); // Asigna el listener al mundo
        // Inicializar para el texto
        font = new BitmapFont();
        spriteBatch = new SpriteBatch();
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
        font.dispose(); // Importante liberar los recursos del font
        spriteBatch.dispose(); // Importante liberar los recursos del spriteBatch
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f,0.5f,0.5f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!isGameOver) { // Solo actualiza y dibuja si el juego no ha terminado
            // Lógica de salto
            if (Gdx.input.isTouched() && contactListener.isPlayerOnGround() && !jumpRequested) {
                player.jump();
                jumpRequested = true;
            } else if (!Gdx.input.isTouched()) {
                jumpRequested = false;
            }

            // Mueve el obstáculo
            obstacle.moveLeft(); // Agregamos esta llamada

            world.step(delta, 6, 2);
            stage.draw();
            stage.act();
        } else {
            // Si es GAME OVER, dibuja el mensaje
            spriteBatch.begin();
            font.getData().setScale(3); // Aumentar el tamaño del texto
            font.draw(
                spriteBatch,
                "GAME OVER",
                (Gdx.graphics.getWidth() - font.getRegion().getRegionWidth()) / 2, // Centrar X
                Gdx.graphics.getHeight() / 2 //Centrar Y
            );
            spriteBatch.end();
        }
    }

    @Override
    public void show() {
        // Player (aproximadamente al centro horizontal, un poco por encima del suelo)
        player = new PlayerEntity(Main.getManager().get("vamo.png", Texture.class), world, new Vector2(2f, 1.5f));
        stage.addActor(player);

        // Obstable
        // Obstacle (a la derecha del jugador, sobre el suelo)
        obstacle = new ObstacleEntity(Main.getManager().get("roca.png", Texture.class), world, new Vector2(10f, 0.3f));
        stage.addActor(obstacle);

        // Suelo
        // Suelo (se extiende a lo largo de la parte inferior)
        // El ancho de 10 unidades de Box2D * 2 (porque setAsBox usa semi-ancho)
        // La posición y del suelo está centrada en 0, lo que significa que la parte superior está en 0.5f
        suelo = new SueloEntity(Main.getManager().get("piso.png", Texture.class), world, new Vector2(5f, 0f));
        stage.addActor(suelo);
    }

    @Override
    public void hide() {
        player.detach();
        player.remove();
        obstacle.detach();
        obstacle.remove();
        suelo.detach();
        suelo.remove();
    }
}
