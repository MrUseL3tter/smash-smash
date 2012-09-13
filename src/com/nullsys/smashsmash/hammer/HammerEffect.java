package com.nullsys.smashsmash.hammer;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class HammerEffect extends ParticleEffect {

    public float delay = 0;

    public float duration = 0;

    public float elapsed = 0;

    public HammerEffect(ParticleEffect effect, Vector2 position, float duration, float delay) {
	super(effect);
	super.setPosition(position.x, position.y);
	this.duration = duration;
	this.delay = delay;
    }

    public void render(SpriteBatch spriteBatch) {
	if (elapsed > delay && elapsed <= duration + delay)
	    draw(spriteBatch);
    }

    @Override
    public void update(float deltaTime) {
	if (elapsed > delay)
	    super.update(deltaTime);
	elapsed += deltaTime;
    }
}
