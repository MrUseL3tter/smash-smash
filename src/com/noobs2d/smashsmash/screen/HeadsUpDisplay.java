package com.noobs2d.smashsmash.screen;

import java.util.ArrayList;
import java.util.List;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Back;
import aurelienribon.tweenengine.equations.Elastic;
import aurelienribon.tweenengine.equations.Linear;
import aurelienribon.tweenengine.equations.Sine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.noobs2d.smashsmash.Art;
import com.noobs2d.smashsmash.Fonts;
import com.noobs2d.smashsmash.Settings;
import com.noobs2d.smashsmash.User;
import com.noobs2d.smashsmash.alien.AliensArt;
import com.noobs2d.smashsmash.buffeffect.BuffEffect;
import com.noobs2d.tweenengine.utils.DynamicCallback.InvisibleOnEnd;
import com.noobs2d.tweenengine.utils.DynamicDisplay;
import com.noobs2d.tweenengine.utils.DynamicDisplay.DynamicRegistration;
import com.noobs2d.tweenengine.utils.DynamicSprite;
import com.noobs2d.tweenengine.utils.DynamicText;

public class HeadsUpDisplay {

    private SmashSmashStage stage;
    private OrthographicCamera cam = new OrthographicCamera();

    private DynamicSprite[] lifePoints = new DynamicSprite[3];
    private DynamicSprite[] ready = new DynamicSprite[5];
    private DynamicSprite[] seconds = new DynamicSprite[9];
    private DynamicSprite[] smash = new DynamicSprite[6];
    private DynamicText[] timer = new DynamicText[5];

    private List<DynamicDisplay> textPool = new ArrayList<DynamicDisplay>();
    private DynamicText flyout;
    private DynamicText comboCount;
    private DynamicText comboBonus;
    private DynamicText comboName;
    private DynamicText missLabel;
    private DynamicText streakBonus;
    private DynamicText streakName;

    private BitmapFont score;
    private BitmapFont feed;

    public HeadsUpDisplay(SmashSmashStage stage) {
	this.stage = stage;
	score = Fonts.KRONIKA_BLACKSTROKE;
	score.setScale(1.14f);
	comboCount = new DynamicText(Fonts.actionJackson115, "", HAlignment.LEFT);
	comboCount.setWrapWidth(512);
	comboCount.position.set(0, 515);
	comboCount.setRegistration(DynamicRegistration.CENTER_LEFT);
	comboBonus = new DynamicText(Fonts.actionJackson115, "", HAlignment.LEFT);
	comboBonus.setWrapWidth(512);
	comboBonus.setPosition(0, 435);
	comboBonus.setScale(.25f, .25f);
	comboBonus.setRegistration(DynamicRegistration.CENTER_LEFT);
	comboName = new DynamicText(Fonts.bdCartoonShoutx32orange, "", HAlignment.LEFT);
	comboName.position.set(0, 590);
	comboName.setRegistration(DynamicRegistration.CENTER_LEFT);
	feed = new BitmapFont();
	feed.scale(.5f);
	flyout = new DynamicText(Fonts.bdCartoonShoutx32orange, "", HAlignment.CENTER);
	streakBonus = new DynamicText(Fonts.bdCartoonShoutx23orange, "", HAlignment.RIGHT);
	streakBonus.setRegistration(DynamicRegistration.CENTER_RIGHT);
	streakName = new DynamicText(Fonts.bdCartoonShoutx32orange, "", HAlignment.RIGHT);
	streakName.setRegistration(DynamicRegistration.CENTER_RIGHT);
	missLabel = new DynamicText(Fonts.bdCartoonShoutx42, "MISS!", HAlignment.CENTER);
	missLabel.setColor(1f, 1f, 1f, 0f);
	for (int i = 0; i < lifePoints.length; i++) {
	    lifePoints[i] = new DynamicSprite(AliensArt.jellyShape, 55 + 97 * i, 749);
	    lifePoints[i].setScale(.35f, .35f);
	}
	if (stage instanceof ArcadeStageScreen)
	    initTimer();
	initReadyPrompt();
    }

    public void render(SpriteBatch spriteBatch) {
	cam.projection.setToOrtho2D(0f, 0f, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
	spriteBatch.setProjectionMatrix(cam.projection);
	renderTexts(spriteBatch);
	if (stage instanceof EndlessStageScreen)
	    renderLifePoints(spriteBatch);
    }

    public void setFlyoutText(String text, Color color) {
	flyout.setText(text);
	flyout.killTween();
	flyout.color.set(color);
	flyout.position.set(640f, 350f);
	flyout.interpolateXY(640, 400, Elastic.OUT, 2000, true);
	flyout.interpolateAlpha(0f, Linear.INOUT, 250, true).delay(3000);
    }

    public void shakeCombos() {
	comboCount.endTween();
	comboCount.setPosition(0, 515);
	comboCount.interpolateXY(comboCount.position.x, comboCount.position.y + 15, Linear.INOUT, 75, true);
	comboCount.interpolateXY(comboCount.position.x, comboCount.position.y - 15, Linear.INOUT, 75, true).delay(75);
	comboCount.interpolateXY(comboCount.position.x, comboCount.position.y, Linear.INOUT, 75, true).delay(150);
    }

    public void shakeLifePoint() {
	if (stage.getSession().getLifepoints() > 0) {
	    float x = lifePoints[stage.getSession().getLifepoints() - 1].position.x;
	    float y = lifePoints[stage.getSession().getLifepoints() - 1].position.y + 15;
	    lifePoints[stage.getSession().getLifepoints() - 1].interpolateXY(x, y, Linear.INOUT, 75, true);
	    x = lifePoints[stage.getSession().getLifepoints() - 1].position.x;
	    y = lifePoints[stage.getSession().getLifepoints() - 1].position.y - 15;
	    lifePoints[stage.getSession().getLifepoints() - 1].interpolateXY(x, y, Linear.INOUT, 75, true).delay(75);
	    x = lifePoints[stage.getSession().getLifepoints() - 1].position.x;
	    y = lifePoints[stage.getSession().getLifepoints() - 1].position.y;
	    lifePoints[stage.getSession().getLifepoints() - 1].interpolateXY(x, y, Linear.INOUT, 75, true).delay(150);
	    lifePoints[stage.getSession().getLifepoints() - 1].setTweenCallback(new InvisibleOnEnd(lifePoints[stage.getSession().getLifepoints() - 1]));
	}
    }

    public void showBuffEffectPrompt(final int buffEffect, float x, float y) {
	DynamicSprite text = null;
	switch (buffEffect) {
	    case BuffEffect.HAMMER_TIME:
		text = new DynamicSprite(Art.pukes.findRegion("HAMMER_TIME"), x, y);
		break;
	    case BuffEffect.INVULNERABILITY:
		text = new DynamicSprite(Art.pukes.findRegion("INVULNERABILITY"), x, y);
		break;
	    case BuffEffect.SCORE_FRENZY:
		text = new DynamicSprite(Art.pukes.findRegion("SCORE_FRENZY"), x, y);
		break;
	    default:
		assert false;
		break;
	}
	text.setColor(1f, 1f, 1f, 0f);
	text.setScale(2.15f, 2.15f);
	text.interpolateAlpha(1f, 200, true);
	text.interpolateScaleXY(1f, 1f, Back.OUT, 200, true);
	text.interpolateScaleXY(1.5f, 1.5f, Back.IN, 200, true).delay(1000).setCallback(new TweenCallback() {

	    @Override
	    public void onEvent(int type, BaseTween<?> source) {
		if (type == TweenCallback.COMPLETE)
		    textPool.remove(this);
	    }
	});
	text.interpolateAlpha(0f, 200, true).delay(1000);
	textPool.add(text);
    }

    public void showComboPrompt() {
	int combos = stage.getSession().getCurrentCombo();
	if (combos > 4 && combos < 25)
	    comboName.setText("GOOD!");
	else if (combos > 24 && combos < 40)
	    comboName.setText("GREAT!");
	else if (combos > 39 && combos < 60)
	    comboName.setText("AMAZING!");
	else if (combos > 59 && combos < 100)
	    comboName.setText("FANTASTIC!");
	else if (combos > 99 && combos < 255)
	    comboName.setText("MONSTROUS!");
	else if (combos > 254)
	    comboName.setText("ALIENLIKE!!");
	if (combos > 4) {
	    comboName.color.a = 1f;
	    comboName.interpolateAlpha(0f, Sine.OUT, 500, true).delay(2000);
	    comboName.position.set(0 - comboName.getBitmapFont().getBounds(comboName.getText()).width, comboName.position.y);
	    comboName.interpolateXY(0, comboName.position.y, Linear.INOUT, 250, true);
	}
    }

    public void showMissPrompt(float x, float y) {
	missLabel.getTweenManager().update(10000);
	missLabel.color.a = 1f;
	missLabel.position.set(x, y);
	missLabel.interpolateXY(x, y + 25, Linear.INOUT, 50, true);
	missLabel.interpolateXY(x, y, Linear.INOUT, 50, true).delay(50);
	missLabel.interpolateAlpha(0f, Linear.INOUT, 2000, true);//.delay(2000);
    }

    public void showReadyPrompt() {
	final DynamicText seconds = new DynamicText(Fonts.actionJackson115, "");
	if (stage instanceof FuryStageScreen)
	    seconds.setText("120 SECONDS");
	else if (stage instanceof ArcadeStageScreen)
	    seconds.setText("90 SECONDS");
	seconds.setAlpha(0f);
	seconds.setPosition(Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);
	seconds.setScale(3f);
	seconds.interpolateAlpha(1f, 250, true);
	seconds.interpolateScaleXY(1f, 1f, Back.OUT, 250, true);
	seconds.interpolateAlpha(0f, 100, true).delay(1000);

	final DynamicText smash = new DynamicText(Fonts.actionJackson115, "SMASH!");
	smash.setAlpha(0f);
	smash.setPosition(Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);
	smash.setScale(3f);
	smash.interpolateAlpha(1f, 150, true).delay(1100);
	smash.interpolateScaleXY(2f, 2f, Back.OUT, 150, true).delay(1100);
	smash.interpolateAlpha(0f, 100, true).delay(2000).setCallback(new TweenCallback() {

	    @Override
	    public void onEvent(int type, BaseTween<?> source) {
		if (type == TweenCallback.COMPLETE) {
		    stage.resume();
		    textPool.remove(seconds);
		    textPool.remove(smash);
		}
	    }
	});
	textPool.add(seconds);
	textPool.add(smash);
    }

    public void showStreakPrompt(int streaks) {
	if (streaks == 5) {
	    streakBonus.setText("Score x5");
	    streakName.setText("QUINTO!");
	} else if (streaks == 4) {
	    streakBonus.setText("Score x4");
	    streakName.setText("QUATRO!");
	} else if (streaks == 3) {
	    streakBonus.setText("Score x3");
	    streakName.setText("TRIO BINGO!");
	} else if (streaks == 2) {
	    streakBonus.setText("Score x2");
	    streakName.setText("DUAL SMASH!");
	}
	streakName.color.a = 1f;
	streakName.endTween();
	streakName.position.set(1280 + streakName.getBitmapFont().getBounds(streakName.getText()).width, 500);
	streakName.interpolateXY(1280, 500, Linear.INOUT, 250, true);
	streakName.interpolateAlpha(0f, Linear.INOUT, 250, true).delay(2000);
	streakBonus.color.a = 1f;
	streakBonus.endTween();
	streakBonus.position.set(1280 + streakName.getBitmapFont().getBounds(streakName.getText()).width, 530);
	streakBonus.interpolateXY(1280, 530, Linear.INOUT, 250, true);
	streakBonus.interpolateAlpha(0f, Linear.INOUT, 250, true).delay(2000);
    }

    public void update(float delta) {
	updateLifePoints(delta);
	updateTexts(delta);
    }

    private void initReadyPrompt() {
	ready[0] = new DynamicSprite(new TextureRegion(Art.readyPrompt, 1, 217, 94, 117), 438, 400); //R
	ready[1] = new DynamicSprite(new TextureRegion(Art.readyPrompt, 95, 216, 88, 116), 529, 399); //E
	ready[2] = new DynamicSprite(new TextureRegion(Art.readyPrompt, 184, 217, 110, 117), 629, 400); //A
	ready[3] = new DynamicSprite(new TextureRegion(Art.readyPrompt, 295, 217, 105, 116), 737, 400); //D
	ready[4] = new DynamicSprite(new TextureRegion(Art.readyPrompt, 400, 216, 100, 116), 840, 399); //Y
	seconds[0] = new DynamicSprite(new TextureRegion(Art.readyPrompt, 501, 216, 89, 113), 231, 389); //9
	seconds[1] = new DynamicSprite(new TextureRegion(Art.readyPrompt, 420, 332, 101, 111), 311, 413); //0
	seconds[2] = new DynamicSprite(new TextureRegion(Art.readyPrompt, 673, 216, 79, 123), 447, 397); //S
	seconds[3] = new DynamicSprite(new TextureRegion(Art.readyPrompt, 753, 216, 90, 121), 533, 396); //E
	seconds[4] = new DynamicSprite(new TextureRegion(Art.readyPrompt, 844, 217, 103, 125), 630, 396); //C
	seconds[5] = new DynamicSprite(new TextureRegion(Art.readyPrompt, 0, 332, 119, 125), 732, 394); //O
	seconds[6] = new DynamicSprite(new TextureRegion(Art.readyPrompt, 120, 333, 111, 121), 848, 393); //N
	seconds[7] = new DynamicSprite(new TextureRegion(Art.readyPrompt, 231, 332, 110, 121), 959, 392); //D
	seconds[8] = new DynamicSprite(new TextureRegion(Art.readyPrompt, 673, 216, 79, 123), 1053, 394); //S
	smash[0] = new DynamicSprite(new TextureRegion(Art.readyPrompt, 0, 0, 144, 220), 273, 400); //S
	smash[1] = new DynamicSprite(new TextureRegion(Art.readyPrompt, 144, 0, 217, 218), 428, 399); //M
	smash[2] = new DynamicSprite(new TextureRegion(Art.readyPrompt, 361, 0, 185, 218), 614, 399); //A
	smash[3] = new DynamicSprite(new TextureRegion(Art.readyPrompt, 0, 0, 144, 220), 759, 399); //S
	smash[4] = new DynamicSprite(new TextureRegion(Art.readyPrompt, 690, 0, 199, 217), 913, 398); //H
	smash[5] = new DynamicSprite(new TextureRegion(Art.readyPrompt, 890, 0, 84, 218), 1035, 398); //!
    }

    private void initTimer() {
	float offsetX = 35f;
	float y = 765f;
	timer[0] = new DynamicText(Fonts.KRONIKA_BLACKSTROKE, "0", HAlignment.CENTER);
	timer[0].setPosition(Settings.SCREEN_WIDTH / 2 - offsetX * 2 + 10, y);
	timer[0].setScale(.7f, .7f);
	timer[1] = new DynamicText(Fonts.KRONIKA_BLACKSTROKE, "0", HAlignment.CENTER);
	timer[1].setPosition(Settings.SCREEN_WIDTH / 2 - offsetX + 10, y);
	timer[1].setScale(.7f, .7f);
	timer[2] = new DynamicText(Fonts.KRONIKA_BLACKSTROKE, ":", HAlignment.CENTER);
	timer[2].setPosition(Settings.SCREEN_WIDTH / 2, y);
	timer[2].setScale(.7f, .7f);
	timer[3] = new DynamicText(Fonts.KRONIKA_BLACKSTROKE, "0", HAlignment.CENTER);
	timer[3].setPosition(Settings.SCREEN_WIDTH / 2 + offsetX - 10, y);
	timer[3].setScale(.7f, .7f);
	timer[4] = new DynamicText(Fonts.KRONIKA_BLACKSTROKE, "0", HAlignment.CENTER);
	timer[4].setPosition(Settings.SCREEN_WIDTH / 2 + offsetX * 2 - 10, y);
	timer[4].setScale(.7f, .7f);
    }

    private void renderLifePoints(SpriteBatch spriteBatch) {
	for (int i = 0; i < lifePoints.length; i++)
	    lifePoints[i].render(spriteBatch);
    }

    private void renderTexts(SpriteBatch batch) {
	score.drawWrapped(batch, "" + stage.getSession().getScore(), 1030f, 800f, 250, HAlignment.RIGHT);

	String message = "GOLD: " + User.gold + "\n";
	message += "FPS: " + Gdx.graphics.getFramesPerSecond() + "fps\n";
	message += "ELAPSED: " + stage.getSession().getStageSecondsElapsed() + "\n";
	message += "SPAWN RATE: " + stage.getSpawnRate() + "\n";
	message += "COMBOS MAX: " + stage.getSession().getHighestCombo() + "\n";
	message += "SMASH LANDED: " + stage.getSession().getLandedSmashes() + "\n";
	message += "hammerEffects SIZE: " + stage.hammerEffects.size() + "\n";
	message += "Press left CTRL to restart." + "\n";
	message += "Press BACKSPACE to pause." + "\n";
	feed.drawWrapped(batch, message, 0f, 232f, 600, HAlignment.LEFT);

	comboName.render(batch);
	flyout.render(batch);
	streakBonus.render(batch);
	streakName.render(batch);
	missLabel.render(batch);

	// FIXME uncomment to show intro prompt
	//	for (int i = 0; i < ready.length; i++)
	//	    ready[i].render(spriteBatch);
	//	for (int i = 0; i < seconds.length; i++)
	//	    seconds[i].render(spriteBatch);
	//	for (int i = 0; i < smash.length; i++)
	//	    smash[i].render(spriteBatch);
	if (stage.getSession().getCurrentCombo() > 1 && !(stage instanceof FuryStageScreen)) {
	    comboCount.render(batch);
	    comboBonus.render(batch);
	}
	for (int i = 0; i < textPool.size(); i++)
	    textPool.get(i).render(batch);

	if (stage instanceof ArcadeStageScreen)
	    for (int i = 0; i < timer.length; i++)
		timer[i].render(batch);
    }

    private void updateLifePoints(float deltaTime) {
	for (int i = 0; i < lifePoints.length; i++)
	    lifePoints[i].update(deltaTime);
    }

    private void updateTexts(float delta) {
	flyout.update(delta);
	comboName.update(delta);
	streakName.update(delta);
	missLabel.update(delta);
	comboBonus.setText(stage.getComboMultiplier() > 1 ? "SCORE x" + stage.getComboMultiplier() : "");
	streakBonus.update(delta);
	comboCount.setText("" + stage.getSession().getCurrentCombo());
	comboCount.update(delta);
	for (int i = 0; i < ready.length; i++)
	    ready[i].update(delta);
	for (int i = 0; i < seconds.length; i++)
	    seconds[i].update(delta);
	for (int i = 0; i < smash.length; i++)
	    smash[i].update(delta);

	for (int i = 0; i < textPool.size(); i++)
	    textPool.get(i).update(delta);

	if (stage instanceof ArcadeStageScreen) {
	    String[] values = ((ArcadeStageScreen) stage).getTimerValues();
	    if (stage instanceof ArcadeStageScreen)
		for (int i = 0; i < timer.length; i++) {
		    timer[i].update(delta);
		    timer[i].setText(values[i]);
		}
	}
    }
}
