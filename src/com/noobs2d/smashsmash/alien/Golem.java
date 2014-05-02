package com.noobs2d.smashsmash.alien;

import aurelienribon.tweenengine.equations.Bounce;

import com.badlogic.gdx.math.MathUtils;
import com.noobs2d.smashsmash.screen.AlienEventListener;
import com.noobs2d.tweenengine.utils.DynamicAnimation;
import com.noobs2d.tweenengine.utils.DynamicDisplay;
import com.noobs2d.tweenengine.utils.DynamicDisplay.DynamicRegistration;
import com.noobs2d.tweenengine.utils.DynamicSprite;

/**
 * A very sturdy alien that requires 3~5 hits to smash. Will only
 * attack if {@link Golem#elapsedTimeWaiting} is equal or greater than
 * {@link Golem#SECONDS_TO_WAIT_BEFORE_ATTACKING}. Only has a 2% chance of 
 * being shortly stunned and has no explode state. 
 * 
 * If attacked during it's attack state, will repeat its attack.
 * 
 * @author Julious Cious Igmen <jcigmen@gmail.com>
 */
public class Golem extends Alien {

    public static final String RESOURCE = "data/gfx/ALIENS.pack";

    public static final String SHAPE = "GOLEM-SHAPE";
    public static final String ATTACK_EYES = "GOLEM-ATTACK-EYES";
    public static final String ATTACK_MOUTH = "GOLEM-ATTACK-MOUTH";
    public static final String IDLE_EYES = "GOLEM-IDLE-EYES";
    public static final String IDLE_MOUTH = "GOLEM-IDLE-MOUTH";
    public static final String BLINK_EYES = "GOLEM-EYES-BLINK";
    public static final String SMASHED_MOUTH = "GOLEM-SMASHED-MOUTH";
    public static final String SMASHED_SHAPE = "GOLEM-SMASHED-SHAPE";
    public static final String STUNNED_EYES = "GOLEM-STUNNED-EYES";
    public static final String STUNNED_SHAPE = "GOLEM-STUNNED-SHAPE";
    public static final String STUNNED_MOUTH = "GOLEM-STUNNED-MOUTH";

    private static final float SECONDS_TO_WAIT_BEFORE_ATTACKING = 8f;

    /** An accumulative counter that increases everytime this Golem goes into
     * {@link AlienState#WAITING} state. Reset every time this alien rises.*/
    private float elapsedTimeWaiting = 0f;

    public Golem(AlienEventListener listener) {
	setAlienEventListener(listener);

	initAttackingState();
	initHidingState();
	initRisingState();
	initSmashedState();
	initStunnedState();
	initWaitingState();

	setStunnedStateTime(1.25f); // very short
	setWaitingStateTime(SECONDS_TO_WAIT_BEFORE_ATTACKING);

	setCriticalChance(2);

	setHP();
    }

    /** Golem doesn't have an explosion state so we just divert explode into smashed state. */
    @Override
    public void explode() {
	state = AlienState.SMASHED;
	interpolateSmashed(true);
    }

    @Override
    public int getScore() {
	return state == AlienState.RISING ? 1 : state == AlienState.ATTACKING ? 2 : 1;
    }

    @Override
    public void rise(float riseDelay, float volume) {
	super.rise(riseDelay, volume);
	elapsedTimeWaiting = 0;
	setHP();
    }

    /** A Golem may have an HP of between 3-5, which is set whenever it rises. */
    private void setHP() {
	int HP = 3 + MathUtils.random(3);
	setHitPointsCurrent(HP);
	setHitPointsTotal(HP);
    }

    /** Our maximum waiting time must be exactly {@link Golem#SECONDS_TO_WAIT_BEFORE_ATTACKING}.*/
    @Override
    protected void addRandomValueToWaitingTime() {
    }

    @Override
    protected void initAttackingState() {
	DynamicSprite shape = new DynamicSprite(AliensArt.golemShape, 0, 0, DynamicRegistration.BOTTOM_CENTER);
	shape.setName(SHAPE);

	DynamicSprite eyes = new DynamicSprite(AliensArt.golemAttackEyes, 0, 157);
	eyes.setName(ATTACK_EYES);

	DynamicSprite mouth = new DynamicSprite(AliensArt.golemAttackMouth, 0, 132);
	mouth.setName(ATTACK_MOUTH);

	attackingState.add(shape);
	attackingState.add(eyes);
	attackingState.add(mouth);
	attackingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    /** No explode state! */
    @Override
    protected void initExplodeState() {
    }

    @Override
    protected void initHidingState() {
	DynamicSprite shape = new DynamicSprite(AliensArt.golemShape, 0, 0, DynamicRegistration.BOTTOM_CENTER);
	shape.setName(SHAPE);

	DynamicSprite eyes = new DynamicSprite(AliensArt.golemIdleEyes, 0, 178, DynamicRegistration.BOTTOM_CENTER);
	eyes.setName(IDLE_EYES);

	DynamicSprite mouth = new DynamicSprite(AliensArt.golemIdleMouth, 0, 156, DynamicRegistration.BOTTOM_CENTER);
	mouth.setName(IDLE_MOUTH);

	hidingState.add(shape);
	hidingState.add(eyes);
	hidingState.add(mouth);
	hidingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    @Override
    protected void initRisingState() {
	DynamicSprite shape = new DynamicSprite(AliensArt.golemShape, 0, 0, DynamicRegistration.BOTTOM_CENTER);
	shape.setName(SHAPE);

	DynamicSprite eyes = new DynamicSprite(AliensArt.golemIdleEyes, 0, 178, DynamicRegistration.BOTTOM_CENTER);
	eyes.setName(IDLE_EYES);

	DynamicSprite mouth = new DynamicSprite(AliensArt.golemIdleMouth, 0, 156, DynamicRegistration.BOTTOM_CENTER);
	mouth.setName(IDLE_MOUTH);

	risingState.add(shape);
	risingState.add(eyes);
	risingState.add(mouth);
	risingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    @Override
    protected void initSmashedState() {
	DynamicSprite shape = new DynamicSprite(AliensArt.golemShape, 0, 0, DynamicRegistration.BOTTOM_CENTER);
	shape.setName(SHAPE);

	DynamicSprite eyes = new DynamicSprite(AliensArt.golemIdleEyes, 0, 187, DynamicRegistration.BOTTOM_CENTER);
	eyes.setName(IDLE_EYES);

	DynamicSprite mouth = new DynamicSprite(AliensArt.golemSmashedMouth, 0, 122, DynamicRegistration.BOTTOM_CENTER);
	mouth.setName(SMASHED_MOUTH);

	smashedState.add(shape);
	smashedState.add(eyes);
	smashedState.add(mouth);
	smashedState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    @Override
    protected void initStunnedState() {
	DynamicSprite shape = new DynamicSprite(AliensArt.golemStunnedShape, 0, 0, DynamicRegistration.BOTTOM_CENTER);
	shape.setName(STUNNED_SHAPE);

	DynamicSprite eyes = new DynamicSprite(AliensArt.golemStunnedEyes, 0, 152, DynamicRegistration.BOTTOM_CENTER);
	eyes.setName(STUNNED_EYES);

	DynamicSprite mouth = new DynamicSprite(AliensArt.golemStunnedMouth, 0, 94, DynamicRegistration.BOTTOM_CENTER);
	mouth.setName(STUNNED_MOUTH);

	stunnedState.add(shape);
	stunnedState.add(eyes);
	stunnedState.add(mouth);
	stunnedState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    @Override
    protected void initWaitingState() {
	DynamicSprite shape = new DynamicSprite(AliensArt.golemShape, 0, 0, DynamicRegistration.BOTTOM_CENTER);
	shape.setName(SHAPE);

	DynamicAnimation eyes = new DynamicAnimation(.35f, AliensArt.golemIdleEyes, AliensArt.golemIdleEyes, AliensArt.golemIdleEyes, AliensArt.golemIdleEyes, AliensArt.golemIdleEyes, AliensArt.golemIdleEyes, AliensArt.golemBlinkEyes, AliensArt.golemIdleEyes);
	eyes.setY(178f);
	eyes.setRegistration(DynamicRegistration.BOTTOM_CENTER);
	eyes.setName(IDLE_EYES);

	DynamicSprite mouth = new DynamicSprite(AliensArt.golemIdleMouth, 0, 156, DynamicRegistration.BOTTOM_CENTER);
	mouth.setName(IDLE_MOUTH);

	waitingState.add(shape);
	waitingState.add(eyes);
	waitingState.add(mouth);
	waitingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    @Override
    protected void interpolateAttacking() {
	// the shape will size (height) up and down 6 times
	DynamicSprite shape = (DynamicSprite) attackingState.getByName(SHAPE);
	int shapeTweenDuration = (int) (1000 * getAttackingStateTime()) / 6;
	shape.setScaleY(waitingState.getByName(SHAPE).getScaleY());
	shape.interpolateScaleY(1.05f, shapeTweenDuration, false).repeatYoyo(5, 0).start(shape.getTweenManager());

	// the eyes will scale up (width & height) 4 times
	DynamicSprite eyes = (DynamicSprite) attackingState.getByName(ATTACK_EYES);
	int eyesTweenDuration = (int) (1000 * getAttackingStateTime()) / 4;
	eyes.interpolateScaleXY(1.1f, 1.1f, eyesTweenDuration, false).repeatYoyo(3, 0).start(eyes.getTweenManager());

	// the mouth will also scale up (width & height) 4 times
	DynamicSprite mouth = (DynamicSprite) attackingState.getByName(ATTACK_MOUTH);
	mouth.interpolateScaleXY(1.25f, 1.1f, eyesTweenDuration, false).repeatYoyo(3, 0).start(mouth.getTweenManager());
    }

    /** Ain't no golem's explodin' here nigga */
    @Override
    protected void interpolateExplode() {
    }

    @Override
    protected void interpolateHiding() {
	int tweenDuration = (int) (1000 * getHidingStateTime());

	DynamicSprite shape = (DynamicSprite) hidingState.getByName(SHAPE);
	shape.setScaleY(waitingState.getByName(SHAPE).getScaleY());
	shape.interpolateScaleY(0f, tweenDuration, true);

	DynamicSprite eyes = (DynamicSprite) hidingState.getByName(IDLE_EYES);
	eyes.setY(waitingState.getByName(IDLE_EYES).getY());
	eyes.setScaleY(1f);
	eyes.interpolateY(0, tweenDuration, true);
	eyes.interpolateScaleY(0f, tweenDuration, true);

	DynamicSprite mouth = (DynamicSprite) hidingState.getByName(IDLE_MOUTH);
	mouth.setY(waitingState.getByName(IDLE_MOUTH).getY());
	mouth.setScaleY(1f);
	mouth.interpolateY(0, tweenDuration, true);
	mouth.interpolateScaleY(0f, tweenDuration, true);
    }

    @Override
    protected void interpolateRising(float delay) {
	int tweenDuration = (int) (1000 * getRisingStateTime());

	DynamicSprite shape = (DynamicSprite) risingState.getByName(SHAPE);
	shape.interpolateScaleY(1f, tweenDuration, false).delay(delay);
	shape.setScaleY(0f);
	shape.startTweenWithManager();

	DynamicSprite eyes = (DynamicSprite) risingState.getByName(IDLE_EYES);
	eyes.interpolateY(eyes.getY(), tweenDuration, false).delay(delay).start(eyes.getTweenManager());
	eyes.interpolateScaleY(1f, tweenDuration, false).delay(delay);
	eyes.setY(0f);
	eyes.setScaleY(0f);
	eyes.startTweenWithManager();

	DynamicSprite mouth = (DynamicSprite) risingState.getByName(IDLE_MOUTH);
	mouth.interpolateY(mouth.getY(), tweenDuration, false).delay(delay).start(mouth.getTweenManager());
	mouth.interpolateScaleY(1f, tweenDuration, false).delay(delay);
	mouth.setY(0f);
	mouth.setScaleY(0f);
	mouth.startTweenWithManager();
    }

    @Override
    protected void interpolateSmashed(boolean dead) {
	// smashed then hide. this should be 60% of the smashed state duration
	int smashedTweenDuration = (int) (1000 * getSmashedStateTime() * .6);

	DynamicDisplay shape = smashedState.getByName(SHAPE);
	shape.setScaleY(waitingState.getByName(SHAPE).getScaleY()); // the Y-scale must be the same as the waiting state's
	shape.interpolateScaleY(.8f, smashedTweenDuration, true);

	DynamicDisplay eyes = smashedState.getByName(IDLE_EYES);
	eyes.setY(187f);
	eyes.setScaleY(1f);
	eyes.interpolateY(eyes.getY() + 80f, smashedTweenDuration, false).repeatYoyo(1, 0).start(eyes.getTweenManager());

	DynamicDisplay mouth = smashedState.getByName(SMASHED_MOUTH);
	mouth.setY(122f);
	mouth.setScaleY(1f);
	mouth.interpolateScaleXY(1.1f, 1.1f, smashedTweenDuration, false).start(mouth.getTweenManager());

	if (dead) {
	    // hide or "shrink" tween. all tween has a delay based on the duration of the smash tween
	    // because we need to wait for that to finish first
	    int hideTweenDuration = (int) (1000 * getSmashedStateTime() * .4);

	    shape.interpolateScaleY(0f, hideTweenDuration, true).delay(smashedTweenDuration);
	    eyes.interpolateScaleY(0f, hideTweenDuration, true).delay(smashedTweenDuration);
	    eyes.interpolateY(0f, hideTweenDuration, true).delay(smashedTweenDuration);
	    mouth.interpolateScaleY(0f, hideTweenDuration, true).delay(smashedTweenDuration);
	    mouth.interpolateY(0f, hideTweenDuration, true).delay(smashedTweenDuration);
	}
    }

    @Override
    protected void interpolateStunned() {
	int tweenDuration = 350;

	DynamicSprite shape = (DynamicSprite) stunnedState.getByName(STUNNED_SHAPE);
	shape.setScaleY(waitingState.getByName(SHAPE).getScaleY());
	shape.interpolateScaleY(.85f, Bounce.OUT, tweenDuration, true);

	DynamicSprite eyes = (DynamicSprite) stunnedState.getByName(STUNNED_EYES);
	eyes.setY(152);
	eyes.interpolateY(eyes.getY() * .85f, Bounce.OUT, tweenDuration, true);
	eyes.setY(eyes.getY() * waitingState.getByName(SHAPE).getScaleY());

	DynamicSprite mouth = (DynamicSprite) stunnedState.getByName(STUNNED_MOUTH);
	mouth.setY(94);
	mouth.interpolateY(mouth.getY() * .85f, Bounce.OUT, tweenDuration, true);
	mouth.setY(mouth.getY() * waitingState.getByName(SHAPE).getScaleY());

    }

    @Override
    protected void interpolateWaiting() {
	int tweenDuration = (int) (1000 * getWaitingStateTime() / 4);

	DynamicSprite shape = (DynamicSprite) waitingState.getByName(SHAPE);
	shape.setScaleY(1f);
	shape.killTween(); // to make sure this tween resets. this supposedly not needed.
	shape.interpolateScaleY(.90f, tweenDuration, false).repeatYoyo(3, 0).start(shape.getTweenManager());

	DynamicAnimation eyes = (DynamicAnimation) waitingState.getByName(IDLE_EYES);
	eyes.setY(178f);
	eyes.killTween();
	eyes.interpolateY(eyes.getY() - 20, tweenDuration, false).repeatYoyo(3, 0).start(eyes.getTweenManager());

	DynamicSprite mouth = (DynamicSprite) waitingState.getByName(IDLE_MOUTH);
	mouth.setY(151f);
	mouth.killTween();
	mouth.interpolateY(mouth.getY() - 20, tweenDuration, false).repeatYoyo(3, 0).start(mouth.getTweenManager());

    }

    @Override
    protected void updateWaiting(float deltaTime) {
	waitingState.update(deltaTime);
	elapsedTimeWaiting += deltaTime;
	if (elapsedTimeWaiting >= SECONDS_TO_WAIT_BEFORE_ATTACKING && isHostile()) {
	    setElapsedTimeVisible(0);
	    attack();
	}
    }
}
