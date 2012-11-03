package com.nullsys.smashsmash.stage;

import aurelienribon.tweenengine.equations.Linear;
import aurelienribon.tweenengine.equations.Quad;
import aurelienribon.tweenengine.equations.Sine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.noobs2d.tweenengine.utils.DynamicCallback.RemoveFromCollectionOnEnd;
import com.noobs2d.tweenengine.utils.DynamicSprite;
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

    public SurvivalStageScreen(Game game) {
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
	    game.setScreen(new SurvivalStageScreen(game));
	return super.keyUp(keycode);
    }

    @Override
    public void onAlienAttack(Alien alien) {
	recoveryDelay = RECOVERY_DISABILITY_DURATION;
	session.combosCurrent = 0;
	hud.shakeLifePoint();
	camera.shake();
	if (session.lifePoints > 0)
	    session.lifePoints--;
	// add 3-5 puke splashes into the screen
	int count = (int) (3 + 1 * Math.random() * 3);
	for (int i = 0; i < count; i++) {
	    float x = (float) (300 + Math.random() * 680);
	    float y = (float) (200 + Math.random() * 400);
	    float targetScale = (float) (0.75f + Math.random() * 1.5f);
	    DynamicSprite puke = new DynamicSprite(Art.pukes.findRegion("PUKE_GREEN"), x, y);
	    puke.setScale(0f, 0f);
	    puke.setRotation((float) (360 * Math.random()));
	    puke.interpolateScaleXY(1f * targetScale, 1f * targetScale, 250, true).delay(i * 100);
	    puke.interpolateAlpha(0f, RECOVERY_DISABILITY_DURATION * 1000, true).delay(i * 100);
	    pukes.add(puke);
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
	    hud.missLabel.interpolateXY(x, y + 25, Linear.INOUT, 50, true);
	    hud.missLabel.interpolateXY(x, y, Linear.INOUT, 50, true).delay(50);
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
	boolean touchedAnAlien = aliensMayAttack() && !touchedACoin ? inputToAliens(position.x, position.y, pointer) : false; // We will only test collisions with the aliens if
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

	if (aliensMayAttack() && !touchedACoin) { // Hammer effects will only be added if a coin is not tapped
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
    public void render(float delta) {
	session.stageSecondsElapsed += delta;

	spriteBatch.setProjectionMatrix(camera.projection);
	getCamera().update();

	spriteBatch.begin();
	renderStage(spriteBatch, delta);
	renderAliens(spriteBatch, delta);
	renderBonusEffects(spriteBatch, delta);
	renderCoins(spriteBatch, delta);
	renderHammerEffects(spriteBatch, delta);
	renderPukes(spriteBatch, delta);
	hud.render(spriteBatch);
	hud.update(delta);
	spriteBatch.end();

	// Check if 3 sec. has passed since the last successful hit. If so, the combos are cancelled.
	if (session.stageSecondsElapsed - session.combosLastDelta >= COMBO_MAX_DURATION && session.combosCurrent > 0) {
	    showComboText();
	    session.combosMax = session.combosCurrent > session.combosMax ? session.combosCurrent : session.combosMax;
	    session.combosCurrent = 0;
	}

	// update streaks
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
	    hud.streakName.interpolateXY(1280, 500, Linear.INOUT, 250, true);
	    hud.streakName.interpolateAlpha(0f, Linear.INOUT, 250, true).delay(2000);
	    hud.streakBonus.color.a = 1f;
	    hud.streakBonus.tweenManager.killAll();
	    hud.streakBonus.position.set(1280 + hud.streakName.bitmapFont.getBounds(hud.streakName.text).width, 530);
	    hud.streakBonus.interpolateXY(1280, 530, Linear.INOUT, 250, true);
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
    public void resume() {
	game.setScreen(new StageLoadingScreen(game, this, Settings.getAssetManager()));
    }

    private void addCoin(float x, float y) {
	Coin coin = new Coin(Art.coins, 0, 0, 64, 64, 8, 8, .125f);
	coin.position.set(x, y);
	coin.interpolateXY(x, y + 100, Quad.OUT, 200, true);
	coin.interpolateXY(x, y, Quad.IN, 200, true).delay(200);
	coins.add(coin);
	if (User.hasEffect(BonusEffect.COIN_RAIN)) {
	    coin.interpolateXY(1280, 800, Linear.INOUT, 350, true).setCallback(new RemoveFromCollectionOnEnd(coins, coin));
	    User.gold += 10;
	}
    }

    private void addHammerEffect(float x, float y) {
	Vector2 position = new Vector2(x, y);
	float duration = User.hammer.getEffect().getEmitters().get(0).getDuration().getLowMax() / 1000;//User.hammer.getEffect().getEmitters().get(0).duration / 2;
	HammerEffect hammerEffect = new HammerEffect(User.hammer.getEffect(), position, duration, 0f);
	hammerEffects.add(hammerEffect);
	//	System.out.println(duration);
	//	Array<ParticleEmitter> a = User.hammer.getEffect().getEmitters();
	//	for (ParticleEmitter p : a)
	//	    System.out.println(p.getDuration().getLowMax());
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

    private void renderAliens(SpriteBatch batch, float delta) {
	// Sort the aliens first according to their y-coordinate. Aliens with lowest y are rendered last.
	for (int i = 0; i < aliens.length; i++)
	    for (int j = i + 1; j < aliens.length; j++)
		if (aliens[j].position.y < aliens[i].position.y) {
		    Alien alien = aliens[j];
		    aliens[j] = aliens[i];
		    aliens[i] = alien;
		}
	for (int i = aliens.length - 1; i > -1; i--) {
	    aliens[i].render(batch);
	    aliens[i].update(delta);
	}

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
			j = aliens.length; //break this loop
		    }
		alienAppearanceDelay = i * (int) (Math.random() * 250);
		// we only show an alien if it doesn't collide with other ones or if it is a sorcerer 
		// and it is allowed to be spawn and doesn't collide to others
		if (!(aliens[i] instanceof Sorcerer) && !overlaps) {
		    float volume = visibles > 0 ? 1.1f - visibles / aliens.length : 1f;
		    aliens[i].rise(alienAppearanceDelay, volume / 2);
		} else if (aliens[i] instanceof Sorcerer && sorcererShouldAppear && !overlaps) {
		    float volume = visibles > 0 ? 1.1f - visibles / aliens.length : 1f;
		    aliens[i].rise(alienAppearanceDelay, volume / 2);
		}
		overlaps = false;
	    }
	}
    }

    private void renderBonusEffects(SpriteBatch batch, float delta) {
	for (int i = 0; i < bonusEffects.size(); i++) {
	    bonusEffects.get(i).render(batch);
	    bonusEffects.get(i).update(delta);
	}
    }

    private void renderCoins(SpriteBatch batch, float delta) {
	for (int i = 0; i < coins.size(); i++) {
	    coins.get(i).render(batch);
	    if (coins.get(i).timeElapsed > 7f)
		coins.remove(i);
	    else
		coins.get(i).update(delta);
	}
    }

    private void renderHammerEffects(SpriteBatch batch, float delta) {
	for (int i = 0; i < hammerEffects.size(); i++) {
	    hammerEffects.get(i).render(batch);
	    if (hammerEffects.get(i).elapsed >= hammerEffects.get(i).duration)
		hammerEffects.remove(hammerEffects.get(i));
	    else
		hammerEffects.get(i).update(delta);
	}
    }

    private void renderPukes(SpriteBatch batch, float delta) {
	for (int i = 0; i < pukes.size(); i++) {
	    pukes.get(i).render(batch);
	    pukes.get(i).update(delta);
	    if (pukes.get(i).tweenManager.getRunningTweensCount() == 0)
		pukes.remove(i);
	}
	recoveryDelay -= delta;
	System.out.println("[SurvivalStageScreen#renderPukes(SpriteBatch,float)] recoveryDelay: " + recoveryDelay);
	//	System.out.println("[SurvivalStageScreen#renderPukes(SpriteBatch,float)] Pukes count: " + pukes.size());
    }

    private void renderStage(SpriteBatch spriteBatch, float delta) {
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
	    hud.comboName.interpolateXY(0, hud.comboName.position.y, Linear.INOUT, 250, true);

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
		break;
	    }
	// Display a "x + 1!" message
	if (hitCount >= 2) {
	    DynamicText text = new DynamicText(Fonts.bdCartoonShoutx23orange, hitCount + " in 1!", HAlignment.CENTER);
	    text.position.set(x, y - 50);
	    text.color.a = 0f;
	    text.interpolateXY(x, y + 50, Sine.OUT, 250, true);
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
		coins.get(coinIndex).interpolateXY(1280, 800, Linear.INOUT, 350, true);
		coins.get(coinIndex).tween.setCallback(new RemoveFromCollectionOnEnd(coins, coins.get(coinIndex)));
		User.gold += 10;
		hit = true;
		coinIndex = -1;
	    }
	return hit;
    }
}
