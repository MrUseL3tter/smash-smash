package com.nullsys.smashsmash;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.noobs2d.tweenengine.utils.DynamicAnimation;

public class Coin extends DynamicAnimation {

    public Coin(float frameDuration, List<TextureRegion> keyFrames) {
	super(frameDuration, keyFrames);
	setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    public Coin(float frameDuration, TextureRegion... keyFrames) {
	super(frameDuration, keyFrames);
	setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    public Coin(Texture texture, int offsetX, int offsetY, int frameWidth, int frameHeight, int framesPerRow, int frameCount, float frameDuration) {
	super(texture, offsetX, offsetY, frameWidth, frameHeight, framesPerRow, frameCount, frameDuration);
	setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    @Override
    public void update(float deltaTime) {
	super.update(deltaTime);
	if (timeElapsed > 5f && color.a == 1f)
	    color.a = .5f;
	else if (timeElapsed > 5f && color.a == .5f)
	    color.a = 1f;
    }
}
