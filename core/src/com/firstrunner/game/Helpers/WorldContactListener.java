package com.firstrunner.game.Helpers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.firstrunner.game.Objects.Items;
import com.firstrunner.game.Objects.Player;

import static com.firstrunner.game.Globals.*;


public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case ITEM_BIT | PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == PLAYER_BIT) {
                    ((Player) fixA.getUserData()).collition(((Items) fixB.getUserData()).collition());
                }else
                    ((Player) fixB.getUserData()).collition(((Items) fixA.getUserData()).collition());
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
}
