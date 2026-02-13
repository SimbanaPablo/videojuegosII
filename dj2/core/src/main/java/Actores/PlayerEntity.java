package Actores;

import static io.github.some_example_name.Constants.PIXELS_IN_METTERS;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PlayerEntity extends Actor {

    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;
    private final float PLAYER_WIDTH = 0.8f; // Ancho del jugador en metros
    private final float PLAYER_HEIGHT = 1.2f; // Alto del jugador en metros
    private final float JUMP_FORCE = 6.5f; // Fuerza de salto

    public PlayerEntity(Texture texture, World world, Vector2 position) {
        this.texture = texture;
        this.world = world;

        BodyDef jugadorDef = new BodyDef();
        jugadorDef.position.set(position);
        jugadorDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(jugadorDef);
        body.setFixedRotation(true); // Evita que el jugador gire
        //System.out.println("Player: " + body.getPosition().x + ", " + body.getPosition().y);

        PolygonShape jugadorShape = new PolygonShape();
        jugadorShape.setAsBox(PLAYER_WIDTH / 2, PLAYER_HEIGHT / 2); // setAsBox usa semi-ancho y semi-alto
        fixture = body.createFixture(jugadorShape, 1); // Densidad
        fixture.setUserData("player");
        jugadorShape.dispose();
        //Establecer el tamaño del actor en pixeles
        setSize(PLAYER_WIDTH * PIXELS_IN_METTERS, PLAYER_HEIGHT * PIXELS_IN_METTERS);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Actualizar la posición del actor con la posición del cuerpo de Box2D
        // Restar la mitad del tamaño para que la textura se dibuje desde la esquina inferior izquierda
        setPosition(
            body.getPosition().x * PIXELS_IN_METTERS - getWidth() / 2,
            body.getPosition().y * PIXELS_IN_METTERS - getHeight() / 2
        );
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }
    public void jump() {
        // Aplica una fuerza vertical al cuerpo del jugador
        body.applyLinearImpulse(new Vector2(0, JUMP_FORCE), body.getWorldCenter(), true);
    }
    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);

    }
}
