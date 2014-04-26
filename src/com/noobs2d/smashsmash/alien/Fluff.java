package com.noobs2d.smashsmash.alien;

import aurelienribon.tweenengine.equations.Bounce;

import com.noobs2d.smashsmash.screen.AlienEventListener;
import com.noobs2d.tweenengine.utils.DynamicAnimation;
import com.noobs2d.tweenengine.utils.DynamicDisplay;
import com.noobs2d.tweenengine.utils.DynamicDisplay.DynamicRegistration;
import com.noobs2d.tweenengine.utils.DynamicSprite;

/**
 * The bluish, hairy, not-so-dangerously-looking alien. Not
 * actually sure if not dangerous.
 * 
 * @author Julious Cious Igmen <jcigmen@gmail.com>
 */
public class Fluff extends Alien {

    public static final String RESOURCE = "data/gfx/ALIENS.pack";
    public static final String SHAPE = "FLUFF-SHAPE";
    public static final String ATTACK_EYES = "FLUFF-ATTACK-EYES";
    public static final String ATTACK_MOUTH = "FLUFF-ATTACK-MOUTH";
    public static final String IDLE_EYES = "FLUFF-IDLE-EYES";
    public static final String IDLE_MOUTH = "FLUFF-IDLE-MOUTH";
    public static final String BLINK_EYES = "FLUFF-BLINK-EYES";
    public static final String SMASHED_MOUTH = "FLUFF-SMASHED-MOUTH";
    public static final String SMASHED_SHAPE = "FLUFF-SMASHED-SHAPE";
    public static final String STUNNED_LEFT_EYE = "FLUFF-STUNNED-LEFTEYE";
    public static final String STUNNED_RIGHT_EYE = "FLUFF-STUNNED-RIGHTEYE";
    public static final String STUNNED_SHAPE = "FLUFF-STUNNED-SHAPE";
    public static final String STUNNED_MOUTH = "FLUFF-STUNNED-MOUTH";

    public Fluff(AlienEventListener stage) {
	super.callback = stage;

	initAttackingState();
	initHidingState();
	initRisingState();
	initSmashedState();
	initStunnedState();
	initWaitingState();

	setAttackingStateTime(.75f);
	setWaitingStateTime(2f);

	hitPointsTotal = 25000;
    }

    /** {@link Fluff} doesn't have an explosion state so we just divert explode into smashed state. */
    @Override
    public void explode() {
	state = AlienState.SMASHED;
	interpolateSmashed(true);
    }

    @Override
    public int getScore() {
	return state == AlienState.RISING ? 1 : state == AlienState.WAITING || state == AlienState.ATTACKING ? 2 : 1;
    }

    @Override
    protected void initAttackingState() {
	DynamicSprite shape = new DynamicSprite(AliensArt.fluffShape, 0, 0, DynamicRegistration.BOTTOM_CENTER);
	shape.setName(SHAPE);

	DynamicSprite eyes = new DynamicSprite(AliensArt.fluffAttackEyes, 0, 186);
	eyes.setName(ATTACK_EYES);

	DynamicSprite mouth = new DynamicSprite(AliensArt.fluffAttackMouth, 0, 136);
	mouth.setName(ATTACK_MOUTH);

	attackingState.add(shape);
	attackingState.add(eyes);
	attackingState.add(mouth);
	attackingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    /** {@link Fluff} doesn't have explode state so explode() will just be overriden. */
    @Override
    protected void initExplodeState() {
    }

    @Override
    protected void initHidingState() {
	DynamicSprite shape = new DynamicSprite(AliensArt.fluffShape, 0, 0, DynamicRegistration.BOTTOM_CENTER);
	shape.setName(SHAPE);

	DynamicSprite eyes = new DynamicSprite(AliensArt.fluffIdleEyes, 0, 190, DynamicRegistration.BOTTOM_CENTER);
	eyes.setName(IDLE_EYES);

	DynamicSprite mouth = new DynamicSprite(AliensArt.fluffIdleMouth, 0, 151, DynamicRegistration.BOTTOM_CENTER);
	mouth.setName(IDLE_MOUTH);

	hidingState.add(shape);
	hidingState.add(eyes);
	hidingState.add(mouth);
	hidingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    @Override
    protected void initRisingState() {
	DynamicSprite shape = new DynamicSprite(AliensArt.fluffShape, 0, 0, DynamicRegistration.BOTTOM_CENTER);
	shape.setName(SHAPE);

	DynamicSprite eyes = new DynamicSprite(AliensArt.fluffIdleEyes, 0, 190, DynamicRegistration.BOTTOM_CENTER);
	eyes.setName(IDLE_EYES);

	DynamicSprite mouth = new DynamicSprite(AliensArt.fluffIdleMouth, 0, 151, DynamicRegistration.BOTTOM_CENTER);
	mouth.setName(IDLE_MOUTH);

	risingState.add(shape);
	risingState.add(eyes);
	risingState.add(mouth);
	risingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    @Override
    protected void initSmashedState() {
	DynamicSprite shape = new DynamicSprite(AliensArt.fluffSmashedShape, 0, 0, DynamicRegistration.BOTTOM_CENTER);
	shape.setName(SHAPE);

	DynamicSprite eyes = new DynamicSprite(AliensArt.fluffIdleEyes, 0, 190);
	eyes.setName(IDLE_EYES);

	DynamicSprite mouth = new DynamicSprite(AliensArt.fluffSmashedMouth, 0, 166);
	mouth.setName(SMASHED_MOUTH);

	smashedState.add(shape);
	smashedState.add(eyes);
	smashedState.add(mouth);
	smashedState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    @Override
    protected void initStunnedState() {
	DynamicSprite shape = new DynamicSprite(AliensArt.fluffStunnedShape, 0, 0, DynamicRegistration.BOTTOM_CENTER);
	shape.setName(STUNNED_SHAPE);

	DynamicSprite leftEye = new DynamicSprite(AliensArt.fluffStunnedLeftEye, -62, 181);
	leftEye.setName(STUNNED_LEFT_EYE);

	DynamicSprite rightEye = new DynamicSprite(AliensArt.fluffStunnedRightEye, 64, 185);
	rightEye.setName(STUNNED_RIGHT_EYE);

	DynamicSprite mouth = new DynamicSprite(AliensArt.fluffStunnedMouth, 0, 140);
	mouth.setName(STUNNED_MOUTH);

	stunnedState.add(shape);
	stunnedState.add(mouth);
	stunnedState.add(leftEye);
	stunnedState.add(rightEye);
	stunnedState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    @Override
    protected void initWaitingState() {
	DynamicSprite shape = new DynamicSprite(AliensArt.fluffShape, 0, 0, DynamicRegistration.BOTTOM_CENTER);
	shape.setName(SHAPE);

	DynamicAnimation eyes = new DynamicAnimation(.35f, AliensArt.fluffIdleEyes, AliensArt.fluffIdleEyes, AliensArt.fluffIdleEyes, AliensArt.fluffBlinkEyes, AliensArt.fluffIdleEyes);
	eyes.setY(190f);
	eyes.setRegistration(DynamicRegistration.BOTTOM_CENTER);
	eyes.setName(IDLE_EYES);

	DynamicSprite mouth = new DynamicSprite(AliensArt.fluffIdleMouth, 0, 151, DynamicRegistration.BOTTOM_CENTER);
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

    /** No explode state for this alien. */
    @Override
    protected void interpolateExplode() {
    }

    @Override
    protected void interpolateHiding() {
	// nothing special; just scale Y down and put position Y to 0 to create a "shrinking" animation
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
	// nothing special; just scale Y up from 0 to 1 and position Y to actual position to create a "rising" animation
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
	eyes.setY(153f);
	eyes.setScaleY(1f);
	eyes.interpolateY(eyes.getY() + 80f, smashedTweenDuration, false).repeatYoyo(1, 0).start(eyes.getTweenManager());

	DynamicDisplay mouth = smashedState.getByName(SMASHED_MOUTH);
	mouth.setY(155f);
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

    /** Bounce the height in like being smashed. */
    @Override
    protected void interpolateStunned() {
	int tweenDuration = 350;

	DynamicSprite shape = (DynamicSprite) stunnedState.getByName(STUNNED_SHAPE);
	shape.setScaleY(waitingState.getByName(SHAPE).getScaleY());
	shape.interpolateScaleY(.85f, Bounce.OUT, tweenDuration, true);

	DynamicSprite leftEye = (DynamicSprite) stunnedState.getByName(STUNNED_LEFT_EYE);
	leftEye.setY(187);
	leftEye.interpolateY(leftEye.getY() * .85f, Bounce.OUT, tweenDuration, true);
	leftEye.setY(leftEye.getY() * waitingState.getByName(SHAPE).getScaleY());

	DynamicSprite rightEye = (DynamicSprite) stunnedState.getByName(STUNNED_RIGHT_EYE);
	rightEye.setY(175);
	rightEye.interpolateY(rightEye.getY() * .85f, Bounce.OUT, tweenDuration, true);
	rightEye.setY(rightEye.getY() * waitingState.getByName(SHAPE).getScaleY());

	DynamicSprite mouth = (DynamicSprite) stunnedState.getByName(STUNNED_MOUTH);
	mouth.setY(127);
	mouth.interpolateY(mouth.getY() * .85f, Bounce.OUT, tweenDuration, true);
	mouth.setY(mouth.getY() * waitingState.getByName(SHAPE).getScaleY());
    }

    /** Tweens position and scale Y to achieve a moving effect. */
    @Override
    protected void interpolateWaiting() {
	int tweenDuration = (int) (1000 * getWaitingStateTime() / 4);

	DynamicSprite shape = (DynamicSprite) waitingState.getByName(SHAPE);
	shape.setScaleY(1f);
	shape.killTween(); // to make sure this tween resets. this supposedly not needed.
	shape.interpolateScaleY(.90f, tweenDuration, false).repeatYoyo(3, 0).start(shape.getTweenManager());

	DynamicAnimation eyes = (DynamicAnimation) waitingState.getByName(IDLE_EYES);
	eyes.setY(190f);
	eyes.killTween();
	eyes.interpolateY(170f, tweenDuration, false).repeatYoyo(3, 0).start(eyes.getTweenManager());

	DynamicSprite mouth = (DynamicSprite) waitingState.getByName(IDLE_MOUTH);
	mouth.setY(151f);
	mouth.killTween();
	mouth.interpolateY(131f, tweenDuration, false).repeatYoyo(3, 0).start(mouth.getTweenManager());
    }
}
