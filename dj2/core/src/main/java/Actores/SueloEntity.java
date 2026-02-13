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

public class SueloEntity extends Actor {

    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;
    private final float GROUND_WIDTH = 20.0f; // Ancho del suelo en metros
    private final float GROUND_HEIGHT = 1.0f; // Alto del suelo en metros
    public SueloEntity(Texture texture, World world, Vector2 position) {
        this.texture = texture;
        this.world = world;

        BodyDef sueloDef = new BodyDef();
        sueloDef.position.set(position);
        sueloDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(sueloDef);
        //System.out.println("Suelo: " + body.getPosition().x + ", " + body.getPosition().y);

        PolygonShape sueloShape = new PolygonShape();
        // setAsBox usa semi-ancho y semi-alto, así que usamos GROUND_WIDTH/2 y GROUND_HEIGHT/2
        sueloShape.setAsBox(GROUND_WIDTH / 2, GROUND_HEIGHT / 2);
        fixture = body.createFixture(sueloShape, 0); // La densidad 0 es para cuerpos static
        fixture.setUserData("suelo");
        sueloShape.dispose();

        // Establecer el tamaño del actor en píxeles
        setSize(GROUND_WIDTH * PIXELS_IN_METTERS, GROUND_HEIGHT * PIXELS_IN_METTERS);
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

    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

}
