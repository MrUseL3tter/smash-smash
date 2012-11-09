package com.nullsys.smashsmash.bonuseffect;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Linear;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.noobs2d.tweenengine.utils.DynamicSprite;
import com.nullsys.smashsmash.Art;
import com.nullsys.smashsmash.User;

public class HammerTime extends BonusEffect {

    public HammerTime(Vector2 position) {
	super.position.set(position);
	body = new DynamicSprite(new TextureRegion(Art.bonusEffects, 0, 0, 115, 129), position.x, position.y);
	pinwheel = new DynamicSprite(new TextureRegion(Art.bonusEffects, 486, 0, 231, 231), position.x, position.y);
	type = HAMMER_TIME;
	pinwheel.interpolateRotation(360, Linear.INOUT, 1000, false).repeat(Tween.INFINITY, 0).start(pinwheel.tweenManager);
    }

    public HammerTime(Vector2 position, float duration) {
	super.position.set(position);
	body = new DynamicSprite(new TextureRegion(Art.bonusEffects, 0, 0, 115, 129), position.x, position.y);
	pinwheel = new DynamicSprite(new TextureRegion(Art.bonusEffects, 486, 0, 231, 231), position.x, position.y);
	type = HAMMER_TIME;
	pinwheel.interpolateRotation(360, Linear.INOUT, 1000, false).repeat(Tween.INFINITY, 0).start(pinwheel.tweenManager);
	this.duration = duration;
    }

    @Override
    public void trigger() {
	active = true;
	User.bonusEffects.add(this);
	secondsElapsed = 0;
    }
}
