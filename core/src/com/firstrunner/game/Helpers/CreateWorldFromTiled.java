package com.firstrunner.game.Helpers;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.firstrunner.game.Firstrunner;
import com.firstrunner.game.Objects.SkullBox;
import com.firstrunner.game.Screens.GameScreen;

import static com.firstrunner.game.Globals.*;

public class CreateWorldFromTiled {

    public CreateWorldFromTiled(GameScreen screen) {
        TiledMap map = screen.getMap();
        World world = screen.getWorld();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;


        // ground objects from Tiled
        for (MapObject object :
                map.getLayers().get("ground").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) /PPM,(rect.getY() + rect.getHeight()/2) /PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth()/2)/PPM,(rect.getHeight()/2)/PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = GROUND_BIT;
            body.createFixture(fdef);

        }
        // objects in world
        for (MapObject object :
                map.getLayers().get("objects").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            screen.addItem(new SkullBox(world,rect.getX()/PPM,rect.getY()/PPM));
            /*
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) /PPM,(rect.getY() + rect.getHeight()/2) /PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth()/2)/PPM,(rect.getHeight()/2)/PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = ITEM_BIT;
            body.createFixture(fdef);
*/
        }



        // The tree at the end. AKA finish line.
        for (MapObject object :
                map.getLayers().get("treeend").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) /PPM,(rect.getY() + rect.getHeight()/2) /PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth()/2)/PPM,(rect.getHeight()/2)/PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = LEVEL_END;
            body.createFixture(fdef);

        }

        shape.dispose();
    }
}
