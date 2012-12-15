package com.nullsys.smashsmash.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.noobs2d.tweenengine.utils.DynamicScreen;
import com.noobs2d.tweenengine.utils.DynamicSprite;
import com.nullsys.smashsmash.Art;
import com.nullsys.smashsmash.Settings;

public class HammerSelectionScreen extends DynamicScreen implements GestureListener {

    private DynamicSprite test;

    private float velX, velY;

    private boolean flinging = false;

    private boolean panning = false;

    public HammerSelectionScreen(Game game) {
	super(game, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
	test = new DynamicSprite(Art.pukes.findRegion("PUKE_GREEN"), Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
	System.out.println("FLING");
	flinging = true;
	velX = velocityX * 0.5f;
	velY = velocityY * 0.5f;
	return false;
    }

    @Override
    public boolean longPress(float x, float y) {
	System.out.println("LONG PRESS");
	return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
	System.out.println("PAN");
	panning = true;
	if (test.getY() <= 600 || test.getY() >= 300)
	    test.setY(test.getY() - deltaY);
	return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public void render(float delta) {
	// TODO Auto-generated method stub
	super.render(delta);

	spriteBatch.begin();
	test.render(spriteBatch);
	test.update(delta);
	spriteBatch.end();

	if (flinging) {
	    velX *= 0.98f;
	    velY *= 0.98f;
	    test.setY(test.getY() - velY * delta);
	    if (Math.abs(velX) < 0.01f)
		velX = 0;
	    if (Math.abs(velY) < 0.01f)
		velY = 0;
	}

	if (!panning && test.getY() >= 600) {
	    flinging = false;
	    test.setY(test.getY() - 10f);
	}

	if (!panning && test.getY() < 300) {
	    flinging = false;
	    test.setY(test.getY() + 10f);
	}
    }

    @Override
    public void show() {
	Gdx.input.setInputProcessor(new GestureDetector(20, 0.5f, 2, 0.15f, this) {

	    @Override
	    public boolean touchUp(float x, float y, int pointer, int button) {
		panning = false;
		return super.touchUp(x, y, pointer, button);
	    }

	});
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
	System.out.println("TAP");
	return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
	System.out.println("DOWN");
	flinging = false;
	return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
	System.out.println("ZOOM");
	return false;
    }

}
