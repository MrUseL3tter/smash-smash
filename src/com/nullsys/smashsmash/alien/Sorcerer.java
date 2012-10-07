package com.nullsys.smashsmash.alien;

import java.util.ArrayList;

import aurelienribon.tweenengine.equations.Linear;
import aurelienribon.tweenengine.equations.Sine;

import com.badlogic.gdx.math.Vector2;
import com.noobs2d.tweenengine.utils.DynamicAnimation;
import com.noobs2d.tweenengine.utils.DynamicAnimationGroup;
import com.noobs2d.tweenengine.utils.DynamicCallback.RemoveFromCollectionOnEnd;
import com.nullsys.smashsmash.Sounds;
import com.nullsys.smashsmash.bonuseffect.HammerTime;
import com.nullsys.smashsmash.bonuseffect.Invulnerability;
import com.nullsys.smashsmash.bonuseffect.ScoreFrenzy;
import com.nullsys.smashsmash.stage.SmashSmashStage;
import com.nullsys.smashsmash.stage.SurvivalStageScreen;

public class Sorcerer extends Alien {

    public Sorcerer(SmashSmashStage stage) {
	super.stage = stage;
	setRegistration(DynamicRegistration.BOTTOM_CENTER);
	SFXspawn = Sounds.jellySpawn;
	SFXsmash = Sounds.jellySmash;
	initAttackingState();
	initHidingState();
	initRisingState();
	initSmashedState();
	initStunnedState();
	initWaitingState();
	waitingStateTime = 500;
    }

    @Override
    public void rise(float delay, float volume) {
	super.rise(delay, volume);
    }

    private void initAttackingState() {
	ArrayList<DynamicAnimation> list = new ArrayList<DynamicAnimation>();
	list.add(new DynamicAnimation(AliensArt.sorcererAttack));
	attackingState = new DynamicAnimationGroup(list);
	attackingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    private void initHidingState() {
	ArrayList<DynamicAnimation> list = new ArrayList<DynamicAnimation>();
	list.add(new DynamicAnimation(AliensArt.sorcererHiding));
	hidingState = new DynamicAnimationGroup(list);
	hidingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    private void initRisingState() {
	ArrayList<DynamicAnimation> list = new ArrayList<DynamicAnimation>();
	list.add(new DynamicAnimation(AliensArt.sorcererRising));
	risingState = new DynamicAnimationGroup(list);
	risingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    private void initSmashedState() {
	ArrayList<DynamicAnimation> list = new ArrayList<DynamicAnimation>();
	list.add(new DynamicAnimation(AliensArt.sorcererSmashed));
	smashedState = new DynamicAnimationGroup(list);
	smashedState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    private void initStunnedState() {
	ArrayList<DynamicAnimation> list = new ArrayList<DynamicAnimation>();
	list.add(new DynamicAnimation(AliensArt.sorcererStunned));
	stunnedState = new DynamicAnimationGroup(list);
	stunnedState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    private void initWaitingState() {
	ArrayList<DynamicAnimation> list = new ArrayList<DynamicAnimation>();
	list.add(new DynamicAnimation(AliensArt.sorcererWaiting));
	waitingState = new DynamicAnimationGroup(list);
	waitingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    @Override
    protected void updateWaiting(float deltaTime) {
	waitingState.update(deltaTime);
	if (upElapsedTime * tweenSpeed >= waitingStateTime) {
	    // Spawn the bonus effects
	    float width = waitingState.getLargestAreaDisplay().getKeyFrame().getRegionWidth();
	    float randomX = position.x - width;
	    float delay = 500;
	    randomX += (float) (Math.random() * width * 2);

	    HammerTime hammerTime = new HammerTime(new Vector2(randomX, position.y));
	    hammerTime.interpolateXY(hammerTime.position.x, hammerTime.position.y + 150, Sine.OUT, 500, true);
	    hammerTime.tween.delay(delay);
	    hammerTime.interpolateAlpha(1f, Linear.INOUT, 150, true);
	    hammerTime.tween.delay(delay);
	    hammerTime.interpolateXY(hammerTime.position.x, hammerTime.position.y, Sine.IN, 500, true);
	    hammerTime.tween.delay(delay + 500);
	    hammerTime.color.a = 0f;
	    hammerTime.interpolateAlpha(0f, Linear.INOUT, 500, true).delay(5000); // The BE disappears after 5 seconds.
	    hammerTime.tween.setCallback(new RemoveFromCollectionOnEnd(stage.bonusEffects, hammerTime));

	    delay += 500;
	    randomX = position.x - width;
	    randomX += (float) (Math.random() * width * 2);

	    Invulnerability invulnerability = new Invulnerability(new Vector2(randomX, position.y));
	    invulnerability.interpolateXY(invulnerability.position.x, invulnerability.position.y + 150, Sine.OUT, 500, true);
	    invulnerability.tween.delay(delay);
	    invulnerability.interpolateAlpha(1f, Linear.INOUT, 150, true);
	    invulnerability.tween.delay(delay);
	    invulnerability.interpolateXY(invulnerability.position.x, invulnerability.position.y, Sine.IN, 500, true);
	    invulnerability.tween.delay(delay + 500);
	    invulnerability.color.a = 0f;
	    invulnerability.interpolateAlpha(0f, Linear.INOUT, 500, true).delay(5000); // The BE disappears after 5 seconds.
	    invulnerability.tween.setCallback(new RemoveFromCollectionOnEnd(stage.bonusEffects, invulnerability));

	    delay += 500;
	    randomX = position.x - width;
	    randomX += (float) (Math.random() * width * 2);

	    ScoreFrenzy scoreFrenzy = new ScoreFrenzy(new Vector2(randomX, position.y));
	    scoreFrenzy.interpolateXY(scoreFrenzy.position.x, scoreFrenzy.position.y + 150, Sine.OUT, 500, true);
	    scoreFrenzy.tween.delay(delay);
	    scoreFrenzy.interpolateAlpha(1f, Linear.INOUT, 150, true);
	    scoreFrenzy.tween.delay(delay);
	    scoreFrenzy.interpolateXY(scoreFrenzy.position.x, scoreFrenzy.position.y, Sine.IN, 500, true);
	    scoreFrenzy.tween.delay(delay + 500);
	    scoreFrenzy.color.a = 0f;
	    scoreFrenzy.interpolateAlpha(0f, Linear.INOUT, 500, true).delay(5000); // The BE disappears after 5 seconds.
	    scoreFrenzy.tween.setCallback(new RemoveFromCollectionOnEnd(stage.bonusEffects, scoreFrenzy));

	    ((SurvivalStageScreen) stage).bonusEffects.add(hammerTime);
	    ((SurvivalStageScreen) stage).bonusEffects.add(invulnerability);
	    ((SurvivalStageScreen) stage).bonusEffects.add(scoreFrenzy);
	    state = AlienState.HIDING;
	    upElapsedTime = 0;
	}
    }
}
