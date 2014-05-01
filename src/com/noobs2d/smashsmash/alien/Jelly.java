package com.noobs2d.smashsmash.alien;

import aurelienribon.tweenengine.equations.Bounce;

import com.noobs2d.smashsmash.screen.AlienEventListener;
import com.noobs2d.tweenengine.utils.DynamicAnimation;
import com.noobs2d.tweenengine.utils.DynamicDisplay.DynamicRegistration;
import com.noobs2d.tweenengine.utils.DynamicSprite;

/**
 * Typical 1-hit alien. Only attacks if stunned and not smashed again.
 * 
 * @author Julious Cious Igmen <jcigmen@gmail.com>
 */
public class Jelly extends Alien {

    public static final String RESOURCE = "data/gfx/ALIENS.pack";
    public static final String SHAPE = "JELLY-SHAPE";
    public static final String ATTACK_EYES = "JELLY-ATTACK-EYES";
    public static final String ATTACK_MOUTH = "JELLY-ATTACK-MOUTH";
    public static final String IDLE_EYES = "JELLY-IDLE-EYES";
    public static final String IDLE_MOUTH = "JELLY-IDLE-MOUTH";
    public static final String BLINK_EYES = "JELLY-BLINK-EYES";
    public static final String SMASHED_MOUTH = "JELLY-SMASHED-MOUTH";
    public static final String SMASHED_SHAPE = "JELLY-SMASHED-SHAPE";
    public static final String STUNNED_LEFT_EYE = "JELLY-STUNNED-LEFTEYE";
    public static final String STUNNED_RIGHT_EYE = "JELLY-STUNNED-RIGHTEYE";
    public static final String STUNNED_SHAPE = "JELLY-STUNNED-SHAPE";
    public static final String STUNNED_MOUTH = "JELLY-STUNNED-MOUTH";
    public static final String EXPLODE = "JELLY-EXPLODE";

    public Jelly(AlienEventListener listener) {
	setAlienEventListener(listener);

	initRisingState();
	initAttackingState();
	initWaitingState();
	initStunnedState();
	initExplodeState();
	initSmashedState();
	initHidingState();
    }

    /** Always 1 no matter what the state is. */
    @Override
    public int getScore() {
	return 1;
    }

    @Override
    protected void initAttackingState() {
	DynamicSprite shape = new DynamicSprite(AliensArt.jellyShape, 0, 0, DynamicRegistration.BOTTOM_CENTER);
	shape.setName(SHAPE);

	DynamicSprite eyes = new DynamicSprite(AliensArt.jellyAttackEyes, 0, 161);
	eyes.setName(ATTACK_EYES);

	DynamicSprite mouth = new DynamicSprite(AliensArt.jellyAttackMouth, 0, 156);
	mouth.setName(ATTACK_MOUTH);

	attackingState.add(shape);
	attackingState.add(eyes);
	attackingState.add(mouth);
	attackingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    @Override
    protected void initExplodeState() {
	DynamicAnimation explode = new DynamicAnimation(0.04f, AliensArt.jellyExplode);
	explode.setRegistration(DynamicRegistration.BOTTOM_CENTER);
	explode.setName(EXPLODE);

	explodeState.add(explode);
    }

    @Override
    protected void initHidingState() {
	DynamicSprite shape = new DynamicSprite(AliensArt.jellyShape, 0, 0, DynamicRegistration.BOTTOM_CENTER);
	shape.setName(SHAPE);

	DynamicSprite eyes = new DynamicSprite(AliensArt.jellyIdleEyes, 0, 154, DynamicRegistration.BOTTOM_CENTER);
	eyes.setName(IDLE_EYES);

	DynamicSprite mouth = new DynamicSprite(AliensArt.jellyIdleMouth, 0, 129, DynamicRegistration.BOTTOM_CENTER);
	mouth.setName(IDLE_MOUTH);

	hidingState.add(shape);
	hidingState.add(eyes);
	hidingState.add(mouth);
	hidingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    @Override
    protected void initRisingState() {
	DynamicSprite shape = new DynamicSprite(AliensArt.jellyShape, 0, 0, DynamicRegistration.BOTTOM_CENTER);
	shape.setName(SHAPE);

	DynamicSprite eyes = new DynamicSprite(AliensArt.jellyIdleEyes, 0, 154, DynamicRegistration.BOTTOM_CENTER);
	eyes.setName(IDLE_EYES);

	DynamicSprite mouth = new DynamicSprite(AliensArt.jellyIdleMouth, 0, 129, DynamicRegistration.BOTTOM_CENTER);
	mouth.setName(IDLE_MOUTH);

	risingState.add(shape);
	risingState.add(eyes);
	risingState.add(mouth);
	risingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    @Override
    protected void initSmashedState() {
	DynamicSprite shape = new DynamicSprite(AliensArt.jellySmashedShape, 0, 0, DynamicRegistration.BOTTOM_CENTER);
	shape.setName(SHAPE);

	DynamicSprite eyes = new DynamicSprite(AliensArt.jellyIdleEyes, 0, 153);
	eyes.setName(IDLE_EYES);

	DynamicSprite mouth = new DynamicSprite(AliensArt.jellySmashedMouth, 0, 155);
	mouth.setName(SMASHED_MOUTH);

	smashedState.add(shape);
	smashedState.add(eyes);
	smashedState.add(mouth);
	smashedState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    @Override
    protected void initStunnedState() {
	DynamicSprite shape = new DynamicSprite(AliensArt.jellyStunnedShape, 0, 0, DynamicRegistration.BOTTOM_CENTER);
	shape.setName(STUNNED_SHAPE);

	DynamicSprite leftEye = new DynamicSprite(AliensArt.jellyStunnedLeftEye, -60, 187);
	leftEye.setName(STUNNED_LEFT_EYE);

	DynamicSprite rightEye = new DynamicSprite(AliensArt.jellyStunnedRightEye, 69, 175);
	rightEye.setName(STUNNED_RIGHT_EYE);

	DynamicSprite mouth = new DynamicSprite(AliensArt.jellyStunnedMouth, 0, 127);
	mouth.setName(STUNNED_MOUTH);

	stunnedState.add(shape);
	stunnedState.add(mouth);
	stunnedState.add(leftEye);
	stunnedState.add(rightEye);
	stunnedState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    @Override
    protected void initWaitingState() {
	DynamicSprite shape = new DynamicSprite(AliensArt.jellyShape, 0, 0, DynamicRegistration.BOTTOM_CENTER);
	shape.setName(SHAPE);

	DynamicAnimation eyes = new DynamicAnimation(.35f, AliensArt.jellyIdleEyes, AliensArt.jellyIdleEyes, AliensArt.jellyIdleEyes, AliensArt.jellyBlinkEyes, AliensArt.jellyIdleEyes);
	eyes.setY(154f);
	eyes.setRegistration(DynamicRegistration.BOTTOM_CENTER);
	eyes.setName(IDLE_EYES);

	DynamicSprite mouth = new DynamicSprite(AliensArt.jellyIdleMouth, 0, 129, DynamicRegistration.BOTTOM_CENTER);
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

	// the mouth will also scale up (width & height) 8 times
	int mouthTweenDuration = (int) (1000 * getAttackingStateTime()) / 8;
	DynamicSprite mouth = (DynamicSprite) attackingState.getByName(ATTACK_MOUTH);
	mouth.interpolateScaleXY(1.2f, 1.2f, mouthTweenDuration, false).repeatYoyo(7, 0).start(mouth.getTweenManager());
    }

    @Override
    protected void interpolateExplode() {
	((DynamicAnimation) explodeState.getByName(EXPLODE)).reset();
    }

    @Override
    protected void interpolateHiding() {
	DynamicSprite shape = (DynamicSprite) hidingState.getByName(SHAPE);
	shape.setScaleY(waitingState.getByName(SHAPE).getScaleY());
	shape.interpolateScaleY(0f, (int) (1000 * getHidingStateTime()), true);

	DynamicSprite eyes = (DynamicSprite) hidingState.getByName(IDLE_EYES);
	eyes.setY(waitingState.getByName(IDLE_EYES).getY());
	eyes.setScaleY(1f);
	eyes.interpolateY(0, (int) (1000 * getHidingStateTime()), true);
	eyes.interpolateScaleY(0f, (int) (1000 * getHidingStateTime()), true);

	DynamicSprite mouth = (DynamicSprite) hidingState.getByName(IDLE_MOUTH);
	mouth.setY(waitingState.getByName(IDLE_MOUTH).getY());
	mouth.setScaleY(1f);
	mouth.interpolateY(0, (int) (1000 * getHidingStateTime()), true);
	mouth.interpolateScaleY(0f, (int) (1000 * getHidingStateTime()), true);
    }

    @Override
    protected void interpolateRising(float delay) {
	DynamicSprite shape = (DynamicSprite) risingState.getByName(SHAPE);
	shape.interpolateScaleY(1f, (int) (1000 * getRisingStateTime()), false).delay(delay);
	shape.setScaleY(0f);
	shape.startTweenWithManager();

	DynamicSprite eyes = (DynamicSprite) risingState.getByName(IDLE_EYES);
	eyes.interpolateY(eyes.getY(), (int) (1000 * getRisingStateTime()), false).delay(delay);
	eyes.startTweenWithManager();
	eyes.interpolateScaleY(1f, (int) (1000 * getRisingStateTime()), false).delay(delay);
	eyes.setY(0f);
	eyes.setScaleY(0f);
	eyes.startTweenWithManager();

	DynamicSprite mouth = (DynamicSprite) risingState.getByName(IDLE_MOUTH);
	mouth.interpolateY(mouth.getY(), (int) (1000 * getRisingStateTime()), false).delay(delay);
	mouth.startTweenWithManager();
	mouth.interpolateScaleY(1f, (int) (1000 * getRisingStateTime()), false).delay(delay);
	mouth.setY(0f);
	mouth.setScaleY(0f);
	mouth.startTweenWithManager();
    }

    @Override
    protected void interpolateSmashed(boolean dead) {
	// smashed then hide. this should be 60% of the smashed state duration
	int smashedTweenDuration = (int) (1000 * getSmashedStateTime() * .6);

	DynamicSprite shape = (DynamicSprite) smashedState.getByName(SHAPE);
	shape.setScaleY(waitingState.getByName(SHAPE).getScaleY());
	shape.interpolateScaleY(.8f, smashedTweenDuration, true);

	DynamicSprite eyes = (DynamicSprite) smashedState.getByName(IDLE_EYES);
	eyes.setY(153f);
	eyes.setScaleY(1f);
	eyes.interpolateY(eyes.getY() + 50f, smashedTweenDuration, false).repeatYoyo(1, 0).start(eyes.getTweenManager());

	DynamicSprite mouth = (DynamicSprite) smashedState.getByName(SMASHED_MOUTH);
	mouth.setY(155f);
	mouth.setScaleY(1f);

	if (dead) {
	    // hide or "shrink" tween. all tween has a delay based on the duration of the smash tween
	    // because we need to wait for that to finish first
	    int hideTweenDuration = (int) (1000 * getSmashedStateTime() * .4);

	    shape.interpolateScaleY(0f, hideTweenDuration, true).delay(150);
	    eyes.interpolateScaleY(0f, hideTweenDuration, true).delay(150);
	    eyes.interpolateY(0f, hideTweenDuration, true).delay(150);
	    mouth.interpolateScaleY(0f, hideTweenDuration, true).delay(150);
	    mouth.interpolateY(0f, hideTweenDuration, true).delay(150);
	}
    }

    @Override
    protected void interpolateStunned() {
	int tweenDuration = 350;

	DynamicSprite shape = (DynamicSprite) stunnedState.getByName(STUNNED_SHAPE);
	shape.setScaleY(waitingState.getByName(SHAPE).getScaleY());
	shape.interpolateScaleY(.85f, Bounce.OUT, tweenDuration, true);

	DynamicSprite leftEye = (DynamicSprite) stunnedState.getByName(STUNNED_LEFT_EYE);
	leftEye.setY(187);
	leftEye.interpolateY(leftEye.getY() * .85f, Bounce.OUT, tweenDuration, true);
	leftEye.setRotation(0);
	leftEye.setY(leftEye.getY() * waitingState.getByName(SHAPE).getScaleY());

	DynamicSprite rightEye = (DynamicSprite) stunnedState.getByName(STUNNED_RIGHT_EYE);
	rightEye.setY(175);
	rightEye.interpolateY(rightEye.getY() * .85f, Bounce.OUT, tweenDuration, true);
	rightEye.setRotation(0);
	rightEye.setY(rightEye.getY() * waitingState.getByName(SHAPE).getScaleY());

	DynamicSprite mouth = (DynamicSprite) stunnedState.getByName(STUNNED_MOUTH);
	mouth.setY(127);
	mouth.interpolateY(mouth.getY() * .85f, Bounce.OUT, tweenDuration, true);
	mouth.setY(mouth.getY() * waitingState.getByName(SHAPE).getScaleY());
    }

    @Override
    protected void interpolateWaiting() {
	int tweenDuration = (int) (1000 * getWaitingStateTime() / 4);

	DynamicSprite shape = (DynamicSprite) waitingState.getByName(SHAPE);
	shape.killTween();
	shape.setScaleY(1f);
	shape.interpolateScaleY(.90f, tweenDuration, false);
	shape.getTween().repeatYoyo(3, 0).start(shape.getTweenManager());

	DynamicAnimation eyes = (DynamicAnimation) waitingState.getByName(IDLE_EYES);
	eyes.killTween();
	eyes.setY(154f);
	eyes.interpolateY(134f, tweenDuration, false).repeatYoyo(3, 0).start(eyes.getTweenManager());

	DynamicSprite mouth = (DynamicSprite) waitingState.getByName(IDLE_MOUTH);
	mouth.killTween();
	mouth.setY(129f);
	mouth.interpolateY(109f, tweenDuration, false).repeatYoyo(3, 0).start(mouth.getTweenManager());
    }

    /** After being stunned, Jelly will attack if not smashed. */
    @Override
    protected void updateStunned(float deltaTime) {
	stunnedState.update(deltaTime);
	if (getElapsedTimeVisible() >= stunnedStateTime) {
	    setElapsedTimeVisible(0);
	    attack();
	}
    }

    /** Jelly will just continue on waiting and idling, then hides. It only attacks if stunned and not smashed again. */
    @Override
    protected void updateWaiting(float deltaTime) {
	waitingState.update(deltaTime);
	if (getElapsedTimeVisible() >= waitingStateTime)
	    hide();
    }

}
