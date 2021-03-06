package com.firstrunner.game;

public class Globals {

    public static final boolean DEBUGGER_BOX2D_ENABLED = false;

    // box2d converting 1m to 1cm per pixel
    public static final int PPM = 100;

    // variables to tweek game
    public static final float PLATFORM_WIDTH = 300f;
    public static final float PLATFORM_JUMPTRIGGER = 1f;

    public static final float BALLFORCEUPP = 200f;
    public static final float BALL_XVELOCITYSPEED = -2f;

    // files textures

    public static final String BACKGROUND_MENU = "art/background/countryside.png";
    public static final String PLAYER_BALL = "art/objects/ball1.png";
    public static final String TEXTURE_CRATE = "art/objects/obj_crate001.png";
    // public static final String TEXTURE_PLATFORM1 = "art/objects/platform1.png";
    public static final String TEXTURE_PLATFORM1 = "art/background/bg1_2.png";
    public static final String BOX_BREAKING = "sound/box_smashed.ogg";
    public static final String SOUND_BALL_ROLLING = "sound/rolling.wav";
    public static final String SOUND_JUMP = "sound/jump.wav";
    public static final String SOUND_LANDING = "sound/ball_landing.wav";
    public static final String MUSIC_LOOP = "sound/music_piano_loop.mp3";
    public static final String SOUND_SPEEDUP = "sound/speedup.wav";
    public static final String TEXTURE_CLICKTOGO = "art/objects/click_to_go.png";
    public static final String TEXTURE_BACKGROUNDENDLESS = "art/background/bg1_1.png";
    public static final String TEXTURE_RETRYBUTTON = "art/objects/retrybutton.png";
    public static final String TEXTURE_EXPLOSION = "art/objects/explosion.png";
    public static final String TEXTURE_MAINMENU = "art/background/main_menu.png";
    public static final String TEXTURE_ARROW = "art/objects/ornamented_arrow.png";
    public static final String TEXTURE_BOUNCINGBALL = "art/objects/poisonblob.png";
    public static final String TEXTURE_DOUBLEENEMY = "art/objects/enemy.png";
    public static final String FONT_ITALIC = "fonts/xcelsion_italic.ttf";
    public static final String FONT_PNG = "fonts/calibri_regular_24.PNG";

    public static final String TEXTURE_TITLETEXT = "art/background/titletext.png";

    public static final float TEXTURE_BOUNCINGBALL_WIDTH = 64;
    public static final float TEXTURE_BOUNCINGBALL_HEIGHT = 64;

    // Contact listener bits
    public static final short PLAYER_BIT = 2;
    public static final short GROUND_BIT = 4;
    public static final short ITEM_BIT = 8;
    public static final short LEVEL_END = 16;
    public static final short TRIGGER_SPAWN = 32;
    public static final short WALL_DESTROYER = 64;

}
