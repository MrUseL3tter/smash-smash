package com.nullsys.smashsmash.alien;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.noobs2d.tweenengine.utils.DynamicAnimation;

public class AliensArt {

    public static Texture diabolic;
    public static DynamicAnimation diabolicAttack;
    public static DynamicAnimation diabolicHiding;
    public static DynamicAnimation diabolicWaiting;
    public static DynamicAnimation diabolicRising;
    public static DynamicAnimation diabolicSmashed;
    public static DynamicAnimation diabolicStunned;

    public static Texture fluff;
    public static DynamicAnimation fluffAttack;
    public static DynamicAnimation fluffHiding;
    public static DynamicAnimation fluffWaiting;
    public static DynamicAnimation fluffRising;
    public static DynamicAnimation fluffSmashed;
    public static DynamicAnimation fluffStunned;

    public static Texture golem;
    public static DynamicAnimation golemAttack;
    public static DynamicAnimation golemHiding;
    public static DynamicAnimation golemWaiting;
    public static DynamicAnimation golemRising;
    public static DynamicAnimation golemSmashed;
    public static DynamicAnimation golemStunned;

    public static Texture jelly;
    public static TextureRegion jellyFeShape;
    public static TextureRegion jellyFeWaitingEye;
    public static TextureRegion jellyFeWaitingMouth;
    public static TextureRegion jellyFeBlinkEye;
    public static TextureRegion jellyFeHitEye;
    public static TextureRegion jellyFeHitMouth;
    public static TextureRegion jellyShape;
    public static TextureRegion jellyWaitingEye;
    public static TextureRegion jellyWaitingMouth;
    public static TextureRegion jellyBlinkEye;
    public static TextureRegion jellyHitEye;
    public static TextureRegion jellyHitMouth;

    public static Texture ogre;
    public static DynamicAnimation ogreAttack;
    public static DynamicAnimation ogreHiding;
    public static DynamicAnimation ogreWaiting;
    public static DynamicAnimation ogreRising;
    public static DynamicAnimation ogreSmashed;
    public static DynamicAnimation ogreStunned;

    public static Texture sorcerer;
    public static DynamicAnimation sorcererAttack;
    public static DynamicAnimation sorcererHiding;
    public static DynamicAnimation sorcererWaiting;
    public static DynamicAnimation sorcererRising;
    public static DynamicAnimation sorcererSmashed;
    public static DynamicAnimation sorcererStunned;

    public static Texture tortoise;
    public static DynamicAnimation tortoiseAttack;
    public static DynamicAnimation tortoiseHiding;
    public static DynamicAnimation tortoiseWaiting;
    public static DynamicAnimation tortoiseRising;
    public static DynamicAnimation tortoiseSmashed;
    public static DynamicAnimation tortoiseStunned;

    public static void load(AssetManager assetManager) {
	assetManager.load("data/gfx/SAMPLE_ALIENS_DISSECTED.png", Texture.class);
	assetManager.load("data/gfx/SAMPLE_ALIENS_ALL.png", Texture.class);
    }

    public static void retrieve(AssetManager assetManager) {
	retrieveDiabolic(assetManager);
	retrieveFluff(assetManager);
	retrieveGolem(assetManager);
	retrieveJelly(assetManager);
	retrieveOgre(assetManager);
	retrieveSorcerer(assetManager);
	retrieveTortoise(assetManager);
    }

    public static void unloadTextures(AssetManager assetManager) {
    }

    private static void retrieveDiabolic(AssetManager assetManager) {
	diabolic = assetManager.get("data/gfx/SAMPLE_ALIENS_ALL.png", Texture.class);
	diabolicAttack = new DynamicAnimation(0.1f, new TextureRegion(diabolic, 205, 241, 162, 267));
	diabolicHiding = new DynamicAnimation(0.1f, new TextureRegion(diabolic, 205, 241, 162, 267));
	diabolicWaiting = new DynamicAnimation(0.1f, new TextureRegion(diabolic, 205, 241, 162, 267));
	diabolicRising = new DynamicAnimation(0.1f, new TextureRegion(diabolic, 205, 241, 162, 267));
	diabolicSmashed = new DynamicAnimation(0.1f, new TextureRegion(diabolic, 205, 241, 162, 267));
	diabolicStunned = new DynamicAnimation(0.1f, new TextureRegion(diabolic, 205, 241, 162, 267));
    }

    private static void retrieveFluff(AssetManager assetManager) {
	fluff = assetManager.get("data/gfx/SAMPLE_ALIENS_ALL.png", Texture.class);
	fluffAttack = new DynamicAnimation(0.1f, new TextureRegion(fluff, 174, 0, 223, 241));
	fluffHiding = new DynamicAnimation(0.1f, new TextureRegion(fluff, 174, 0, 223, 241));
	fluffWaiting = new DynamicAnimation(0.1f, new TextureRegion(fluff, 174, 0, 223, 241));
	fluffRising = new DynamicAnimation(0.1f, new TextureRegion(fluff, 174, 0, 223, 241));
	fluffSmashed = new DynamicAnimation(0.1f, new TextureRegion(fluff, 174, 0, 223, 241));
	fluffStunned = new DynamicAnimation(0.1f, new TextureRegion(fluff, 174, 0, 223, 241));
    }

    private static void retrieveGolem(AssetManager assetManager) {
	golem = assetManager.get("data/gfx/SAMPLE_ALIENS_ALL.png", Texture.class);
	golemAttack = new DynamicAnimation(0.1f, new TextureRegion(golem, 367, 240, 207, 239));
	golemHiding = new DynamicAnimation(0.1f, new TextureRegion(golem, 367, 240, 207, 239));
	golemWaiting = new DynamicAnimation(0.1f, new TextureRegion(golem, 367, 240, 207, 239));
	golemRising = new DynamicAnimation(0.1f, new TextureRegion(golem, 367, 240, 207, 239));
	golemSmashed = new DynamicAnimation(0.1f, new TextureRegion(golem, 367, 240, 207, 239));
	golemStunned = new DynamicAnimation(0.1f, new TextureRegion(golem, 367, 240, 207, 239));

    }

    private static void retrieveJelly(AssetManager assetManager) {
	try {
	    jelly = assetManager.get("data/gfx/SAMPLE_ALIENS_DISSECTED.png", Texture.class);
	    jelly.setFilter(TextureFilter.Linear, TextureFilter.Linear);

	    jellyShape = new TextureRegion(jelly, 0, 0, 164, 210);
	    jellyWaitingEye = new TextureRegion(jelly, 432, 124, 78, 78);
	    jellyWaitingMouth = new TextureRegion(jelly, 372, 202, 59, 17);
	    jellyBlinkEye = new TextureRegion(jelly, 355, 219, 76, 15);
	    jellyHitEye = new TextureRegion(jelly, 359, 131, 73, 71);
	    jellyHitMouth = new TextureRegion(jelly, 430, 201, 82, 18);

	    jellyFeShape = new TextureRegion(jelly, 164, 0, 195, 212);
	    jellyFeWaitingEye = new TextureRegion(jelly, 359, 43, 78, 78);
	    jellyFeWaitingMouth = new TextureRegion(jelly, 444, 0, 46, 17);
	    jellyFeBlinkEye = new TextureRegion(jelly, 438, 43, 74, 27);
	    jellyFeHitEye = new TextureRegion(jelly, 443, 70, 64, 54);
	    jellyFeHitMouth = new TextureRegion(jelly, 447, 22, 55, 21);
	} catch (NullPointerException e) {
	    System.out.println("Jelly was not loaded!");
	}
    }

    private static void retrieveOgre(AssetManager assetManager) {
	ogre = assetManager.get("data/gfx/SAMPLE_ALIENS_ALL.png", Texture.class);
	ogreAttack = new DynamicAnimation(0.1f, new TextureRegion(ogre, 0, 241, 204, 238));
	ogreHiding = new DynamicAnimation(0.1f, new TextureRegion(ogre, 0, 241, 204, 238));
	ogreWaiting = new DynamicAnimation(0.1f, new TextureRegion(ogre, 0, 241, 204, 238));
	ogreSmashed = new DynamicAnimation(0.1f, new TextureRegion(ogre, 0, 241, 204, 238));
	ogreStunned = new DynamicAnimation(0.1f, new TextureRegion(ogre, 0, 241, 204, 238));
	ogreRising = new DynamicAnimation(0.1f, new TextureRegion(ogre, 0, 241, 204, 238));
    }

    private static void retrieveSorcerer(AssetManager assetManager) {
	sorcerer = assetManager.get("data/gfx/SAMPLE_ALIENS_ALL.png", Texture.class);
	sorcererAttack = new DynamicAnimation(0.1f, new TextureRegion(sorcerer, 622, 0, 219, 267));
	sorcererHiding = new DynamicAnimation(0.1f, new TextureRegion(sorcerer, 622, 0, 219, 267));
	sorcererWaiting = new DynamicAnimation(0.1f, new TextureRegion(sorcerer, 622, 0, 219, 267));
	sorcererRising = new DynamicAnimation(0.1f, new TextureRegion(sorcerer, 622, 0, 219, 267));
	sorcererSmashed = new DynamicAnimation(0.1f, new TextureRegion(sorcerer, 622, 0, 219, 267));
	sorcererStunned = new DynamicAnimation(0.1f, new TextureRegion(sorcerer, 622, 0, 219, 267));
    }

    private static void retrieveTortoise(AssetManager assetManager) {
	tortoise = assetManager.get("data/gfx/SAMPLE_ALIENS_ALL.png", Texture.class);
	tortoiseAttack = new DynamicAnimation(0.1f, new TextureRegion(tortoise, 397, 0, 225, 204));
	tortoiseHiding = new DynamicAnimation(0.1f, new TextureRegion(tortoise, 397, 0, 225, 204));
	tortoiseWaiting = new DynamicAnimation(0.1f, new TextureRegion(tortoise, 397, 0, 225, 204));
	tortoiseSmashed = new DynamicAnimation(0.1f, new TextureRegion(tortoise, 397, 0, 225, 204));
	tortoiseStunned = new DynamicAnimation(0.1f, new TextureRegion(tortoise, 397, 0, 225, 204));
	tortoiseRising = new DynamicAnimation(0.1f, new TextureRegion(tortoise, 397, 0, 225, 204));
    }
}
