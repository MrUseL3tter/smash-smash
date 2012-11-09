package com.nullsys.smashsmash;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GoldBar extends Coin {

    public GoldBar(float frameDuration, TextureRegion... keyFrames) {
	super(frameDuration, keyFrames);
    }

    public GoldBar(Texture texture, int offsetX, int offsetY, int frameWidth, int frameHeight, int framesPerRow, int frameCount, float frameDuration) {
	super(texture, offsetX, offsetY, frameWidth, frameHeight, framesPerRow, frameCount, frameDuration);
    }

}
