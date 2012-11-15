package com.nullsys.smashsmash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class Particles {

    public static ParticleEffect fierySmashNormal;
    public static ParticleEffect fierySmashHammerTime;
    public static ParticleEffect frostBiteNormal;
    public static ParticleEffect frostBiteHammerTime;

    public static void load() {
	fierySmashNormal = new ParticleEffect();
	fierySmashNormal.load(Gdx.files.internal("data/particles/FIERY-SMASH.p"), Gdx.files.internal("data/particles"));
	fierySmashHammerTime = new ParticleEffect();
	fierySmashHammerTime.load(Gdx.files.internal("data/particles/FIERY-SMASH-HE.p"), Gdx.files.internal("data/particles"));

	frostBiteNormal = new ParticleEffect();
	frostBiteNormal.load(Gdx.files.internal("data/particles/FROST-BITE.p"), Gdx.files.internal("data/particles"));
	frostBiteHammerTime = new ParticleEffect();
	frostBiteHammerTime.load(Gdx.files.internal("data/particles/FROST-BITE-HE.p"), Gdx.files.internal("data/particles"));
    }
}
