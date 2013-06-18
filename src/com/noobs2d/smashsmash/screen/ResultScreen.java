package com.noobs2d.smashsmash.screen;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Back;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.noobs2d.smashsmash.Art;
import com.noobs2d.smashsmash.Fonts;
import com.noobs2d.smashsmash.Settings;
import com.noobs2d.tweenengine.utils.DynamicButton;
import com.noobs2d.tweenengine.utils.DynamicScreen;
import com.noobs2d.tweenengine.utils.DynamicSprite;
import com.noobs2d.tweenengine.utils.DynamicText;
import com.noobs2d.tweenengine.utils.DynamicValue;

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
	stageEndLabel.setTweenCallback(this);
	stageEndLabel.setTweenCallbackTriggers(COMPLETE);
	yourScore = new DynamicText(Fonts.bdCartoonShoutx42, "Your score:");
	yourScore.setPosition(Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT + Settings.SCREEN_HEIGHT / 2);
	score = new DynamicText(Fonts.akaDylanCollage64, "0");
	score.setPosition(Settings.SCREEN_WIDTH / 2, 0 - Settings.SCREEN_HEIGHT / 2);
	score.setScale(4f, 4f);
	scoreValue = new DynamicValue(0, stage.getSession().getScore(), 1000, 3000);
	scoreValue.setTweenCallback(this);
	scoreValue.setTweenCallbackTriggers(COMPLETE);
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
	    if (source.equals(scoreValue.getTween()) && (scoreValue.getValue() > Settings.arcadeHighscore || scoreValue.getValue() > Settings.endlessHighscore)) {
		if (stage instanceof ArcadeStageScreen)
		    Settings.arcadeHighscore = (int) scoreValue.getValue();
		else
		    Settings.endlessHighscore = (int) scoreValue.getValue();
		newHighscore.setX(score.getBitmapFont().getBounds(score.getText()).width / 2 + Settings.SCREEN_WIDTH / 2);
		newHighscore.setY(score.getY() - score.getBitmapFont().getBounds(score.getText()).height / 2 + 40);
		newHighscore.interpolateX(newHighscore.getX(), 500, true);
		newHighscore.interpolateAlpha(1f, 500, true);
		newHighscore.setTweenCallback(this);
		newHighscore.setTweenCallbackTriggers(COMPLETE);
		newHighscore.setX(newHighscore.getX() - 200);
	    } else if (source.equals(stageEndLabel.getTween())) {
		blackFill.interpolateAlpha(.75f, 200, true);
		yourScore.interpolateY(580, 500, true);
		score.interpolateY(Settings.SCREEN_HEIGHT / 2, 500, true);
		score.setTweenCallback(this);
	    } else if (source.equals(score.getTween())) {
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
		game.setScreen(new FuryStageScreen(game, true));
	    else if (stage instanceof ArcadeStageScreen)
		game.setScreen(new ArcadeStageScreen(game, true));
	    else
		game.setScreen(new EndlessStageScreen(game));
    }

    @Override
    public void render(float delta) {
	super.render(delta);
	scoreValue.update(delta);

	stage.render(delta);
	batch.begin();
	blackFill.render(batch);
	blackFill.update(delta);
	stageEndLabel.render(batch);
	stageEndLabel.update(delta);
	yourScore.render(batch);
	yourScore.update(delta);
	score.render(batch);
	score.update(delta);
	score.setText("" + (int) scoreValue.getValue());
	newHighscore.render(batch);
	newHighscore.update(delta);
	hammers.render(batch);
	hammers.update(delta);
	retry.render(batch);
	retry.update(delta);
	menu.render(batch);
	menu.update(delta);
	batch.end();

	//	System.out.println("[ResultScreen#render(float)] blackFill.color.a: " + blackFill.getColor().a);
    }
}
