package com.nullsys.smashsmash.screen;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Back;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.noobs2d.tweenengine.utils.DynamicButton;
import com.noobs2d.tweenengine.utils.DynamicScreen;
import com.noobs2d.tweenengine.utils.DynamicSprite;
import com.noobs2d.tweenengine.utils.DynamicText;
import com.noobs2d.tweenengine.utils.DynamicValue;
import com.nullsys.smashsmash.Art;
import com.nullsys.smashsmash.Fonts;
import com.nullsys.smashsmash.Settings;

public class ResultScreen extends DynamicScreen implements TweenCallback {

    SmashSmashStage stage;

    DynamicText stageEndLabel;
    DynamicText score;
    DynamicText yourScore;
    DynamicValue scoreValue;
    DynamicSprite newHighscore;
    DynamicSprite blackFill;
    DynamicButton hammers;
    DynamicButton retry;
    DynamicButton menu;

    public ResultScreen(Game game, SmashSmashStage stage) {
	super(game, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
	this.stage = stage;
	this.stage.setUIVisible(false);
	this.stage.setAllowSpawn(false);
	//	stage.pause();

	stageEndLabel = new DynamicText(Fonts.actionJackson115, stage instanceof ArcadeStageScreen ? "TIME'S UP!" : "");
	stageEndLabel.setPosition(-(Settings.SCREEN_WIDTH / 2), Settings.SCREEN_HEIGHT / 2);
	stageEndLabel.interpolateX(Settings.SCREEN_WIDTH / 2, 500, true);
	stageEndLabel.interpolateX(Settings.SCREEN_WIDTH / 2 + Settings.SCREEN_WIDTH, 500, true).delay(stage instanceof ArcadeStageScreen ? 2000 : 1000);
	stageEndLabel.tween.setCallback(this);
	stageEndLabel.tween.setCallbackTriggers(COMPLETE);
	yourScore = new DynamicText(Fonts.bdCartoonShoutx42, "Your score:");
	yourScore.setPosition(Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT + Settings.SCREEN_HEIGHT / 2);
	score = new DynamicText(Fonts.akaDylanCollage64, "0");
	score.setPosition(Settings.SCREEN_WIDTH / 2, 0 - Settings.SCREEN_HEIGHT / 2);
	score.setScale(4f, 4f);
	scoreValue = new DynamicValue(0, stage.session.score, 1000, 3000);
	scoreValue.tween.setCallback(this);
	scoreValue.tween.setCallbackTriggers(COMPLETE);
	newHighscore = new DynamicSprite(Art.result.findRegion("NEW-HIGHSCORE"), 0, 0);
	newHighscore.setColor(1f, 1f, 1f, 0f);
	blackFill = new DynamicSprite(new TextureRegion(Art.blackFill), Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);
	blackFill.setScale(15f, 15f);
	blackFill.setColor(new Color(1f, 1f, 1f, 0f));

	TextureRegion upstate, downstate, hoverstate;
	upstate = Art.result.findRegion("HAMMERS");
	downstate = Art.result.findRegion("HAMMERS");
	hoverstate = Art.result.findRegion("HAMMERS");
	hammers = new DynamicButton(upstate, hoverstate, downstate, new Vector2(450, 170));
	hammers.setScale(0f, 0f);

	upstate = Art.result.findRegion("RETRY");
	downstate = Art.result.findRegion("RETRY");
	hoverstate = Art.result.findRegion("RETRY");
	retry = new DynamicButton(upstate, hoverstate, downstate, new Vector2(640, 170));
	retry.setScale(0f, 0f);

	upstate = Art.result.findRegion("MENU");
	downstate = Art.result.findRegion("MENU");
	hoverstate = Art.result.findRegion("MENU");
	menu = new DynamicButton(upstate, hoverstate, downstate, new Vector2(830, 170));
	menu.setScale(0f, 0f);

    }

    @Override
    public void onEvent(int type, BaseTween<?> source) {
	if (type == COMPLETE)
	    if (source.equals(scoreValue.tween) && (scoreValue.value > Settings.arcadeHighscore || scoreValue.value > Settings.endlessHighscore)) {
		if (stage instanceof ArcadeStageScreen)
		    Settings.arcadeHighscore = (int) scoreValue.value;
		else
		    Settings.endlessHighscore = (int) scoreValue.value;
		newHighscore.setX(score.bitmapFont.getBounds(score.text).width / 2 + Settings.SCREEN_WIDTH / 2);
		newHighscore.setY(score.getY() - score.bitmapFont.getBounds(score.text).height / 2 + 40);
		newHighscore.interpolateX(newHighscore.getX(), 500, true);
		newHighscore.interpolateAlpha(1f, 500, true);
		newHighscore.tween.setCallback(this);
		newHighscore.tween.setCallbackTriggers(COMPLETE);
		newHighscore.setX(newHighscore.getX() - 200);
	    } else if (source.equals(stageEndLabel.tween)) {
		blackFill.interpolateAlpha(.75f, 200, true);
		yourScore.interpolateY(580, 500, true);
		score.interpolateY(Settings.SCREEN_HEIGHT / 2, 500, true);
		score.tween.setCallback(this);
	    } else if (source.equals(score.tween)) {
		yourScore.interpolateY(yourScore.getY() + 50, 500, true).delay(1000);
		score.interpolateY(score.getY() + 50, 500, true).delay(1000);
		hammers.interpolateScaleXY(.75f, .75f, Back.OUT, 500, true).delay(1200);
		retry.interpolateScaleXY(.75f, .75f, Back.OUT, 500, true).delay(1000);
		menu.interpolateScaleXY(.75f, .75f, Back.OUT, 500, true).delay(1400);
	    }
    }

    @Override
    public void onTouchDown(float x, float y, int pointer, int button) {
	x *= (float) Settings.SCREEN_WIDTH / Gdx.graphics.getWidth();
	y = (Gdx.graphics.getHeight() * camera.zoom - y) * Settings.SCREEN_HEIGHT / Gdx.graphics.getHeight();
	if (menu.getBounds().contains(x, y))
	    game.setScreen(new MainMenuScreen(game));
	else if (retry.getBounds().contains(x, y))
	    if (stage instanceof FuryStageScreen)
		game.setScreen(new FuryStageScreen(game));
	    else if (stage instanceof ArcadeStageScreen)
		game.setScreen(new ArcadeStageScreen(game));
	    else
		game.setScreen(new EndlessStageScreen(game));
    }

    @Override
    public void render(float delta) {
	super.render(delta);

	spriteBatch.begin();
	spriteBatch.draw(Art.bonusEffects, 0, 0);
	spriteBatch.end();
	scoreValue.update(delta);

	stage.render(delta);
	spriteBatch.begin();
	blackFill.render(spriteBatch);
	blackFill.update(delta);
	stageEndLabel.render(spriteBatch);
	stageEndLabel.update(delta);
	yourScore.render(spriteBatch);
	yourScore.update(delta);
	score.render(spriteBatch);
	score.update(delta);
	score.text = "" + (int) scoreValue.value;
	newHighscore.render(spriteBatch);
	newHighscore.update(delta);
	hammers.render(spriteBatch);
	hammers.update(delta);
	retry.render(spriteBatch);
	retry.update(delta);
	menu.render(spriteBatch);
	menu.update(delta);
	spriteBatch.end();

	//	System.out.println("[ResultScreen#render(float)] blackFill.color.a: " + blackFill.getColor().a);
    }

}
