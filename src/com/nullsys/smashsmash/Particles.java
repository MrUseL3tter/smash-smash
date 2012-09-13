package com.nullsys.smashsmash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class Particles {

    public static ParticleEffect frostBiteNormal;
    public static ParticleEffect frostBiteHammerTime;
    public static ParticleEffect leafSpawn;

    public static void load() {
	frostBiteNormal = new ParticleEffect();
	frostBiteNormal.load(Gdx.files.internal("data/particles/FROST-BITE.p"), Gdx.files.internal("data/particles"));
	frostBiteHammerTime = new ParticleEffect();
	frostBiteHammerTime.load(Gdx.files.internal("data/particles/FROST-BITE-HE.p"), Gdx.files.internal("data/particles"));
	leafSpawn = new ParticleEffect();
	leafSpawn.load(Gdx.files.internal("data/particles/LEAF-SPAWN.p"), Gdx.files.internal("data/particles"));
    }
}
