package com.nullsys.smashsmash.alien;

import com.badlogic.gdx.graphics.Color;
import com.nullsys.smashsmash.screen.SmashSmashStage;

public class HammerTimeJelly extends Jelly {

    public HammerTimeJelly(SmashSmashStage stage) {
	super(stage);
	risingState.setColor(Color.GREEN);
	waitingState.setColor(Color.GREEN);
	attackingState.setColor(Color.GREEN);
	stunnedState.setColor(Color.GREEN);
	smashedState.setColor(Color.GREEN);
	hidingState.setColor(Color.GREEN);
    }

}
