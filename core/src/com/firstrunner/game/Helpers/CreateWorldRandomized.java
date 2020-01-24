package com.firstrunner.game.Helpers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.firstrunner.game.Firstrunner;
import com.firstrunner.game.Objects.SkullBox;
import com.firstrunner.game.Screens.GameScreen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.firstrunner.game.Globals.GROUND_BIT;
import static com.firstrunner.game.Globals.PPM;

public class CreateWorldRandomized {

    private static World world;
    private Array<GroundPlatform> groundPlatforms;
    private static Array<SkullBox> objects;
    private GameScreen screen;
    private static boolean firstPlatform;
    private static float playerX;
    private static boolean spawnNewPlat;
    private static float chunk;

    public CreateWorldRandomized(GameScreen screen) {
    this.screen = screen;
    this.world = screen.getWorld();
    this.playerX = screen.getPlayerX();
    //this.chunkNr = 1;
    firstPlatform = true;
    secondPlatform = false;
    groundPlatforms = new Array<>();
    objects = new Array<>();
    spawnNewPlat = false;
    this.chunk = 0;
    spawnPlatform();
    }

    // private int chunkNr;

    public  void spawnPlatform() {
        int platformX;
        platformX = 400;
        if (firstPlatform) {
            firstPlatform = false;
            groundPlatforms.add(new GroundPlatform(world,chunk));
            objects.add(new SkullBox(world, 250f / PPM, 50f / PPM));
        }else {
            groundPlatforms.add(new GroundPlatform(world, chunk));
            //objects.add(new SkullBox(screen, (platformX+150f) / PPM, 50f / PPM));
        }
        chunk = chunk + 1;

    }

    public static void spawnNewPlatform() {
        spawnNewPlat = true;
    }

    public World getWorld(){
        return world;
    }

    private float speed;

    public void update(float delta){
        for (GroundPlatform ground : groundPlatforms) {
            ground.update(delta);
        }

        for (SkullBox object : objects) {
            object.update(delta);
        }

        if (spawnNewPlat) {
            spawnNewPlat =false;
            spawnPlatform();
        }

       removeGrounds();
    }

    private boolean secondPlatform;

    private void removeGrounds() {
        for (int i = 0; i < groundPlatforms.size; i++) {
            if (groundPlatforms.get(i).isDestroyed) {
                groundPlatforms.removeIndex(i);
            }
        }
    }
}
