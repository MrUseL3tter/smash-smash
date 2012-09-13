package com.nullsys.smashsmash.bonuseffect;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Linear;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.noobs2d.tweenengine.utils.DynamicSprite;
import com.nullsys.smashsmash.Art;
import com.nullsys.smashsmash.User;

public class ScoreFrenzy extends BonusEffect {

    public ScoreFrenzy(Vector2 position) {
	super.position.set(position);
	body = new DynamicSprite(new TextureRegion(Art.bonusEffects, 115, 0, 134, 134), position.x, position.y);
	pinwheel = new DynamicSprite(new TextureRegion(Art.bonusEffects, 252, 0, 231, 231), position.x, position.y);
	type = SCORE_FRENZY;
	pinwheel.interpolateRotation(360, Linear.INOUT, 1000, false).repeat(Tween.INFINITY, 0).start(pinwheel.tweenManager);
    }

    @Override
    public void trigger() {
	User.bonusEffects.add(new ScoreFrenzy(position));
	secondsPassed = 0;
    }
}
