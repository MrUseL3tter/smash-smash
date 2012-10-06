package com.nullsys.smashsmash.stage;

import java.util.ArrayList;
import java.util.List;

import aurelienribon.tweenengine.Tween;
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
import com.badlogic.gdx.math.Vector2;
import com.noobs2d.tweenengine.utils.DynamicCallback.InvisibleOnEnd;
import com.noobs2d.tweenengine.utils.DynamicSprite;
import com.noobs2d.tweenengine.utils.DynamicText;
import com.nullsys.smashsmash.Art;
import com.nullsys.smashsmash.Fonts;
import com.nullsys.smashsmash.Settings;
import com.nullsys.smashsmash.User;
import com.nullsys.smashsmash.bonuseffect.BonusEffect;

public class HeadsUpDisplay {

    public SurvivalStageScreen stage;

    public DynamicSprite[] lifePoints = new DynamicSprite[3];
    public DynamicSprite[] ready = new DynamicSprite[5];
    public DynamicSprite[] seconds = new DynamicSprite[9];
    public DynamicSprite[] smash = new DynamicSprite[6];

    public DynamicSprite itemEffectCoinRain;
    public DynamicSprite itemEffectHammerTime;
    public DynamicSprite itemEffectScoreFrenzy;

    public DynamicSprite bonusEffectBlackFill;
    public DynamicSprite bonusEffectPinwheel;

    public List<DynamicText> textPool = new ArrayList<DynamicText>();
    public DynamicText flyout;
    public DynamicText comboCount;
    public DynamicText comboName;
    public DynamicText missLabel;
    public DynamicText streakBonus;
    public DynamicText streakName;

    public BitmapFont score;
    public BitmapFont feed;

    public HeadsUpDisplay(SurvivalStageScreen stage) {
	this.stage = stage;
	itemEffectScoreFrenzy = new DynamicSprite(Art.hudBuffEffectScoreFrenzy, 0, 0);
	itemEffectHammerTime = new DynamicSprite(Art.hudBuffEffectHammerTime, 0, 0);
	itemEffectCoinRain = new DynamicSprite(Art.hudBuffEffectCoinRain, 0, 0);
	bonusEffectBlackFill = new DynamicSprite(new TextureRegion(Art.blackFill), Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);
	bonusEffectBlackFill.scale.set(15f, 15f);
	bonusEffectPinwheel = new DynamicSprite(new TextureRegion(Art.pinwheel), Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);
	bonusEffectPinwheel.scale.set(3f, 3f);
	bonusEffectPinwheel.interpolateRotation(360, Linear.INOUT, 3000, false).repeat(Tween.INFINITY, 0).start(bonusEffectPinwheel.tweenManager);
	score = Fonts.akaDylanCollage64;
	comboCount = new DynamicText(Fonts.actionJackson115, "", HAlignment.LEFT);
	comboCount.position.set(0, 515);
	comboName = new DynamicText(Fonts.bdCartoonShoutx32orange, "", HAlignment.LEFT);
	comboName.position.set(0, 550);
	feed = new BitmapFont();
	feed.scale(.5f);
	flyout = new DynamicText(Fonts.goodGirlx32, "", HAlignment.CENTER);
	streakBonus = new DynamicText(Fonts.bdCartoonShoutx23orange, "", HAlignment.RIGHT);
	streakName = new DynamicText(Fonts.bdCartoonShoutx32orange, "", HAlignment.RIGHT);
	missLabel = new DynamicText(Fonts.bdCartoonShoutx42, "", HAlignment.CENTER);
	for (int i = 0; i < lifePoints.length; i++)
	    lifePoints[i] = new DynamicSprite(Art.hudLifePoint, 55 + 97 * i, 749);
	initReadyPrompt();
    }

    public void render(SpriteBatch spriteBatch) {
	renderBonusEffects(spriteBatch);
	renderTexts(spriteBatch);
	if (stage instanceof SurvivalStageScreen)
	    renderLifePoints(spriteBatch);
    }

    public void setFlyoutText(String text, Color color) {
	flyout.text = text;
	flyout.color.set(color);
	flyout.position.set(640f, 350f);
	flyout.interpolateXY(new Vector2(640, 400), Elastic.OUT, 2000, true);
	flyout.interpolateAlpha(0f, Linear.INOUT, 250, true).delay(3000);
    }

    public void shakeCombos() {
	comboCount.interpolateXY(new Vector2(comboCount.position.x, comboCount.position.y + 15), Linear.INOUT, 75, true);
	comboCount.interpolateXY(new Vector2(comboCount.position.x, comboCount.position.y - 15), Linear.INOUT, 75, true).delay(75);
	comboCount.interpolateXY(new Vector2(comboCount.position.x, comboCount.position.y), Linear.INOUT, 75, true).delay(150);
    }

    public void shakeLifePoint() {
	if (stage.session.lifePoints > 0) {
	    float x = lifePoints[stage.session.lifePoints - 1].position.x;
	    float y = lifePoints[stage.session.lifePoints - 1].position.y + 15;
	    lifePoints[stage.session.lifePoints - 1].interpolateXY(new Vector2(x, y), Linear.INOUT, 75, true);
	    x = lifePoints[stage.session.lifePoints - 1].position.x;
	    y = lifePoints[stage.session.lifePoints - 1].position.y - 15;
	    lifePoints[stage.session.lifePoints - 1].interpolateXY(new Vector2(x, y), Linear.INOUT, 75, true).delay(75);
	    x = lifePoints[stage.session.lifePoints - 1].position.x;
	    y = lifePoints[stage.session.lifePoints - 1].position.y;
	    lifePoints[stage.session.lifePoints - 1].interpolateXY(new Vector2(x, y), Linear.INOUT, 75, true).delay(150);
	    lifePoints[stage.session.lifePoints - 1].tween.setCallback(new InvisibleOnEnd(lifePoints[stage.session.lifePoints - 1]));
	}
    }

    public void showReadyPrompt() {
	int delay = 1250, duration = 1000;
	seconds[0].color.a = 0f;
	seconds[0].position.y -= 50;
	seconds[0].interpolateXY(new Vector2(seconds[0].position.x, seconds[0].position.y + 50), Sine.OUT, 250, true);
	seconds[0].interpolateXY(new Vector2(seconds[0].position.x, seconds[0].position.y - 50), Linear.INOUT, 250, true).delay(delay);
	seconds[0].interpolateAlpha(1f, Linear.INOUT, 250, true);
	seconds[0].interpolateAlpha(0f, Linear.INOUT, 250, true).delay(delay);
	delay += 50;
	seconds[1].color.a = 0f;
	seconds[1].position.y += 50;
	seconds[1].interpolateXY(new Vector2(seconds[1].position.x, seconds[1].position.y - 50), Sine.OUT, 250, true);
	seconds[1].interpolateXY(new Vector2(seconds[1].position.x, seconds[1].position.y - 150), Linear.INOUT, 250, true).delay(delay);
	seconds[1].interpolateAlpha(1f, Linear.INOUT, 250, true);
	seconds[1].interpolateAlpha(0f, Linear.INOUT, 250, true).delay(delay);
	delay += 50;
	seconds[2].color.a = 0f;
	seconds[2].position.y -= 50;
	seconds[2].interpolateXY(new Vector2(seconds[2].position.x, seconds[2].position.y + 50), Back.OUT, 250, true);
	seconds[2].interpolateXY(new Vector2(seconds[2].position.x, seconds[2].position.y - 50), Linear.INOUT, 250, true).delay(delay);
	seconds[2].interpolateAlpha(1f, Linear.INOUT, 250, true);
	seconds[2].interpolateAlpha(0f, Linear.INOUT, 250, true).delay(delay);
	delay += 50;
	seconds[3].color.a = 0f;
	seconds[3].position.y -= 50;
	seconds[3].interpolateXY(new Vector2(seconds[3].position.x, seconds[3].position.y + 50), Back.OUT, 250, true).delay(50);
	seconds[3].interpolateXY(new Vector2(seconds[3].position.x, seconds[3].position.y - 50), Linear.INOUT, 250, true).delay(delay);
	seconds[3].interpolateAlpha(1f, Linear.INOUT, 250, true);
	seconds[3].interpolateAlpha(0f, Linear.INOUT, 250, true).delay(delay);
	delay += 50;
	seconds[4].color.a = 0f;
	seconds[4].position.y -= 50;
	seconds[4].interpolateXY(new Vector2(seconds[4].position.x, seconds[4].position.y + 50), Back.OUT, 250, true).delay(100);
	seconds[4].interpolateXY(new Vector2(seconds[4].position.x, seconds[4].position.y - 50), Linear.INOUT, 250, true).delay(delay);
	seconds[4].interpolateAlpha(1f, Linear.INOUT, 250, true);
	seconds[4].interpolateAlpha(0f, Linear.INOUT, 250, true).delay(delay);
	delay += 50;
	seconds[5].color.a = 0f;
	seconds[5].position.y -= 50;
	seconds[5].interpolateXY(new Vector2(seconds[5].position.x, seconds[5].position.y + 50), Back.OUT, 250, true).delay(150);
	seconds[5].interpolateXY(new Vector2(seconds[5].position.x, seconds[5].position.y - 50), Linear.INOUT, 250, true).delay(delay);
	seconds[5].interpolateAlpha(1f, Linear.INOUT, 250, true);
	seconds[5].interpolateAlpha(0f, Linear.INOUT, 250, true).delay(delay);
	delay += 50;
	seconds[6].color.a = 0f;
	seconds[6].position.y -= 50;
	seconds[6].interpolateXY(new Vector2(seconds[6].position.x, seconds[6].position.y + 50), Back.OUT, 250, true).delay(200);
	seconds[6].interpolateXY(new Vector2(seconds[6].position.x, seconds[6].position.y - 50), Linear.INOUT, 250, true).delay(delay);
	seconds[6].interpolateAlpha(1f, Linear.INOUT, 250, true);
	seconds[6].interpolateAlpha(0f, Linear.INOUT, 250, true).delay(delay);
	delay += 50;
	seconds[7].color.a = 0f;
	seconds[7].position.y -= 50;
	seconds[7].interpolateXY(new Vector2(seconds[7].position.x, seconds[7].position.y + 50), Back.OUT, 250, true).delay(250);
	seconds[7].interpolateXY(new Vector2(seconds[7].position.x, seconds[7].position.y - 50), Linear.INOUT, 250, true).delay(delay);
	seconds[7].interpolateAlpha(1f, Linear.INOUT, 250, true);
	seconds[7].interpolateAlpha(0f, Linear.INOUT, 250, true).delay(delay);
	delay += 50;
	seconds[8].color.a = 0f;
	seconds[8].position.y -= 50;
	seconds[8].interpolateXY(new Vector2(seconds[8].position.x, seconds[8].position.y + 50), Back.OUT, 250, true).delay(300);
	seconds[8].interpolateXY(new Vector2(seconds[8].position.x, seconds[8].position.y - 50), Linear.INOUT, 250, true).delay(delay);
	seconds[8].interpolateAlpha(1f, Linear.INOUT, 250, true);
	seconds[8].interpolateAlpha(0f, Linear.INOUT, 250, true).delay(delay);
	ready[0].color.a = 0f;
	ready[0].position.y += 50;
	ready[0].interpolateXY(new Vector2(ready[0].position.x, ready[0].position.y - 50), Linear.INOUT, 250, true).delay(delay - 50);
	ready[0].interpolateAlpha(1f, Linear.INOUT, 250, true).delay(delay);
	ready[0].interpolateAlpha(0f, Linear.INOUT, 150, true).delay(delay + duration - 50);
	delay += 50;
	ready[1].color.a = 0f;
	ready[1].position.y += 50;
	ready[1].interpolateXY(new Vector2(ready[1].position.x, ready[1].position.y - 50), Linear.INOUT, 250, true).delay(delay);
	ready[1].interpolateAlpha(1f, Linear.INOUT, 250, true).delay(delay);
	ready[1].interpolateAlpha(0f, Linear.INOUT, 150, true).delay(delay + duration - 50);
	delay += 50;
	ready[2].color.a = 0f;
	ready[2].position.y += 50;
	ready[2].interpolateXY(new Vector2(ready[2].position.x, ready[2].position.y - 50), Linear.INOUT, 250, true).delay(delay);
	ready[2].interpolateAlpha(1f, Linear.INOUT, 250, true).delay(delay);
	ready[2].interpolateAlpha(0f, Linear.INOUT, 150, true).delay(delay + duration - 50);
	delay += 50;
	ready[3].color.a = 0f;
	ready[3].position.y += 50;
	ready[3].interpolateXY(new Vector2(ready[3].position.x, ready[3].position.y - 50), Linear.INOUT, 250, true).delay(delay);
	ready[3].interpolateAlpha(1f, Linear.INOUT, 250, true).delay(delay);
	ready[3].interpolateAlpha(0f, Linear.INOUT, 150, true).delay(delay + duration - 50);
	delay += 50;
	ready[4].color.a = 0f;
	ready[4].position.y += 50;
	ready[4].interpolateXY(new Vector2(ready[4].position.x, ready[4].position.y - 50), Linear.INOUT, 250, true).delay(delay);
	ready[4].interpolateAlpha(1f, Linear.INOUT, 250, true).delay(delay);
	ready[4].interpolateAlpha(0f, Linear.INOUT, 150, true).delay(delay + duration - 50);
	smash[0].color.a = 0f;
	smash[0].position.x -= 500;
	smash[0].interpolateXY(new Vector2(smash[0].position.x + 500, smash[0].position.y), Linear.INOUT, 250, true).delay(delay + duration);
	smash[0].interpolateAlpha(1f, Linear.INOUT, 250, true).delay(delay + duration);
	smash[0].interpolateAlpha(0f, Linear.INOUT, 150, true).delay(delay + duration * 2);
	smash[1].color.a = 0f;
	smash[1].position.x -= 250;
	smash[1].interpolateXY(new Vector2(smash[1].position.x + 250, smash[1].position.y), Linear.INOUT, 250, true).delay(delay + duration);
	smash[1].interpolateAlpha(1f, Linear.INOUT, 250, true).delay(delay + duration);
	smash[1].interpolateAlpha(0f, Linear.INOUT, 150, true).delay(delay + duration * 2);
	smash[2].color.a = 0f;
	smash[2].position.x -= 125;
	smash[2].interpolateXY(new Vector2(smash[2].position.x + 125, smash[2].position.y), Linear.INOUT, 250, true).delay(delay + duration);
	smash[2].interpolateAlpha(1f, Linear.INOUT, 250, true).delay(delay + duration);
	smash[2].interpolateAlpha(0f, Linear.INOUT, 150, true).delay(delay + duration * 2);
	smash[3].color.a = 0f;
	smash[3].position.x += 125;
	smash[3].interpolateXY(new Vector2(smash[3].position.x - 125, smash[3].position.y), Linear.INOUT, 250, true).delay(delay + duration);
	smash[3].interpolateAlpha(1f, Linear.INOUT, 250, true).delay(delay + duration);
	smash[3].interpolateAlpha(0f, Linear.INOUT, 150, true).delay(delay + duration * 2);
	smash[4].color.a = 0f;
	smash[4].position.x += 250;
	smash[4].interpolateXY(new Vector2(smash[4].position.x - 250, smash[4].position.y), Linear.INOUT, 250, true).delay(delay + duration);
	smash[4].interpolateAlpha(1f, Linear.INOUT, 250, true).delay(delay + duration);
	smash[4].interpolateAlpha(0f, Linear.INOUT, 150, true).delay(delay + duration * 2);
	smash[5].color.a = 0f;
	smash[5].position.x += 500;
	smash[5].interpolateXY(new Vector2(smash[5].position.x - 500, smash[5].position.y), Linear.INOUT, 250, true).delay(delay + duration);
	smash[5].interpolateAlpha(1f, Linear.INOUT, 250, true).delay(delay + duration);
	smash[5].interpolateAlpha(0f, Linear.INOUT, 150, true).delay(delay + duration * 2);
    }

    public void update(float deltaTime) {
	updateBonusEffects(deltaTime);
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

    private void renderBonusEffects(SpriteBatch spriteBatch) {
	if (User.hasEffect(BonusEffect.SCORE_FRENZY))
	    bonusEffectPinwheel.render(spriteBatch);
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
	message += "APPEARANCE RATE: " + stage.alienAppearanceRate + "\n";
	message += "COMBOX MAX: " + stage.session.combosMax + "\n";
	message += "COMBOS TOTAL: " + stage.session.combosTotal + "\n";
	message += "Press left CTRL to restart." + "\n";
	feed.drawWrapped(spriteBatch, message, 0f, 180f, 600, HAlignment.LEFT);

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
	if (stage.session.combosCurrent > 1)
	    comboCount.render(spriteBatch);
	//	for (int i = 0; i < textPool.size(); i++)
	//	    textPool.get(i).render(spriteBatch);
    }

    private void updateBonusEffects(float deltaTime) {
	bonusEffectBlackFill.update(deltaTime);
	bonusEffectPinwheel.update(deltaTime);
	for (int itemEffectIndex = 0; itemEffectIndex < User.bonusEffects.size(); itemEffectIndex++)
	    User.bonusEffects.get(itemEffectIndex).update(deltaTime);
    }

    private void updateLifePoints(float deltaTime) {
	for (int i = 0; i < lifePoints.length; i++)
	    lifePoints[i].update(deltaTime);
    }

    private void updateTexts(float deltaTime) {
	flyout.update(deltaTime);
	comboCount.text = "" + stage.session.combosCurrent;
	comboCount.update(deltaTime);
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
    }
}
