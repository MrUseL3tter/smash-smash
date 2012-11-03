package com.nullsys.smashsmash.stage;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Back;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.noobs2d.tweenengine.utils.DynamicButton;
import com.noobs2d.tweenengine.utils.DynamicScreen;
import com.noobs2d.tweenengine.utils.DynamicSprite;
import com.noobs2d.tweenengine.utils.DynamicText;
import com.nullsys.smashsmash.Art;
import com.nullsys.smashsmash.Settings;

public class MainMenuScreen extends DynamicScreen {

    DynamicButton stats;
    DynamicButton hammers;
    DynamicButton arcade;
    DynamicButton survival;
    DynamicSprite title;
    DynamicText prompt;

    boolean showPlayOptions;

    public MainMenuScreen(Game game) {
	super(game, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
	TextureRegion upstate, hoverstate, downstate;
	upstate = Art.menu.findRegion("STATS");
	hoverstate = Art.menu.findRegion("STATS");
	downstate = Art.menu.findRegion("STATS");
	stats = new DynamicButton(upstate, hoverstate, downstate, new Vector2(984, 41));

	upstate = Art.menu.findRegion("HAMMERS");
	hoverstate = Art.menu.findRegion("HAMMERS");
	downstate = Art.menu.findRegion("HAMMERS");
	hammers = new DynamicButton(upstate, hoverstate, downstate, new Vector2(1182, 41));

	upstate = Art.menu.findRegion("ARCADE");
	hoverstate = Art.menu.findRegion("ARCADE");
	downstate = Art.menu.findRegion("ARCADE");
	arcade = new DynamicButton(upstate, hoverstate, downstate, new Vector2(320, 420));
	arcade.visible = false;

	upstate = Art.menu.findRegion("SURVIVAL");
	hoverstate = Art.menu.findRegion("SURVIVAL");
	downstate = Art.menu.findRegion("SURVIVAL");
	survival = new DynamicButton(upstate, hoverstate, downstate, new Vector2(938, 420));
	survival.visible = false;

	title = new DynamicSprite(Art.menu.findRegion("TITLE"), Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);

	prompt = new DynamicText(new BitmapFont(), "TAP THE SCREEN");
	prompt.setPosition(Settings.SCREEN_WIDTH / 2, 120);
	prompt.setScale(2f, 2f);
	prompt.interpolateAlpha(.5f, 150, false).repeatYoyo(Tween.INFINITY, 150).start(prompt.tweenManager);
    }

    @Override
    public void onTouchUp(float x, float y, int pointer, int button) {
	super.onTouchUp(x, y, pointer, button);
	if (!showPlayOptions && stats.getBounds().contains(x, Settings.SCREEN_HEIGHT - y))
	    System.out.println("STATS TAPPED");
	else if (!showPlayOptions && hammers.getBounds().contains(x, Settings.SCREEN_HEIGHT - y))
	    System.out.println("HAMMERS TAPPED");
	else if (!showPlayOptions) {
	    System.out.println("SHOW PLAY OPTIONS");
	    showPlayOptions = true;
	    arcade.visible = true;
	    arcade.setScale(0f, 0f);
	    arcade.interpolateScaleXY(1f, 1f, Back.OUT, 250, true);
	    survival.visible = true;
	    survival.setScale(0f, 0f);
	    survival.interpolateScaleXY(1f, 1f, Back.OUT, 250, true).delay(150);
	    title.interpolateAlpha(0f, 100, true);
	    stats.interpolateY(stats.getY() - 200, 100, true);
	    hammers.interpolateY(hammers.getY() - 200, 100, true);
	    prompt.visible = false;
	} else if (showPlayOptions && arcade.getBounds().contains(x, Settings.SCREEN_HEIGHT - y))
	    System.out.println("ARCADE");
	else if (showPlayOptions && survival.getBounds().contains(x, Settings.SCREEN_HEIGHT - y))
	    game.setScreen(new SurvivalStageScreen(game));

    }

    @Override
    public void render(float delta) {
	super.render(delta);
	spriteBatch.begin();
	stats.render(spriteBatch);
	stats.update(delta);
	hammers.render(spriteBatch);
	hammers.update(delta);
	arcade.render(spriteBatch);
	arcade.update(delta);
	survival.render(spriteBatch);
	survival.update(delta);
	title.render(spriteBatch);
	title.update(delta);
	prompt.render(spriteBatch);
	prompt.update(delta);
	spriteBatch.end();
    }

}
