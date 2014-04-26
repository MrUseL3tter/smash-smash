package com.noobs2d.smashsmash.alien;

import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Bounce;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.noobs2d.smashsmash.buffeffect.BuffEffect;
import com.noobs2d.smashsmash.screen.AlienEventListener;
import com.noobs2d.tweenengine.utils.DynamicCallback.ReturnValues;

public class ScoreFrenzyJelly extends Jelly {

    private float spawnDelay = 0;

    public ScoreFrenzyJelly(AlienEventListener stage) {
	super(stage);
	risingState.setColor(new Color(0f, 0f, .5f, 1f));
	waitingState.setColor(new Color(0f, 0f, .5f, 1f));
	attackingState.setColor(new Color(0f, 0f, .5f, 1f));
	stunnedState.setColor(new Color(0f, 0f, .5f, 1f));
	smashedState.setColor(new Color(0f, 0f, .5f, 1f));
	hidingState.setColor(new Color(0f, 0f, .5f, 1f));
	setHostile(false);
    }

    @Override
    public void rise(float delay, float volume) {
	if (spawnDelay <= 0 && state == AlienState.HIDDEN) {
	    spawnDelay = 15f + MathUtils.random(25f);
	    risingStateTime = 1 + delay / 1000;
	    waitingStateTime = 1.25f;

	    state = AlienState.RISING;

	    setVisible(true);
	    reset(true);
	    interpolateRising(delay);
	}
    }

    @Override
    public void smash() {
	callback.onBonusEffectTrigger(this, BuffEffect.SCORE_FRENZY);
	super.smash();
    }

    @Override
    public void update(float delta) {
	super.update(delta);
	spawnDelay -= delta;
    }

    /** Do the attacking animation. It's a must that the animation duration matches {@link Alien#attackingStateTime}. */
    @Override
    protected void interpolateAttacking() {
    }

    /** Do the exploding animation. Not all aliens have an explosion animation. 
     * It's a must that the animation duration matches {@link Alien#explodeStateTime}.*/
    @Override
    protected void interpolateExplode() {
    }

    /** Do the hiding (shrinking) animation. It's a must that the animation duration matches {@link Alien#hidingStateTime}.*/
    @Override
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

    /** Do the rising animation. It's a must that the animation duration matches {@link Alien#risingStateTime}.*/
    @Override
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

    /** Do the smashed animation. */
    @Override
    protected void interpolateSmashed(boolean dead) {
	for (int i = 0; i < smashedState.displays.size(); i++) {
	    smashedState.displays.get(i).interpolateScaleXY(1f, 0f, Bounce.IN, 1000, true);
	    smashedState.displays.get(i).setTweenCallback(new ReturnValues(smashedState.displays.get(i)));
	    smashedState.displays.get(i).setTweenCallbackTriggers(TweenCallback.COMPLETE);
	    smashedState.displays.get(i).interpolateXY(smashedState.displays.get(i).position.x, 0, Bounce.IN, 1000, true);
	    smashedState.displays.get(i).setTweenCallback(new ReturnValues(smashedState.displays.get(i)));
	    smashedState.displays.get(i).setTweenCallbackTriggers(TweenCallback.COMPLETE);
	}
    }

    /** Do the stunned animation. Take note that this must always be short; {@link Alien#stunnedStateTime} is not the duration
     * of the stun-animation itself, but of the entire duration of being stunned. It's best to keep the animation duration to less than 500ms. */
    @Override
    protected void interpolateStunned() {
    }

    /**  Do the idling/waiting animation. */
    @Override
    protected void interpolateWaiting() {
    }

}
