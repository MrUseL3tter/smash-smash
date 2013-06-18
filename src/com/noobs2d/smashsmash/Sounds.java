package com.noobs2d.smashsmash;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

public class Sounds {

    public static Sound jellySmash;
    public static Sound jellySpawn;

    public static Sound hammerIceFlakes;

    public static void dispose() {
	jellySmash.dispose();
	jellySpawn.dispose();
	hammerIceFlakes.dispose();
    }

    public static void load(AssetManager assetManager) {
	assetManager.load("data/sfx/SAMPLE-ALIEN-SMASH.mp3", Sound.class);
	assetManager.load("data/sfx/SAMPLE-ALIEN-SPAWN.wav", Sound.class);
	assetManager.load("data/sfx/SAMPLE-ICE-FLAKES-SMASH.mp3", Sound.class);
    }

    public static void retrieve(AssetManager assetManager) {
	jellySmash = assetManager.get("data/sfx/SAMPLE-ALIEN-SMASH.mp3", Sound.class);
	jellySpawn = assetManager.get("data/sfx/SAMPLE-ALIEN-SPAWN.wav", Sound.class);
	hammerIceFlakes = assetManager.get("data/sfx/SAMPLE-ICE-FLAKES-SMASH.mp3", Sound.class);
    }
}
