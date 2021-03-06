package com.noobs2d.smashsmash.alien;

import java.util.ArrayList;

import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Bounce;

import com.badlogic.gdx.math.MathUtils;
import com.noobs2d.smashsmash.User;
import com.noobs2d.smashsmash.buffeffect.BuffEffect;
import com.noobs2d.smashsmash.screen.AlienEventListener;
import com.noobs2d.tweenengine.utils.DynamicAnimation;
import com.noobs2d.tweenengine.utils.DynamicCallback.ReturnValues;
import com.noobs2d.tweenengine.utils.DynamicDisplay;
import com.noobs2d.tweenengine.utils.DynamicDisplay.DynamicRegistration;
import com.noobs2d.tweenengine.utils.DynamicDisplayGroup;

public class Sorcerer extends Alien {

    protected static final int WAITING_STATE_TIME = 7;
    protected float spawnDelay = 0;

    public Sorcerer(AlienEventListener listener) {
	setAlienEventListener(listener);

	initAttackingState();
	initHidingState();
	initRisingState();
	initSmashedState();
	initStunnedState();
	initWaitingState();
	waitingStateTime = 500;
    }

    @Override
    public int getScore() {
	return 0;
    }

    @Override
    public void rise(float delay, float volume) {
	boolean sorcererShouldAppear = !User.hasBuffEffect(BuffEffect.HAMMER_TIME) && !User.hasBuffEffect(BuffEffect.INVULNERABILITY);
	sorcererShouldAppear = spawnDelay <= 0 && sorcererShouldAppear && !User.hasBuffEffect(BuffEffect.SCORE_FRENZY);
	if (sorcererShouldAppear && state == AlienState.HIDDEN) {
	    spawnDelay = 15f + MathUtils.random(25f);
	    risingStateTime = 1 + delay / 1000;
	    waitingStateTime = WAITING_STATE_TIME;

	    reset(true);
	    setVisible(true);
	    state = AlienState.RISING;

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
    }

    @Override
    public void update(float delta) {
	super.update(delta);
	spawnDelay -= delta;
    }

    @Override
    protected void initAttackingState() {
	ArrayList<DynamicDisplay> list = new ArrayList<DynamicDisplay>();
	list.add(new DynamicAnimation(AliensArt.sorcererAttack));
	attackingState = new DynamicDisplayGroup(list);
	attackingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    @Override
    protected void initExplodeState() {
	// no explode state too
    }

    @Override
    protected void initHidingState() {
	ArrayList<DynamicDisplay> list = new ArrayList<DynamicDisplay>();
	list.add(new DynamicAnimation(AliensArt.sorcererHiding));
	hidingState = new DynamicDisplayGroup(list);
	hidingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    @Override
    protected void initRisingState() {
	ArrayList<DynamicDisplay> list = new ArrayList<DynamicDisplay>();
	list.add(new DynamicAnimation(AliensArt.sorcererRising));
	risingState = new DynamicDisplayGroup(list);
	risingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    @Override
    protected void initSmashedState() {
	ArrayList<DynamicDisplay> list = new ArrayList<DynamicDisplay>();
	list.add(new DynamicAnimation(AliensArt.sorcererSmashed));
	smashedState = new DynamicDisplayGroup(list);
	smashedState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    @Override
    protected void initStunnedState() {
	ArrayList<DynamicDisplay> list = new ArrayList<DynamicDisplay>();
	list.add(new DynamicAnimation(AliensArt.sorcererStunned));
	stunnedState = new DynamicDisplayGroup(list);
	stunnedState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    @Override
    protected void initWaitingState() {
	ArrayList<DynamicDisplay> list = new ArrayList<DynamicDisplay>();
	list.add(new DynamicAnimation(AliensArt.sorcererWaiting));
	waitingState = new DynamicDisplayGroup(list);
	waitingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
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

    @Override
    protected void updateWaiting(float deltaTime) {
	waitingState.update(deltaTime);
    }
}
