package com.firstrunner.game;

public class Globals {

    // box2d converting 1m to 1cm per pixel
    public static final int PPM = 100;

    // Contact listener bits
    public static final short PLAYER_BIT = 2;
    public static final short GROUND_BIT = 4;
    public static final short ITEM_BIT = 8;
    public static final short LEVEL_END = 16;
    public static final short TRIGGER_SPAWN = 32;
    public static final short WALL_DESTROYER = 64;



    // files textures

    public static final String BACKGROUND_MENU = "countryside.png";
    public static final String PLAYER_BALL = "ball.png";
    public static final String BOX_BREAKING = "sound/box_smashed.ogg";




}
