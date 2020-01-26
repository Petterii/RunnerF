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

    public static final String BACKGROUND_MENU = "art/background/countryside.png";
    public static final String PLAYER_BALL = "art/objects/ball1.png";
    public static final String TEXTURE_CRATE = "art/objects/obj_crate001.png";
    public static final String TEXTURE_PLATFORM1 = "art/objects/platform1.png";
    public static final String BOX_BREAKING = "sound/box_smashed.ogg";
    public static final String SOUND_BALL_ROLLING = "sound/ball_loop.ogg";
    public static final String SOUND_JUMP = "sound/jump.wav";
    public static final String SOUND_LANDING = "sound/ball_landing.wav";
    public static final String MUSIC_LOOP = "sound/music_piano_loop.mp3";
    public static final String SOUND_SPEEDUP = "sound/speedup.wav";
    public static final String TEXTURE_CLICKTOGO = "art/objects/click_to_go.png";
    public static final String TEXTURE_BACKGROUNDENDLESS = "art/objects/full_background.png";

  public static final String TEXTURE_EXPLOSION = "art/objects/explosion.png";



}
