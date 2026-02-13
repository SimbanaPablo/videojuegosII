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

public class ObstacleEntity extends Actor {

    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;
    private final float OBSTACLE_SIZE = 1.0f; // Tamaño del obstáculo en metros (cuadrado)
    private final float MOVEMENT_SPEED = -2.0f; // Velocidad de movimiento hacia la izquierda en m/s

    public ObstacleEntity(Texture texture, World world, Vector2 position) {
        this.texture = texture;
        this.world = world;

        BodyDef obstacleDef = new BodyDef();
        obstacleDef.position.set(position);
        obstacleDef.type = BodyDef.BodyType.KinematicBody; // Si la roca no se mueve deber ser static
        body = world.createBody(obstacleDef);

        PolygonShape obstacleShape = new PolygonShape();
        obstacleShape.setAsBox(OBSTACLE_SIZE / 2, OBSTACLE_SIZE / 2); // setAsBox usa semi-ancho y semi-alto
        fixture = body.createFixture(obstacleShape, 1); // Densidad
        fixture.setUserData("obstacle");
        obstacleShape.dispose();
        // Establecer el tamaño del actor en píxeles
        setSize(OBSTACLE_SIZE * PIXELS_IN_METTERS, OBSTACLE_SIZE * PIXELS_IN_METTERS);
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
    public void moveLeft() {
        // Establece la velocidad lineal del cuerpo para moverlo a la izquierda
        body.setLinearVelocity(MOVEMENT_SPEED, body.getLinearVelocity().y);
    }
    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}
