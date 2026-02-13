package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Box2DScreen extends BaseScreen {

    private World world;
    private Box2DDebugRenderer render;

    private OrthographicCamera camera;

    private Body jugadorBody, sueloBody, rocaBody;
    private Fixture jugadorFixture,sueloFixture, rocaFixture;

    private boolean colisionDetectada = false;
    // 🔹 Añadimos estos para dibujar el mensaje
    private SpriteBatch batch;
    private BitmapFont font;
    private boolean juegoTerminado = false; // 🔹 bandera para saber si se terminó el juego


    public Box2DScreen(Main Game) {
        super(Game);
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -10), true);
        render = new Box2DDebugRenderer();
        camera = new OrthographicCamera(32, 18);

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);
        font.getData().setScale(2f); // tamaño del texto

        // 🔹 Listener de colisiones
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                boolean jugadorConRoca =
                    (fixtureA == jugadorFixture && fixtureB == rocaFixture) ||
                        (fixtureA == rocaFixture && fixtureB == jugadorFixture);

                if (jugadorConRoca && !colisionDetectada) {
                    colisionDetectada = true;
                    detenerJugador();
                    detenerJugadorYTerminarJuego(); // 🔹 detener al jugador y terminar
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

        BodyDef jugadorDef = createDef();
        jugadorDef.position.set(-10, 0);
        jugadorBody = world.createBody(jugadorDef);
        PolygonShape jugadorShape = new PolygonShape();
        jugadorShape.setAsBox(1, 1);
        jugadorFixture = jugadorBody.createFixture(jugadorShape, 1);
        jugadorShape.dispose();

        // --- Suelo ---
        BodyDef sueloDef = new BodyDef();
        sueloDef.position.set(0, -5); // posición del suelo (y=0)
        sueloDef.type = BodyDef.BodyType.StaticBody; // el suelo no se mueve
        Body sueloBody = world.createBody(sueloDef);

        PolygonShape sueloShape = new PolygonShape();
        sueloShape.setAsBox(250, 0.5f); // 500 de ancho (250x2) y 1 de alto (0.5x2)

        sueloFixture = sueloBody.createFixture(sueloShape, 1);
        sueloShape.dispose();

        BodyDef obstaculoDef = obstaculoDef(5);
        rocaBody = world.createBody(obstaculoDef);
        Vector2[] vertices = new Vector2[3];
        vertices[0]= new Vector2(-0.5f, -0.5f);
        vertices[1]= new Vector2(0.5f, -0.5f);
        vertices[2]= new Vector2(0f, 0.5f);

        PolygonShape obstaculoShape = new PolygonShape();
        obstaculoShape.set(vertices);

        rocaFixture = rocaBody.createFixture(obstaculoShape,1);
        obstaculoShape.dispose();

    }


    @Override
    public void dispose() {
        world.destroyBody(jugadorBody);
        world.destroyBody(sueloBody);
        world.dispose();
        render.dispose();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!colisionDetectada) {
            // 🔹 Solo se mueve mientras no haya colisión
            jugadorBody.setLinearVelocity(2, jugadorBody.getLinearVelocity().y);
        }

        if (Gdx.input.justTouched() && !colisionDetectada) {
            saltar();
        }

        world.step(delta, 6, 2);
        camera.update();
        render.render(world, camera.combined);

        // 🔹 Si hubo colisión, mostramos el mensaje en pantalla
        if (colisionDetectada) {
            batch.begin();
            font.draw(batch, "GAME OVER", Gdx.graphics.getWidth() / 2f - 80, Gdx.graphics.getHeight() / 2f);
            batch.end();
        }
    }

    private BodyDef createDef() {
        BodyDef def = new BodyDef();
        def.position.set(0, -3);
        def.type = BodyDef.BodyType.DynamicBody;
        return def;
    }
    private BodyDef obstaculoDef(float x){
        BodyDef def = new BodyDef();
        def.position.set(x, -4f);
        return def;
    }
    private void saltar(){
        Vector2 position = jugadorBody.getPosition();
        jugadorBody.applyLinearImpulse(0,7, position.x, position.y, true);
    }
    // 🔹 Método para detener el movimiento y terminar el juego
    private void detenerJugadorYTerminarJuego() {
        jugadorBody.setLinearVelocity(0, 0);
        jugadorBody.setAwake(false); // lo “duerme”, sin movimiento físico
        juegoTerminado = true;

        System.out.println("Colisión detectada — Juego terminado.");
    }
    // 🔹 Método para detener al jugador por completo
    private void detenerJugador() {
        jugadorBody.setLinearVelocity(0, 0);
        jugadorBody.setAngularVelocity(0);
        jugadorBody.setAwake(false); // lo “duerme” físicamente
    }
}
