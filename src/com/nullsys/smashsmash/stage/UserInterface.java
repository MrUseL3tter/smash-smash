package com.nullsys.smashsmash.stage;

import java.util.ArrayList;
import java.util.List;

import aurelienribon.tweenengine.equations.Back;
import aurelienribon.tweenengine.equations.Elastic;
import aurelienribon.tweenengine.equations.Linear;
import aurelienribon.tweenengine.equations.Sine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.noobs2d.tweenengine.utils.DynamicCallback.InvisibleOnEnd;
import com.noobs2d.tweenengine.utils.DynamicDisplay.DynamicRegistration;
import com.noobs2d.tweenengine.utils.DynamicSprite;
import com.noobs2d.tweenengine.utils.DynamicText;
import com.nullsys.smashsmash.Art;
import com.nullsys.smashsmash.Fonts;
import com.nullsys.smashsmash.Settings;
import com.nullsys.smashsmash.User;

public class UserInterface {

    public SmashSmashStage stage;

    public DynamicSprite[] lifePoints = new DynamicSprite[3];
    public DynamicSprite[] ready = new DynamicSprite[5];
    public DynamicSprite[] seconds = new DynamicSprite[9];
    public DynamicSprite[] smash = new DynamicSprite[6];
    public DynamicText[] timer = new DynamicText[5];

    public DynamicSprite itemEffectCoinRain;
    public DynamicSprite itemEffectHammerTime;
    public DynamicSprite itemEffectScoreFrenzy;

    public List<DynamicText> textPool = new ArrayList<DynamicText>();
    public DynamicText flyout;
    public DynamicText comboCount;
    public DynamicText comboBonus;
    public DynamicText comboName;
    public DynamicText missLabel;
    public DynamicText streakBonus;
    public DynamicText streakName;

    public BitmapFont score;
    public BitmapFont feed;

    public UserInterface(SmashSmashStage stage) {
	this.stage = stage;
	itemEffectScoreFrenzy = new DynamicSprite(Art.hudBuffEffectScoreFrenzy, 0, 0);
	itemEffectHammerTime = new DynamicSprite(Art.hudBuffEffectHammerTime, 0, 0);
	itemEffectCoinRain = new DynamicSprite(Art.hudBuffEffectCoinRain, 0, 0);
	score = Fonts.akaDylanCollage64;
	comboCount = new DynamicText(Fonts.actionJackson115, "", HAlignment.LEFT);
	comboCount.wrapWidth = 512;
	comboCount.position.set(0, 515);
	comboCount.setRegistration(DynamicRegistration.LEFT_CENTER);
	comboBonus = new DynamicText(Fonts.actionJackson115, "", HAlignment.LEFT);
	comboBonus.wrapWidth = 512;
	comboBonus.setPosition(0, 435);
	comboBonus.setScale(.25f, .25f);
	comboBonus.setRegistration(DynamicRegistration.LEFT_CENTER);
	comboName = new DynamicText(Fonts.bdCartoonShoutx32orange, "", HAlignment.LEFT);
	comboName.position.set(0, 590);
	comboName.setRegistration(DynamicRegistration.LEFT_CENTER);
	feed = new BitmapFont();
	feed.scale(.5f);
	flyout = new DynamicText(Fonts.goodGirlx32, "", HAlignment.CENTER);
	streakBonus = new DynamicText(Fonts.bdCartoonShoutx23orange, "", HAlignment.RIGHT);
	streakBonus.setRegistration(DynamicRegistration.RIGHT_CENTER);
	streakName = new DynamicText(Fonts.bdCartoonShoutx32orange, "", HAlignment.RIGHT);
	streakName.setRegistration(DynamicRegistration.RIGHT_CENTER);
	missLabel = new DynamicText(Fonts.bdCartoonShoutx42, "", HAlignment.CENTER);
	for (int i = 0; i < lifePoints.length; i++)
	    lifePoints[i] = new DynamicSprite(Art.hudLifePoint, 55 + 97 * i, 749);
	if (stage instanceof ArcadeStageScreen)
	    initTimer();
	initReadyPrompt();
    }

    public void render(SpriteBatch spriteBatch) {
	renderTexts(spriteBatch);
	if (stage instanceof EndlessStageScreen)
	    renderLifePoints(spriteBatch);
    }

    public void setFlyoutText(String text, Color color) {
	flyout.text = text;
	flyout.color.set(color);
	flyout.position.set(640f, 350f);
	flyout.interpolateXY(640, 400, Elastic.OUT, 2000, true);
	flyout.interpolateAlpha(0f, Linear.INOUT, 250, true).delay(3000);
    }

    public void shakeCombos() {
	comboCount.interpolateXY(comboCount.position.x, comboCount.position.y + 15, Linear.INOUT, 75, true);
	comboCount.interpolateXY(comboCount.position.x, comboCount.position.y - 15, Linear.INOUT, 75, true).delay(75);
	comboCount.interpolateXY(comboCount.position.x, comboCount.position.y, Linear.INOUT, 75, true).delay(150);
    }

    public void shakeLifePoint() {
	if (stage.session.lifePoints > 0) {
	    float x = lifePoints[stage.session.lifePoints - 1].position.x;
	    float y = lifePoints[stage.session.lifePoints - 1].position.y + 15;
	    lifePoints[stage.session.lifePoints - 1].interpolateXY(x, y, Linear.INOUT, 75, true);
	    x = lifePoints[stage.session.lifePoints - 1].position.x;
	    y = lifePoints[stage.session.lifePoints - 1].position.y - 15;
	    lifePoints[stage.session.lifePoints - 1].interpolateXY(x, y, Linear.INOUT, 75, true).delay(75);
	    x = lifePoints[stage.session.lifePoints - 1].position.x;
	    y = lifePoints[stage.session.lifePoints - 1].position.y;
	    lifePoints[stage.session.lifePoints - 1].interpolateXY(x, y, Linear.INOUT, 75, true).delay(150);
	    lifePoints[stage.session.lifePoints - 1].tween.setCallback(new InvisibleOnEnd(lifePoints[stage.session.lifePoints - 1]));
	}
    }

    public void showComboPrompt(int combos) {
	if (combos > 4 && combos < 25)
	    comboName.text = "GOOD!";
	else if (combos > 24 && combos < 40)
	    comboName.text = "GREAT!";
	else if (combos > 39 && combos < 60)
	    comboName.text = "AMAZING!";
	else if (combos > 59 && combos < 100)
	    comboName.text = "FANTASTIC!";
	else if (combos > 99 && combos < 255)
	    comboName.text = "MONSTROUS!";
	else if (combos > 254)
	    comboName.text = "ALIENLIKE!!";
	if (combos > 4) {
	    comboName.color.a = 1f;
	    comboName.interpolateAlpha(0f, Sine.OUT, 500, true).delay(2000);
	    comboName.position.set(0 - comboName.bitmapFont.getBounds(comboName.text).width, comboName.position.y);
	    comboName.interpolateXY(0, comboName.position.y, Linear.INOUT, 250, true);
	}
    }

    public void showMissPrompt(float x, float y) {
	missLabel.text = "MISS!";
	missLabel.color.a = 1f;
	missLabel.position.set(x, y);
	missLabel.interpolateXY(x, y + 25, Linear.INOUT, 50, true);
	missLabel.interpolateXY(x, y, Linear.INOUT, 50, true).delay(50);
	missLabel.interpolateAlpha(0f, Linear.INOUT, 250, true).delay(2000);
    }

    public void showReadyPrompt() {
	int delay = 1250, duration = 1000;
	seconds[0].color.a = 0f;
	seconds[0].position.y -= 50;
	seconds[0].interpolateXY(seconds[0].position.x, seconds[0].position.y + 50, Sine.OUT, 250, true);
	seconds[0].interpolateXY(seconds[0].position.x, seconds[0].position.y - 50, Linear.INOUT, 250, true).delay(delay);
	seconds[0].interpolateAlpha(1f, Linear.INOUT, 250, true);
	seconds[0].interpolateAlpha(0f, Linear.INOUT, 250, true).delay(delay);
	delay += 50;
	seconds[1].color.a = 0f;
	seconds[1].position.y += 50;
	seconds[1].interpolateXY(seconds[1].position.x, seconds[1].position.y - 50, Sine.OUT, 250, true);
	seconds[1].interpolateXY(seconds[1].position.x, seconds[1].position.y - 150, Linear.INOUT, 250, true).delay(delay);
	seconds[1].interpolateAlpha(1f, Linear.INOUT, 250, true);
	seconds[1].interpolateAlpha(0f, Linear.INOUT, 250, true).delay(delay);
	delay += 50;
	seconds[2].color.a = 0f;
	seconds[2].position.y -= 50;
	seconds[2].interpolateXY(seconds[2].position.x, seconds[2].position.y + 50, Back.OUT, 250, true);
	seconds[2].interpolateXY(seconds[2].position.x, seconds[2].position.y - 50, Linear.INOUT, 250, true).delay(delay);
	seconds[2].interpolateAlpha(1f, Linear.INOUT, 250, true);
	seconds[2].interpolateAlpha(0f, Linear.INOUT, 250, true).delay(delay);
	delay += 50;
	seconds[3].color.a = 0f;
	seconds[3].position.y -= 50;
	seconds[3].interpolateXY(seconds[3].position.x, seconds[3].position.y + 50, Back.OUT, 250, true).delay(50);
	seconds[3].interpolateXY(seconds[3].position.x, seconds[3].position.y - 50, Linear.INOUT, 250, true).delay(delay);
	seconds[3].interpolateAlpha(1f, Linear.INOUT, 250, true);
	seconds[3].interpolateAlpha(0f, Linear.INOUT, 250, true).delay(delay);
	delay += 50;
	seconds[4].color.a = 0f;
	seconds[4].position.y -= 50;
	seconds[4].interpolateXY(seconds[4].position.x, seconds[4].position.y + 50, Back.OUT, 250, true).delay(100);
	seconds[4].interpolateXY(seconds[4].position.x, seconds[4].position.y - 50, Linear.INOUT, 250, true).delay(delay);
	seconds[4].interpolateAlpha(1f, Linear.INOUT, 250, true);
	seconds[4].interpolateAlpha(0f, Linear.INOUT, 250, true).delay(delay);
	delay += 50;
	seconds[5].color.a = 0f;
	seconds[5].position.y -= 50;
	seconds[5].interpolateXY(seconds[5].position.x, seconds[5].position.y + 50, Back.OUT, 250, true).delay(150);
	seconds[5].interpolateXY(seconds[5].position.x, seconds[5].position.y - 50, Linear.INOUT, 250, true).delay(delay);
	seconds[5].interpolateAlpha(1f, Linear.INOUT, 250, true);
	seconds[5].interpolateAlpha(0f, Linear.INOUT, 250, true).delay(delay);
	delay += 50;
	seconds[6].color.a = 0f;
	seconds[6].position.y -= 50;
	seconds[6].interpolateXY(seconds[6].position.x, seconds[6].position.y + 50, Back.OUT, 250, true).delay(200);
	seconds[6].interpolateXY(seconds[6].position.x, seconds[6].position.y - 50, Linear.INOUT, 250, true).delay(delay);
	seconds[6].interpolateAlpha(1f, Linear.INOUT, 250, true);
	seconds[6].interpolateAlpha(0f, Linear.INOUT, 250, true).delay(delay);
	delay += 50;
	seconds[7].color.a = 0f;
	seconds[7].position.y -= 50;
	seconds[7].interpolateXY(seconds[7].position.x, seconds[7].position.y + 50, Back.OUT, 250, true).delay(250);
	seconds[7].interpolateXY(seconds[7].position.x, seconds[7].position.y - 50, Linear.INOUT, 250, true).delay(delay);
	seconds[7].interpolateAlpha(1f, Linear.INOUT, 250, true);
	seconds[7].interpolateAlpha(0f, Linear.INOUT, 250, true).delay(delay);
	delay += 50;
	seconds[8].color.a = 0f;
	seconds[8].position.y -= 50;
	seconds[8].interpolateXY(seconds[8].position.x, seconds[8].position.y + 50, Back.OUT, 250, true).delay(300);
	seconds[8].interpolateXY(seconds[8].position.x, seconds[8].position.y - 50, Linear.INOUT, 250, true).delay(delay);
	seconds[8].interpolateAlpha(1f, Linear.INOUT, 250, true);
	seconds[8].interpolateAlpha(0f, Linear.INOUT, 250, true).delay(delay);
	ready[0].color.a = 0f;
	ready[0].position.y += 50;
	ready[0].interpolateXY(ready[0].position.x, ready[0].position.y - 50, Linear.INOUT, 250, true).delay(delay - 50);
	ready[0].interpolateAlpha(1f, Linear.INOUT, 250, true).delay(delay);
	ready[0].interpolateAlpha(0f, Linear.INOUT, 150, true).delay(delay + duration - 50);
	delay += 50;
	ready[1].color.a = 0f;
	ready[1].position.y += 50;
	ready[1].interpolateXY(ready[1].position.x, ready[1].position.y - 50, Linear.INOUT, 250, true).delay(delay);
	ready[1].interpolateAlpha(1f, Linear.INOUT, 250, true).delay(delay);
	ready[1].interpolateAlpha(0f, Linear.INOUT, 150, true).delay(delay + duration - 50);
	delay += 50;
	ready[2].color.a = 0f;
	ready[2].position.y += 50;
	ready[2].interpolateXY(ready[2].position.x, ready[2].position.y - 50, Linear.INOUT, 250, true).delay(delay);
	ready[2].interpolateAlpha(1f, Linear.INOUT, 250, true).delay(delay);
	ready[2].interpolateAlpha(0f, Linear.INOUT, 150, true).delay(delay + duration - 50);
	delay += 50;
	ready[3].color.a = 0f;
	ready[3].position.y += 50;
	ready[3].interpolateXY(ready[3].position.x, ready[3].position.y - 50, Linear.INOUT, 250, true).delay(delay);
	ready[3].interpolateAlpha(1f, Linear.INOUT, 250, true).delay(delay);
	ready[3].interpolateAlpha(0f, Linear.INOUT, 150, true).delay(delay + duration - 50);
	delay += 50;
	ready[4].color.a = 0f;
	ready[4].position.y += 50;
	ready[4].interpolateXY(ready[4].position.x, ready[4].position.y - 50, Linear.INOUT, 250, true).delay(delay);
	ready[4].interpolateAlpha(1f, Linear.INOUT, 250, true).delay(delay);
	ready[4].interpolateAlpha(0f, Linear.INOUT, 150, true).delay(delay + duration - 50);
	smash[0].color.a = 0f;
	smash[0].position.x -= 500;
	smash[0].interpolateXY(smash[0].position.x + 500, smash[0].position.y, Linear.INOUT, 250, true).delay(delay + duration);
	smash[0].interpolateAlpha(1f, Linear.INOUT, 250, true).delay(delay + duration);
	smash[0].interpolateAlpha(0f, Linear.INOUT, 150, true).delay(delay + duration * 2);
	smash[1].color.a = 0f;
	smash[1].position.x -= 250;
	smash[1].interpolateXY(smash[1].position.x + 250, smash[1].position.y, Linear.INOUT, 250, true).delay(delay + duration);
	smash[1].interpolateAlpha(1f, Linear.INOUT, 250, true).delay(delay + duration);
	smash[1].interpolateAlpha(0f, Linear.INOUT, 150, true).delay(delay + duration * 2);
	smash[2].color.a = 0f;
	smash[2].position.x -= 125;
	smash[2].interpolateXY(smash[2].position.x + 125, smash[2].position.y, Linear.INOUT, 250, true).delay(delay + duration);
	smash[2].interpolateAlpha(1f, Linear.INOUT, 250, true).delay(delay + duration);
	smash[2].interpolateAlpha(0f, Linear.INOUT, 150, true).delay(delay + duration * 2);
	smash[3].color.a = 0f;
	smash[3].position.x += 125;
	smash[3].interpolateXY(smash[3].position.x - 125, smash[3].position.y, Linear.INOUT, 250, true).delay(delay + duration);
	smash[3].interpolateAlpha(1f, Linear.INOUT, 250, true).delay(delay + duration);
	smash[3].interpolateAlpha(0f, Linear.INOUT, 150, true).delay(delay + duration * 2);
	smash[4].color.a = 0f;
	smash[4].position.x += 250;
	smash[4].interpolateXY(smash[4].position.x - 250, smash[4].position.y, Linear.INOUT, 250, true).delay(delay + duration);
	smash[4].interpolateAlpha(1f, Linear.INOUT, 250, true).delay(delay + duration);
	smash[4].interpolateAlpha(0f, Linear.INOUT, 150, true).delay(delay + duration * 2);
	smash[5].color.a = 0f;
	smash[5].position.x += 500;
	smash[5].interpolateXY(smash[5].position.x - 500, smash[5].position.y, Linear.INOUT, 250, true).delay(delay + duration);
	smash[5].interpolateAlpha(1f, Linear.INOUT, 250, true).delay(delay + duration);
	smash[5].interpolateAlpha(0f, Linear.INOUT, 150, true).delay(delay + duration * 2);
    }

    public void showStreakPrompt(int streaks) {
	if (streaks == 5) {
	    streakBonus.text = "Score x5";
	    streakName.text = "QUINTO!";
	} else if (streaks == 4) {
	    streakBonus.text = "Score x4";
	    streakName.text = "QUATRO!";
	} else if (streaks == 3) {
	    streakBonus.text = "Score x3";
	    streakName.text = "TRIO BINGO!";
	} else if (streaks == 2) {
	    streakBonus.text = "Score x2";
	    streakName.text = "DUAL SMASH!";
	}
	streakName.color.a = 1f;
	streakName.tweenManager.update(1000);
	streakName.position.set(1280 + streakName.bitmapFont.getBounds(streakName.text).width, 500);
	streakName.interpolateXY(1280, 500, Linear.INOUT, 250, true);
	streakName.interpolateAlpha(0f, Linear.INOUT, 250, true).delay(2000);
	streakBonus.color.a = 1f;
	streakBonus.tweenManager.update(1000);
	streakBonus.position.set(1280 + streakName.bitmapFont.getBounds(streakName.text).width, 530);
	streakBonus.interpolateXY(1280, 530, Linear.INOUT, 250, true);
	streakBonus.interpolateAlpha(0f, Linear.INOUT, 250, true).delay(2000);
    }

    public void update(float deltaTime) {
	updateLifePoints(deltaTime);
	updateTexts(deltaTime);
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
	float offsetX = 28f;
	float y = 773f;
	timer[0] = new DynamicText(Fonts.bdCartoonShoutx23orange, "0", HAlignment.CENTER);
	timer[0].setPosition(Settings.SCREEN_WIDTH / 2 - offsetX * 2, y);
	timer[0].setScale(2f, 2f);
	timer[1] = new DynamicText(Fonts.bdCartoonShoutx23orange, "0", HAlignment.CENTER);
	timer[1].setPosition(Settings.SCREEN_WIDTH / 2 - offsetX, y);
	timer[1].setScale(2f, 2f);
	timer[2] = new DynamicText(Fonts.bdCartoonShoutx23orange, ":", HAlignment.CENTER);
	timer[2].setPosition(Settings.SCREEN_WIDTH / 2, y);
	timer[2].setScale(2f, 2f);
	timer[3] = new DynamicText(Fonts.bdCartoonShoutx23orange, "0", HAlignment.CENTER);
	timer[3].setPosition(Settings.SCREEN_WIDTH / 2 + offsetX, y);
	timer[3].setScale(2f, 2f);
	timer[4] = new DynamicText(Fonts.bdCartoonShoutx23orange, "0", HAlignment.CENTER);
	timer[4].setPosition(Settings.SCREEN_WIDTH / 2 + offsetX * 2, y);
	timer[4].setScale(2f, 2f);
    }

    private void renderLifePoints(SpriteBatch spriteBatch) {
	for (int i = 0; i < lifePoints.length; i++)
	    lifePoints[i].render(spriteBatch);
    }

    private void renderTexts(SpriteBatch spriteBatch) {
	score.drawWrapped(spriteBatch, "" + stage.session.score, 1030f, 800f, 250, HAlignment.RIGHT);

	String message = "GOLD: " + User.gold + "\n";
	message += "FPS: " + Gdx.graphics.getFramesPerSecond() + "fps\n";
	message += "ELAPSED: " + stage.session.stageSecondsElapsed + "\n";
	message += "SPAWN RATE: " + stage.getSpawnRate() + "\n";
	message += "COMBOS MAX: " + stage.session.combosMax + "\n";
	message += "SMASH LANDED: " + stage.session.smashLanded + "\n";
	message += "hammerEffects SIZE: " + stage.hammerEffects.size() + "\n";
	message += "Press left CTRL to restart." + "\n";
	message += "Press BACKSPACE to pause." + "\n";
	feed.drawWrapped(spriteBatch, message, 0f, 232f, 600, HAlignment.LEFT);

	comboName.render(spriteBatch);
	flyout.render(spriteBatch);
	streakBonus.render(spriteBatch);
	streakName.render(spriteBatch);
	missLabel.render(spriteBatch);

	// FIXME uncomment to show intro prompt
	//	for (int i = 0; i < ready.length; i++)
	//	    ready[i].render(spriteBatch);
	//	for (int i = 0; i < seconds.length; i++)
	//	    seconds[i].render(spriteBatch);
	//	for (int i = 0; i < smash.length; i++)
	//	    smash[i].render(spriteBatch);
	if (stage.session.combosCurrent > 1) {
	    comboCount.render(spriteBatch);
	    comboBonus.render(spriteBatch);
	}
	//	for (int i = 0; i < textPool.size(); i++)
	//	    textPool.get(i).render(spriteBatch);

	if (stage instanceof ArcadeStageScreen)
	    for (int i = 0; i < timer.length; i++)
		timer[i].render(spriteBatch);
    }

    private void updateLifePoints(float deltaTime) {
	for (int i = 0; i < lifePoints.length; i++)
	    lifePoints[i].update(deltaTime);
    }

    private void updateTexts(float deltaTime) {
	flyout.update(deltaTime);
	comboCount.text = "" + stage.session.combosCurrent;
	comboCount.update(deltaTime);
	comboBonus.text = stage.getComboMultiplier() > 1 ? "SCORE x" + stage.getComboMultiplier() : "";
	comboName.update(deltaTime);
	streakBonus.update(deltaTime);
	streakName.update(deltaTime);
	missLabel.update(deltaTime);
	for (int i = 0; i < ready.length; i++)
	    ready[i].update(deltaTime);
	for (int i = 0; i < seconds.length; i++)
	    seconds[i].update(deltaTime);
	for (int i = 0; i < smash.length; i++)
	    smash[i].update(deltaTime);

	for (int i = 0; i < textPool.size(); i++)
	    textPool.get(i).update(deltaTime);

	if (stage instanceof ArcadeStageScreen) {
	    String[] values = ((ArcadeStageScreen) stage).getTimerValues();
	    if (stage instanceof ArcadeStageScreen)
		for (int i = 0; i < timer.length; i++) {
		    timer[i].update(deltaTime);
		    timer[i].text = values[i];
		}
	}
    }
}
