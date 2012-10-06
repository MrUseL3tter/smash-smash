package com.nullsys.smashsmash.stage;

import aurelienribon.tweenengine.equations.Linear;
import aurelienribon.tweenengine.equations.Quad;
import aurelienribon.tweenengine.equations.Sine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.noobs2d.tweenengine.utils.DynamicCallback.RemoveFromCollectionOnEnd;
import com.noobs2d.tweenengine.utils.DynamicText;
import com.nullsys.smashsmash.Art;
import com.nullsys.smashsmash.Coin;
import com.nullsys.smashsmash.Fonts;
import com.nullsys.smashsmash.Settings;
import com.nullsys.smashsmash.Sounds;
import com.nullsys.smashsmash.User;
import com.nullsys.smashsmash.alien.Alien;
import com.nullsys.smashsmash.alien.Alien.AlienState;
import com.nullsys.smashsmash.alien.Diabolic;
import com.nullsys.smashsmash.alien.Fluff;
import com.nullsys.smashsmash.alien.Golem;
import com.nullsys.smashsmash.alien.Jelly;
import com.nullsys.smashsmash.alien.Ogre;
import com.nullsys.smashsmash.alien.Sorcerer;
import com.nullsys.smashsmash.alien.Tortoise;
import com.nullsys.smashsmash.bonuseffect.BonusEffect;
import com.nullsys.smashsmash.bonuseffect.Invulnerability;
import com.nullsys.smashsmash.bonuseffect.ScoreFrenzy;
import com.nullsys.smashsmash.hammer.HammerEffect;

public class SurvivalStageScreen extends SmashSmashStage {

    public SurvivalStageScreen(Game game, AssetManager assetManager) {
	super(game);
	initGridPoints();
	initAliens();
	initHUD();
	User.init();
	hud.showReadyPrompt();
    }

    @Override
    public void dispose() {
	assetManager.clear();
    }

    /*
     * (non-Javadoc)
     * @see com.noobs2d.tweenengine.utils.DynamicScreen#keyUp(int)
     */
    @Override
    public boolean keyUp(int keycode) {
	// TODO disable for stable release
	if (keycode == Keys.CONTROL_LEFT || keycode == Keys.MENU)
	    game.setScreen(new SurvivalStageScreen(game, assetManager));
	return super.keyUp(keycode);
    }

    @Override
    public void onAlienAttack(Alien alien) {
	if (!User.hasEffect(BonusEffect.INVULNERABILITY)) {
	    session.combosCurrent = 0;
	    hud.shakeLifePoint();
	    camera.shake();
	    if (session.lifePoints > 0)
		session.lifePoints--;
	}
    }

    @Override
    public void onAlienSmashed(Alien alien) {
	addScore(alien);
	// Add coin as per percentage
	int random = (int) (Math.random() * 100);
	if (User.hasEffect(BonusEffect.COIN_RAIN))
	    addCoin(alien.position.x, alien.position.y);
	else if (random >= 18 && random <= 20)
	    addCoin(alien.position.x, alien.position.y);
    }

    public void onSmashMissed(float x, float y) {
	if (session.combosCurrent > 1) {
	    hud.missLabel.text = "MISS!";
	    hud.missLabel.color.a = 1f;
	    hud.missLabel.position.set(x, y);
	    hud.missLabel.interpolateXY(new Vector2(x, y + 25), Linear.INOUT, 50, true);
	    hud.missLabel.interpolateXY(new Vector2(x, y), Linear.INOUT, 50, true).delay(50);
	    hud.missLabel.interpolateAlpha(0f, Linear.INOUT, 250, true).delay(2000);
	}
	showComboText();
	session.combosMax = session.combosCurrent > session.combosMax ? session.combosCurrent : session.combosMax;
	session.combosCurrent = 0;
    }

    @Override
    public void onTouchDown(float x, float y, int pointer, int button) {
	Vector2 position = new Vector2(x, y);
	position.x *= (float) Settings.SCREEN_WIDTH / Gdx.graphics.getWidth();
	position.y = (Gdx.graphics.getHeight() * camera.zoom - position.y) * Settings.SCREEN_HEIGHT / Gdx.graphics.getHeight();

	boolean touchedACoin = inputToCoins(position.x, position.y, pointer);
	boolean touchedAnAlien = !touchedACoin ? inputToAliens(position.x, position.y, pointer) : false; // We will only test collisions with the aliens if
													 // there are no collisions with any coin

	boolean touchedABonusEffect = false;
	for (int i = 0; i < bonusEffects.size(); i++)
	    if (bonusEffects.get(i).getBounds().contains(position.x, position.y)) {
		BonusEffect effect = bonusEffects.remove(i);
		effect.trigger();
		touchedABonusEffect = true;
		if (effect instanceof Invulnerability) {
		    hud.bonusEffectBlackFill.color.a = 0f;
		    hud.bonusEffectBlackFill.interpolateAlpha(1f, Linear.INOUT, 500, true);
		    hud.bonusEffectBlackFill.interpolateAlpha(0f, Linear.INOUT, 500, true).delay(9500);
		} else if (effect instanceof ScoreFrenzy) {
		    hud.bonusEffectPinwheel.color.a = 0f;
		    hud.bonusEffectPinwheel.interpolateAlpha(1f, Linear.INOUT, 500, true);
		    hud.bonusEffectPinwheel.interpolateAlpha(0f, Linear.INOUT, 500, true).delay(9500);
		}
	    }

	if (touchedAnAlien && !touchedACoin || !touchedAnAlien && !touchedACoin) { // Hammer effects will only be added if a coin is not tapped 
	    if (User.hasEffect(BonusEffect.HAMMER_TIME))
		camera.shake();
	    addHammerEffect(position.x, position.y);
	    if (Settings.soundEnabled)
		Sounds.hammerIceFlakes.play();
	}

	if (!touchedAnAlien && !touchedACoin && !touchedABonusEffect && !User.hasEffect(BonusEffect.HAMMER_TIME))
	    onSmashMissed(position.x, position.y);

	pointers[pointer] = touchedAnAlien;
    }

    @Override
    public void render(float deltaTime) {
	session.stageSecondsElapsed += deltaTime;

	spriteBatch.setProjectionMatrix(camera.projection);
	getCamera().update();

	spriteBatch.begin();
	renderStage(spriteBatch);
	renderAliens(spriteBatch);
	renderBonusEffects(spriteBatch);
	renderCoins(spriteBatch);
	renderHammerEffects(spriteBatch);
	hud.render(spriteBatch);
	spriteBatch.end();

	updateHUD(deltaTime);
	updateAliens(deltaTime);
	updateBonusEffects(deltaTime);
	updateCoins(deltaTime);
	updateCombos(deltaTime);
	updateHammerEffects(deltaTime);
	updateStreaks(deltaTime);
    }

    @Override
    public void resume() {
	game.setScreen(new StageLoadingScreen(game, this, Settings.getAssetManager()));
    }

    private void addCoin(float x, float y) {
	Coin coin = new Coin(Art.coins, 0, 0, 64, 64, 8, 8, .125f);
	coin.position.set(x, y);
	coin.interpolateXY(new Vector2(x, y + 100), Quad.OUT, 200, true);
	coin.interpolateXY(new Vector2(x, y), Quad.IN, 200, true).delay(200);
	coins.add(coin);
	if (User.hasEffect(BonusEffect.COIN_RAIN)) {
	    coin.interpolateXY(new Vector2(1280, 800), Linear.INOUT, 350, true).setCallback(new RemoveFromCollectionOnEnd(coins, coin));
	    User.gold += 10;
	}
    }

    private void addHammerEffect(float x, float y) {
	Vector2 position = new Vector2(x, y);
	float duration = User.hammer.getEffect().getEmitters().get(0).duration / 2;
	HammerEffect hammerEffect = new HammerEffect(User.hammer.getEffect(), position, duration, 0f);
	hammerEffects.add(hammerEffect);
    }

    private void addScore(Alien alien) {
	int multiplier = 1;
	if (session.combosCurrent > 4 && session.combosCurrent < 25)
	    multiplier = 2;
	else if (session.combosCurrent > 24 && session.combosCurrent < 40)
	    multiplier = 3;
	else if (session.combosCurrent > 39 && session.combosCurrent < 60)
	    multiplier = 4;
	else if (session.combosCurrent > 59 && session.combosCurrent < 100)
	    multiplier = 5;
	else if (session.combosCurrent > 99 && session.combosCurrent < 255)
	    multiplier = 7;
	else if (session.combosCurrent > 254)
	    multiplier = 10;

	multiplier *= User.hasEffect(BonusEffect.SCORE_FRENZY) ? 3 : 1;
	session.score += alien.scoreValue * multiplier;
    }

    private int getVisibleAliens() {
	int visibles = 0;
	for (int i = 0; i < aliens.length; i++)
	    if (aliens[i].visible)
		visibles++;
	return visibles;
    }

    private void initAliens() {
	aliens[0] = new Diabolic(this);
	aliens[1] = new Diabolic(this);
	aliens[2] = new Diabolic(this);
	aliens[3] = new Fluff(this);
	aliens[4] = new Fluff(this);
	aliens[5] = new Fluff(this);
	aliens[6] = new Golem(this);
	aliens[7] = new Golem(this);
	aliens[8] = new Golem(this);
	aliens[9] = new Jelly(this);
	aliens[10] = new Jelly(this);
	aliens[11] = new Jelly(this);
	aliens[12] = new Ogre(this);
	aliens[13] = new Ogre(this);
	aliens[14] = new Ogre(this);
	aliens[15] = new Tortoise(this);
	aliens[15] = new Tortoise(this);
	aliens[15] = new Tortoise(this);
	aliens[16] = new Sorcerer(this);
	for (int i = 0; i < aliens.length; i++)
	    aliens[i].visible = false;
    }

    private void initGridPoints() {
	for (int gridIndex = 0; gridIndex < 5; gridIndex++)
	    gridPoints[gridIndex] = new Vector2((float) (285.676f + 176.666 * gridIndex), 800 - 672.267f);

	for (int gridIndex = 5; gridIndex < 11; gridIndex++)
	    gridPoints[gridIndex] = new Vector2((float) (186.769f + 176.666 * (gridIndex - 5)), 800 - 464.687f);

	for (int gridIndex = 11; gridIndex < 16; gridIndex++)
	    gridPoints[gridIndex] = new Vector2((float) (286.666f + 176.666 * (gridIndex - 11)), 800 - 251.2f);
    }

    private void initHUD() {
	hud = new HeadsUpDisplay(this);
    }

    private void renderAliens(SpriteBatch spriteBatch) {
	// Sort the aliens first according to their y-coordinate. Aliens with lowest y are rendered last.
	for (int i = 0; i < aliens.length; i++)
	    for (int j = i + 1; j < aliens.length; j++)
		if (aliens[j].position.y < aliens[i].position.y) {
		    Alien alien = aliens[j];
		    aliens[j] = aliens[i];
		    aliens[i] = alien;
		}
	for (int alienIndex = aliens.length - 1; alienIndex > -1; alienIndex--)
	    aliens[alienIndex].render(spriteBatch);
    }

    private void renderBonusEffects(SpriteBatch spriteBatch) {
	for (int i = 0; i < bonusEffects.size(); i++)
	    bonusEffects.get(i).render(spriteBatch);
    }

    private void renderCoins(SpriteBatch spriteBatch) {
	for (int i = 0; i < coins.size(); i++)
	    coins.get(i).render(spriteBatch);
    }

    private void renderHammerEffects(SpriteBatch spriteBatch) {
	for (int i = 0; i < hammerEffects.size(); i++)
	    hammerEffects.get(i).render(spriteBatch);
    }

    private void renderStage(SpriteBatch spriteBatch) {
	spriteBatch.draw(Art.lawnBackground1, 0, -(1024 - 800));
	spriteBatch.draw(Art.lawnBackground2, 1280 - 256, -(1024 - 800));
	if (User.hasEffect(BonusEffect.INVULNERABILITY))
	    hud.bonusEffectBlackFill.render(spriteBatch);
    }

    private void setAlienAppearanceRate() {
	boolean hasRampage = User.hasEffect(BonusEffect.COIN_RAIN) && User.hasEffect(BonusEffect.HAMMER_TIME) && User.hasEffect(BonusEffect.SCORE_FRENZY);
	if (hasRampage)
	    alienAppearanceRate = aliens.length;
	else if (session.combosCurrent > 4 && session.combosCurrent < 25)
	    alienAppearanceRate = 4;
	else if (session.combosCurrent > 24 && session.combosCurrent < 40)
	    alienAppearanceRate = 5;
	else if (session.combosCurrent > 39 && session.combosCurrent < 60)
	    alienAppearanceRate = 6;
	else if (session.combosCurrent > 59 && session.combosCurrent < 100)
	    alienAppearanceRate = 8;
	else if (session.combosCurrent > 99 && session.combosCurrent < 255)
	    alienAppearanceRate = 10;
	else if (session.combosCurrent > 254)
	    alienAppearanceRate = aliens.length;
	else
	    alienAppearanceRate = 4;
	//	alienAppearanceRate = aliens.length;
    }

    private void setAlienPositions() {
	for (int i = 0; i < aliens.length; i++) {
	    float targetWidth = aliens[i].waitingState.getLargestAreaDisplay().getKeyFrame().getRegionWidth();
	    float targetHeight = aliens[i].waitingState.getLargestAreaDisplay().getKeyFrame().getRegionHeight();
	    aliens[i].getBounds().width = targetWidth;
	    aliens[i].getBounds().height = targetHeight;
	    if (!aliens[i].visible) {
		float randomX = (float) (targetWidth / 2 + Math.random() * (Settings.SCREEN_WIDTH - targetWidth * 2));
		float randomY = (float) (Math.random() * (Settings.SCREEN_HEIGHT - targetHeight));
		aliens[i].position.set(randomX, randomY);
		aliens[i].getBounds().x = randomX - targetWidth / 2;
		aliens[i].getBounds().y = randomY;
	    }
	}
    }

    private void showComboText() {
	if (session.combosCurrent > 4 && session.combosCurrent < 25)
	    hud.comboName.text = "GOOD!";
	else if (session.combosCurrent > 24 && session.combosCurrent < 40)
	    hud.comboName.text = "GREAT!";
	else if (session.combosCurrent > 39 && session.combosCurrent < 60)
	    hud.comboName.text = "AMAZING!";
	else if (session.combosCurrent > 59 && session.combosCurrent < 100)
	    hud.comboName.text = "FANTASTIC!";
	else if (session.combosCurrent > 99 && session.combosCurrent < 255)
	    hud.comboName.text = "MONSTROUS!";
	else if (session.combosCurrent > 254)
	    hud.comboName.text = "GODLIKE!!";
	if (session.combosCurrent > 4) {
	    hud.comboName.color.a = 1f;
	    hud.comboName.interpolateAlpha(0f, Sine.OUT, 500, true).delay(2000);
	    hud.comboName.position.set(0 - hud.comboName.bitmapFont.getBounds(hud.comboName.text).width, hud.comboName.position.y);
	    hud.comboName.interpolateXY(new Vector2(0, hud.comboName.position.y), Linear.INOUT, 250, true);

	}
    }

    private void updateAliens(float deltaTime) {
	// FIXME set to delay before the intro prompt ends
	if (session.stageSecondsElapsed > 1) {
	    setAlienAppearanceRate();
	    setAlienPositions();
	    int visibles = getVisibleAliens();
	    boolean overlaps = false;
	    boolean sorcererShouldAppear = !User.hasEffect(BonusEffect.HAMMER_TIME) && !User.hasEffect(BonusEffect.INVULNERABILITY);
	    sorcererShouldAppear = sorcererShouldAppear && !User.hasEffect(BonusEffect.SCORE_FRENZY);
	    for (int i = 0; visibles < alienAppearanceRate - 1 && i < aliens.length && i < alienAppearanceRate - 1; i++) {
		for (int j = 0; j < aliens.length; j++)
		    if (i != j && !aliens[i].visible && aliens[i].getBounds().overlaps(aliens[j].getBounds())) {
			overlaps = true;
			j = aliens.length;
		    }
		if (!(aliens[i] instanceof Sorcerer) && !overlaps) {
		    alienAppearanceDelay = i * (int) (Math.random() * 250);
		    float volume = visibles > 0 ? 1.1f - visibles / aliens.length : 1f;
		    aliens[i].rise(alienAppearanceDelay, volume / 2);
		} else if (aliens[i] instanceof Sorcerer && sorcererShouldAppear && !overlaps) {
		    alienAppearanceDelay = i * (int) (Math.random() * 250);
		    float volume = visibles > 0 ? 1.1f - visibles / aliens.length : 1f;
		    aliens[i].rise(alienAppearanceDelay, volume / 2);
		}
		overlaps = false;
	    }
	    for (int i = 0; i < aliens.length; i++)
		aliens[i].update(deltaTime);
	}
    }

    private void updateBonusEffects(float deltaTime) {
	for (int i = 0; i < bonusEffects.size(); i++)
	    bonusEffects.get(i).update(deltaTime);
    }

    private void updateCoins(float deltaTime) {
	for (int coinIndex = 0; coinIndex < coins.size(); coinIndex++)
	    if (coins.get(coinIndex).timeElapsed > 7f)
		coins.remove(coinIndex);
	    else
		coins.get(coinIndex).update(deltaTime);
    }

    private void updateCombos(float deltaTime) {
	// Check if 3 sec. has passed since the last successful hit. If so, the combos are cancelled.
	if (session.stageSecondsElapsed - session.combosLastDelta >= COMBO_MAX_DURATION && session.combosCurrent > 0) {
	    showComboText();
	    session.combosMax = session.combosCurrent > session.combosMax ? session.combosCurrent : session.combosMax;
	    session.combosCurrent = 0;
	}
    }

    private void updateHammerEffects(float deltaTime) {
	for (int hammerEffectIndex = 0; hammerEffectIndex < hammerEffects.size(); hammerEffectIndex++)
	    if (hammerEffects.get(hammerEffectIndex).elapsed >= hammerEffects.get(hammerEffectIndex).duration)
		hammerEffects.remove(hammerEffects.get(hammerEffectIndex));
	    else
		hammerEffects.get(hammerEffectIndex).update(deltaTime);
    }

    private void updateHUD(float deltaTime) {
	hud.update(deltaTime);
    }

    private void updateStreaks(float deltaTime) {
	int streaks = 0;
	for (int i = 0; i < pointers.length; i++)
	    if (pointers[i])
		streaks++;
	if (streaks == 5) {
	    hud.streakBonus.text = "Score +5";
	    hud.streakName.text = "QUINTO!";
	} else if (streaks == 4) {
	    hud.streakBonus.text = "Score +4";
	    hud.streakName.text = "QUATRO!";
	} else if (streaks == 3) {
	    hud.streakBonus.text = "Score +3";
	    hud.streakName.text = "TRIO BINGO!";
	} else if (streaks == 2) {
	    hud.streakBonus.text = "Score +2";
	    hud.streakName.text = "DUAL SMASH!";
	}
	if (streaks >= 2) {
	    hud.streakName.color.a = 1f;
	    hud.streakName.tweenManager.killAll();
	    hud.streakName.position.set(1280 + hud.streakName.bitmapFont.getBounds(hud.streakName.text).width, 500);
	    hud.streakName.interpolateXY(new Vector2(1280, 500), Linear.INOUT, 250, true);
	    hud.streakName.interpolateAlpha(0f, Linear.INOUT, 250, true).delay(2000);
	    hud.streakBonus.color.a = 1f;
	    hud.streakBonus.tweenManager.killAll();
	    hud.streakBonus.position.set(1280 + hud.streakName.bitmapFont.getBounds(hud.streakName.text).width, 530);
	    hud.streakBonus.interpolateXY(new Vector2(1280, 530), Linear.INOUT, 250, true);
	    hud.streakBonus.interpolateAlpha(0f, Linear.INOUT, 250, true).delay(2000);
	    for (int i = 0; i < pointers.length; i++)
		pointers[i] = false;
	}
	try {
	    if (session.stageSecondsElapsed > 0 && Integer.parseInt(("" + session.stageSecondsElapsed).split(".")[1]) % 2 == 0) // .2 seconds has passed
		for (int i = 0; i < pointers.length; i++)
		    pointers[i] = false;
	} catch (ArrayIndexOutOfBoundsException e) {

	}
    }

    @Override
    protected boolean inputToAliens(float x, float y, int pointer) {
	int diameter = User.hammer.getDiameter();
	Rectangle bounds = new Rectangle(x - diameter / 2, y - diameter / 2, diameter, diameter);
	int hitCount = 0;
	for (int i = 0; i < aliens.length; i++)
	    if (aliens[i].visible && aliens[i].getBounds().overlaps(bounds) && aliens[i].state != AlienState.SMASHED) {
		aliens[i].smash();
		onAlienSmashed(aliens[i]);
		hitCount++;
		session.combosCurrent++;
		session.combosTotal++;
		session.combosLastDelta = session.stageSecondsElapsed;
		hud.shakeCombos();
	    }
	// Display a "x + 1!" message
	if (hitCount >= 2) {
	    DynamicText text = new DynamicText(Fonts.bdCartoonShoutx23orange, hitCount + " in 1!", HAlignment.CENTER);
	    text.position.set(x, y - 50);
	    text.color.a = 0f;
	    text.interpolateXY(new Vector2(x, y + 50), Sine.OUT, 250, true);
	    text.interpolateAlpha(1f, Sine.OUT, 250, true);
	    text.interpolateAlpha(0f, Sine.OUT, 250, true).delay(1000);
	    hud.textPool.add(text);
	}
	return hitCount > 0;
    }

    @Override
    protected boolean inputToCoins(float x, float y, int pointer) {
	Rectangle bounds = new Rectangle(x - 25, y - 25, 50, 50);
	boolean hit = false;
	for (int coinIndex = coins.size() - 1; coinIndex >= 0; coinIndex--)
	    if (coins.get(coinIndex).getBounds().overlaps(bounds)) {
		coins.get(coinIndex).tweenManager.killAll();
		coins.get(coinIndex).interpolateXY(new Vector2(1280, 800), Linear.INOUT, 350, true);
		coins.get(coinIndex).tween.setCallback(new RemoveFromCollectionOnEnd(coins, coins.get(coinIndex)));
		User.gold += 10;
		hit = true;
		coinIndex = -1;
	    }
	return hit;
    }
}
