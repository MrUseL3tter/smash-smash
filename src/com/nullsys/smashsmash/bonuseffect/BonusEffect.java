package com.nullsys.smashsmash.bonuseffect;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.noobs2d.tweenengine.utils.DynamicDisplay;
import com.noobs2d.tweenengine.utils.DynamicSprite;
import com.nullsys.smashsmash.User;

public abstract class BonusEffect extends DynamicDisplay {

    protected static final int DEFAULT_DURATION = 10;

    public static final int HAMMER_TIME = 1;
    public static final int SCORE_FRENZY = 2;
    public static final int COIN_RAIN = 4;
    public static final int STUN_EXPLOSION = 5;
    public static final int INVULNERABILITY = 6;
    public static final int GOLD_SNAP = 7;

    public DynamicSprite body;
    public DynamicSprite pinwheel;
    public int type;

    float secondsPassed;
    float delta = 0;

    @Override
    public Rectangle getBounds() {
	float x = position.x, y = position.y;
	float width = body.getBounds().width, height = body.getBounds().height;
	bounds.width = width * scale.x;
	bounds.height = height * scale.y;
	switch (registration) {
	    case BOTTOM_CENTER:
		bounds.x = x - width / 2;
		break;
	    case BOTTOM_RIGHT:
		bounds.x = x - width;
		break;
	    case CENTER_CENTER:
		bounds.x = x - width / 2;
		bounds.y = y - height / 2;
		break;
	    case LEFT_CENTER:
		bounds.x = x - width;
		bounds.y = y - height / 2;
		break;
	    case TOP_CENTER:
		bounds.x = x - width / 2;
		bounds.y = y - height;
		break;
	    case TOP_LEFT:
		bounds.x = x - width;
		bounds.y = y - height;
		break;
	    case TOP_RIGHT:
		bounds.y = y - height;
		break;
	}
	return bounds;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
	if (visible) {
	    pinwheel.color.set(color);
	    pinwheel.render(spriteBatch);
	    body.color.set(color);
	    body.render(spriteBatch);
	}
    }

    @Override
    public void setRegistration(DynamicRegistration registration) {
	this.registration = registration;
	float x = position.x, y = position.y;
	float width = body.getWidth(), height = body.getHeight();
	switch (registration) {
	    case BOTTOM_CENTER:
		origin.set(x - width * scale.x / 2, y);
		break;
	    case BOTTOM_LEFT:
		origin.set(x, y);
		break;
	    case BOTTOM_RIGHT:
		origin.set(x - width * scale.x, y);
		break;
	    case CENTER_CENTER:
		origin.set(width / 2, height / 2);
		break;
	    case LEFT_CENTER:
		origin.set(x - width * scale.x, y - height * scale.y / 2);
		break;
	    case RIGHT_CENTER:
		origin.set(x, y - height * scale.y / 2);
		break;
	    case TOP_CENTER:
		origin.set(x - width * scale.x / 2, y - height * scale.y);
		break;
	    case TOP_LEFT:
		origin.set(x - width * scale.x, y - height * scale.y);
		break;
	    case TOP_RIGHT:
		origin.set(x, y - height * scale.y);
		break;
	}
    }

    public abstract void trigger();

    @Override
    public void update(float deltaTime) {
	body.update(deltaTime);
	body.position.set(position);
	pinwheel.update(deltaTime);
	pinwheel.position.set(position);
	if (secondsPassed >= DEFAULT_DURATION)
	    User.bonusEffects.remove(this);

	if (delta >= 1 && (int) delta % 1 == 0) {
	    secondsPassed++;
	    delta = 0;
	}
	delta += deltaTime;
	updateTween();
    }
}
