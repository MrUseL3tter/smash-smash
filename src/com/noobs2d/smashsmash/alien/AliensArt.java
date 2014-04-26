package com.noobs2d.smashsmash.alien;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.noobs2d.smashsmash.Settings;
import com.noobs2d.tweenengine.utils.DynamicAnimation;

/**
 * Placeholder for the static Texture and related classes for the {@link Alien}s.
 * The method {@link AliensArt#load(AssetManager)} is the one to invoke to start the loading,
 * then {@link AliensArt#retrieve(AssetManager)}, which must be called after loading is finished,
 * to start placing the loaded assets into the respective instances.
 * 
 * @author Julious Cious Igmen <jcigmen@gmail.com>
 */
public class AliensArt {

    public static Texture diabolic;
    public static DynamicAnimation diabolicAttack;
    public static DynamicAnimation diabolicHiding;
    public static DynamicAnimation diabolicWaiting;
    public static DynamicAnimation diabolicRising;
    public static DynamicAnimation diabolicSmashed;
    public static DynamicAnimation diabolicStunned;

    public static TextureRegion fluffShape;
    public static TextureRegion fluffAttackEyes;
    public static TextureRegion fluffAttackMouth;
    public static TextureRegion fluffIdleEyes;
    public static TextureRegion fluffIdleMouth;
    public static TextureRegion fluffBlinkEyes;
    public static TextureRegion fluffSmashedMouth;
    public static TextureRegion fluffSmashedShape;
    public static TextureRegion fluffStunnedLeftEye;
    public static TextureRegion fluffStunnedRightEye;
    public static TextureRegion fluffStunnedShape;
    public static TextureRegion fluffStunnedMouth;

    public static TextureRegion golemShape;
    public static TextureRegion golemAttackEyes;
    public static TextureRegion golemAttackMouth;
    public static TextureRegion golemIdleEyes;
    public static TextureRegion golemIdleMouth;
    public static TextureRegion golemBlinkEyes;
    public static TextureRegion golemSmashedMouth;
    public static TextureRegion golemSmashedShape;
    public static TextureRegion golemStunnedLeftEye;
    public static TextureRegion golemStunnedRightEye;
    public static TextureRegion golemStunnedShape;
    public static TextureRegion golemStunnedMouth;

    public static TextureRegion[] jellyExplode;
    public static TextureRegion jellyShape;
    public static TextureRegion jellyAttackEyes;
    public static TextureRegion jellyAttackMouth;
    public static TextureRegion jellyIdleEyes;
    public static TextureRegion jellyIdleMouth;
    public static TextureRegion jellyBlinkEyes;
    public static TextureRegion jellySmashedMouth;
    public static TextureRegion jellySmashedShape;
    public static TextureRegion jellyStunnedLeftEye;
    public static TextureRegion jellyStunnedRightEye;
    public static TextureRegion jellyStunnedShape;
    public static TextureRegion jellyStunnedMouth;

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
	assetManager.load(Jelly.RESOURCE, TextureAtlas.class);
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
	try {
	    TextureAtlas atlas = assetManager.get(Fluff.RESOURCE, TextureAtlas.class);
	    fluffShape = atlas.findRegion(Fluff.SHAPE);
	    fluffAttackEyes = atlas.findRegion(Fluff.ATTACK_EYES);
	    fluffAttackMouth = atlas.findRegion(Fluff.ATTACK_MOUTH);
	    fluffIdleEyes = atlas.findRegion(Fluff.IDLE_EYES);
	    fluffIdleMouth = atlas.findRegion(Fluff.IDLE_MOUTH);
	    fluffBlinkEyes = atlas.findRegion(Fluff.BLINK_EYES);
	    fluffSmashedMouth = atlas.findRegion(Fluff.SMASHED_MOUTH);
	    fluffSmashedShape = atlas.findRegion(Fluff.SMASHED_SHAPE);
	    fluffStunnedLeftEye = atlas.findRegion(Fluff.STUNNED_LEFT_EYE);
	    fluffStunnedRightEye = atlas.findRegion(Fluff.STUNNED_RIGHT_EYE);
	    fluffStunnedShape = atlas.findRegion(Fluff.STUNNED_SHAPE);
	    fluffStunnedMouth = atlas.findRegion(Fluff.STUNNED_MOUTH);
	} catch (NullPointerException e) {
	    Settings.log("AliensArt", "retrieveFluff(AssetManager)", " Fluff was not loaded due to " + e.toString());
	}
    }

    private static void retrieveGolem(AssetManager assetManager) {
	try {
	    TextureAtlas atlas = assetManager.get(Golem.RESOURCE, TextureAtlas.class);
	    golemShape = atlas.findRegion(Golem.SHAPE);
	    golemAttackEyes = atlas.findRegion(Golem.ATTACK_EYES);
	    golemAttackMouth = atlas.findRegion(Golem.ATTACK_MOUTH);
	    golemIdleEyes = atlas.findRegion(Golem.IDLE_EYES);
	    golemIdleMouth = atlas.findRegion(Golem.IDLE_MOUTH);
	    golemBlinkEyes = atlas.findRegion(Golem.BLINK_EYES);
	    golemSmashedMouth = atlas.findRegion(Golem.SMASHED_MOUTH);
	    golemSmashedShape = atlas.findRegion(Golem.SMASHED_SHAPE);
	    golemStunnedLeftEye = atlas.findRegion(Golem.STUNNED_LEFT_EYE);
	    golemStunnedRightEye = atlas.findRegion(Golem.STUNNED_RIGHT_EYE);
	    golemStunnedShape = atlas.findRegion(Golem.STUNNED_SHAPE);
	    golemStunnedMouth = atlas.findRegion(Golem.STUNNED_MOUTH);
	} catch (NullPointerException e) {
	    Settings.log("AliensArt", "retrieveGolem(AssetManager)", " Golem was not loaded due to " + e.toString());
	}
    }

    private static void retrieveJelly(AssetManager assetManager) {
	try {
	    TextureAtlas atlas = assetManager.get(Jelly.RESOURCE, TextureAtlas.class);
	    jellyShape = atlas.findRegion(Jelly.SHAPE);
	    jellyAttackEyes = atlas.findRegion(Jelly.ATTACK_EYES);
	    jellyAttackMouth = atlas.findRegion(Jelly.ATTACK_MOUTH);
	    jellyIdleEyes = atlas.findRegion(Jelly.IDLE_EYES);
	    jellyIdleMouth = atlas.findRegion(Jelly.IDLE_MOUTH);
	    jellyBlinkEyes = atlas.findRegion(Jelly.BLINK_EYES);
	    jellySmashedMouth = atlas.findRegion(Jelly.SMASHED_MOUTH);
	    jellySmashedShape = atlas.findRegion(Jelly.SMASHED_SHAPE);
	    jellyStunnedLeftEye = atlas.findRegion(Jelly.STUNNED_LEFT_EYE);
	    jellyStunnedRightEye = atlas.findRegion(Jelly.STUNNED_RIGHT_EYE);
	    jellyStunnedShape = atlas.findRegion(Jelly.STUNNED_SHAPE);
	    jellyStunnedMouth = atlas.findRegion(Jelly.STUNNED_MOUTH);
	    jellyExplode = new TextureRegion[8];
	    for (int i = 0; i < 8; i++)
		jellyExplode[i] = atlas.findRegion(Jelly.EXPLODE + (i + 1));
	} catch (NullPointerException e) {
	    Settings.log("AliensArt", "retrieveJelly(AssetManager)", " Jelly was not loaded due to " + e.toString());
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
