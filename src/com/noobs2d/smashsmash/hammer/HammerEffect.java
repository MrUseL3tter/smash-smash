package com.noobs2d.smashsmash.hammer;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;

public class HammerEffect implements Poolable {

    public float delay = 0;
    public float duration = 0;
    public float elapsed = 0;

    private ParticleEffect instance;
    private Pool<HammerEffect> pool;

    public HammerEffect(ParticleEffect effect, Vector2 position, float duration, float delay) {
	this.duration = duration;
	this.delay = delay;

	instance = new ParticleEffect(effect);
	instance.setPosition(position.x, position.y);
    }

    public void free(ArrayList<HammerEffect> list, int index) {
	pool.free(list.remove(index));
    }

    public boolean isComplete() {
	return elapsed >= duration + delay;
    }

    public void render(SpriteBatch spriteBatch) {
	if (elapsed > delay && elapsed <= duration + delay)
	    instance.draw(spriteBatch);
	//	    draw(spriteBatch);
    }

    @Override
    public void reset() {
	instance = new ParticleEffect(instance);
	elapsed = 0;
    }

    public void setPool(Pool<HammerEffect> pool) {
	this.pool = pool;
    }

    public void setPosition(float x, float y) {
	instance.setPosition(x, y);
    }

    public void update(float deltaTime) {
	if (elapsed > delay)
	    instance.update(deltaTime);
	//	    super.update(deltaTime);
	elapsed += deltaTime;
    }
}
