package com.nullsys.smashsmash.alien;

import java.util.ArrayList;

import com.noobs2d.tweenengine.utils.DynamicAnimation;
import com.noobs2d.tweenengine.utils.DynamicAnimationGroup;
import com.nullsys.smashsmash.Sounds;
import com.nullsys.smashsmash.stage.SmashSmashStage;

public class Jelly extends Alien {

    public Jelly(SmashSmashStage stage) {
	super.stage = stage;
	setRegistration(DynamicRegistration.BOTTOM_CENTER);
	SFXspawn = Sounds.jellySpawn;
	SFXsmash = Sounds.jellySmash;
	initRisingState();
	initAttackingState();
	initWaitingState();
	initStunnedState();
	initSmashedState();
	initHidingState();
    }

    private void initAttackingState() {
	DynamicAnimation jellyShapeAnim = new DynamicAnimation(0.5f, AliensArt.jellyShape);
	jellyShapeAnim.setRegistration(DynamicRegistration.BOTTOM_CENTER);

	DynamicAnimation jellyWaitingEyeAnim = new DynamicAnimation(0.5f, AliensArt.jellyWaitingEye, AliensArt.jellyWaitingEye, AliensArt.jellyBlinkEye, AliensArt.jellyWaitingEye);
	jellyWaitingEyeAnim.position.y = 78;
	jellyWaitingEyeAnim.setRegistration(DynamicRegistration.BOTTOM_CENTER);

	DynamicAnimation jellyWaitingMouthAnim = new DynamicAnimation(0.5f, AliensArt.jellyWaitingMouth);
	jellyWaitingMouthAnim.position.y = 66;
	jellyWaitingMouthAnim.setRegistration(DynamicRegistration.BOTTOM_CENTER);

	ArrayList<DynamicAnimation> waitingStateAnims = new ArrayList<DynamicAnimation>();
	waitingStateAnims.add(jellyShapeAnim);
	waitingStateAnims.add(jellyWaitingEyeAnim);
	waitingStateAnims.add(jellyWaitingMouthAnim);

	attackingState = new DynamicAnimationGroup(waitingStateAnims);
	attackingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    private void initHidingState() {
	DynamicAnimation jellyShapeAnim = new DynamicAnimation(0.5f, AliensArt.jellyShape);
	jellyShapeAnim.setRegistration(DynamicRegistration.BOTTOM_CENTER);

	DynamicAnimation jellyWaitingEyeAnim = new DynamicAnimation(0.5f, AliensArt.jellyWaitingEye, AliensArt.jellyWaitingEye, AliensArt.jellyBlinkEye, AliensArt.jellyWaitingEye);
	jellyWaitingEyeAnim.position.y = 78;
	jellyWaitingEyeAnim.setRegistration(DynamicRegistration.BOTTOM_CENTER);

	DynamicAnimation jellyWaitingMouthAnim = new DynamicAnimation(0.5f, AliensArt.jellyWaitingMouth);
	jellyWaitingMouthAnim.position.y = 66;
	jellyWaitingMouthAnim.setRegistration(DynamicRegistration.BOTTOM_CENTER);

	ArrayList<DynamicAnimation> waitingStateAnims = new ArrayList<DynamicAnimation>();
	waitingStateAnims.add(jellyShapeAnim);
	waitingStateAnims.add(jellyWaitingEyeAnim);
	waitingStateAnims.add(jellyWaitingMouthAnim);

	hidingState = new DynamicAnimationGroup(waitingStateAnims);
	hidingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    private void initRisingState() {
	DynamicAnimation jellyShapeAnim = new DynamicAnimation(0.5f, AliensArt.jellyShape);
	jellyShapeAnim.setRegistration(DynamicRegistration.BOTTOM_CENTER);

	DynamicAnimation jellyWaitingEyeAnim = new DynamicAnimation(0.5f, AliensArt.jellyWaitingEye, AliensArt.jellyWaitingEye, AliensArt.jellyBlinkEye, AliensArt.jellyWaitingEye);
	jellyWaitingEyeAnim.position.y = 78;
	jellyWaitingEyeAnim.setRegistration(DynamicRegistration.BOTTOM_CENTER);

	DynamicAnimation jellyWaitingMouthAnim = new DynamicAnimation(0.5f, AliensArt.jellyWaitingMouth);
	jellyWaitingMouthAnim.position.y = 66;
	jellyWaitingMouthAnim.setRegistration(DynamicRegistration.BOTTOM_CENTER);

	ArrayList<DynamicAnimation> waitingStateAnims = new ArrayList<DynamicAnimation>();
	waitingStateAnims.add(jellyShapeAnim);
	waitingStateAnims.add(jellyWaitingEyeAnim);
	waitingStateAnims.add(jellyWaitingMouthAnim);

	risingState = new DynamicAnimationGroup(waitingStateAnims);
	risingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    private void initSmashedState() {
	DynamicAnimation jellyShapeAnim = new DynamicAnimation(0.5f, AliensArt.jellyShape);
	jellyShapeAnim.setRegistration(DynamicRegistration.BOTTOM_CENTER);

	DynamicAnimation jellyStunnedEyeAnim = new DynamicAnimation(0.5f, AliensArt.jellyHitEye);
	jellyStunnedEyeAnim.position.y = 86;

	DynamicAnimation jellyStunnedMouthAnim = new DynamicAnimation(0.5f, AliensArt.jellyWaitingMouth);
	jellyStunnedMouthAnim.position.y = 66;
	jellyStunnedMouthAnim.setRegistration(DynamicRegistration.BOTTOM_CENTER);

	ArrayList<DynamicAnimation> stunnedStateAnims = new ArrayList<DynamicAnimation>();
	stunnedStateAnims.add(jellyShapeAnim);
	stunnedStateAnims.add(jellyStunnedEyeAnim);
	stunnedStateAnims.add(jellyStunnedMouthAnim);

	smashedState = new DynamicAnimationGroup(stunnedStateAnims);
	smashedState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    private void initStunnedState() {
	DynamicAnimation jellyShapeAnim = new DynamicAnimation(0.5f, AliensArt.jellyShape);
	jellyShapeAnim.setRegistration(DynamicRegistration.BOTTOM_CENTER);

	DynamicAnimation jellyStunnedEyeAnim = new DynamicAnimation(0.5f, AliensArt.jellyHitEye);
	jellyStunnedEyeAnim.position.y = 86;

	DynamicAnimation jellyStunnedMouthAnim = new DynamicAnimation(0.5f, AliensArt.jellyWaitingMouth);
	jellyStunnedMouthAnim.position.y = 66;
	jellyStunnedMouthAnim.setRegistration(DynamicRegistration.BOTTOM_CENTER);

	ArrayList<DynamicAnimation> stunnedStateAnims = new ArrayList<DynamicAnimation>();
	stunnedStateAnims.add(jellyShapeAnim);
	stunnedStateAnims.add(jellyStunnedEyeAnim);
	stunnedStateAnims.add(jellyStunnedMouthAnim);

	stunnedState = new DynamicAnimationGroup(stunnedStateAnims);
	stunnedState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    private void initWaitingState() {
	DynamicAnimation jellyShapeAnim = new DynamicAnimation(0.5f, AliensArt.jellyShape);
	jellyShapeAnim.setRegistration(DynamicRegistration.BOTTOM_CENTER);

	DynamicAnimation jellyWaitingEyeAnim = new DynamicAnimation(0.5f, AliensArt.jellyWaitingEye, AliensArt.jellyWaitingEye, AliensArt.jellyBlinkEye, AliensArt.jellyWaitingEye);
	jellyWaitingEyeAnim.position.y = 78;
	jellyWaitingEyeAnim.setRegistration(DynamicRegistration.BOTTOM_CENTER);

	DynamicAnimation jellyWaitingMouthAnim = new DynamicAnimation(0.5f, AliensArt.jellyWaitingMouth);
	jellyWaitingMouthAnim.position.y = 66;
	jellyWaitingMouthAnim.setRegistration(DynamicRegistration.BOTTOM_CENTER);

	ArrayList<DynamicAnimation> waitingStateAnims = new ArrayList<DynamicAnimation>();
	waitingStateAnims.add(jellyShapeAnim);
	waitingStateAnims.add(jellyWaitingEyeAnim);
	waitingStateAnims.add(jellyWaitingMouthAnim);

	waitingState = new DynamicAnimationGroup(waitingStateAnims);
	waitingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

}
