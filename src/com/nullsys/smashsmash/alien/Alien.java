package com.nullsys.smashsmash.alien;

import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Bounce;
import aurelienribon.tweenengine.equations.Linear;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.noobs2d.tweenengine.utils.DynamicAnimationGroup;
import com.noobs2d.tweenengine.utils.DynamicCallback.ReturnValues;
import com.noobs2d.tweenengine.utils.DynamicDisplay;
import com.nullsys.smashsmash.Particles;
import com.nullsys.smashsmash.Settings;
import com.nullsys.smashsmash.User;
import com.nullsys.smashsmash.bonuseffect.BonusEffect;
import com.nullsys.smashsmash.hammer.HammerEffect;
import com.nullsys.smashsmash.stage.SmashSmashStage;

public class Alien extends DynamicDisplay {

    public static final int ALIEN_JELLY_MALE = 101;

    public static final int ALIEN_JELLY_FEMALE = 102;
    public static final int ALIEN_FLUFF_MALE = 201;
    public static final int ALIEN_FLUFF_FEMALE = 202;
    public static final int ALIEN_TORTOISE_MALE = 301;
    public static final int ALIEN_TORTOISE_FEMALE = 302;
    public static final int ALIEN_OGRE_MALE = 401;
    public static final int ALIEN_OGRE_FEMALE = 402;
    public static final int ALIEN_DIABOLIC_MALE = 501;
    public static final int ALIEN_DIABOLIC_FEMALE = 502;
    public static final int ALIEN_GOLEM_MALE = 601;
    public static final int ALIEN_GOLEM_FEMALE = 602;

    public enum AlienState {
	ATTACKING, WAITING, RISING, HIDING, SMASHED, HIDDEN, STUNNED
    }

    public AlienState state = AlienState.HIDDEN;

    protected SmashSmashStage stage;

    public DynamicAnimationGroup risingState;
    public DynamicAnimationGroup waitingState;
    public DynamicAnimationGroup attackingState;
    public DynamicAnimationGroup stunnedState;
    public DynamicAnimationGroup smashedState;
    public DynamicAnimationGroup hidingState;

    public Sound SFXattack;
    public Sound SFXsmash;
    public Sound SFXspawn;
    public Sound SFXstun;

    public HammerEffect spawnEffect;
    public float spawnEffectDelay = 0;

    public float risingStateTime = 1f;
    public float waitingStateTime = 1f;
    public float attackingStateTime = 1f;
    public float stunnedStateTime = 3f;
    public float smashedStateTime = .95f;
    public float hidingStateTime = 1f;

    public int scoreValue = 1;
    public int tensionValue = 1;

    public int hitPoints = 1;
    public int hitPointsTotal = 1;

    public float upElapsedTime = 0;

    @Override
    public Rectangle getBounds() {
	if (getStateAnimation() != null) {
	    float scaleX = getStateAnimation().getLargestAreaDisplay().scale.x;
	    float scaleY = getStateAnimation().getLargestAreaDisplay().scale.y;
	    float x = position.x - getStateAnimation().getLargestAreaDisplay().getKeyFrame().getRegionWidth() * scaleX / 2;
	    float y = position.y;
	    float width = getStateAnimation().getLargestAreaDisplay().getKeyFrame().getRegionWidth() * scaleX;
	    float height = getStateAnimation().getLargestAreaDisplay().getKeyFrame().getRegionHeight() * scaleY;
	    bounds.set(x, y, width, height);
	}
	return bounds;
    }

    public DynamicAnimationGroup getStateAnimation() {
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
	else
	    return waitingState;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
	if (visible) {
	    spriteBatch.setColor(color.r, color.g, color.b, color.a);
	    getStateAnimation().color.set(color);
	    getStateAnimation().position.add(position);
	    getStateAnimation().render(spriteBatch);
	    getStateAnimation().position.sub(position);
	    spawnEffect.render(spriteBatch);
	}
    }

    public void reset() {
	attackingState.reset();
	hidingState.reset();
	risingState.reset();
	stunnedState.reset();
	smashedState.reset();
	waitingState.reset();
	color = new Color(1f, 1f, 1f, 1f);
    }

    public void rise(float delay, float volume) {
	if (state == AlienState.HIDDEN) {
	    risingStateTime = 1 + delay / 1000;
	    waitingStateTime = (float) (Math.random() * 3 + 2.5);
	    upElapsedTime = 0;

	    reset();
	    visible = true;
	    state = AlienState.RISING;
	    spawnEffectDelay = delay / 1000;
	    spawnEffect = new HammerEffect(Particles.leafSpawn, position, 1.0f, spawnEffectDelay);

	    for (int i = 0; i < risingState.displays.size(); i++) {
		risingState.displays.get(i).interpolateXY(risingState.displays.get(i).position.x, risingState.displays.get(i).position.y, Bounce.OUT, 1000, true);
		risingState.displays.get(i).tween.setCallback(new ReturnValues(risingState.displays.get(i)));
		risingState.displays.get(i).tween.setCallbackTriggers(TweenCallback.COMPLETE);
		risingState.displays.get(i).tween.delay(delay);
		risingState.displays.get(i).interpolateScaleXY(1f, 1f, Bounce.OUT, 1000, true);
		risingState.displays.get(i).tween.setCallback(new ReturnValues(risingState.displays.get(i)));
		risingState.displays.get(i).tween.setCallbackTriggers(TweenCallback.COMPLETE);
		risingState.displays.get(i).tween.delay(delay);
		risingState.displays.get(i).position.set(0, 0);
		risingState.displays.get(i).scale.y = 0f;
	    }
	    //	    SFXspawn.play(volume);
	}
    }

    @Override
    public void setRegistration(DynamicRegistration registration) {
	// This is technically routed to the registration of each DynamicAnimationGroups' displays.
    }

    public void smash() {
	upElapsedTime = 0;
	reset();
	if (Settings.soundEnabled)
	    SFXsmash.play();
	hitPoints--;
	if (User.hasEffect(BonusEffect.HAMMER_TIME) || hitPoints <= 0 && state != AlienState.SMASHED) { //alien dead
	    upElapsedTime = 0;
	    hitPoints = hitPointsTotal;
	    state = AlienState.SMASHED;
	    for (int i = 0; i < smashedState.displays.size(); i++) {
		smashedState.displays.get(i).interpolateScaleXY(1f, 0f, Bounce.IN, 1000, true);
		smashedState.displays.get(i).tween.setCallback(new ReturnValues(smashedState.displays.get(i)));
		smashedState.displays.get(i).tween.setCallbackTriggers(TweenCallback.COMPLETE);
		smashedState.displays.get(i).interpolateXY(smashedState.displays.get(i).position.x, 0, Bounce.IN, 1000, true);
		smashedState.displays.get(i).tween.setCallback(new ReturnValues(smashedState.displays.get(i)));
		smashedState.displays.get(i).tween.setCallbackTriggers(TweenCallback.COMPLETE);
	    }
	} else
	    for (int i = 0; i < getStateAnimation().displays.size(); i++) {
		getStateAnimation().displays.get(i).interpolateScaleXY(1f, .75f, Linear.INOUT, 150, true);
		float x = getStateAnimation().displays.get(i).position.x;
		float y = getStateAnimation().displays.get(i).position.y * .75f;
		getStateAnimation().displays.get(i).interpolateXY(x, y, Linear.INOUT, 150, true);
		getStateAnimation().displays.get(i).interpolateScaleXY(1f, 1f, Bounce.OUT, 500, true);
		getStateAnimation().displays.get(i).tween.delay(150);
		x = getStateAnimation().displays.get(i).position.x;
		y = getStateAnimation().displays.get(i).position.y;
		getStateAnimation().displays.get(i).interpolateXY(x, y, Bounce.OUT, 500, true);
		getStateAnimation().displays.get(i).tween.delay(150);
	    }
    }

    public void stun() {
	if (state != AlienState.SMASHED) {
	    upElapsedTime = 0;
	    state = AlienState.STUNNED;
	    for (int i = 0; i < stunnedState.displays.size(); i++) {
		stunnedState.displays.get(i).interpolateXY(stunnedState.displays.get(i).position.x, stunnedState.displays.get(i).position.y, Bounce.OUT, 1000, true);
		stunnedState.displays.get(i).tween.setCallbackTriggers(TweenCallback.COMPLETE);
		stunnedState.displays.get(i).tween.setCallback(new ReturnValues(stunnedState.displays.get(i)));
		stunnedState.displays.get(i).interpolateScaleXY(1f, 1f, Bounce.OUT, 1000, true);
		stunnedState.displays.get(i).tween.setCallbackTriggers(TweenCallback.COMPLETE);
		stunnedState.displays.get(i).tween.setCallback(new ReturnValues(stunnedState.displays.get(i)));
		stunnedState.displays.get(i).tween.start(stunnedState.displays.get(i).tweenManager);
		stunnedState.displays.get(i).position.set(0, 0);
		stunnedState.displays.get(i).scale.y = 0f;
	    }
	}
    }

    @Override
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
	    }
	    updateTween();
	    upElapsedTime += deltaTime;
	    spawnEffect.update(deltaTime);
	}
    }

    protected void updateAttacking(float deltaTime) {
	attackingState.update(deltaTime);
	if (upElapsedTime * tweenSpeed >= attackingStateTime) {
	    for (int i = 0; i < hidingState.displays.size(); i++) {
		hidingState.displays.get(i).interpolateScaleXY(1f, 0f, Bounce.IN, 1000, true);
		hidingState.displays.get(i).tween.setCallbackTriggers(TweenCallback.COMPLETE);
		hidingState.displays.get(i).tween.setCallback(new ReturnValues(hidingState.displays.get(i)));
		hidingState.displays.get(i).interpolateXY(hidingState.displays.get(i).position.x, 0, Bounce.IN, 1000, true);
		hidingState.displays.get(i).tween.setCallback(new ReturnValues(hidingState.displays.get(i)));
		hidingState.displays.get(i).tween.setCallbackTriggers(TweenCallback.COMPLETE);
	    }
	    state = AlienState.HIDING;
	    upElapsedTime = 0;
	    stage.onAlienAttack(this);
	}
    }

    protected void updateHiding(float deltaTime) {
	hidingState.update(deltaTime);
	if (upElapsedTime * tweenSpeed >= hidingStateTime) {
	    visible = false;
	    state = AlienState.HIDDEN;
	    upElapsedTime = 0;
	}
    }

    protected void updateRising(float deltaTime) {
	risingState.update(deltaTime);
	if (upElapsedTime * tweenSpeed >= risingStateTime) {
	    state = AlienState.WAITING;
	    upElapsedTime = 0;
	}
    }

    protected void updateSmashed(float deltaTime) {
	smashedState.update(deltaTime);
	if (upElapsedTime * tweenSpeed >= smashedStateTime) {
	    visible = false;
	    state = AlienState.HIDDEN;
	    upElapsedTime = 0;
	}
    }

    protected void updateStunned(float deltaTime) {
	stunnedState.update(deltaTime);
	if (upElapsedTime * tweenSpeed >= stunnedStateTime) {
	    state = AlienState.WAITING;
	    upElapsedTime = 0;
	}
    }

    protected void updateWaiting(float deltaTime) {
	waitingState.update(deltaTime);
	if (upElapsedTime * tweenSpeed >= waitingStateTime) {
	    state = AlienState.ATTACKING;
	    upElapsedTime = 0;
	}
    }
}
