package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;

// ContactListener para manejar colisiones
public class WorldContactListener implements ContactListener {
    private boolean playerOnGround = false;

    public boolean isPlayerOnGround() {
        return playerOnGround;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        // Identificar qué fixtures colisionaron
        boolean playerVsSuelo = (fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("suelo")) ||
            (fixtureA.getUserData().equals("suelo") && fixtureB.getUserData().equals("player"));
        boolean playerVsObstacle = (fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("obstacle")) ||
            (fixtureA.getUserData().equals("obstacle") && fixtureB.getUserData().equals("player"));


        if (playerVsSuelo) {
            playerOnGround = true;
            Gdx.app.log("Contact", "Player touched ground");
        }

        if (playerVsObstacle) {
            Gdx.app.log("Contact", "Player touched obstacle - GAME OVER!");
            GameScreen.isGameOver = true; // Establecer GAME OVER
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        boolean playerVsSuelo = (fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("suelo")) ||
            (fixtureA.getUserData().equals("suelo") && fixtureB.getUserData().equals("player"));

        if (playerVsSuelo) {
            playerOnGround = false;
            Gdx.app.log("Contact", "Player left ground");
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
