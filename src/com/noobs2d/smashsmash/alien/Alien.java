package com.noobs2d.smashsmash.alien;

import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Bounce;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.noobs2d.smashsmash.Settings;
import com.noobs2d.smashsmash.User;
import com.noobs2d.smashsmash.buffeffect.BuffEffect;
import com.noobs2d.smashsmash.screen.SmashSmashStageCallback;
import com.noobs2d.tweenengine.utils.DynamicCallback.ReturnValues;
import com.noobs2d.tweenengine.utils.DynamicDisplayGroup;

/**
 * The mother of all aliens. aw yiss
 * 
 * @author MrUseL3tter
 */
public abstract class Alien {

    public enum AlienState {
	ATTACKING, WAITING, RISING, HIDING, SMASHED, HIDDEN, STUNNED, EXPLODING
    }

    public Vector2 position = new Vector2(0, 0);

    protected AlienState state = AlienState.HIDDEN;
    protected DynamicDisplayGroup risingState;
    protected DynamicDisplayGroup waitingState;
    protected DynamicDisplayGroup attackingState;
    protected DynamicDisplayGroup stunnedState;
    protected DynamicDisplayGroup smashedState;
    protected DynamicDisplayGroup hidingState;
    protected DynamicDisplayGroup explodeState;
    protected Rectangle collisionBounds = new Rectangle(0, 0, 96, 276);
    protected Rectangle hitBounds = new Rectangle(0, 0, 0, 0);
    protected SmashSmashStageCallback stage;

    protected float risingStateTime = 1f;
    protected float waitingStateTime = 1f;
    protected float attackingStateTime = 1f;
    protected float explodeStateTime = 1f;
    protected float stunnedStateTime = 3f;
    protected float smashedStateTime = .95f;
    protected float hidingStateTime = 1f;
    protected int hitPointsCurrent = 1;
    protected int hitPointsTotal = 1;
    protected int scoreValue = 1;

    private Sound attackSound;
    private Sound smashSound;
    private Sound spawnSound;
    private Sound stunSound;

    private float upElapsedTime = 0f;
    private float riseDelay = 0f;
    private boolean hostile = true;
    private boolean visible = true;

    protected Alien() {
	risingState = new DynamicDisplayGroup();
	waitingState = new DynamicDisplayGroup();
	attackingState = new DynamicDisplayGroup();
	stunnedState = new DynamicDisplayGroup();
	smashedState = new DynamicDisplayGroup();
	hidingState = new DynamicDisplayGroup();
	explodeState = new DynamicDisplayGroup();
    }

    public void attack() {
	stage.onAlienAttack(this);
	interpolateAttacking();
    }

    public boolean collides(Alien alien) {
	collisionBounds.x = position.x - 48;
	collisionBounds.y = position.y - 110;
	alien.collisionBounds.x = alien.position.x - 48;
	alien.collisionBounds.y = alien.position.y - 110;
	return collisionBounds.overlaps(alien.getHitBounds());
    }

    public float getAttackingStateTime() {
	return attackingStateTime;
    }

    public float getExplodeStateTime() {
	return explodeStateTime;
    }

    public float getHidingStateTime() {
	return hidingStateTime;
    }

    public Rectangle getHitBounds() {
	if (getStateAnimation() != null) {
	    float scaleX = getStateAnimation().getLargestAreaDisplay().scale.x;
	    float scaleY = getStateAnimation().getLargestAreaDisplay().scale.y;
	    float x = position.x - getStateAnimation().getLargestAreaDisplay().getWidth() * scaleX / 2;
	    float y = position.y;
	    float width = getStateAnimation().getLargestAreaDisplay().getWidth() * scaleX;
	    float height = getStateAnimation().getLargestAreaDisplay().getHeight() * scaleY;
	    hitBounds.set(x, y, width, height);
	}
	return hitBounds;
    }

    public float getRisingStateTime() {
	return risingStateTime;
    }

    public int getScore() {
	return 1;
    }

    public float getSmashedStateTime() {
	return smashedStateTime;
    }

    public DynamicDisplayGroup getStateAnimation() {
	if (state == AlienState.ATTACKING)
	    return attackingState;
	else if (state == AlienState.HIDING)
	    return hidingState;
	else if (state == AlienState.RISING)
	    return risingState;
	else if (state == AlienState.SMASHED)
	    return smashedState;
	else if (state == AlienState.STUNNED)
	    return stunnedState;
	else if (state == AlienState.WAITING)
	    return waitingState;
	else if (state == AlienState.EXPLODING)
	    return explodeState;
	else
	    return waitingState;
    }

    /**
     * @return the stunnedStateTime
     */
    public float getStunnedStateTime() {
	return stunnedStateTime;
    }

    /**
     * @return the upElapsedTime
     */
    public float getUpElapsedTime() {
	return upElapsedTime;
    }

    /**
     * @return the waitingStateTime
     */
    public float getWaitingStateTime() {
	return waitingStateTime;
    }

    public void hide() {
	interpolateHiding();
	state = AlienState.HIDING;
	upElapsedTime = 0;
    }

    public boolean hit(Rectangle rectangle) {
	return visible && state != AlienState.SMASHED && getHitBounds().overlaps(rectangle);
    }

    public boolean isHostile() {
	return hostile;
    }

    public boolean isVisible() {
	return visible;
    }

    public void pauseTween() {
	risingState.pauseTween();
	waitingState.pauseTween();
	attackingState.pauseTween();
	stunnedState.pauseTween();
	smashedState.pauseTween();
	hidingState.pauseTween();
    }

    public void render(SpriteBatch spriteBatch) {
	if (visible) {
	    getStateAnimation().position.add(position);
	    getStateAnimation().render(spriteBatch);
	    getStateAnimation().position.sub(position);
	}
    }

    public void reset() {
	upElapsedTime = 0f;
	attackingState.reset();
	hidingState.reset();
	risingState.reset();
	stunnedState.reset();
	smashedState.reset();
	waitingState.reset();
    }

    public void resumeTween() {
	risingState.resumeTween();
	waitingState.resumeTween();
	attackingState.resumeTween();
	stunnedState.resumeTween();
	smashedState.resumeTween();
	hidingState.resumeTween();
    }

    public void rise(float riseDelay, float volume) {
	this.riseDelay = riseDelay;
	if (state == AlienState.HIDDEN) {
	    waitingStateTime = (float) (Math.random() * 3 + 2.5);
	    upElapsedTime = 0;

	    visible = true;
	    state = AlienState.RISING;

	    reset();
	    interpolateRising(riseDelay);
	    if (Settings.soundEnabled)
		spawnSound.play(volume);
	}
    }

    /**
     * @param attackingStateTime the attackingStateTime to set
     */
    public void setAttackingStateTime(float attackingStateTime) {
	this.attackingStateTime = attackingStateTime;
    }

    public void setAttackSound(Sound attackSound) {
	this.attackSound = attackSound;
    }

    /**
     * @param explodeStateTime the explodeStateTime to set
     */
    public void setExplodeStateTime(float explodeStateTime) {
	this.explodeStateTime = explodeStateTime;
    }

    /**
     * @param hidingStateTime the hidingStateTime to set
     */
    public void setHidingStateTime(float hidingStateTime) {
	this.hidingStateTime = hidingStateTime;
    }

    public void setHostile(boolean hostile) {
	this.hostile = hostile;
    }

    /**
     * @param risingStateTime the risingStateTime to set
     */
    public void setRisingStateTime(float risingStateTime) {
	this.risingStateTime = risingStateTime;
    }

    /**
     * @param smashedStateTime the smashedStateTime to set
     */
    public void setSmashedStateTime(float smashedStateTime) {
	this.smashedStateTime = smashedStateTime;
    }

    public void setSmashSound(Sound smashSound) {
	this.smashSound = smashSound;
    }

    public void setSpawnSound(Sound spawnSound) {
	this.spawnSound = spawnSound;
    }

    /**
     * @param stunnedStateTime the stunnedStateTime to set
     */
    public void setStunnedStateTime(float stunnedStateTime) {
	this.stunnedStateTime = stunnedStateTime;
    }

    public void setStunSound(Sound stunSound) {
	this.stunSound = stunSound;
    }

    public void setVisible(boolean visible) {
	this.visible = visible;
    }

    /**
     * @param waitingStateTime the waitingStateTime to set
     */
    public void setWaitingStateTime(float waitingStateTime) {
	this.waitingStateTime = waitingStateTime;
    }

    public void smash() {
	reset();
	if (Settings.soundEnabled)
	    smashSound.play();
	hitPointsCurrent--;
	if (User.hasBuffEffect(BuffEffect.HAMMER_TIME) || hitPointsCurrent <= 0 && state != AlienState.SMASHED && state != AlienState.EXPLODING) { //alien dead
	    hitPointsCurrent = hitPointsTotal;
	    boolean critical = isSmashCritical();
	    if (state == AlienState.STUNNED) {
		state = AlienState.EXPLODING;
		interpolateExplode();
	    } else if (critical) {
		state = AlienState.STUNNED;
		interpolateStunned();
	    } else {
		state = AlienState.SMASHED;
		interpolateSmashed();
	    }
	}
	stage.onAlienSmashed(this);
    }

    public void stun() {
	Settings.log(getClass().getName(), "stun()", "");
	if (state != AlienState.SMASHED) {
	    upElapsedTime = 0;
	    state = AlienState.STUNNED;
	    for (int i = 0; i < stunnedState.displays.size(); i++) {
		stunnedState.displays.get(i).interpolateXY(stunnedState.displays.get(i).position.x, stunnedState.displays.get(i).position.y, Bounce.OUT, 1000, true);
		stunnedState.displays.get(i).setTweenCallbackTriggers(TweenCallback.COMPLETE);
		stunnedState.displays.get(i).setTweenCallback(new ReturnValues(stunnedState.displays.get(i)));
		stunnedState.displays.get(i).interpolateScaleXY(1f, 1f, Bounce.OUT, 1000, true);
		stunnedState.displays.get(i).setTweenCallbackTriggers(TweenCallback.COMPLETE);
		stunnedState.displays.get(i).setTweenCallback(new ReturnValues(stunnedState.displays.get(i)));
		stunnedState.displays.get(i).getTween().start(stunnedState.displays.get(i).getTweenManager());
		stunnedState.displays.get(i).position.set(0, 0);
		stunnedState.displays.get(i).scale.y = 0f;
	    }
	}
    }

    public void update(float deltaTime) {
	if (visible) {
	    switch (state) {
		case ATTACKING:
		    updateAttacking(deltaTime);
		    break;
		case HIDING:
		    updateHiding(deltaTime);
		    break;
		case RISING:
		    updateRising(deltaTime);
		    break;
		case WAITING:
		    updateWaiting(deltaTime);
		    break;
		case SMASHED:
		    updateSmashed(deltaTime);
		    break;
		case STUNNED:
		    updateStunned(deltaTime);
		    break;
		case EXPLODING:
		    updateExploding(deltaTime);
		    break;
	    }
	    upElapsedTime += deltaTime;
	}
    }

    protected void interpolateAttacking() {

    }

    protected void interpolateExplode() {

    }

    protected void interpolateHiding() {
	for (int i = 0; i < hidingState.displays.size(); i++) {
	    hidingState.displays.get(i).interpolateScaleXY(1f, 0f, Bounce.IN, 200, true);
	    hidingState.displays.get(i).setTweenCallbackTriggers(TweenCallback.COMPLETE);
	    hidingState.displays.get(i).setTweenCallback(new ReturnValues(hidingState.displays.get(i)));
	    hidingState.displays.get(i).interpolateXY(hidingState.displays.get(i).position.x, 0, Bounce.IN, 200, true);
	    hidingState.displays.get(i).setTweenCallback(new ReturnValues(hidingState.displays.get(i)));
	    hidingState.displays.get(i).setTweenCallbackTriggers(TweenCallback.COMPLETE);
	}
    }

    protected void interpolateRising(float delay) {
	for (int i = 0; i < risingState.displays.size(); i++) {
	    risingState.displays.get(i).interpolateXY(risingState.displays.get(i).position.x, risingState.displays.get(i).position.y, Bounce.OUT, 1000, true);
	    risingState.displays.get(i).setTweenCallback(new ReturnValues(risingState.displays.get(i)));
	    risingState.displays.get(i).setTweenCallbackTriggers(TweenCallback.COMPLETE);
	    risingState.displays.get(i).getTween().delay(delay);
	    risingState.displays.get(i).interpolateScaleXY(1f, 1f, Bounce.OUT, 1000, true);
	    risingState.displays.get(i).setTweenCallback(new ReturnValues(risingState.displays.get(i)));
	    risingState.displays.get(i).setTweenCallbackTriggers(TweenCallback.COMPLETE);
	    risingState.displays.get(i).getTween().delay(delay);
	    risingState.displays.get(i).position.set(0, 0);
	    risingState.displays.get(i).scale.y = 0f;
	}
    }

    protected void interpolateSmashed() {
	for (int i = 0; i < smashedState.displays.size(); i++) {
	    smashedState.displays.get(i).interpolateScaleXY(1f, 0f, Bounce.IN, 1000, true);
	    smashedState.displays.get(i).setTweenCallback(new ReturnValues(smashedState.displays.get(i)));
	    smashedState.displays.get(i).setTweenCallbackTriggers(TweenCallback.COMPLETE);
	    smashedState.displays.get(i).interpolateXY(smashedState.displays.get(i).position.x, 0, Bounce.IN, 1000, true);
	    smashedState.displays.get(i).setTweenCallback(new ReturnValues(smashedState.displays.get(i)));
	    smashedState.displays.get(i).setTweenCallbackTriggers(TweenCallback.COMPLETE);
	}
    }

    protected void interpolateStunned() {

    }

    protected void interpolateWaiting() {

    }

    protected boolean isSmashCritical() {
	return MathUtils.random(1, 3) == 1;
    }

    protected void playAttackSound() {
	if (Settings.soundEnabled)
	    attackSound.play();
    }

    protected void playSmashSound() {
	if (Settings.soundEnabled)
	    smashSound.play();
    }

    protected void playSpawnSound() {
	if (Settings.soundEnabled)
	    spawnSound.play();
    }

    protected void playStunSound() {
	if (Settings.soundEnabled)
	    stunSound.play();
    }

    protected void updateAttacking(float deltaTime) {
	attackingState.update(deltaTime);
	if (upElapsedTime >= attackingStateTime)
	    hide();
    }

    protected void updateExploding(float deltaTime) {
	explodeState.update(deltaTime);
	if (upElapsedTime >= explodeStateTime) {
	    visible = false;
	    state = AlienState.HIDDEN;
	    upElapsedTime = 0;
	}
    }

    protected void updateHiding(float deltaTime) {
	hidingState.update(deltaTime);
	if (upElapsedTime >= hidingStateTime) {
	    visible = false;
	    state = AlienState.HIDDEN;
	    upElapsedTime = 0;
	    stage.onAlienEscaped(this);
	}
    }

    protected void updateRising(float deltaTime) {
	risingState.update(deltaTime);
	if (upElapsedTime >= risingStateTime + riseDelay / 1000) {
	    interpolateWaiting();
	    state = AlienState.WAITING;
	    upElapsedTime = 0;
	}
    }

    protected void updateSmashed(float deltaTime) {
	smashedState.update(deltaTime);
	if (upElapsedTime >= smashedStateTime) {
	    visible = false;
	    state = AlienState.HIDDEN;
	    upElapsedTime = 0;
	}
    }

    protected void updateStunned(float deltaTime) {
	stunnedState.update(deltaTime);
	if (upElapsedTime >= stunnedStateTime) {
	    state = AlienState.HIDING;
	    upElapsedTime = 0;
	    interpolateHiding();
	}
    }

    protected void updateWaiting(float deltaTime) {
	waitingState.update(deltaTime);
	if (hostile && upElapsedTime >= waitingStateTime) {
	    state = AlienState.ATTACKING;
	    upElapsedTime = 0;
	    attack();
	} else if (upElapsedTime >= waitingStateTime)
	    hide();
    }
}
